package io.homecloud.synchronizedFolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringApp {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringApp.class, args);

		application.run(SpringApp.class, args);

		makeLocalhostPublic();
	}

	private static void makeLocalhostPublic() {
		String ANSI_RED = "\u001B[31m";
		String ANSI_RESET = "\u001B[0m";
		String domain = "";
		try {
			Process p = Runtime.getRuntime().exec("ssh -R 80:localhost:9090 ssh.localhost.run");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = null;
			System.out.println("\n\n");

			while ((s = stdInput.readLine()) != null) {
				System.out.println(ANSI_RED + s + ANSI_RESET);
				String[] splitcmdOutPut = s.split(" ");
				domain = splitcmdOutPut[4];
				//TODO:send to aws
			}

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}

		} catch (IOException e) {
			System.out.println("Could not run \"ssh -R 80:localhost:9090 ssh.localhost.run\" command");
			e.printStackTrace();
		}

	}

}
