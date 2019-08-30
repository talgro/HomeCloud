package io.homecloud.synchronizedFolder.controler;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.homecloud.synchronizedFolder.utils.NotifyMessage;

//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

import io.homecloud.synchronizedFolder.utils.SNSService;
import io.homecloud.synchronizedFolder.utils.SNSUpdate;

@RestController
public class MainController {

	@Autowired
	private SNSService _service;

	@RequestMapping(value = "SNSNotification", method = RequestMethod.POST)
	public ResponseEntity updateSNS(@RequestHeader Map<String, String> headers, @RequestBody String payload) { 
		System.out.println("POST to /SNSNotification");
		String requstedHeader = headers.get("x-amz-sns-message-type");
		System.out.println("header: " + requstedHeader);
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
		Gson gson = builder.create(); 
		NotifyMessage mssg = gson.fromJson(payload, NotifyMessage.class);
		if(requstedHeader.equals("SubscriptionConfirmation")) {		
			String urlToSubscribe = mssg.getSubscribeURL();

			//			String subString = payload.substring(payload.indexOf("SubscribeURL"));
			//			String urlToSubscribe = subString.split("\"")[4];

			HTTPHandller handler = new HTTPHandller(urlToSubscribe);
			try {
				handler.openConnection();
				String response = handler.sendGET();
				System.out.println(response);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				handler.disconnect();
			}

		}

		else { //equals "Notification"

			String subject = mssg.getSubject();
			String message = mssg.getMessage();
			System.out.println("Subject: " + subject);
			System.out.println("message: " + message);

		}
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "SNSNotification", method = RequestMethod.GET)
	@ResponseBody
	public String print() {
		return "hello";
	}

	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public String print2() {
		return "hello";
	}

}
