package io.homecloud.synchronizedFolder;

import java.io.File;
import java.util.HashMap;

public class main {

	private final static String folder_dir = System.getProperty("user.home") + File.separator + "HomeCloud";

	public static void main(String[] args) {

		//*** 1*** - Run login GUI +  REST

		Runnable loginRun = new LoginPage(args);
		Thread loginThread = new Thread(loginRun);
		loginThread.start();
		try {
			loginThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		//*** 3 *** - Run FolderListner

	
	}

}
