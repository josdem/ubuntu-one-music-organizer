package org.ubuntuone.music.organizer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.ubuntuone.music.organizer.model.Playlist;
import org.ubuntuone.music.organizer.model.PlaylistWrapper;
import org.ubuntuone.music.organizer.model.Song;
import org.ubuntuone.music.organizer.model.SongWrapper;

import com.google.gson.Gson;

@Service
public class ReaderService {
	
	public List<Song> getSongs(String json){
		SongWrapper response = new Gson().fromJson(json, SongWrapper.class);
	    return response == null ? new ArrayList<Song>() : response.getResponse().getSongs();
	}

	public List<Playlist> getPlaylists(String json) {
		PlaylistWrapper response = new Gson().fromJson(json, PlaylistWrapper.class);
		return response == null ? new ArrayList<Playlist>() : response.getResponse().getPlaylists();
	}
	
}
