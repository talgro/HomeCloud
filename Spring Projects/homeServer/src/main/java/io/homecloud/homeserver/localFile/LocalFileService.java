package io.homecloud.homeserver.localFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

@Service /*This annotation acts as a Singleton */
public class LocalFileService {

	private final String _root = new File(System.getProperty("user.home")).getParentFile().getParent() + 
			File.separator + "homeServer" + File.separator + "root"; 
	
	SimpleDateFormat _sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public final static byte SUCCESS = 0;
	public final static byte NOT_FOUND = 1;
	public final static byte ERROR = 2;
	public final static byte FILE_EXISTS = 3;

	//reurns null if pth does not exists
	public DataResponse getFoldersFromPath(String path) {
		//build actual local path
		String fullPath = _root + "\\" +path;
		List<LocalFile> files = new ArrayList<LocalFile>();
		File checkDirExists = new File(fullPath);
		if(!checkDirExists.exists())
			return null;
		File[] fileArray = new File(fullPath).listFiles();
		String name, type, lastModifiedDate, createdDate;		
		FileTime time = FileTime.fromMillis(0);
		long tempLong;
		long size;
		//iterate over items in directory
		for (File file : fileArray) {
			name = file.getName();
			tempLong = file.lastModified();
			lastModifiedDate = _sdf.format(tempLong);
			try {
				BasicFileAttributes attrs;
				attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				time = attrs.creationTime();
			} catch (IOException e) {
				e.printStackTrace();
			}
			createdDate = _sdf.format(new Date(time.toMillis()));
			if (file.isDirectory()) {		
				type = "folder";
				size = fileSize(file.toPath());
			}
			else {
				type = getFileExtension(file);
				size = file.length();
			}
			files.add(new LocalFile(name, type, size, lastModifiedDate, createdDate));
		}

		return new DataResponse(files);
	}

	public byte deleteFile(String filePath) {
		String fullPath = _root + "\\" +filePath;
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

	//This method changes the name of folder/file at "filePath" to "newFileName"
	public byte updateFile(String filePath, String newFileName) {
		String fullPath = _root + "\\" + filePath;
		File oldFileName = new File(fullPath);
		String fullNewFilePath = "";
		if(filePath.contains("/"))
			fullNewFilePath = filePath.substring(0, filePath.lastIndexOf('/')) + "\\";
		fullNewFilePath += newFileName;
		File newFileNameFile = new File(_root + "\\" + fullNewFilePath);
		if(!oldFileName.exists())
			return NOT_FOUND;
		//fileName already exists
		if(newFileNameFile.exists()) {
			return FILE_EXISTS;
		}
		boolean sucess = oldFileName.renameTo(newFileNameFile);
		return sucess ? SUCCESS : ERROR;
	}

	public void addUser(String userName) {
		File newDirToCreate = new File(_root + File.separator + userName);
		if(newDirToCreate.exists())
			throw new RuntimeException("User '" + userName + "' Already exists!");
		newDirToCreate.mkdir();
		if(!newDirToCreate.exists()) {
			throw new RuntimeException("Failed creating folder for '" + userName + "'");
		}
		
	}
	
	//method to get file extension
	private String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".") + 1;
		if (lastIndexOf == -1) {
			return "un-defined"; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	//method to calculate folder size
	public static long fileSize(Path path) {

		final AtomicLong size = new AtomicLong(0);

		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

					size.addAndGet(attrs.size());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) {

					System.out.println("skipped: " + file + " (" + exc + ")");
					// Skip folders that can't be traversed
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

					if (exc != null)
						System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
					// Ignore errors traversing a folder
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
		}

		return size.get();
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



}
