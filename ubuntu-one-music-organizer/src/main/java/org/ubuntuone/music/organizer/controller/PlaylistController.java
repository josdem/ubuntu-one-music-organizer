package org.ubuntuone.music.organizer.controller;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.ubuntuone.music.organizer.action.ActionResult;
import org.ubuntuone.music.organizer.action.Actions;
import org.ubuntuone.music.organizer.model.Playlist;
import org.ubuntuone.music.organizer.service.PlaylistService;

@Controller
public class PlaylistController {
	
	@Autowired
	private PlaylistService playlistService;
	
	private Log log = LogFactory.getLog(getClass());

	@RequestMethod(Actions.GET_PLAYLIST)
	public ActionResult getPlaylist() {
		log.info("GETTING playlist");
		List<Playlist> playlists = playlistService.getPlaylists();
		for (Playlist playlist : playlists) {
			log.info("playlist: " + ToStringBuilder.reflectionToString(playlist));
		}
		return ActionResult.Complete;
	}

}
