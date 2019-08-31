package io.homecloud.homeserver.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class snsSubscribe {
	
	private final static String region = "us-east-2";
	private final static String accessKeyId = "AKIA6HHXEGHLALKLJTCN ";
	private final static String secretKeyId = "Wbp6y8VPYIVHujrbcljUIWYSFTfUwmwMd4ue8AxC";
	private String _topicARN = "arn:aws:sns:us-east-2:977623331286:homecloud_files";
		
	public snsSubscribe(String topicARN) {
		super();
		this._topicARN = topicARN;
	}


	/**
	 * This function subscribes a given domain to the topcARN of the object
	 * @param domain to subscribe
	 * @return the request ID for the SubscribeRequest action
	 */
	public String subscibeDomain(String domain) {
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKeyId);
		AWSCredentialsProvider credentialsProvider  = new AWSStaticCredentialsProvider(awsCreds);
		ClientConfiguration config = new ClientConfiguration();
		AmazonSNS snsClient = AmazonSNSClient.builder()
				.withRegion(region)
				.withClientConfiguration(config)
				.withCredentials(credentialsProvider)
				.build();
		
		// Subscribe an email endpoint to an Amazon SNS topic.
		final SubscribeRequest subscribeRequest = new SubscribeRequest(_topicARN , "https", domain);
		snsClient.subscribe(subscribeRequest);

		// Print the request ID for the SubscribeRequest action.
		return snsClient.getCachedResponseMetadata(subscribeRequest).toString();
	
	}
	
}
