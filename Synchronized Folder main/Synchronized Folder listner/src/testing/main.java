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

import io.homecloud.synchronizedFolder.HTTPUtils.HttpDownloadUtility;

public class main {

	public static void main(String[] args) {
		
		String fileURL = "https://danie-ydyb.localhost.run/download/tomgr/test2/DSC00225.JPG";
		String saveDir = "C:\\Users\\danie\\Desktop\\Studies";
		
		try {
			HttpDownloadUtility.downloadFile(fileURL, saveDir);
		} catch (IOException e) {		
			e.printStackTrace();
		}
		System.out.println("done");
		
	}

}
