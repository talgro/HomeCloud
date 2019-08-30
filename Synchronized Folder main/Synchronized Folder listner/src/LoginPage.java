import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class LoginPage extends JDialog implements Runnable{

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private boolean running;
	/**
	 * Launch the application.
	 */

	@Override
	public void run() {
		try {
			LoginPage dialog = new LoginPage();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			while(dialog.getRunning()) { Thread.sleep(100);}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean getRunning() {
		return running;
	}

	/**
	 * Create the dialog.
	 */
	public LoginPage() {
		running = true;
		setBounds(100, 100, 791, 471);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(167, 119, 168, 47);
		textPane.setText("User Name:   ");
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textPane.setEditable(false);
		contentPanel.add(textPane);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textField.setColumns(10);
		textField.setBounds(374, 119, 279, 47);
		contentPanel.add(textField);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("Password");
		textPane_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textPane_1.setEditable(false);
		textPane_1.setBounds(167, 182, 168, 54);
		contentPanel.add(textPane_1);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passwordField.setBounds(374, 189, 279, 47);
		contentPanel.add(passwordField);

		JCheckBox checkBox = new JCheckBox("Show Password");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()) {
					passwordField.setEchoChar((char)0);					
				} else {
					passwordField.setEchoChar('*');
				}
			}
		}
				);
		checkBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		checkBox.setBounds(374, 248, 161, 29);
		contentPanel.add(checkBox);

		JButton button = new JButton("Login\r\n");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = textField.getText();
				char[] psswrd = passwordField.getPassword(); 
				running = false;
				dispose();
				//Thread.currentThread().interrupt();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 23));
		button.setBounds(322, 333, 181, 54);
		contentPanel.add(button);


	}

}
