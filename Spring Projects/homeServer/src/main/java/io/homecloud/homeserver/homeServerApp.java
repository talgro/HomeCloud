package io.homecloud.homeserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = null;
			System.out.println("\n\n");
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(ANSI_RED + s + ANSI_RESET);
			}
			
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdError.readLine()) != null) {
			    System.out.println(s);
			}
			//String userName = System.getProperty("user.name");
			//System.out.println(ANSI_RED + "\n\n Access to server is at: http://"+userName+".localhost.run\n\n"+ ANSI_RESET);

		} catch (IOException e) {
			System.out.println("Could not run \"ssh -R 80:localhost:8080 ssh.localhost.run\" command");
			e.printStackTrace();
		}
	}

}
