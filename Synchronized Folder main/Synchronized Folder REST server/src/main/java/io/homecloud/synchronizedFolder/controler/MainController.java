package io.homecloud.synchronizedFolder.controler;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.homecloud.synchronizedFolder.utils.HTTPHandller;
import io.homecloud.synchronizedFolder.utils.SNSMessage;
import io.homecloud.synchronizedFolder.utils.SNSService;
import io.homecloud.synchronizedFolder.controler.FileSystemStorageService;
import io.homecloud.synchronizedFolder.sns.*;

@RestController
public class MainController {

	@Autowired
	private SNSService _service;

	@Autowired /*A Singleton object */
	private FileSystemStorageService _storageService;
	
	@RequestMapping(value = "SNSNotification", method = RequestMethod.POST)
	public ResponseEntity updateSNS(@RequestHeader Map<String, String> headers, @RequestBody String payload) { 
		System.out.println("POST to /SNSNotification");
		
		String requstedHeader = headers.get("x-amz-sns-message-type");
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
		Gson gson = builder.create(); 
		SNSMessage mssg = gson.fromJson(payload, SNSMessage.class);
		
		if(requstedHeader.equals("SubscriptionConfirmation")) {		
			String urlToSubscribe = mssg.getSubscribeURL();
			System.out.println("url: " + urlToSubscribe);

			//send GET to urlToSubscribe inorder to subscribe
			HTTPHandller handler = new HTTPHandller(urlToSubscribe);
			try {
				handler.openConnection();
				String response = handler.sendGET();
				System.out.println(response);

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
			_service.handleSNSNotification(subject, message);
		}
		//TODO return right response
		return ResponseEntity.ok().build();
	}

	
	//TODO test this end point
	// GET - /download/{userName}/..
	//GET request for downloading file(for web client) 
	@RequestMapping(value = "download/{userName}/**", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("userName") String userName) {
		String downloadPath = getCurentPath();
		System.out.println("GET to: " + downloadPath);
		downloadPath = downloadPath.substring(9);
		//downloadPath = removeRoot(downloadPath);
		Resource file = _storageService.loadAsResource(downloadPath);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	//This function is to get the current path URI
	private String getCurentPath() {
		UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
		String filePath = builder.buildAndExpand().getPath();
		return filePath;
	}
	
}
