package org.ubuntuone.music.organizer.model;

import java.util.List;

public class Response {
	private List<Song> songs;
	
	public List<Song> getSongs() {
		return songs;
	}
	
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
}
