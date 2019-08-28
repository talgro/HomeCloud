package System.Tray;

import java.awt.EventQueue;
import java.awt.TrayIcon;

import javax.swing.JInternalFrame;

public class guiIcon extends JInternalFrame {
	
	TrayClass tray = new TrayClass();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiIcon frame = new guiIcon();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public guiIcon() {
		setBounds(100, 100, 450, 300);

	}

}
