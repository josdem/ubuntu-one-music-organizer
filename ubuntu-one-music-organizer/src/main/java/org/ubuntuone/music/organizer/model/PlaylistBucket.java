package org.ubuntuone.music.organizer.model;

import java.util.List;

public class PlaylistBucket {
	private String playlist_id;
	private List<String> song_id_list;
	
	public String getPlaylist_id() {
		return playlist_id;
	}
	
	public void setPlaylist_id(String playlist_id) {
		this.playlist_id = playlist_id;
	}
	
	public List<String> getSong_id_list() {
		return song_id_list;
	}
	
	public void setSong_id_list(List<String> song_id_list) {
		this.song_id_list = song_id_list;
	}
}
