package org.ubuntuone.music.organizer.controller;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.ubuntuone.music.organizer.action.ActionResult;
import org.ubuntuone.music.organizer.action.Actions;
import org.ubuntuone.music.organizer.bean.PlaylistBean;
import org.ubuntuone.music.organizer.bean.SongBean;
import org.ubuntuone.music.organizer.model.PlaylistWrapper;
import org.ubuntuone.music.organizer.model.Song;
import org.ubuntuone.music.organizer.service.GenreService;
import org.ubuntuone.music.organizer.service.OauthService;
import org.ubuntuone.music.organizer.service.ReaderService;
import org.ubuntuone.music.organizer.service.SongAdapterService;
import org.ubuntuone.music.organizer.state.ApplicationState;

import com.google.gson.Gson;

@Controller
public class UbuntuOneMusicController {
	
	@Autowired
	private ReaderService playlistService;
	@Autowired
	private OauthService oauthService;
	@Autowired
	private SongAdapterService songAdapterService;
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Log log = LogFactory.getLog(getClass());

	@RequestMethod(Actions.GET_SONGS)
	public ActionResult getSongs(List<SongBean> beanSongs) {
		log.info("GETTING songs");
		String json = oauthService.requestUbuntuOneMusic(ApplicationState.GET_SONGS_URL);
		List<Song> songs = playlistService.getSongs(json);
		songAdapterService.adapt(beanSongs, songs);
		return ActionResult.COMPLETE;
	}
	
	@RequestMethod(Actions.GET_GENRES)
	public Set<String> getGenres(List<SongBean> beanSongs) {
		log.info("GETTING genres");
		return genreService.getGenres(beanSongs);
	}
	
	@RequestMethod(Actions.GET_PLAYLISTS)
	public ActionResult getPlaylists(List<String> playlists) {
		log.info("GETTING playlists");
		String json = oauthService.requestUbuntuOneMusic(ApplicationState.GET_PLAYLISTS_URL);
		for (String name : playlistService.getPlaylists(json)) {
			playlists.add(name);
		}
		return ActionResult.COMPLETE;
	}
	
	@RequestMethod(Actions.CREATE_PLAYLIST)
	public ActionResult createPlaylist(String name) {
		log.info("CREATING playlist");
		PlaylistBean playlistBean = new PlaylistBean();
		playlistBean.setName(name);
		String json = new Gson().toJson(playlistBean);
		log.info("json:" + json);
		
		HttpHeaders headers = new HttpHeaders();  
        headers.setContentType( MediaType.APPLICATION_JSON );  
        HttpEntity<String> request= new HttpEntity<String>( json, headers );
        
		String result = restTemplate.postForObject(ApplicationState.GET_PLAYLISTS_URL, request, String.class);
		log.info("result:" + result);
		PlaylistWrapper playlistWrapper = new Gson().fromJson(result, PlaylistWrapper.class);
		return playlistWrapper.getMeta().getMessage().equals("Created") ? ActionResult.COMPLETE : ActionResult.FAIL;
	}

}
