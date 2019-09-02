import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.homecloud.synchronizedFolder.HTTPUtils.HttpDownloadUtility;
import io.homecloud.synchronizedFolder.utils.DataResponse;
import io.homecloud.synchronizedFolder.utils.LocalFile;

public class main {

	private final static String folder_dir = System.getProperty("user.home") + File.separator + "HomeCloud";
	private final static String user = "talgrop";
	public static void main(String[] args) {

		//		Runnable loginRun = new LoginPage();
		//		Thread loginThread = new Thread(loginRun);
		//		loginThread.start();
		//		try {
		//			loginThread.join();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//
		//		//done loggin
		//		//create synchronized folder
		//		File syncFolder = new File(folder_dir);
		//		if(!syncFolder.exists()) {
		//			syncFolder.mkdir();
		//		}

		File syncFolderUser = new File(folder_dir + File.separator + user);
		if(!syncFolderUser.exists())
			syncFolderUser.mkdir();
		String domain = "https://danie-ck7m.localhost.run";
		String endPoint = "/" + user;
		try {
			getAllFiles(domain, endPoint , folder_dir + File.separator + user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\n\nDone.");

		//		Runnable folderListner = new FolderListner(folder_dir);		
		//		folderListner = new FolderListner(folder_dir);
		//		Thread folderListnerThread = new Thread(folderListner);
		//		folderListnerThread.start();

	}

	private static void getAllFiles(String domain, String endPoint, String folderDir) throws IOException {
		HTTPHandller h = new HTTPHandller(domain +  endPoint);
		h.openConnection();
		String json = h.getResponse();
		if(json.isEmpty())
			return;
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
		Gson gson = builder.create(); 
		DataResponse response = gson.fromJson(json, DataResponse.class);
		for (LocalFile file : response.getContent()) {
			if(file.getType().equals("folder")) { //recursive call
				new File(folderDir + File.separator + file.getName()).mkdir();//create folder
				getAllFiles(domain,  endPoint + "/" + file.getName(), folderDir + "/" + file.getName());
			}
			else { //download file
				System.out.println("downloading: " + endPoint + "/" + file.getName() + "...");
				HttpDownloadUtility.downloadFile(domain + "/download" + endPoint + "/" + URLEncoder.encode(file.getName(),java.nio.charset.StandardCharsets.UTF_8.toString()), folderDir);
			}
		}

	}


}
