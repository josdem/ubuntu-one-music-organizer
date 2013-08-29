package org.ubuntuone.music.organizer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.ubuntuone.music.organizer.bean.SongBean;

@Service
public class GenreService {

	public Set<String> getGenres(List<SongBean> songs){
		Set<String> genres = new HashSet<String>();
		for (SongBean songBean : songs) {
			genres.add(songBean.getGenre());
		}
		return genres;
	}
	
}
