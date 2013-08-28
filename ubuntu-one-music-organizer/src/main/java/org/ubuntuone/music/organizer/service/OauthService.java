package org.ubuntuone.music.organizer.service;

import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.UbuntuOneApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ubuntuone.music.organizer.collaborator.BrowserOpener;
import org.ubuntuone.music.organizer.collaborator.TokenStorer;

@Service
public class OauthService {
	
	private static final String YOUR_API_KEY = "ubuntuone";
	private static final String YOUR_API_SECRET = "hammertime";
	private static final String PROTECTED_RESOURCE_URL = "https://one.ubuntu.com/api/music/v2/playlists/";
	
	@Autowired
	private TokenStorer tokenStorer;
	@Autowired
	private BrowserOpener browserOpener;
	
	private Token accessToken = null;
	
	private Log log = LogFactory.getLog(getClass());

	public String getUbuntuOnePlaylist() {
		OAuthService service = new ServiceBuilder()
		.provider(UbuntuOneApi.class)
		.apiKey(YOUR_API_KEY)
		.apiSecret(YOUR_API_SECRET)
		.callback("http://127.0.0.1:8081")
		.build();
		
		if (tokenStorer.isAccessTokenStored()){
			accessToken = tokenStorer.getToken();
		} else {
			Token requestToken = service.getRequestToken();
			String authorizationUrl = service.getAuthorizationUrl(requestToken);
			browserOpener.openUrl(authorizationUrl);
			
			Scanner in = new Scanner(System.in);
			Verifier verifier = new Verifier(in.nextLine());
			in.close();
			
			accessToken = service.getAccessToken(requestToken, verifier);
			tokenStorer.saveAccessToken(accessToken.getToken(), accessToken.getSecret());
		}

		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service.signRequest(accessToken, request); // the access token from step 4
		Response response = request.send();
		String responseBody = response.getBody();
		log.info(responseBody);
		return responseBody;
	}

}
