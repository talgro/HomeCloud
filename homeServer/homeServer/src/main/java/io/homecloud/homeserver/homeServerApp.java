package io.homecloud.homeserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import io.homecloud.homeserver.Filters.CORSFilter;
import io.homecloud.homeserver.utils.snsSubscribe;

@SpringBootApplication
public class homeServerApp {

	private static final String subscribeTopicArn = "arn:aws:sns:us-east-2:977623331286:synchronizedFolder_changes";

	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";

	public static void run(String[] args, String userName, String serverId, String awsDomain) {

		SpringApplication application = new SpringApplication(homeServerApp.class);

		//adding properties for 
		Properties properties = new Properties();
		properties.put("spring.http.multipart.max-file-size", "700MB");
		properties.put("spring.http.multipart.max-request-size", "700MB");
		application.setDefaultProperties(properties);

		application.run(args);

		//run ssh to do remote port forwarding
		try {
			Process p = Runtime.getRuntime().exec("ssh -R 80:localhost:8080 ssh.localhost.run");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = null;
			System.out.println("\n\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(ANSI_RED + s + ANSI_RESET);
				String[] splitcmdOutPut = s.split(" ");
				String domain = splitcmdOutPut[4];
				//subscirbe homeServer to sns of sync folder
//				snsSubscribe subscriber = new snsSubscribe(subscribeTopicArn);
//				System.out.println(subscriber.subscibeDomain(domain + "/SNSNotification"));
				//TODO: send awsDomain userName, serverId , domain
				//System.out.println("userName: " + userName + ", serverId: " + serverId + ", AWSdomain:" + awsDomain + ", homeServer domain: " + domain);
			}

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}

		} catch (IOException e) {
			System.out.println("Could not run \"ssh -R 80:localhost:8080 ssh.localhost.run\" command");
			e.printStackTrace();
		}


	}

	//** commented out becuase of jwt filter which does the job
	//	//This is for creating the CORS filter
	//	@Bean
	//	public FilterRegistrationBean corsFilterRegistration() {
	//		FilterRegistrationBean registrationBean =
	//				new FilterRegistrationBean(new CORSFilter());
	//		registrationBean.setName("CORS Filter");
	//		registrationBean.addUrlPatterns("/*");
	//		registrationBean.setOrder(1);
	//		return registrationBean;
	//	}



}
