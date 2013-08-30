package org.ubuntuone.music.organizer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ubuntuone.music.organizer.collaborator.ReaderCollaborator;
import org.ubuntuone.music.organizer.model.PlaylistWrapper;
import org.ubuntuone.music.organizer.model.Song;
import org.ubuntuone.music.organizer.model.SongWrapper;

import com.google.gson.Gson;

@Service
public class ReaderService {
	
	@Autowired
	private ReaderCollaborator readerCollaborator;

	public List<Song> getSongs(String json){
		SongWrapper response = new Gson().fromJson(json, SongWrapper.class);
	    return response == null ? new ArrayList<Song>() : response.getResponse().getSongs();
	}

	public List<String> getPlaylists(String json) {
		PlaylistWrapper response = new Gson().fromJson(json, PlaylistWrapper.class);
		return response == null ? new ArrayList<String>() : readerCollaborator.getPlaylistNames(response.getResponse().getPlaylists());
	}
	
}
