package org.ubuntuone.music.organizer.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

public class OauthDialog {
	
	public static String getOauthVerifierCode(){
		
		return (String)JOptionPane.showInputDialog(
            new JFrame(),
            "We opened an authorizaton request in your web browser.\n" + 
            "Please provide the Oauth Verfier code:\n",
            "Authorization is required",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            StringUtils.EMPTY);
	}
	
	public static String getGenreSelection(Object[] posibilities){
		
		return (String)JOptionPane.showInputDialog(
            new JFrame(),
            "Please, select a genre.\n",
            "Genre selection",
            JOptionPane.PLAIN_MESSAGE,
            null,
            posibilities,
            StringUtils.EMPTY);
	}
	
	public static String getPlaylistSelection(Object[] posibilities){
		
		return (String)JOptionPane.showInputDialog(
            new JFrame(),
            "Please, select a playlist.\n",
            "Playlist selection",
            JOptionPane.PLAIN_MESSAGE,
            null,
            posibilities,
            StringUtils.EMPTY);
	}

	public static String getCreatePlaylistDialog() {
		return (String)JOptionPane.showInputDialog(
	            new JFrame(),
	            "Provide a new playlist name\n", 
	            "New Playlist",
	            JOptionPane.PLAIN_MESSAGE,
	            null,
	            null,
	            StringUtils.EMPTY);
	}

	public static void playlistCreated() {
		JOptionPane.showMessageDialog(new JFrame(), "Playlist created successfully");
	}

	public static void playlistCreationFailed() {
		JOptionPane.showMessageDialog(new JFrame(), "Playlist creation failed");
	}

}
