package org.ubuntuone.music.organizer.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.ubuntuone.music.organizer.service.GenreService;
import org.ubuntuone.music.organizer.state.ApplicationState;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 7053782125260126509L;
	private static final String JMENU_ITEM_LABEL = "Show songs";
	private static final String JMENU_SELECT_ITEM_LABEL = "Select Genre";
	private static final String JMENU_EXIT_LABEL = "Exit";
	private static final String JMENU_LABEL = "File";
	private static final Rectangle SCROLL_PANE_BOUNDS = new Rectangle(10, 10, 1004, 520);
	
	private JMenuBar menuBar;
	private JMenu mainMenu;
	private JMenuItem showSongsMenuItem;
	private JMenuItem selectMenuItem;
	private JMenuItem exitMenuItem;
	private JTable descriptionTable;
	private JScrollPane scrollPane;
	
	private List<SongBean> songs;
	
	@Autowired
	private ViewEngineConfigurator viewEngineConfigurator;
	@Autowired
	private GenreService genreService;
	
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
		this.setJMenuBar(getMenubar());
		this.setVisible(true);
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
			menuBar.add(getFileMenu());
		}
		return menuBar;
	}
	
	private JMenu getFileMenu() {
		if (mainMenu == null) {
			mainMenu = new JMenu(JMENU_LABEL);
			mainMenu.setMnemonic(KeyEvent.VK_F);
			mainMenu.add(getSongsMenuItem());
			mainMenu.add(getSelectMenuItem());
			mainMenu.add(getExitMenuItem());
		}
		return mainMenu;
	}
	
	private JMenuItem getSongsMenuItem() {
		if (showSongsMenuItem == null) {
			showSongsMenuItem = new JMenuItem(JMENU_ITEM_LABEL);
			showSongsMenuItem.setMnemonic(KeyEvent.VK_S);

			showSongsMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new PlaylistWork();
				}
			});
		}
		return showSongsMenuItem;
	}
	
	private JMenuItem getSelectMenuItem() {
		if (selectMenuItem == null) {
			selectMenuItem = new JMenuItem(JMENU_SELECT_ITEM_LABEL);
			selectMenuItem.setMnemonic(KeyEvent.VK_G);
			selectMenuItem.setEnabled(false);

			selectMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new GenreWorker();
				}
			});
		}
		return selectMenuItem;
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
							log.info("RESPONSE getSongs ready");
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
							}
						}

					});
					return true;
				}
				
				@Override
				protected void done() {
					MainWindow.this.songs = songs;
					selectMenuItem.setEnabled(true);
				}
				
			};
			swingWorker.execute();
		}
	}
	
	private class GenreWorker {
		
		public GenreWorker() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {
				
				protected Boolean doInBackground() throws Exception {
					MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.GENRES, songs, new ResponseCallback<Set<String>>() {

						public void onResponse(Set<String> response) {
							log.info("RESPONSE getGenres ready");
							String genre = OauthDialog.getGenreSelection(response.toArray());
							log.info("Genre selected: " + genre);
							JTable descriptionTable = getDescriptionTable();
							DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
							model.getDataVector().removeAllElements();
							for (SongBean songBean : songs) {
								if (songBean.getGenre().equals(genre)){
									int row = descriptionTable.getRowCount();
									model.addRow(new Object[] { "", "", "", "", "", "", "", "" });
									descriptionTable.setValueAt(songBean.getArtist(), row, ApplicationState.ARTIST_COLUMN);
									descriptionTable.setValueAt(songBean.getTitle(), row, ApplicationState.TITLE_COLUMN);
									descriptionTable.setValueAt(songBean.getAlbum(), row, ApplicationState.ALBUM_COLUMN);
									descriptionTable.setValueAt(songBean.getGenre(), row, ApplicationState.GENRE_COLUMN);
									descriptionTable.setValueAt(songBean.getYear(), row, ApplicationState.YEAR_COLUMN);
									descriptionTable.setValueAt(songBean.getTrack(), row, ApplicationState.TRACK_NUMBER_COLUMN);
								}
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
