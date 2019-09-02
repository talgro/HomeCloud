package io.homecloud.synchronizedFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.homecloud.synchronizedFolder.utils.HttpDownloadUtility;
import io.homecloud.synchronizedFolder.utils.DataResponse;
import io.homecloud.synchronizedFolder.utils.HTTPHandller;
import io.homecloud.synchronizedFolder.utils.HomeServerConnectionDetailsDto;
import io.homecloud.synchronizedFolder.utils.LocalFile;

public class SyncFolderRESTStarter {

	public static void run(String[] args, String userName) {

		String homeServerDir = createMainAppFolders();

		creatUserFolder(homeServerDir, userName);

		String serverId = userName + "-SyncFolderServer";
		createXML(homeServerDir, serverId, userName);

		HashMap<String, HashMap<String, String>> config = readXML(homeServerDir + File.separator + "config.xml");

		serverId = config.get("SyncFolderServer").get("server-id");
		String awsDomain = config.get("AWS").get("domain");
		String token = config.get("AWS").get("JWT");

		//*****  Downloading all files to HomeCloud folder from homeServer
//		String AwsEndPoint = "/clients/getMyHomeServerDetails";
//		String homeServerDomain = getHomeServerDomainFromAws(awsDomain + AwsEndPoint, token);
//		String endPoint = "/" + userName;
//
//		try {
//			getAllFiles(homeServerDomain, endPoint , homeServerDir + File.separator + userName);
//			System.out.println("Done downloading all files to HomeCloud folder.");
//		} catch (IOException e) {
//			System.out.println("Error downloading all files");
//			e.printStackTrace();
//		}

		//*** start folder listner
		Runnable folderListnerRun = new FolderListner(homeServerDir + File.separator + userName);
		Thread folderListnerThread = new Thread(folderListnerRun);
		folderListnerThread.start();

		//*** Start Rest folder
		SyncFolderRESTApp.run(args, userName,serverId, awsDomain, token);
	}

	private static String getHomeServerDomainFromAws(String uri, String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Barear " + token);

		HomeServerConnectionDetailsDto response = restTemplate.getForObject(uri, HomeServerConnectionDetailsDto.class,
				headers);
		return response.getHomeServerAddress();
	}

		public static HashMap<String, HashMap<String, String>> readXML(String xmlFile) {
			HashMap<String, HashMap<String, String>> rtn = new HashMap<String, HashMap<String, String>>();
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				Element root = doc.getDocumentElement();
				NodeList nlist = root.getChildNodes();
				HashMap<String, String> tempList;
				for (int temp = 0; temp < nlist.getLength(); temp++) {
					tempList = new HashMap<String, String>();
					Node nNode = nlist.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						String nodeName = nNode.getNodeName();

						NodeList n2list = nNode.getChildNodes();
						for (int temp2 = 0; temp2 < n2list.getLength(); temp2++) {
							Node n2Node = n2list.item(temp2);
							if (n2Node.getNodeType() == Node.ELEMENT_NODE)
								tempList.put(n2Node.getNodeName(), n2Node.getTextContent());
						}
						rtn.put(nodeName, tempList);
					}
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}

			return rtn;
		}

		private static void createXML(String homeServerDir, String serverId, String userName) {
			//exit if config file exists
			if(new File(homeServerDir + File.separator + "config.xml").exists())
				return;
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			try {
				icBuilder = icFactory.newDocumentBuilder();
				Document doc = icBuilder.newDocument();
				Element root = doc.createElement("config");

				//SyncFolderServer Node
				Element serverInfoMainNode = doc.createElement("SyncFolderServer");
				Element serverIdElem = doc.createElement("server-id");
				serverIdElem.appendChild(doc.createTextNode(serverId));
				Element userNameElem = doc.createElement("userName");
				userNameElem.appendChild(doc.createTextNode(userName));
				serverInfoMainNode.appendChild(userNameElem);
				serverInfoMainNode.appendChild(serverIdElem);
				root.appendChild(serverInfoMainNode);

				//AWS Node
				Element serverAWSMainNode = doc.createElement("AWS");
				Element awsDomain = doc.createElement("domain");
				awsDomain.appendChild(doc.createTextNode("www.aws.com"));
				serverAWSMainNode.appendChild(awsDomain);
				Element jwtElement = doc.createElement("JWT");
				jwtElement.appendChild(doc.createTextNode("JWT-PLACE-HOLDER"));
				serverAWSMainNode.appendChild(jwtElement);
				root.appendChild(serverAWSMainNode);

				doc.appendChild(root);

				try {
					Transformer tr = TransformerFactory.newInstance().newTransformer();
					tr.setOutputProperty(OutputKeys.INDENT, "yes");
					tr.setOutputProperty(OutputKeys.METHOD, "xml");
					tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

					// send DOM to file
					tr.transform(new DOMSource(doc), 
							new StreamResult(new FileOutputStream(homeServerDir + File.separator + "config.xml")));
				}  catch (TransformerException te) {
					System.out.println("tr");
					System.out.println(te.getMessage());
				} catch (IOException ioe) {
					System.out.println("IO");
					System.out.println(ioe.getMessage());
				}

			} catch (ParserConfigurationException e) {
				System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + e);
			}

		}

		//a method to create user dir if not exists
		private static void creatUserFolder(String homeServerDir, String userName) {
			File userDir = new File(homeServerDir + File.separator + userName);
			if(!userDir.exists())
				userDir.mkdir();
		}

		//a method to create main app folders
		//return main homeServer dir
		public static String createMainAppFolders() {
			//get name of main root (C:/)
			File homeServerDir = new File(System.getProperty("user.home"));
			homeServerDir = new File(homeServerDir.getPath() + File.separator + "HomeCloud");
			if(!homeServerDir.exists())
				homeServerDir.mkdir();
			return homeServerDir.getPath();
		}

		private static void getAllFiles(String domain, String endPoint, String folderDir) throws IOException {
			HTTPHandller h = new HTTPHandller(domain +  endPoint);
			h.openConnection();
			String json = h.getResponse();
			if(json.isEmpty())
				return;
			GsonBuilder builder = new GsonBuilder(); 
			builder.setPrettyPrinting(); 
			Gson gson = builder.create(); 
			DataResponse response = gson.fromJson(json, DataResponse.class);
			for (LocalFile file : response.getContent()) {
				if(file.getType().equals("folder")) { //recursive call
					new File(folderDir + File.separator + file.getName()).mkdir();//create folder
					getAllFiles(domain,  endPoint + "/" + file.getName(), folderDir + "/" + file.getName());
				}
				else { //download file
					System.out.println("downloading: " + endPoint + "/" + file.getName() + "...");
					HttpDownloadUtility.downloadFile(domain + "/download" + endPoint + "/" + URLEncoder.encode(file.getName(),java.nio.charset.StandardCharsets.UTF_8.toString()), folderDir);
				}
			}

		}

	}
