package org.ubuntuone.music.organizer.collaborator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.ubuntuone.music.organizer.model.Playlist;

@Component
public class ReaderCollaborator {

	public List<String> getPlaylistNames(List<Playlist> playlists) {
		List<String> names = new ArrayList<String>();
		for (Playlist playlist : playlists) {
			names.add(playlist.getName());
		}
		return names;
	}

}
