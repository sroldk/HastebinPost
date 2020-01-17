package your.package;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HastebinPost {

    public static final JsonParser JSON_PARSER = new JsonParser();

    public static String postText(String content) {
        String result;
        try {
            byte[] postData = content.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String request = "https://hasteb.in/documents";
            URL url = new URL(request);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            httpURLConnection.setRequestProperty("Content-Type", "text/plain");
            httpURLConnection.setRequestProperty("charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            httpURLConnection.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.write(postData);
            Reader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));

            String json = ((BufferedReader) bufferedReader).readLine();

            JsonElement jsonElement = JSON_PARSER.parse(json);
            if (!jsonElement.getAsJsonObject().has("key")) {
                return null;
            }

            String key = jsonElement.getAsJsonObject().get("key").getAsString();
            result = "https://hasteb.in/" + key;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return result;
    }

}
