package org.ubuntuone.music.organizer.collaborator;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.scribe.model.Token;

public class TestTokenStorer {
	
	private Path path = Paths.get("src/test/resources/accessTokenFile.token");

	private TokenStorer tokenStorer = new TokenStorer();
	
	private String token = "vmLjhklWq85x7zjQ6xPK";
	private String secret = "04hC7X2M8n49mbTg0ZMk8MkpqzwlJ4Qc7V5sJt7CLTd9rQZkbRjsZDgZv0dgf0WTTtzXdf1lsxJrjNJH";
	
	@Test
	public void shouldGetToken() throws Exception {
		tokenStorer.setPath(path);
		
		Token result = tokenStorer.getToken();
		
		assertEquals(token, result.getToken());
		assertEquals(secret, result.getSecret());
	}

}
