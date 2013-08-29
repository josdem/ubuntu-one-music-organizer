package org.ubuntuone.music.organizer.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.ubuntuone.music.organizer.action.ActionResult;
import org.ubuntuone.music.organizer.action.Actions;
import org.ubuntuone.music.organizer.bean.SongBean;
import org.ubuntuone.music.organizer.model.Song;
import org.ubuntuone.music.organizer.service.OauthService;
import org.ubuntuone.music.organizer.service.PlaylistService;
import org.ubuntuone.music.organizer.service.SongAdapterService;

@Controller
public class UbuntuOneMusicController {
	
	@Autowired
	private PlaylistService playlistService;
	@Autowired
	private OauthService oauthService;
	@Autowired
	private SongAdapterService songAdapterService;
	
	private Log log = LogFactory.getLog(getClass());

	@RequestMethod(Actions.GET_SONGS)
	public ActionResult getPlaylist(List<SongBean> beanSongs) {
		log.info("GETTING playlist");
		String json = oauthService.getUbuntuOneSongs();
		List<Song> songs = playlistService.getSongs(json);
		songAdapterService.adapt(beanSongs, songs);
		return ActionResult.Complete;
	}

}
