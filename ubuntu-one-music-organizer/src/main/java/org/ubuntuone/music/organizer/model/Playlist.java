package org.ubuntuone.music.organizer.model;

public class Playlist {
	
	private String playlist_url;
	private Integer song_count;
	private String id;
	private String name;
	
	public String getPlaylist_url() {
		return playlist_url;
	}
	
	public void setPlaylist_url(String playlist_url) {
		this.playlist_url = playlist_url;
	}
	
	public Integer getSong_count() {
		return song_count;
	}
	
	public void setSong_count(Integer song_count) {
		this.song_count = song_count;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
