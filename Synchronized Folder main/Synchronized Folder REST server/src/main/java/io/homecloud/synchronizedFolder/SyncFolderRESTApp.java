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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import io.homecloud.synchronizedFolder.sns.snsSubscribe;

@SpringBootApplication
public class SyncFolderRESTApp {

	private static final String subscribeTopicArn = "arn:aws:sns:us-east-2:977623331286:homeServer_changes";
	
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	public static void run(String[] args, String userName, String serverId, String awsDomain, String token) {
		SpringApplication application = new SpringApplication(SyncFolderRESTApp.class, args);

		application.run(SyncFolderRESTApp.class, args);

		try {
			Process p = Runtime.getRuntime().exec("ssh -R 80:localhost:9090 ssh.localhost.run");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = null;
			System.out.println("\n\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(ANSI_RED + s + ANSI_RESET);
				String[] splitcmdOutPut = s.split(" ");
				String domain = splitcmdOutPut[4];

				//subscribe to aws sns topic
				snsSubscribe subscriber = new snsSubscribe(subscribeTopicArn);
				System.out.println(subscriber.subscibeDomain(domain + "/SNSNotification"));
				
				//update AWS with syncFolder ssh address
				String endpoint = "/clients/registerNewSyncedFolder";
				postToAws(awsDomain + endpoint, domain, token);
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

	//TODO Test this! (post SyncFOlder domain to aws)
	private static void postToAws(String uri, String domain, String token) {
		RegisterSyncFolderServer register = new RegisterSyncFolderServer(domain);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Barear " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RegisterSyncFolderServer> request = new HttpEntity<>(register, headers);
		restTemplate.postForObject(uri, request, RegisterSyncFolderServer.class);		
	}

}
