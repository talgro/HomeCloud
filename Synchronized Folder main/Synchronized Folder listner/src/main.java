import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

public class main {

	private final static String folder_dir = System.getProperty("user.home") + File.separator + "homeServer";

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

		Runnable folderListner = new FolderListner(folder_dir);		
		folderListner = new FolderListner(folder_dir);
		Thread folderListnerThread = new Thread(folderListner);
		folderListnerThread.start();
		
//		HTTPHandller h = new HTTPHandller("http://danie-yde6.localhost.run/danielbi");
//		File file = new File("C:\\Users\\danie\\Downloads\\test_client_upload.txt");
//		try {
//			h.openConnection();
//			//h.setRequestMethod("DELETE");
//			//System.out.println(h.sendPUT("clientTest"));
//			h.addProperty("authorization","Barear eyJraWQiOiJpbU9cL2wycERtZ282MUE3Q2I2T1BCVEVka1R2TkNGaG9pT3VBVmlQRjEybz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI1YjZkY2Y5Mi1mNzdhLTRlZTItOTZiMi05Y2I4OTVhOGRlMzMiLCJldmVudF9pZCI6ImNjM2E0YjU3LWJiNjktNDdlMy04YzZlLTVhNDc2ODI3OTI3ZSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE1NjY4MjIxNTUsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX3lDTWhPRkoyaCIsImV4cCI6MTU2Njk5NzIxMCwiaWF0IjoxNTY2OTkzNjEwLCJqdGkiOiI5YWNiOWM0Ni1iMzg5LTQ4Y2YtOGVhMi1iMWUzMzFhOTdjZjMiLCJjbGllbnRfaWQiOiIyczVpNnRhanM3NWdwZ3UzYjNpNHN0YTZxNCIsInVzZXJuYW1lIjoiNWI2ZGNmOTItZjc3YS00ZWUyLTk2YjItOWNiODk1YThkZTMzIn0.W5BoBuXLyYelH8IGu_nGt0DKzjzoyiyGLiDelBqX1QLTOmB_e6077maj6s_tjtjzQTYVh3uT8aArTchRo7Yvr5vpfXmuU-Ve77RN7JodHZ3nKpNGjL6iAb-fT6GMjFVnRlhAGLBWAEfOg7g2KoE6O7eMGbKu12pihG0lAJCC-aLotSSdLMA0L27mg1S7EFdx8LOreFk_tEWorwbWlzTjbcimnOQQ0sKuHFkHTyhQXbgQtznxviD29NaGfPMnQ1KZl3COqWSURWfQgv7NEVldbiMJOlKXL0lYXTizRwvRty7fRAphaoc0N7AotS4Lhgaw3pbEIl4ybD9tk-O4Emyp1A");
//			System.out.println(h.sendPOST(file));
//			System.out.println("\n\ndone");
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}

	}


}
