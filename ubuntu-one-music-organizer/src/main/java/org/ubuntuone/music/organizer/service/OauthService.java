package org.ubuntuone.music.organizer.service;

import org.apache.commons.lang3.StringUtils;
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
import org.ubuntuone.music.organizer.gui.OauthDialog;
import org.ubuntuone.music.organizer.state.ApplicationState;

@Service
public class OauthService {
	
	private static final String YOUR_API_KEY = "ubuntuone";
	private static final String YOUR_API_SECRET = "hammertime";
	private static final String PROTECTED_RESOURCE_URL = "https://one.ubuntu.com/api/music/v2/songs/";
	
	@Autowired
	private TokenStorer tokenStorer;
	@Autowired
	private BrowserOpener browserOpener;
	
	private Token accessToken = null;
	
	private Log log = LogFactory.getLog(getClass());

	public String getUbuntuOneSongs() {
		OAuthService service = new ServiceBuilder()
		.provider(UbuntuOneApi.class)
		.apiKey(YOUR_API_KEY)
		.apiSecret(YOUR_API_SECRET)
		.callback(ApplicationState.CALLBACK_URL)
		.build();
		
		if (tokenStorer.isAccessTokenStored()){
			accessToken = tokenStorer.getToken();
		} else {
			Token requestToken = service.getRequestToken();
			String authorizationUrl = service.getAuthorizationUrl(requestToken);
			browserOpener.openUrl(authorizationUrl);
			
			String oauthVerifierCode = OauthDialog.getOauthVerifierCode();
			if(oauthVerifierCode == null) 
				return StringUtils.EMPTY;
			Verifier verifier = new Verifier(oauthVerifierCode);
			
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
