package org.ubuntuone.music.organizer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ubuntuone.music.organizer.model.PlaylistResponse;
import org.ubuntuone.music.organizer.model.Song;

import com.google.gson.Gson;

@Service
public class PlaylistService {

	@Autowired
	private RestTemplate restTemplate;
	
	public List<Song> getSongs(String json){
		PlaylistResponse response = new Gson().fromJson(json, PlaylistResponse.class);
	    return response == null ? new ArrayList<Song>() : response.getResponse().getSongs();
	}
	
}
