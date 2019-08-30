import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

public class FolderListner implements Runnable {

	private String _folder;
	private HTTPHandller _httpHandler;

	public FolderListner(String _folder) {
		this._folder = _folder;
		_httpHandler = new HTTPHandller(_folder);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) { }

	}

	class Listener implements JNotifyListener {
		public void fileRenamed(int wd, String rootPath, String oldName,
				String newName) {
			print("renamed " + rootPath + " : " + oldName + " -> " + newName);
		}
		public void fileModified(int wd, String rootPath, String name) {
			print("modified " + rootPath + " : " + name);
		}
		public void fileDeleted(int wd, String rootPath, String name) {
			print("deleted " + rootPath + " : " + name);
		}
		public void fileCreated(int wd, String rootPath, String name) {
			print("created " + rootPath + " : " + name);
		}
		void print(String msg) {
			System.err.println(msg);
		}
	}
}
