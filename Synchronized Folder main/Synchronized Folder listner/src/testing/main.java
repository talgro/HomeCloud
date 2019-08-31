package testing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.JSONInput;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class main {

	public static void main(String[] args) {
		
		String region = "us-east-2";
		String accessKeyId = "AKIA6HHXEGHLALKLJTCN ";
		String secretKeyId = "Wbp6y8VPYIVHujrbcljUIWYSFTfUwmwMd4ue8AxC";
		String topicARN = "arn:aws:sns:us-east-2:977623331286:homecloud_files";
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKeyId);
		AWSCredentialsProvider credentialsProvider  = new AWSStaticCredentialsProvider(awsCreds);
		ClientConfiguration config = new ClientConfiguration();
		AmazonSNS snsClient = AmazonSNSClient.builder()
				.withRegion(region)
				.withClientConfiguration(config)
				.withCredentials(credentialsProvider)
				.build();
//		
//		// Subscribe an email endpoint to an Amazon SNS topic.
//		String domain = "https://danie-b3ij.localhost.run/SNSNotification";
//		final SubscribeRequest subscribeRequest = new SubscribeRequest(topcARN , "https", domain);
//		snsClient.subscribe(subscribeRequest);
//
//		// Print the request ID for the SubscribeRequest action.
//		System.out.println("SubscribeRequest: " + snsClient.getCachedResponseMetadata(subscribeRequest));
	
		// Publish a message to an Amazon SNS topic.
		final String msg = "If you receive this message, publishing a message to an Amazon SNS topic works.";
		final String subject = "subject test";
		final PublishRequest publishRequest = new PublishRequest(topicARN, msg, subject);
		final PublishResult publishResponse = snsClient.publish(publishRequest);

		// Print the MessageId of the message.
		System.out.println("MessageId: " + publishResponse.getMessageId());
		
	}

}
