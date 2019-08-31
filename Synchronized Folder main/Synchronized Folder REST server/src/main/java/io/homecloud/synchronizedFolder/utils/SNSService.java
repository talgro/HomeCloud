package io.homecloud.synchronizedFolder.utils;

import org.springframework.stereotype.Service;

@Service
public class SNSService {

	public void handleSNSNotification(String subject, String message) {
		System.out.println("******/n/n GOT Notification ********/n");
		System.out.println("Subject: " + subject + "\nMessage: " + message);	
	}
	
	
	
}
