package io.homecloud.homeserver.localFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.homecloud.homeserver.utils.SNSMessage;
import io.homecloud.homeserver.utils.SNSPublish;
import io.homecloud.homeserver.utils.HTTPHandller;

@RestController
public class LocalFileController {

	@Autowired /*A Singleton object */
	private LocalFileService _localFileService;

	@Autowired /*A Singleton object */
	private FileSystemStorageService _storageService;

	@Autowired
	private SNSService _service;

	private static final String publishTopicArn = "arn:aws:sns:us-east-2:977623331286:homeServer_changes";

	private static final SNSPublish publisher = new SNSPublish(publishTopicArn);

	//-------------mapping functions-------------------------

	//GET - /{userName}/..
	//Get request for getting json of given path
	@RequestMapping(value = "{userName}/**", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getFiles(@PathVariable("userName") String userName) {  
		String filePath = "";
		try {
			filePath = getCurentPath();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error decoding url");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		System.out.println("GET to: " + filePath);
		//filePath = removeRoot(filePath);
		DataResponse response = _localFileService.getFoldersFromPath(filePath);
		if(response == null) {
			Map<String, Object> errors = new LinkedHashMap<String, Object>();
			errors.put("status", 404);
			errors.put("error", "Not Found");
			errors.put("message","content not found");
			UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
			String filePath2 = builder.buildAndExpand().getPath();
			errors.put("path",filePath2);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
		}
		ResponseEntity<DataResponse> rtn = new ResponseEntity<DataResponse>(response,HttpStatus.OK);
		return rtn;
	}

	// GET - /download/{userName}/..
	//GET request for downloading file(for web client) 
	@RequestMapping(value = "download/{userName}/**", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("userName") String userName) {
		String downloadPath = "";
		try {
			downloadPath = getCurentPath();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error decoding url");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		System.out.println("GET to: " + downloadPath);
		downloadPath = downloadPath.substring(9);
		//downloadPath = removeRoot(downloadPath);
		Resource file = _storageService.loadAsResource(downloadPath);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	//PUT - /{userName}/../file.txt 
	//PUT request for renaming file/folder
	@RequestMapping(value = "{userName}/**", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity updateLocalFile(@PathVariable("userName") String userName ,@RequestBody String newFileName) {
		String filePath = "";
		try {
			filePath = getCurentPath();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error decoding url");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		System.out.println("PUT to: " + filePath);
		if(filePath.length() <= userName.length() + 2) 
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();//405
		byte response = _localFileService.updateFile(filePath, newFileName);
		switch (response) {
		//Successfully changed name
		case LocalFileService.SUCCESS :
			publisher.publish("PUT", filePath + "," + newFileName);
			return ResponseEntity.ok().build();//200
			//file/folder does not exist
		case LocalFileService.NOT_FOUND :
			return ResponseEntity.notFound().build();//404
		case LocalFileService.FILE_EXISTS:
			return ResponseEntity.badRequest().body("Reason: name already exists");

		default:
			break;
		}
		//error while trying to rename
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
	}

	//DELETE - /{userName}/..
	//DELETE request for deleting file/folder location)
	@RequestMapping(value = "{userName}/**", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity deletefile(@PathVariable("userName") String userName) {
		String filePath = "";
		try {
			filePath = getCurentPath();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error decoding url");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		System.out.println("DELETE to: " + filePath);
		//filePath = removeRoot(filePath);
		if(filePath.length() <= userName.length()+2) //trying to delete root 
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();//405
		byte response = _localFileService.deleteFile(filePath);
		switch (response) {
		//Successfully deleted file
		case LocalFileService.SUCCESS :
			publisher.publish("DELETE", filePath);//for SNS
			return ResponseEntity.ok().build();//200
			//file/folder does not exist
		case LocalFileService.NOT_FOUND :
			return ResponseEntity.notFound().build();//404
		default:
			break;
		}
		//error while trying to delete file
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
	}

	//POST - /upload/{userName}/..
	//POST for upload file
	@RequestMapping(value = "upload/{userName}/**", method = RequestMethod.POST)
	public ResponseEntity<Void> handleFileUpload(@PathVariable("userName") String userName, @RequestParam("file") MultipartFile file) {
		String uploadPath = "";
		try {
			uploadPath = getCurentPath();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error decoding url");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		System.out.println("POST to: " + uploadPath);
		uploadPath = uploadPath.substring(7);
		//uploadPath = removeRoot(uploadPath);
		_storageService.store(file, new File(uploadPath).toPath());
		publisher.publish("GET", uploadPath + "/" + StringUtils.cleanPath(file.getOriginalFilename()));
		return ResponseEntity.ok().build();//200
	}

	//POST - /addUser
	//POST for adding new user
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	public ResponseEntity<Void> addUser(@RequestBody String userName) {
		String path = "";
		try {
			path = getCurentPath();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error decoding url");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		System.out.println("POST to: " + path + ", body: " + userName);
		_localFileService.addUser(userName);
		return ResponseEntity.ok().build();//200
	}


	//############################### for SNS #########################################
	@RequestMapping(value = "SNSNotification", method = RequestMethod.POST)
	public ResponseEntity updateSNS(@RequestHeader Map<String, String> headers, @RequestBody String payload) { 
		System.out.println("POST to /SNSNotification");

		String requstedHeader = headers.get("x-amz-sns-message-type");
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
		Gson gson = builder.create(); 
		SNSMessage mssg = gson.fromJson(payload, SNSMessage.class);

		if(requstedHeader.equals("SubscriptionConfirmation")) {	
			System.out.println("Subscribing to SNS...");
			String urlToSubscribe = mssg.getSubscribeURL();

			//send GET to urlToSubscribe inorder to subscribe
			HTTPHandller handler = new HTTPHandller(urlToSubscribe);
			try {
				handler.openConnection();
				String response = handler.sendGET();
				if(response.contains("Error status: "))
					System.out.println("Error subscribing to SNS. " + response);
				else
					System.out.println("Successfully subscibed to SNS");

			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				handler.disconnect();
			}
		}

		else { //equals "Notification"

			String subject = mssg.getSubject();
			String message = mssg.getMessage();
			System.out.println("*POST SNS Notification*\nSubject: " + subject + "\nMessage: " + message);
			return(_service.handleSNSNotification(subject, message));
		}
		return ResponseEntity.badRequest().build();
	}



	//######################### other functions #####################################	



	//This function is to get the current path URI
	private String getCurentPath() throws UnsupportedEncodingException {
		UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
		String filePath = builder.buildAndExpand().getPath();
		filePath = URLDecoder.decode(filePath, java.nio.charset.StandardCharsets.UTF_8.toString());
		return filePath;
	}

}
