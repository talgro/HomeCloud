package io.homecloud.homeserver.localFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service /*This annotation acts as a Singleton */

public class FileSystemStorageService {

	private final String _root = new File(System.getProperty("user.home")).getParentFile().getParent() + 
			File.separator + "homeServer" + File.separator + "root"; 

	public void store(MultipartFile file, Path uploadLocation) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		File uploadPathFile = new File(_root + File.separator + uploadLocation.toString());
		Path uploadPath = uploadPathFile.toPath();
		try {
			if (file.isEmpty()) {
				throw new RuntimeException("Failed to store empty file " + filename);
			}

			// This is a security check
			if (filename.contains("..")) {
				throw new RuntimeException("Cannot store file with relative path outside current directory " + filename);
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + filename, e);
		}
	}

	public Resource loadAsResource(String filenamePath) {
		try {
			Path file = new File(_root + File.separator + filenamePath).toPath();
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read file: " + filenamePath);
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Could not read file: " + filenamePath, e);
		}
	}

}
