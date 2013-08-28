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

}
