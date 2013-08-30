package org.ubuntuone.music.organizer.model;

import java.util.List;

public class PlaylistResponse {
	private List<Playlist> playlists;
	
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	
}
