package io.homecloud.homeserver.localFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.homecloud.homeserver.homeServerApp;

public class homeServerStarter {

	public static void run(String[] args, String userName) {

		//create folders for app
		String homeServerDir = createMainAppFolders();
		creatUserFolder(homeServerDir, userName);

		//create config.xml
		String serverId = userName + "-homeServer";
		createXML(homeServerDir, serverId);

		HashMap<String, HashMap<String, String>> config = readXML(homeServerDir + File.separator + "config.xml");
		
		serverId = config.get("homeServer").get("server-id");
		String awsDomain = config.get("AWS").get("domain");
		String token = config.get("AWS").get("JWT");
		
		homeServerApp.run(args, userName,serverId, awsDomain, token);

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

	private static void createXML(String homeServerDir, String serverId) {
		//exit if config file exists
		if(new File(homeServerDir + File.separator + "config.xml").exists())
			return;
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element root = doc.createElement("config");

			//homeServer Node
			Element serverInfoMainNode = doc.createElement("homeServer");
			Element serverIdElem = doc.createElement("server-id");
			serverIdElem.appendChild(doc.createTextNode(serverId));
			serverInfoMainNode.appendChild(serverIdElem);
			root.appendChild(serverInfoMainNode);

			//AWS Node
			Element serverAWSMainNode = doc.createElement("AWS");
			Element awsDomain = doc.createElement("domain");
			awsDomain.appendChild(doc.createTextNode("http://talgropper-5kee.localhost.run"));
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
		File userDir = new File(homeServerDir + File.separator + "root" + File.separator + userName);
		if(!userDir.exists())
			userDir.mkdir();
	}

	//a method to create main app folders
	//return main homeServer dir
	public static String createMainAppFolders() {
		//get name of main root (C:/)
		File homeServerDir = new File(System.getProperty("user.home"));
		homeServerDir = homeServerDir.getParentFile();
		homeServerDir = homeServerDir.getParentFile();
		homeServerDir = new File(homeServerDir.getPath() + File.separator + "homeServer");
		if(!homeServerDir.exists())
			homeServerDir.mkdir();
		String rtn = homeServerDir.getPath();
		File homeServerRoot = new File(homeServerDir.getPath() + File.separator + "root");
		if(!homeServerRoot.exists())
			homeServerRoot.mkdir();
		return rtn;
	}

}
