package org.ubuntuone.music.organizer.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.action.ResponseCallback;
import org.asmatron.messengine.engines.support.ViewEngineConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.ubuntuone.music.organizer.action.ActionResult;
import org.ubuntuone.music.organizer.action.Actions;
import org.ubuntuone.music.organizer.bean.SongBean;
import org.ubuntuone.music.organizer.gui.table.DescriptionTable;
import org.ubuntuone.music.organizer.state.ApplicationState;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 7053782125260126509L;
	private static final String JMENU_ITEM_LABEL = "Show songs";
	private static final String JMENU_EXIT_LABEL = "Exit";
	private static final String JMENU_LABEL = "File";
	private static final Rectangle SCROLL_PANE_BOUNDS = new Rectangle(320, 10, 693, 390);
	
	private JMenuBar menuBar;
	private JMenu mainMenu;
	private JMenuItem lastFmMenuItem;
	private JMenuItem exitMenuItem;
	private JTable descriptionTable;
	private JScrollPane scrollPane;
	
	@Autowired
	private ViewEngineConfigurator viewEngineConfigurator;
	
	private Log log = LogFactory.getLog(getClass());
	
	public MainWindow() {
		super(ApplicationState.APPLICATION_NAME);
		initialize();
		getDescriptionTable();
	}

	private void initialize() {
		this.setBounds(0, 0, ApplicationState.WIDTH, ApplicationState.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setJMenuBar(getMenubar());
		this.add(getScrollPane());
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getDescriptionTable());
			scrollPane.setBounds(SCROLL_PANE_BOUNDS);
		}
		return scrollPane;
	}
	
	public JTable getDescriptionTable() {
		if (descriptionTable == null) {
			descriptionTable = new DescriptionTable();
		}

		return descriptionTable;
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
		
		private List<SongBean> songs = new ArrayList<SongBean>();
		
		public PlaylistWork() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {
				
				protected Boolean doInBackground() throws Exception {

					MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.SONGS, songs, new ResponseCallback<ActionResult>() {

						public void onResponse(ActionResult response) {
							log.info("RESPONSE getPlaylist ready");
							JTable descriptionTable = getDescriptionTable();
							DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
							for (SongBean songBean : songs) {
								int row = descriptionTable.getRowCount();
								model.addRow(new Object[] { "", "", "", "", "", "", "", "" });
								descriptionTable.setValueAt(songBean.getArtist(), row, ApplicationState.ARTIST_COLUMN);
								descriptionTable.setValueAt(songBean.getTitle(), row, ApplicationState.TITLE_COLUMN);
								descriptionTable.setValueAt(songBean.getAlbum(), row, ApplicationState.ALBUM_COLUMN);
								descriptionTable.setValueAt(songBean.getGenre(), row, ApplicationState.GENRE_COLUMN);
								descriptionTable.setValueAt(songBean.getYear(), row, ApplicationState.YEAR_COLUMN);
								descriptionTable.setValueAt(songBean.getTrack(), row, ApplicationState.TRACK_NUMBER_COLUMN);
								descriptionTable.setValueAt(songBean.getDiscNumber(), row, ApplicationState.CD_NUMBER_COLUMN);
							}
						}

					});
					return true;
				}
			};
			swingWorker.execute();
		}
	}
}
