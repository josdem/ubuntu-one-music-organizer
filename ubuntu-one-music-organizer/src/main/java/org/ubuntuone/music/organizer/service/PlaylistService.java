package org.ubuntuone.music.organizer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.ubuntuone.music.organizer.model.PlaylistResponse;
import org.ubuntuone.music.organizer.model.Song;

import com.google.gson.Gson;

@Service
public class PlaylistService {

	public List<Song> getSongs(String json){
		PlaylistResponse response = new Gson().fromJson(json, PlaylistResponse.class);
	    return response == null ? new ArrayList<Song>() : response.getResponse().getSongs();
	}
	
}
