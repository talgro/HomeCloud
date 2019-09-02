package Test;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import io.homecloud.synchronizedFolder.RegisterSyncFolderServer;
import io.homecloud.synchronizedFolder.utils.HomeServerConnectionDetailsDto;

public class testSyncFolder {

	public static void main(String[] args) {
		
		//TODO TEST!
		
		String awsDomain = "";
		String token = "";
		
		//test posting homeServer addrs to AWS
		String endpoint1 = "/clients/registerNewSyncedFolder";
		String syncFolderDomain = "https://daniel-test-SyncFolder.localhost.run";
		System.out.println("posting to AWS...");
		postToAws(awsDomain + endpoint1, syncFolderDomain, token);
		System.out.println("Done posting to AWS.");
		
		//test getting homeServer addrss to AWS
		String endPoint2 = "/clients/getMyHomeServerDetails";
		System.out.println("\ngetting address of SyncFOlder from aws...");
		String homeServerDomain = getHomeServerDomainFromAws(awsDomain + endPoint2, token);
		System.out.println("syncFolderDomain: " + homeServerDomain);
		System.out.println("\nDone.");
	
	}
	
	private static String getHomeServerDomainFromAws(String uri, String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Barear " + token);

		HomeServerConnectionDetailsDto response = restTemplate.getForObject(uri, HomeServerConnectionDetailsDto.class,
				headers);
		return response.getHomeServerAddress();
	}

	private static void postToAws(String uri, String domain, String token) {
		RegisterSyncFolderServer register = new RegisterSyncFolderServer(domain);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Barear " + token);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		restTemplate.postForObject(uri, register, RegisterSyncFolderServer.class, headers);		
	}

	
	
	
}
