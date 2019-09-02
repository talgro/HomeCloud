import java.io.File;

import io.homecloud.synchronizedFolder.sns.SNSPublish;
import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

public class FolderListner implements Runnable {

	private String _folder;
	
	private static final String publishTopicArn = "arn:aws:sns:us-east-2:977623331286:synchronizedFolder_changes";
	private static final SNSPublish publisher = new SNSPublish(publishTopicArn);
	
	public FolderListner(String _folder) {
		this._folder = _folder;
	}

	@Override
	public void run() {
		System.out.println("running listner....");
		int mask = JNotify.FILE_CREATED  | 
				JNotify.FILE_DELETED  | 
				JNotify.FILE_MODIFIED | 
				JNotify.FILE_RENAMED;

		boolean watchSubtree = true;

		try {
			int watchID = JNotify.addWatch(_folder, mask, watchSubtree, new Listener());
		} catch (JNotifyException e) {
			e.printStackTrace();
		}
		while(true) { }

	}

	class Listener implements JNotifyListener {
		public void fileRenamed(int wd, String rootPath, String oldName,
				String newName) {
			print("renamed " + rootPath + " : " + oldName + " -> " + newName);
			oldName = oldName.replace("\\", File.separator);
			newName = newName.replace("\\", File.separator);
			newName = newName.substring(newName.lastIndexOf(File.separator)+1, newName.length());
			//publisher.publish("PUT", oldName + "," + newName);
		}
		public void fileModified(int wd, String rootPath, String name) {
			print("modified " + rootPath + " : " + name);
		}
		public void fileDeleted(int wd, String rootPath, String name) {
			print("deleted " + rootPath + " : " + name);
			//publisher.publish("DELETE", name);
		}
		public void fileCreated(int wd, String rootPath, String name) {
			print("created " + rootPath + " : " + name);
			name = name.replace("\\", File.separator);
			if(new File(rootPath + File.separator + name).isDirectory())
				System.out.println("Is Dir!");
			//publisher.publish("GET", name);
		}
		void print(String msg) {
			System.err.println(msg);
		}
	}
}
