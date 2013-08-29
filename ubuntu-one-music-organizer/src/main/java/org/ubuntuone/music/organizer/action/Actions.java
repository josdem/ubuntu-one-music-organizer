package org.ubuntuone.music.organizer.action;

import static org.asmatron.messengine.action.ActionId.cm;

import java.util.List;
import java.util.Set;

import org.asmatron.messengine.action.ActionId;
import org.asmatron.messengine.action.RequestAction;
import org.ubuntuone.music.organizer.bean.SongBean;

public interface Actions {
	String GET_SONGS = "getSongs";
	ActionId<RequestAction<List<SongBean>, ActionResult>> SONGS = cm(GET_SONGS);
	
	String GET_GENRES = "getGenres";
	ActionId<RequestAction<List<SongBean>, Set<String>>> GENRES = cm(GET_GENRES);
	
}
