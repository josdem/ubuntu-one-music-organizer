package org.ubuntuone.music.organizer.collaborator;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class BrowserOpener {
	
	private Log log = LogFactory.getLog(getClass());

	public void openUrl(String authorizationUrl){
		log.info("TRYING TO OPEN AUTH URL to the default browser: " + authorizationUrl);
		try {
			Desktop.getDesktop().browse(new URI(authorizationUrl));
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		} catch (URISyntaxException use) {
			log.error(use, use);
		}
	}
	
}
