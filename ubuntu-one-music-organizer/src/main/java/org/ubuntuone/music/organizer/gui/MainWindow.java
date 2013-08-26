package org.ubuntuone.music.organizer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.action.ResponseCallback;
import org.asmatron.messengine.engines.support.ViewEngineConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.ubuntuone.music.organizer.action.ActionResult;
import org.ubuntuone.music.organizer.action.Actions;
import org.ubuntuone.music.organizer.state.ApplicationState;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 7053782125260126509L;
	private static final String JMENU_ITEM_LABEL = "Show playlist";
	private static final String JMENU_EXIT_LABEL = "Exit";
	private static final String JMENU_LABEL = "File";
	
	private JMenuBar menuBar;
	private JMenu mainMenu;
	private JMenuItem lastFmMenuItem;
	private JMenuItem exitMenuItem;
	
	@Autowired
	private ViewEngineConfigurator viewEngineConfigurator;
	
	private Log log = LogFactory.getLog(getClass());
	
	public MainWindow() {
		super(ApplicationState.APPLICATION_NAME);
		initialize();
	}

	private void initialize() {
		this.setBounds(0, 0, ApplicationState.WIDTH, ApplicationState.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setJMenuBar(getMenubar());
	}
	
	private JMenuBar getMenubar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getLastFmMenu());
		}
		return menuBar;
	}

	private JMenu getLastFmMenu() {
		if (mainMenu == null) {
			mainMenu = new JMenu(JMENU_LABEL);
			mainMenu.setMnemonic(KeyEvent.VK_F);
			mainMenu.add(getLastFmMenuItem());
			mainMenu.add(getExitMenuItem());
		}
		return mainMenu;
	}
	
	private JMenuItem getLastFmMenuItem() {
		if (lastFmMenuItem == null) {
			lastFmMenuItem = new JMenuItem(JMENU_ITEM_LABEL);
			lastFmMenuItem.setMnemonic(KeyEvent.VK_S);

			lastFmMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new PlaylistWork();
				}
			});
		}
		return lastFmMenuItem;
	}
	
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem(JMENU_EXIT_LABEL);
			exitMenuItem.setMnemonic(KeyEvent.VK_E);

			exitMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}
	
	private class PlaylistWork {
		
		public PlaylistWork() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {
				
				protected Boolean doInBackground() throws Exception {

					MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.PLAYLIST, null, new ResponseCallback<ActionResult>() {

						public void onResponse(ActionResult response) {
							log.info("RESPONSE getPlaylist ready");
						}

					});
					return true;
				}
			};
			swingWorker.execute();
		}
	}
}
