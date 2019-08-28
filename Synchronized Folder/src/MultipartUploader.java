
//import com.google.common.base.Strings;
//import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MultipartUploader {

    private static final String CHARSET = "UTF-8";

    private static final String CRLF = "\r\n";

    public static void main(String[] args) throws IOException {
        // create /tmp/test.txt file first and add some text to it.
        final byte[] bytes = Files.readAllBytes(Paths.get("/tmp/test.txt"));
        new MultipartUploader().httpUpload("http://httpbin.org/post", "test.txt", bytes);
    }
    public String httpUpload(String url, String filename, byte[] byteStream)
            throws MalformedURLException, IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        final String boundary = Strings.repeat("-", 15) + Long.toHexString(System.currentTimeMillis());

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        OutputStream directOutput = connection.getOutputStream();
        PrintWriter body = new PrintWriter(new OutputStreamWriter(directOutput, CHARSET), true);

        body.append(CRLF);
        addSimpleFormData("myuser", "myUserName", body, boundary);
        addSimpleFormData("mypassword", "mySecretPassword", body, boundary);
        addFileData("myfile", filename, byteStream, body, directOutput, boundary);
        addCloseDelimiter(body, boundary);

        int responseCode = connection.getResponseCode();
        System.out.println("responseCode: " + responseCode);
        String responseMessage = connection.getResponseMessage();
        System.out.println("responseMessage: " + responseMessage);

        String payload = CharStreams.toString(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        System.out.println("payload: " + payload);
        return payload;
    }

    private static void addSimpleFormData(String paramName, String wert, PrintWriter body,
                                          final String boundary) {

        body.append("--").append(boundary).append(CRLF);
        body.append("Content-Disposition: form-data; name=\"" + paramName + "\"").append(CRLF);
        body.append("Content-Type: text/plain; charset=" + CHARSET).append(CRLF);
        body.append(CRLF);
        body.append(wert).append(CRLF);
        body.flush();
    }

    private static void addFileData(String paramName, String filename, byte[] byteStream, PrintWriter body,
                                    OutputStream directOutput, final String boundary) throws IOException {

        body.append("--").append(boundary).append(CRLF);
        body.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + filename + "\"")
                .append(CRLF);
        body.append("Content-Type: application/octed-stream").append(CRLF);
        body.append("Content-Transfer-Encoding: binary").append(CRLF);
        body.append(CRLF);
        body.flush();

        directOutput.write(byteStream);
        directOutput.flush();

        body.append(CRLF);
        body.flush();
    }

    private static void addCloseDelimiter(PrintWriter body, final String boundary) {
        body.append("--").append(boundary).append("--").append(CRLF);
        body.flush();
    }
}