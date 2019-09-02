package Test;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import io.homecloud.homeserver.utils.RegisterHomeServer;
import io.homecloud.homeserver.utils.SyncedFolderConnectionDetailsDto;

public class homeServerTest {
	public static void main(String[] args) {
		
		//TODO TEST!
		
		String awsDomain = "http://talgropper-4mto.localhost.run";
		String token = "eyJraWQiOiJKVVwveGN4aXIxOXhvcFwvcFJ4YlExdWxTdWpJWlByMDN2cjlnZ05QdzgrZGc9IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJlOTZiNGQ2OS1hYzk1LTQ4ZTktYmY2OC1hMWY3ZmFiMmU2ODYiLCJhdWQiOiIyczVpNnRhanM3NWdwZ3UzYjNpNHN0YTZxNCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJldmVudF9pZCI6IjU2NzgzNDdmLTlhZTMtNDA5My1hY2VkLWI3OWIzMDhkYmY3ZSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTY3NDE4NzcwLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV95Q01oT0ZKMmgiLCJjb2duaXRvOnVzZXJuYW1lIjoiZTk2YjRkNjktYWM5NS00OGU5LWJmNjgtYTFmN2ZhYjJlNjg2IiwiZXhwIjoxNTY3NDI1ODAzLCJpYXQiOjE1Njc0MjIyMDMsImVtYWlsIjoiZGFuaWVsYmlueWFtaW5AZ21haWwuY29tIn0.m2Uzl0xkTMGxKoXKvc0Z4vZKLWSvlacXbslwOqN_7_wVpAkGm_giVGmYSSQFvyuLrmiuIbOHVFq8lzv_FSNZ2gcCg84KABkT3jVgzpFgU7vM_CL9y7f1kzao2mCF0cyo3dFrNILqdOb2Hj66LB4GgwjO0JoZpzursdrHQKQt4RLX4xhHFQi-Ab6Y_--8119xLZozT_KxZGhchA0kKB9injbTTmqCxBR6TfyIntAY1_leoAfDfIDWNpk3kXmkMqAVLH76N0d4XSzOLaIM3zOwC9C6kDVGJJ-Pt7-ep2pWTWf_9QXbPVO9Q1QtzlJY_jR_j92vlJeRXSn7gms1Y2JmzA";
		
		//test posting homeServer addrs to AWS
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
		
	}
	
	private static void postToAws(String uri, String domain, String serverId, String token) {
		RegisterHomeServer register = new RegisterHomeServer(domain, serverId);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Barear " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplate.postForObject(uri, register, RegisterHomeServer.class, headers);
	}
	
	private static String getDomainFromAws(String awsDomain, String token) {
		String uri = awsDomain;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Barear " + token);

		SyncedFolderConnectionDetailsDto response = restTemplate.getForObject(uri, SyncedFolderConnectionDetailsDto.class,
				headers);
		return response.getHomeServerAddress();
	}
	
	
}
