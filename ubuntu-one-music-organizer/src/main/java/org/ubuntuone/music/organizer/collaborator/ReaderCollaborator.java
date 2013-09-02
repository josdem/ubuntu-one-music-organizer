package org.ubuntuone.music.organizer.collaborator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.ubuntuone.music.organizer.bean.SongBean;
import org.ubuntuone.music.organizer.model.Playlist;
import org.ubuntuone.music.organizer.model.SongList;

@Component
public class ReaderCollaborator {

	public List<String> getPlaylistNames(List<Playlist> playlists) {
		List<String> names = new ArrayList<String>();
		for (Playlist playlist : playlists) {
			names.add(playlist.getName());
		}
		return names;
	}

	public Playlist getPlaylistSelected(List<Playlist> playlists, String name) {
		for (Playlist playlist : playlists) {
			if (playlist.getName().equals(name)){
				return playlist;
			}
		}
		return null;
	}

	public List<String> getSongsIds(List<SongBean> songsFiltered) {
		List<String> songsIds = new ArrayList<String>(); 
		for (SongBean songBean : songsFiltered) {
			songsIds.add(songBean.getId());
		}
		return songsIds;
	}

	public SongList getSongList(List<String> song_id_list) {
		SongList songList = new SongList();
		for (String string : song_id_list) {
			songList.getSong_id_list().add(string);
		}
		return songList;
	}

}
