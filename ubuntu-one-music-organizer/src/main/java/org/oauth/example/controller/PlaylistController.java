package org.oauth.example.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.oauth.example.action.ActionResult;
import org.oauth.example.action.Actions;
import org.springframework.stereotype.Controller;

@Controller
public class PlaylistController {
	
	private Log log = LogFactory.getLog(getClass());

	@RequestMethod(Actions.GET_PLAYLIST)
	public ActionResult getPlaylist() {
		log.info("GETTING playlist");
		System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>HEY");
		return ActionResult.Complete;
	}

}
