package org.ubuntuone.music.organizer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ubuntuone.music.organizer.model.Playlist;
import org.ubuntuone.music.organizer.model.PlaylistResponse;

@Service
public class PlaylistService {

	@Autowired
	private RestTemplate restTemplate;
	
	public List<Playlist> getPlaylists(){
		String url = "https://one.ubuntu.com/api/music/v2/playlists/";
	    PlaylistResponse response = (PlaylistResponse) restTemplate.getForObject(url, PlaylistResponse.class);
	    return response.getResponse().getPlaylists();
	}
	
}
