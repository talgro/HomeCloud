package io.homecloud.homeserver.localFile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;


//TODO: ??implement POST??
@RestController
public class LocalFileController {

	@Autowired /*A Singleton object */
	private LocalFileService _localFolderService;

	//GET request for file location
	@RequestMapping(value = "root/**", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getFiles() {  
		String filePath=getCurentPath();
		DataResponse response = _localFolderService.getFoldersFromPath(filePath);
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

	//	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	//	@ResponseBody
	//	public HttpEntity<UploadedFile> uploadMultipart(
	//	        final HttpServletRequest request,
	//	        final HttpServletResponse response,
	//	        @RequestParam("file") final MultipartFile multiPart) {
	//
	//	    //handle regular MultipartFile
	//
	//	    // IE <=9 offers to save file, if it is returned as json, so set content type to plain.
	//	    HttpHeaders headers = new HttpHeaders();
	//	    headers.setContentType(MediaType.TEXT_PLAIN);
	//	    return new HttpEntity<>(new UploadedFile(), headers);
	//	}

	//POST request to localHost:8080/files
	//	@RequestMapping(method=RequestMethod.POST, value="/files")
	//	public void addLocalFile(@RequestBody /* expect a json of this object */ LocalFile file) {
	//		_localFileService.addFile(file);
	//	}

	//PUT request to localHost:8080/files
	@RequestMapping(value = "root/**", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity updateLocalFile(@RequestBody String newFileName) {
		String filePath = getCurentPath();
		if(filePath.isEmpty()) //trying to change name of root
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();//405
		byte response = _localFolderService.updateFile(filePath, newFileName);
		switch (response) {
		//Successfully changed name
		case LocalFileService.SUCCESS :
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

	//DELETE request for file/folder location
	@RequestMapping(value = "root/**", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> deletefile() {
		String filePath = getCurentPath();
		if(filePath.isEmpty()) //trying to delete root
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();//405
		byte response = _localFolderService.deleteFile(filePath);
		switch (response) {
		//Successfully deleted file
		case LocalFileService.SUCCESS :
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

	private String getCurentPath() {
		UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
		String filePath = builder.buildAndExpand().getPath();
		if(filePath.length() > 5)
			return filePath.substring(6, filePath.length());
		return "";
	}

}
