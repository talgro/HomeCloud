package io.homecloud.synchronizedFolder.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.homecloud.synchronizedFolder.SyncFolderRESTStarter;

@Service
public class SNSService {

	private final static String folder_dir = System.getProperty("user.home") + File.separator + "HomeCloud";

	public final static byte SUCCESS = 0;
	public final static byte NOT_FOUND = 1;
	public final static byte ERROR = 2;
	public final static byte FILE_EXISTS = 3;

	public ResponseEntity handleSNSNotification(String subject, String message) {
		switch (subject) {

		case "DELETE":
			//message = filePath of file
			if(message.isEmpty()) //trying to delete root TODO fix with users
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();//405
			byte responseDelete = deleteFile(message);
			switch (responseDelete) {
			//Successfully deleted file
			case SUCCESS :
				return ResponseEntity.ok().build();//200
				//file/folder does not exist
			case NOT_FOUND :
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
			byte responsePut = updateFile(filePath, newFileName);
			switch (responsePut) {
			//Successfully changed name
			case SUCCESS :
				return ResponseEntity.ok().build();//200
				//file/folder does not exist
			case NOT_FOUND :
				return ResponseEntity.notFound().build();//404
			case FILE_EXISTS:
				return ResponseEntity.badRequest().body("Reason: name already exists");

			default:
				break;
			}
			//error while trying to rename
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500

		case "GET":
			//message = filePath of file
			
			//String domain = getDomain(); old function
			String domain = getDomainFromAws();
			domain += "/download";
			//remove file from extension to get folder
			String fileDir = message.substring(0, message.lastIndexOf("/"));
			System.out.println("end point: " + domain + ", fileDir: " + fileDir);
			try {
				HttpDownloadUtility.downloadFile(domain + message, folder_dir + File.separator + fileDir);
				return ResponseEntity.ok().build();//200
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
			}
		case "FOLDER":
			//message = filePath,newFolderName
			String[] splitMssg2 = message.split(",");
			String folderPath = splitMssg2[0];
			String newFolderName = splitMssg2[1];
			File newFolder = new File(folder_dir + File.separator + folderPath + File.separator + newFolderName);
			newFolder.mkdir();
		default:
			break;
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();//500	

	}

	//TODO Test this! (get homeServer domain from aws)
	private String getDomainFromAws() {
		HashMap<String, String> map = getAwsAndTokenFromXML();
		String awsDomain = map.get("awsDomain");
		String token = map.get("JWT");
		String endPoint = "/clients/getMyHomeServerDetails";
		String uri = awsDomain + endPoint;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Barear " + token);
		HttpEntity request = new HttpEntity(headers);

		ResponseEntity<HomeServerConnectionDetailsDto> response = restTemplate.exchange(uri, HttpMethod.GET, request,
				HomeServerConnectionDetailsDto.class);
		return response.getBody().getHomeServerAddress();
	}

	private HashMap<String, String> getAwsAndTokenFromXML() {
		HashMap<String, HashMap<String, String>> config = SyncFolderRESTStarter.readXML(folder_dir + File.separator + "config.xml");
		HashMap<String, String> rtn = new HashMap<String, String>();
		rtn.put("awsDomain", config.get("AWS").get("domain"));
		rtn.put("JWT", config.get("AWS").get("JWT"));
		return rtn;
	}

	//This method changes the name of folder/file at "filePath" to "newFileName"
	public byte updateFile(String filePath, String newFileName) {
		String fullPath = folder_dir + "\\" + filePath;
		File oldFileName = new File(fullPath);
		String fullNewFilePath = "";
		if(filePath.contains("/"))
			fullNewFilePath = filePath.substring(0, filePath.lastIndexOf('/')) + "\\";
		fullNewFilePath += newFileName;
		File newFileNameFile = new File(folder_dir + "\\" + fullNewFilePath);
		if(!oldFileName.exists())
			return NOT_FOUND;
		//fileName already exists
		if(newFileNameFile.exists()) {
			return FILE_EXISTS;
		}
		boolean sucess = oldFileName.renameTo(newFileNameFile);
		return sucess ? SUCCESS : ERROR;
	}

	private byte deleteFile(String filePath) {
		String fullPath = folder_dir + "\\" +filePath;
		File fileToDelete = new File(fullPath);
		if(!fileToDelete.exists())
			return NOT_FOUND;
		if(!fileToDelete.isDirectory())
			fileToDelete.delete();
		else
			deleteFolderAndContent(fileToDelete);
		if(fileToDelete.exists()) //if file still there and not deleted
			return ERROR;
		return SUCCESS;
	}

	private void deleteFolderAndContent(File fileToDelete) {
		Path path = fileToDelete.toPath();
		try {
			Files.walk(path)
			.sorted(Comparator.reverseOrder())
			.map(Path::toFile)
			.forEach(File::delete);
		} catch (IOException e) {
			System.out.println("Error deleting directory: " + fileToDelete + "\n");
			e.printStackTrace();
		}
	}

	private String getDomain() {
		String file  = folder_dir + File.separator + "homeServer_domain.txt";
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
