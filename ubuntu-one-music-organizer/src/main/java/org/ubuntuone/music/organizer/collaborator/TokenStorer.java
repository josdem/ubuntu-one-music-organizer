package org.ubuntuone.music.organizer.collaborator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.model.Token;
import org.springframework.stereotype.Service;
import org.ubuntuone.music.organizer.state.ApplicationState;


@Service
public class TokenStorer {

	private Log log = LogFactory.getLog(getClass());
	private Path path = Paths.get(ApplicationState.ACCESS_TOKEN_FILE_NAME);

	public Boolean isAccessTokenStored() {
		File file = new File(ApplicationState.ACCESS_TOKEN_FILE_NAME);
		return file.exists();
	}
	
	public void saveAccessToken(String token, String secret){
		try {
			BufferedWriter writer = Files.newBufferedWriter(Files.createFile(path), Charset.defaultCharset());
			writer.append(ApplicationState.TOKEN);
			writer.append(token);
			writer.newLine();
			writer.append(ApplicationState.SECRET);
			writer.append(secret);
			writer.flush();
			writer.close();
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
	}
	
	public Token getToken(){
		String token = null;
		String secret = null;
		
		try {
			Scanner scanner = new Scanner(path);
			token = scanner.findInLine(ApplicationState.TOKEN);
			secret = scanner.findInLine(ApplicationState.SECRET);
			scanner.close();
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
		
		return new Token(token, secret);
	}
}
