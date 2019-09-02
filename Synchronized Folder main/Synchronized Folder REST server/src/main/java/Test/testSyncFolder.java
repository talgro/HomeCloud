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

		String awsDomain = "http://talgropper-cufw.localhost.run";
		String token = "eyJraWQiOiJKVVwveGN4aXIxOXhvcFwvcFJ4YlExdWxTdWpJWlByMDN2cjlnZ05QdzgrZGc9IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJlOTZiNGQ2OS1hYzk1LTQ4ZTktYmY2OC1hMWY3ZmFiMmU2ODYiLCJhdWQiOiIyczVpNnRhanM3NWdwZ3UzYjNpNHN0YTZxNCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJldmVudF9pZCI6ImY5NTkxZTA4LWIxMWItNDRhNC1iNmVmLWFjNDhiZTUzYTYwYyIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTY3NDQ4NDE4LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV95Q01oT0ZKMmgiLCJjb2duaXRvOnVzZXJuYW1lIjoiZTk2YjRkNjktYWM5NS00OGU5LWJmNjgtYTFmN2ZhYjJlNjg2IiwiZXhwIjoxNTY3NDUyMDE5LCJpYXQiOjE1Njc0NDg0MTksImVtYWlsIjoiZGFuaWVsYmlueWFtaW5AZ21haWwuY29tIn0.MBTS1X848BRcJ0fntVffulW4GsdzCkuzn1uww1U2FasXbIzkzowu1bEG21Z7VkROtEWiLK4luurjaNHAYN9unu7qs3A4nwW5NqwXyIKqvZujC67tiPiPHM37bAezoKEVB7X8WkiGzNM703LmVyrYgfxYymqwc09T30BlCUTKCpivRnYi7RpqZBN6J_-X67REgjyLuDd8x-8k-dyguG3BjFJk_kHtX7XPd23MjwYbbQtlRTFiTQqW5MJPtpUIhfsMqHuHWJBnh-rMRMTzSLEViX7ROlj3WyJTgba0jCerJ1JByfRycVIsxiXMegGiVHlJ49ifiJupkFfbkmlqrqVJeg";

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
