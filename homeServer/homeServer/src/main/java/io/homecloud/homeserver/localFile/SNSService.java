package io.homecloud.homeserver.localFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.homecloud.homeserver.utils.HttpDownloadUtility;

@Service
public class SNSService {

	@Autowired /*A Singleton object */
	private LocalFileService _localFileService;

	@Autowired /*A Singleton object */
	private FileSystemStorageService _storageService;

	private final String _root = new File(System.getProperty("user.home")).getParentFile().getParent() + 
			File.separator + "homeServer" + File.separator + "root";
	
	public ResponseEntity handleSNSNotification(String subject, String message) {
		
		switch (subject) {
		
		case "DELETE":
			//message = filePath of file
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
			//message = filePath,newFileName
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
			//message = filePath of file
			String domain = getDomain();
			domain += "/download";
			String fileDir = message.substring(0, message.lastIndexOf(File.separator));
			message = message.replace("\\", "/");
			System.out.println("end point: " + domain + ", fileDir: " + fileDir + ",Message: " +message);
			try {
				HttpDownloadUtility.downloadFile(domain + "/" + message, _root + File.separator + fileDir);
				return ResponseEntity.ok().build();//200
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
			}

		default:
			break;
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500
	}
	
	private String getDomain() {
		String file  = _root + File.separator + "sync_folder_domain.txt";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					file));
			String line = reader.readLine();
			reader.close();
			return line;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}



}
