package org.ubuntuone.music.organizer.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.ubuntuone.music.organizer.action.ActionResult;
import org.ubuntuone.music.organizer.action.Actions;
import org.ubuntuone.music.organizer.model.Song;
import org.ubuntuone.music.organizer.service.OauthService;
import org.ubuntuone.music.organizer.service.PlaylistService;

@Controller
public class PlaylistController {
	
	@Autowired
	private PlaylistService playlistService;
	@Autowired
	private OauthService oauthService;
	
	private Log log = LogFactory.getLog(getClass());

	@RequestMethod(Actions.GET_SONGS)
	public ActionResult getPlaylist(List<Song> songs) {
		log.info("GETTING playlist");
		String json = oauthService.getUbuntuOneSongs();
		Collections.copy(songs, playlistService.getSongs(json));
		return ActionResult.Complete;
	}

}
