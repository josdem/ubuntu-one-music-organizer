package org.ubuntuone.music.organizer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.ubuntuone.music.organizer.bean.SongBean;
import org.ubuntuone.music.organizer.model.Song;

@Service
public class SongAdapterService {

	public void adapt(List<SongBean> beanSongs, List<Song> songs) {
		for (Song song : songs) {
			SongBean songBean = new SongBean();
			songBean.setId(song.getId());
			songBean.setArtist(song.getArtist());
			songBean.setTitle(song.getTitle());
			songBean.setAlbum(song.getAlbum());
			songBean.setGenre(song.getGenre());
			songBean.setYear(song.getYear());
			songBean.setTrack(song.getTrack());
			beanSongs.add(songBean);
		}
	}

}
