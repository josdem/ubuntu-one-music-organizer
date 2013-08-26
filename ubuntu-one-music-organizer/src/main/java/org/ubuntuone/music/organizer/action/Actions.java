package org.ubuntuone.music.organizer.action;

import static org.asmatron.messengine.action.ActionId.cm;

import org.asmatron.messengine.action.ActionId;
import org.asmatron.messengine.action.EmptyAction;
import org.asmatron.messengine.action.RequestAction;

public interface Actions {
	String GET_METADATA = "getPlaylist1";
	ActionId<EmptyAction> METADATA = cm(GET_METADATA);
	
	String GET_PLAYLIST = "getPlaylist";
	ActionId<RequestAction<Integer, ActionResult>> PLAYLIST = cm(GET_PLAYLIST);
	
}
