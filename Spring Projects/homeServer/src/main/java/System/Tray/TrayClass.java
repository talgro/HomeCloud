package System.Tray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TrayClass implements Runnable {

	static TrayIcon trayIcon;

	public TrayClass() { 
		show(); 
	}

	public static void show() {

		if(!SystemTray.isSupported()) {
			System.exit(0);
		}
		trayIcon = new TrayIcon(createIcon("homeServerIcon.png", "Icon"));
		trayIcon.setToolTip("blablabla1");
		final SystemTray tray = SystemTray.getSystemTray();

		final PopupMenu menu = new PopupMenu();
		MenuItem about = new MenuItem("about");
		MenuItem exit = new MenuItem("exit");

		menu.add(about);
		menu.addSeparator();
		menu.add(exit);

		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "this is the about box");

			}
		});

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		trayIcon.setPopupMenu(menu);

		try {
			tray.add(trayIcon);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected static Image createIcon(String path, String desc) {
		URL imageURL = TrayClass.class.getResource(path);
		return (new ImageIcon(imageURL, desc)).getImage();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
