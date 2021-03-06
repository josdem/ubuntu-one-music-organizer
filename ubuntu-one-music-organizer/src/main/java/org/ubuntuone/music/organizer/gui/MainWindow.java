package org.ubuntuone.music.organizer.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
import org.ubuntuone.music.organizer.collaborator.ReaderCollaborator;
import org.ubuntuone.music.organizer.gui.table.DescriptionTable;
import org.ubuntuone.music.organizer.model.Playlist;
import org.ubuntuone.music.organizer.model.PlaylistBucket;
import org.ubuntuone.music.organizer.state.ApplicationState;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 7053782125260126509L;
	private static final String JMENU_ITEM_LABEL = "Show songs";
	private static final String JMENU_SELECT_GENRE_LABEL = "Filter by Genre";
	private static final String JMENU_SELECT_PLAYLIST_LABEL = "Move to Playlist";
	private static final String JMENU_EXIT_LABEL = "Exit";
	private static final String JMENU_FILE_LABEL = "File";
	private static final String JMENU_CREATE_LABEL = "Create";
	private static final String JMENU_CREATE_PLAYLIST_LABEL = "Create Playlist";
	private static final String STATUS_LABEL = "Status";
	private static final String MOVE = "Move";
	
	private static final Rectangle SCROLL_PANE_BOUNDS = new Rectangle(10, 10, 1004, 500);
	private static final Rectangle BOTTOM_PANEL_BOUNDS = new Rectangle(10, 500, 1004, 20);
	private static final Rectangle LABEL_BOUNDS = new Rectangle(10, 515, 200, 20);
	private static final Rectangle MOVE_BUTTON_BOUNDS = new Rectangle(902, 515, 110, 28);
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu createMenu;
	private JMenuItem showSongsMenuItem;
	private JMenuItem selectGenreMenuItem;
	private JMenuItem selectPlaylistMenuItem;
	private JMenuItem createPlaylistMenuItem;
	private JMenuItem exitMenuItem;
	private JTable descriptionTable;
	private JScrollPane scrollPane;
	private JPanel bottomPanel;
	private JLabel statusLabel;
	private JButton moveButton;
	
	private List<SongBean> songs;
	private Playlist playlistSelected;
	private List<SongBean> songsFiltered = new ArrayList<SongBean>();
	
	@Autowired
	private ViewEngineConfigurator viewEngineConfigurator;
	@Autowired
	private ReaderCollaborator readerCollaborator;
	
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
		this.add(getScrollPane());
		this.add(getBottomPanel());
		this.setVisible(true);
	}
	
	private JPanel getBottomPanel() {
		if (bottomPanel == null) {
			bottomPanel = new JPanel();
			bottomPanel.setLayout(null);
			bottomPanel.setBounds(BOTTOM_PANEL_BOUNDS);
			bottomPanel.add(getStatusLabel());
			bottomPanel.add(getMoveButton());
		}
		return bottomPanel;
	}
	
	public JButton getMoveButton() {
		if (moveButton == null) {
			moveButton = new JButton(MOVE);
			moveButton.setBounds(MOVE_BUTTON_BOUNDS);
			moveButton.setEnabled(false);

			moveButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new MoveSongsWorker();
				}
			});
		}
		return moveButton;
	}
	
	public JLabel getStatusLabel() {
		if (statusLabel == null) {
			statusLabel = new JLabel(STATUS_LABEL);
			statusLabel.setBounds(LABEL_BOUNDS);
		}
		return statusLabel;
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
			menuBar.add(getCreateMenu());
		}
		return menuBar;
	}
	
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu(JMENU_FILE_LABEL);
			fileMenu.setMnemonic(KeyEvent.VK_F);
			fileMenu.add(getSongsMenuItem());
			fileMenu.add(getSelectGenreMenuItem());
			fileMenu.add(getSelectPlaylistMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}
	
	private JMenu getCreateMenu() {
		if (createMenu == null) {
			createMenu = new JMenu(JMENU_CREATE_LABEL);
			createMenu.setMnemonic(KeyEvent.VK_C);
			createMenu.add(getCreatePlaylistMenuItem());
		}
		return createMenu;
	}
	
	private JMenuItem getCreatePlaylistMenuItem() {
		if (createPlaylistMenuItem == null) {
			createPlaylistMenuItem = new JMenuItem(JMENU_CREATE_PLAYLIST_LABEL);
			createPlaylistMenuItem.setMnemonic(KeyEvent.VK_P);

			createPlaylistMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					String playlist = OauthDialog.getCreatePlaylistDialog();
					new CreatePlaylistWorker(playlist);
				}
			});
		}
		return createPlaylistMenuItem;
	}
	
	private JMenuItem getSongsMenuItem() {
		if (showSongsMenuItem == null) {
			showSongsMenuItem = new JMenuItem(JMENU_ITEM_LABEL);
			showSongsMenuItem.setMnemonic(KeyEvent.VK_S);

			showSongsMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					getStatusLabel().setText(ApplicationState.WORKING);
					new SongsWorker();
				}
			});
		}
		return showSongsMenuItem;
	}
	
	private JMenuItem getSelectGenreMenuItem() {
		if (selectGenreMenuItem == null) {
			selectGenreMenuItem = new JMenuItem(JMENU_SELECT_GENRE_LABEL);
			selectGenreMenuItem.setMnemonic(KeyEvent.VK_G);
			selectGenreMenuItem.setEnabled(false);

			selectGenreMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new GenreWorker();
				}
			});
		}
		return selectGenreMenuItem;
	}
	
	private JMenuItem getSelectPlaylistMenuItem() {
		if (selectPlaylistMenuItem == null) {
			selectPlaylistMenuItem = new JMenuItem(JMENU_SELECT_PLAYLIST_LABEL);
			selectPlaylistMenuItem.setMnemonic(KeyEvent.VK_P);
			selectPlaylistMenuItem.setEnabled(false);

			selectPlaylistMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new PlaylistWorker();
				}
			});
		}
		return selectPlaylistMenuItem;
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
	
	private class SongsWorker {
		
		private List<SongBean> songs = new ArrayList<SongBean>();
		
		public SongsWorker() {
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
							MainWindow.this.songs = songs;
							selectGenreMenuItem.setEnabled(true);
							getStatusLabel().setText(ApplicationState.DONE);
						}

					});
					return true;
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
									songsFiltered.add(songBean);
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
							selectPlaylistMenuItem.setEnabled(true);
						}

					});
					return true;
				}
				
			};
			swingWorker.execute();
		}
	}
	
	private class PlaylistWorker {
		private List<Playlist> playlists = new ArrayList<Playlist>();
		
		public PlaylistWorker() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {
				
				protected Boolean doInBackground() throws Exception {
					MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.PLAYLIST, playlists, new ResponseCallback<ActionResult>() {

						public void onResponse(ActionResult response) {
							log.info("RESPONSE getPlaylist ready");
							List<String> names = readerCollaborator.getPlaylistNames(playlists);
							String playlist = OauthDialog.getPlaylistSelection(names.toArray());
							log.info("Selected playlist: " + playlist);
							getStatusLabel().setText(ApplicationState.PLAYLIST_SELECTED + playlist);
							playlistSelected = readerCollaborator.getPlaylistSelected(playlists, playlist);
							getMoveButton().setEnabled(true);
						}

					});
					return true;
				}
				
			};
			swingWorker.execute();
		}
	}
	
	private class CreatePlaylistWorker {
		private String playlist;

		public CreatePlaylistWorker(String playlist) {
			this.playlist = playlist;
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {
				
				protected Boolean doInBackground() throws Exception {
					MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.CREATE, playlist, new ResponseCallback<ActionResult>() {

						public void onResponse(ActionResult response) {
							log.info("RESPONSE createPlaylist ready");
							if (response != null && response.equals(ActionResult.COMPLETE)){
								OauthDialog.playlistCreated();
							} else {
								OauthDialog.playlistCreationFailed();
							}
						}

					});
					return true;
				}
				
			};
			swingWorker.execute();
		}
	}
	
	private class MoveSongsWorker {
		private PlaylistBucket playlistBucket = null;

		public MoveSongsWorker() {
			playlistBucket = new PlaylistBucket();
			playlistBucket.setPlaylist_id(playlistSelected.getId());
			playlistBucket.setSong_id_list(readerCollaborator.getSongsIds(songsFiltered));
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {
				
				protected Boolean doInBackground() throws Exception {
					MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.MOVE, playlistBucket, new ResponseCallback<ActionResult>() {

						public void onResponse(ActionResult response) {
							log.info("RESPONSE moveSongsToPlaylist ready");
						}

					});
					return true;
				}
				
			};
			swingWorker.execute();
		}
	}
}
