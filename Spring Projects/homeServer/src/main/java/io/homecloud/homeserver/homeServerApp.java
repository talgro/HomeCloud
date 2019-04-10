package io.homecloud.homeserver;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class homeServerApp {

	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	public static void main(String[] args) {
		SpringApplication.run(homeServerApp.class, args);
		
		//This is for making localHost public
		//Access at danie.localhost.run
		try {
			Process p = Runtime.getRuntime().exec("ssh -R 80:localhost:8080 ssh.localhost.run");
			String userName = System.getProperty("user.name");
			System.out.println(ANSI_RED + "\n\n Access to server is at: http://"+userName+".localhost.run\n\n"+ ANSI_RESET);
			
		} catch (IOException e) {
			System.out.println("Could not run \"ssh -R 80:localhost:8080 ssh.localhost.run\" command");
			e.printStackTrace();
		}
	}

}
