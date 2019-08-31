package io.homecloud.homeserver.localFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SNSService {

	@Autowired /*A Singleton object */
	private LocalFileService _localFileService;

	@Autowired /*A Singleton object */
	private FileSystemStorageService _storageService;

	public ResponseEntity handleSNSNotification(String subject, String message) {
		
		switch (subject) {
		
		case "DELETE":
			if(message.isEmpty()) //trying to delete root TODO fix with users
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();//405
			byte responseDelete = _localFileService.deleteFile(message);
			switch (responseDelete) {
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
		case "PUT":
			String[] splitMssg = message.split(",");
			String filePath = splitMssg[0];
			String newFileName = splitMssg[1];
			if(filePath.isEmpty()) //trying to change name of root TODO handle users
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();//405
			byte responsePut = _localFileService.updateFile(filePath, newFileName);
			switch (responsePut) {
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

		case "GET":
			//TODO get domainName of syncFolder REST from aws
			//TODO send get to SpringApp file sync REST to /download/{userName}/...

		default:
			break;
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
	}



}
