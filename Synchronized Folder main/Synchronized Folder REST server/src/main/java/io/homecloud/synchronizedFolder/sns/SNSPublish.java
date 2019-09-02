package io.homecloud.synchronizedFolder.sns;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SNSPublish {

	private final static String region = "us-east-2";
	private final static String accessKeyId = "AKIA6HHXEGHLALKLJTCN ";
	private final static String secretKeyId = "Wbp6y8VPYIVHujrbcljUIWYSFTfUwmwMd4ue8AxC";
	private String _topicARN;

	public SNSPublish(String _topicARN) {
		super();
		this._topicARN = _topicARN;
	}

	/**
	 * This function published to the topcARN of the object
	 * @param subject
	 * @param message
	 * @return the MessageId of the message
	 */
	public String publish(String subject, String mssg) {
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKeyId);
		AWSCredentialsProvider credentialsProvider  = new AWSStaticCredentialsProvider(awsCreds);
		ClientConfiguration config = new ClientConfiguration();
		AmazonSNS snsClient = AmazonSNSClient.builder()
				.withRegion(region)
				.withClientConfiguration(config)
				.withCredentials(credentialsProvider)
				.build();
		
		// Publish a message to an Amazon SNS topic.
		final PublishRequest publishRequest = new PublishRequest(_topicARN, mssg, subject);
		final PublishResult publishResponse = snsClient.publish(publishRequest);

		// Print the MessageId of the message.
		return publishResponse.getMessageId();
	}

}
