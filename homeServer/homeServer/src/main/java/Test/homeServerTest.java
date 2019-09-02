package Test;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.homecloud.homeserver.utils.RegisterHomeServer;
import io.homecloud.homeserver.utils.SyncedFolderConnectionDetailsDto;

public class homeServerTest {
	public static void main(String[] args) {
		
		//TODO TEST!
		
		String awsDomain = "http://talgropper-goyj.localhost.run";
		String token = "eyJraWQiOiJKVVwveGN4aXIxOXhvcFwvcFJ4YlExdWxTdWpJWlByMDN2cjlnZ05QdzgrZGc9IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJlOTZiNGQ2OS1hYzk1LTQ4ZTktYmY2OC1hMWY3ZmFiMmU2ODYiLCJhdWQiOiIyczVpNnRhanM3NWdwZ3UzYjNpNHN0YTZxNCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJldmVudF9pZCI6ImY5NTkxZTA4LWIxMWItNDRhNC1iNmVmLWFjNDhiZTUzYTYwYyIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTY3NDQ4NDE4LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV95Q01oT0ZKMmgiLCJjb2duaXRvOnVzZXJuYW1lIjoiZTk2YjRkNjktYWM5NS00OGU5LWJmNjgtYTFmN2ZhYjJlNjg2IiwiZXhwIjoxNTY3NDUyMDE5LCJpYXQiOjE1Njc0NDg0MTksImVtYWlsIjoiZGFuaWVsYmlueWFtaW5AZ21haWwuY29tIn0.MBTS1X848BRcJ0fntVffulW4GsdzCkuzn1uww1U2FasXbIzkzowu1bEG21Z7VkROtEWiLK4luurjaNHAYN9unu7qs3A4nwW5NqwXyIKqvZujC67tiPiPHM37bAezoKEVB7X8WkiGzNM703LmVyrYgfxYymqwc09T30BlCUTKCpivRnYi7RpqZBN6J_-X67REgjyLuDd8x-8k-dyguG3BjFJk_kHtX7XPd23MjwYbbQtlRTFiTQqW5MJPtpUIhfsMqHuHWJBnh-rMRMTzSLEViX7ROlj3WyJTgba0jCerJ1JByfRycVIsxiXMegGiVHlJ49ifiJupkFfbkmlqrqVJeg";
		
//		//test posting homeServer addrs to AWS
//		String endpoint1 = "/clients/registerNewServer";
//		String homeServerDomain = "https://daniel-test-homeServer.localhost.run";
//		String serverId = "daniel-test-server";
//		System.out.println("posting to AWS...");
//		postToAws(awsDomain + endpoint1, homeServerDomain, serverId, token);
//		System.out.println("Done posting to AWS.");
		
		
		//test getting syncFolder addrss to AWS
		String endPoint2 = "/clients/getMySyncedFolderDetails";
		System.out.println("\ngetting address of SyncFOlder from aws...");
		String syncFolderDomain = getDomainFromAws(awsDomain + endPoint2, token);
		System.out.println("syncFolderDomain: " + syncFolderDomain);
		System.out.println("\nDone.");
//		
	}
	
	private static void postToAws(String uri, String domain, String serverId, String token) {
		RegisterHomeServer register = new RegisterHomeServer(domain, serverId);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Barear " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RegisterHomeServer> request = new HttpEntity<>(register, headers);
		restTemplate.postForObject(uri, request, RegisterHomeServer.class);
	}
	
	private static String getDomainFromAws(String uri, String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Barear " + token);
		HttpEntity request = new HttpEntity(headers);
		ResponseEntity<SyncedFolderConnectionDetailsDto> response = restTemplate.exchange(uri, HttpMethod.GET, request,
				SyncedFolderConnectionDetailsDto.class);
		return response.getBody().getHomeServerAddress();
	}
	
	
}
