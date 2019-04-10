package io.homecloud.homeserver.localFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service /*This annotation acts as a Singleton */
public class LocalFileService {

	List<LocalFile> _testFiles = new ArrayList<>(Arrays.asList(
			new LocalFile(1, "file1", "//path1", "mp3"),
			new LocalFile(2, "file2", "//path2", "mp4"),
			new LocalFile(3, "file3", "//path3", "jpg")
			));

	public List<LocalFile> getAllFiles() {
		return _testFiles;
	}

	public LocalFile getFile(int id) {
		//get the file with this filePath
		return _testFiles.stream().filter(x -> x.get_id() == id).findFirst().get();
	}

	public void addFile(LocalFile file) {
		_testFiles.add(file);		
	}

	//A function that updates a file with id to file
	public void updateFile(int id, LocalFile file) {
		for (int i = 0; i < _testFiles.size(); i++) {
			if(_testFiles.get(i).get_id() == id) {
				_testFiles.set(i, file);
				return;
			}
		}

	}

	public void deleteFile(int id) {
		for (int i = 0; i < _testFiles.size(); i++) {
			if(_testFiles.get(i).get_id() == id) {
				_testFiles.remove(i);
				return;
			}
		}
		
	}
}
