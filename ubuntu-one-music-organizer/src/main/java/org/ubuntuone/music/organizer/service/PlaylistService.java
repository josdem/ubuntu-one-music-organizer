package org.ubuntuone.music.organizer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ubuntuone.music.organizer.model.Playlist;
import org.ubuntuone.music.organizer.model.PlaylistResponse;

import com.google.gson.Gson;

@Service
public class PlaylistService {

	@Autowired
	private RestTemplate restTemplate;
	
	public List<Playlist> getPlaylists(String json){
		PlaylistResponse response = new Gson().fromJson(json, PlaylistResponse.class);
	    return response.getResponse().getPlaylists();
	}
	
}
