package org.ubuntuone.music.organizer.action;

import static org.asmatron.messengine.action.ActionId.cm;

import java.util.List;

import org.asmatron.messengine.action.ActionId;
import org.asmatron.messengine.action.EmptyAction;
import org.asmatron.messengine.action.RequestAction;
import org.ubuntuone.music.organizer.bean.SongBean;

public interface Actions {
	String GET_METADATA = "getPlaylist1";
	ActionId<EmptyAction> METADATA = cm(GET_METADATA);
	
	String GET_SONGS = "getSongs";
	ActionId<RequestAction<List<SongBean>, ActionResult>> SONGS = cm(GET_SONGS);
	
}
