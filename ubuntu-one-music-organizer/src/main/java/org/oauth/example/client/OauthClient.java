package org.oauth.example.client;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.UbuntuOneApi;
import org.scribe.oauth.OAuthService;

public class OauthClient {
	
	private static final String YOUR_API_KEY = "ubuntuone";
	private static final String YOUR_API_SECRET = "hammertime";

	public static void main(String[] args) {
		OAuthService service = new ServiceBuilder()
		.provider(UbuntuOneApi.class)
		.apiKey(YOUR_API_KEY)
		.apiSecret(YOUR_API_SECRET)
		.build();
		System.out.println(service.getRequestToken());
	}

}
