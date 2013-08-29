package org.ubuntuone.music.organizer.controller;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.ubuntuone.music.organizer.action.ActionResult;
import org.ubuntuone.music.organizer.action.Actions;
import org.ubuntuone.music.organizer.bean.SongBean;
import org.ubuntuone.music.organizer.model.Song;
import org.ubuntuone.music.organizer.service.GenreService;
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
	@Autowired
	private GenreService genreService;
	
	private Log log = LogFactory.getLog(getClass());

	@RequestMethod(Actions.GET_SONGS)
	public ActionResult getSongs(List<SongBean> beanSongs) {
		log.info("GETTING songs");
		String json = oauthService.getUbuntuOneSongs();
		List<Song> songs = playlistService.getSongs(json);
		songAdapterService.adapt(beanSongs, songs);
		return ActionResult.Complete;
	}
	
	@RequestMethod(Actions.GET_GENRES)
	public Set<String> getGenres(List<SongBean> beanSongs) {
		log.info("GETTING genres");
		return genreService.getGenres(beanSongs);
	}
	
	@RequestMethod(Actions.GET_PLAYLISTS)
	public ActionResult getPlaylists(List<SongBean> beanSongs) {
		log.info("GETTING playlists");
		return ActionResult.Complete;
	}

}
