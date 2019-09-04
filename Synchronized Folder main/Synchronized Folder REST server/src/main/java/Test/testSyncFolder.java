package Test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.homecloud.synchronizedFolder.RegisterSyncFolderServer;
import io.homecloud.synchronizedFolder.utils.HomeServerConnectionDetailsDto;

public class testSyncFolder {

	public static void main(String[] args) {

		//TODO TEST!

		String awsDomain = "http://talgropper-ogsf.localhost.run";
		String token = "eyJraWQiOiJYSStGVlZWNHNyQVBvSlFjRktqSEQrS2x4cG9NWmpoVkloTDcxYWhBcnBvPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJkYWZlZjM0NC1hMzBiLTQ2NDctOWQ5MS1lMTIwMjQ3ZGQ4ZTYiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLmV1LXdlc3QtMS5hbWF6b25hd3MuY29tXC9ldS13ZXN0LTFfODB0cE9TSzlFIiwiY29nbml0bzp1c2VybmFtZSI6ImRhZmVmMzQ0LWEzMGItNDY0Ny05ZDkxLWUxMjAyNDdkZDhlNiIsImdpdmVuX25hbWUiOiJ0b20iLCJhdWQiOiI3MWFtZnJubzNtanBkc2d1ajFyYWZrcTVyayIsImV2ZW50X2lkIjoiMjYwODVhYzMtOGNjMS00ZmYyLWJiYTUtMWFhY2NkYjMyNWViIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE1Njc0Njc1NTAsImV4cCI6MTU2NzQ3MTE1MCwiaWF0IjoxNTY3NDY3NTUwLCJmYW1pbHlfbmFtZSI6Imdyb3BwZXIiLCJlbWFpbCI6Imdyb3BwZXJ0b21AZ21haWwuY29tIn0.guj0fU6bI1QlIKfnZ-8TixTNmKkibjlUpC57Qyce7gT7IPtEujz4jLc9mHNW3fVt9Afx2iTc9rDF_v0J4tnPW7aGofoWSa5Ht8mstes0Juj7X82n0LpR3Ser1fekqwC5oNKERcUz20XoVz03ewa8UbYoW30ritEpnbKANGXFVNQtE1xvVTv-7JqFW7QBM2Of9DN88HaYGwZaxTmEVgyZRyNux15i0ZHsIPVzk4zw6WPCeWVmcl4yCA-r4qlO_STdij_kA-neVkgkN2CalWocOtyKekKrOzinq8n4X-ULK1ciqTfyEurSBfueRkY04EBjZH77vU_ea_-lJu8EYYBW9g";

		//test posting homeServer addrs to AWS
		String endpoint1 = "/clients/registerNewSyncedFolder";
		String syncFolderDomain = "https://daniel-test-SyncFolder.localhost.run";
		System.out.println("posting to AWS...");
		postToAws(awsDomain + endpoint1, syncFolderDomain, token);
		System.out.println("Done posting to AWS.");

		//test getting homeServer addrss to AWS
//		String endPoint2 = "/clients/getMyHomeServerDetails";
//		System.out.println("\ngetting address of homeServer from aws...");
//		String homeServerDomain = getHomeServerDomainFromAws(awsDomain + endPoint2, token);
//		System.out.println("HomeServerDomain: " + homeServerDomain);
//		System.out.println("\nDone.");

	}

	private static void postToAws(String uri, String domain, String token) {
		RegisterSyncFolderServer register = new RegisterSyncFolderServer(domain);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Barear " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RegisterSyncFolderServer> request = new HttpEntity<>(register, headers);
		restTemplate.postForObject(uri, request, RegisterSyncFolderServer.class);		
	}

	private static String getHomeServerDomainFromAws(String uri, String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Barear " + token);
		HttpEntity request = new HttpEntity(headers);

		ResponseEntity<HomeServerConnectionDetailsDto> response = restTemplate.exchange(uri, HttpMethod.GET, request,
				HomeServerConnectionDetailsDto.class);
		return response.getBody().getHomeServerAddress();
	}






}
