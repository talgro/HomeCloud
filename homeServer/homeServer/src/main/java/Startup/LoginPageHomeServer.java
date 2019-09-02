package Startup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Checkbox;

import javax.swing.JTextPane;

import org.springframework.beans.factory.DisposableBean;

import io.homecloud.homeserver.homeServerApp;
import io.homecloud.homeserver.localFile.homeServerStarter;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class LoginPageHomeServer {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPageHomeServer window = new LoginPageHomeServer(args);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPageHomeServer(String[] args) {
		initialize(args);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[] args) {
		frame = new JFrame();
		frame.setBounds(100, 100, 791, 471);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textField.setBounds(383, 103, 279, 47);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JTextPane txtpnUserName = new JTextPane();
		txtpnUserName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtpnUserName.setEditable(false);
		txtpnUserName.setText("User Name:   ");
		txtpnUserName.setBounds(184, 103, 161, 47);
		frame.getContentPane().add(txtpnUserName);

		JTextPane txtpnPassword = new JTextPane();
		txtpnPassword.setText("Password");
		txtpnPassword.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtpnPassword.setEditable(false);
		txtpnPassword.setBounds(184, 186, 161, 47);
		frame.getContentPane().add(txtpnPassword);

		JButton btnNewButton = new JButton("Login\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = textField.getText();
				char[] psswrd = passwordField.getPassword(); 
				System.out.println(userName + ", " + Arrays.toString(psswrd));

				//TODO: validate with AWS

				Thread thread = new Thread() {
					public void run(){
						homeServerStarter.run(args, userName);
					}
				};
				thread.start();
				
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnNewButton.setBounds(290, 345, 181, 54);
		frame.getContentPane().add(btnNewButton);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passwordField.setBounds(383, 186, 279, 47);
		frame.getContentPane().add(passwordField);

		JCheckBox chckbxShowPassword = new JCheckBox("Show Password");
		chckbxShowPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chckbxShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxShowPassword.isSelected()) {
					passwordField.setEchoChar((char)0);					
				} else {
					passwordField.setEchoChar('*');
				}
			}
		});
		chckbxShowPassword.setBounds(383, 245, 161, 29);
		frame.getContentPane().add(chckbxShowPassword);
	}
}
