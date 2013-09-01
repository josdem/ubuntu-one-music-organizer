package org.ubuntuone.music.organizer.model;

import java.util.ArrayList;
import java.util.List;

public class SongList {
	private List<String> song_id_list = new ArrayList<String>();
	
	public List<String> getSong_id_list() {
		return song_id_list;
	}
	
	public void setSong_id_list(List<String> song_id_list) {
		this.song_id_list = song_id_list;
	}
}
