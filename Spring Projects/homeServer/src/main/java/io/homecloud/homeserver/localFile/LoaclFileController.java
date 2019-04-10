package io.homecloud.homeserver.localFile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoaclFileController {

	@Autowired /*A Singleton object */
	private LocalFileService _localFileService;

	//GET request to localHost:8080/files
	@RequestMapping("/files")
	public List<LocalFile> getAllFiles() {
		return _localFileService.getAllFiles();
	}

	//GET request to localHost:8080/files/#param# id
	@RequestMapping("/files/{id}")
	public LocalFile getFile(@PathVariable int id) {
		return _localFileService.getFile(id);
	}

	//POST request to localHost:8080/files
	@RequestMapping(method=RequestMethod.POST, value="/files")
	public void addLocalFile(@RequestBody /* expect a json of this object */ LocalFile file) {
		_localFileService.addFile(file);
	}

	//PUT request to localHost:8080/files
	@RequestMapping(method=RequestMethod.PUT, value="/files/{id}")
	public void updateLocalFile(@RequestBody /* expect a json of this object */ LocalFile file, @PathVariable int id) {
		_localFileService.updateFile(id, file);
	}

	//DELETE request to localHost:8080/files
	@RequestMapping(method=RequestMethod.DELETE, value="/files/{id}")
	public void deleteLocalFile(@PathVariable int id) {
		_localFileService.deleteFile(id);
	}

}
