package org.ubuntuone.music.organizer.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;


@Service
public class FileService {

	public Boolean isAccessTokenStored() {
		File file = new File("accessTokenFile.token");
		return file.exists();
	}
	
	public void saveAccessToken(String token, String secret){
		Path path = Paths.get("accessTokenFile.token");
		try {
			BufferedWriter writer = Files.newBufferedWriter(Files.createFile(path), Charset.defaultCharset());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
