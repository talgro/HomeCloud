package io.homecloud.synchronizedFolder.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HTTPHandller {

	private HttpURLConnection _con;
	private String _url;

	public HTTPHandller(String url) {
		this._url = url;
	}

	public void openConnection() throws IOException {
		URL url = new URL(_url);
		_con = (HttpURLConnection) url.openConnection();
	}

	/**
	 * This function sets request method(GET, SET, POST etc.)
	 * @param request
	 * @throws ProtocolException
	 */
	public void setRequestMethod(String request) throws ProtocolException {
		_con.setRequestMethod(request);
	}

	/**
	 * This function sets the method request + request headers
	 * @param request
	 * @param properties
	 * @throws ProtocolException
	 */
	public void setRequestMethod(String request, HashMap<String, String> properties) throws ProtocolException {
		_con.setRequestMethod(request);
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			_con.setRequestProperty(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * This function adds a request header.
	 * @param key
	 * @param value
	 */
	public void addProperty(String key, String value) {
		_con.setRequestProperty(key, value);
	}

	/**
	 * This function sends the given method response and returns the returned status.
	 * @return
	 * @throws IOException
	 */
	public String getResponse() throws IOException {
		int status = _con.getResponseCode();
		//OK status

		String rtn = "";
		if(status == 200) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(_con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			rtn = content.toString();
		}

		return rtn;
	}

	public String sendGET() throws IOException {
		_con.setRequestMethod("GET");
		int status = _con.getResponseCode();
		String rtn = "";

		//OK status
		if(status == 200) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(_con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			rtn = content.toString();
		}
		else {
			rtn = "Error status: " + status;
		}

		return rtn;
	}

	public int sendDELETE() throws IOException {
		_con.setRequestMethod("DELETE");
		int status = _con.getResponseCode();
		return status;
	}

	public int sendPUT(String body) throws IOException {
		_con.setDoOutput(true);
		_con.setDoInput(true); 
		_con.setRequestMethod("PUT");
		OutputStream out = _con.getOutputStream();
		OutputStreamWriter wout = new OutputStreamWriter(out, "UTF-8");
		wout.write(body);
		wout.flush();
		out.close();
		int status = _con.getResponseCode();
		return status;
	}

	public int sendPOST(File file) throws IOException {
		// Indicate that we want to write to the HTTP request body
		_con.setDoOutput(true);
		_con.setRequestMethod("POST");
		String boundaryString = "";
		_con.addRequestProperty("Content-Type", "multipart/form-data;");

		OutputStream outputStreamToRequestBody = _con.getOutputStream();
		BufferedWriter httpRequestBodyWriter =
				new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));

		// Include value from the myFileDescription text area in the post data
		//		httpRequestBodyWriter.write("\n\n--" + boundaryString + "\n");
		//		httpRequestBodyWriter.write("Content-Disposition: form-data; name=\"myFileDescription\"");
		//		httpRequestBodyWriter.write("\n\n");
		//		httpRequestBodyWriter.write("Log file for 20150208");

		// Include the section to describe the file
		//httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
		httpRequestBodyWriter.write("Content-Disposition: form-data;"
				+ "name=\"myFile\";"
				+ "filename=\""+ file.getName() +"\""
				+ "\nContent-Type: text/plain\n\n");
		httpRequestBodyWriter.flush();

		// Write the actual file contents
		FileInputStream inputStreamToLogFile = new FileInputStream(file);

		int bytesRead;
		byte[] dataBuffer = new byte[1024];
		while((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
			outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
		}

		outputStreamToRequestBody.flush();

		// Mark the end of the multipart http request
		//httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
		httpRequestBodyWriter.flush();

		// Close the streams
		outputStreamToRequestBody.close();
		httpRequestBodyWriter.close();

		int status = _con.getResponseCode();
		return status;
	}
	
	public String multipartRequest(Map<String, String> parmas, String filepath, String filefield, String fileMimeType) throws Exception {
		DataOutputStream outputStream = null;
		InputStream inputStream = null;

		String twoHyphens = "--";
		String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
		String lineEnd = "\r\n";

		String result = "";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;

		String[] q = filepath.split("/");
		int idx = q.length - 1;

		try {
			File file = new File(filepath);
			FileInputStream fileInputStream = new FileInputStream(file);

			_con.setDoInput(true);
			_con.setDoOutput(true);
			_con.setUseCaches(false);

			_con.setRequestMethod("POST");
			_con.setRequestProperty("Connection", "Keep-Alive");
			_con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			outputStream = new DataOutputStream(_con.getOutputStream());
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
			outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
			outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

			outputStream.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			while (bytesRead > 0) {
				outputStream.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			outputStream.writeBytes(lineEnd);

			// Upload POST Data
			Iterator<String> keys = parmas.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				String value = parmas.get(key);

				outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				outputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
				outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
				outputStream.writeBytes(lineEnd);
				outputStream.writeBytes(value);
				outputStream.writeBytes(lineEnd);
			}

			outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


			if (200 != _con.getResponseCode()) {
				throw new Exception("Failed to upload code:" + _con.getResponseCode() + " " + _con.getResponseMessage());
			}

			inputStream = _con.getInputStream();

			result = this.convertStreamToString(inputStream);

			fileInputStream.close();
			inputStream.close();
			outputStream.flush();
			outputStream.close();

			return result;
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}


	public void disconnect() {
		_con.disconnect();
	}
}
