package org.ubuntuone.music.organizer.gui.table;

public enum DescriptionTableColumns {
	
	ARTIST("Artist"), TRACK("Track"), ALBUM("Album"), GENRE("Genre"), 
	YEAR("Year"), N_TRACK("# Trk"), N_CD("# CD");
	
	private final String name;
	
	private DescriptionTableColumns(String name) {
		this.name = name;
	}
	
	public String label() {
		return getName() == null ? "" : getName();
	}

	public String getName() {
		return name;
	}
	
}
