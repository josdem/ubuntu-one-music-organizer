package org.ubuntuone.music.organizer.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.springframework.stereotype.Controller;
import org.ubuntuone.music.organizer.action.ActionResult;
import org.ubuntuone.music.organizer.action.Actions;

@Controller
public class PlaylistController {
	
	private Log log = LogFactory.getLog(getClass());

	@RequestMethod(Actions.GET_PLAYLIST)
	public ActionResult getPlaylist() {
		log.info("GETTING playlist");
		return ActionResult.Complete;
	}

}
