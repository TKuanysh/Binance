package binance.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpClient {
    public static String sendRequest(String url) {
        HttpURLConnection con = null;

        try {
            URL urlReq = new URL(url);
            con = (HttpURLConnection) urlReq.openConnection();
            con.setRequestMethod("GET");

            StringBuilder content;
            int status = con.getResponseCode();

            if (status > 299) {
                System.out.println(con.getResponseCode());
                return null;
            } else {
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {

                    String line;
                    content = new StringBuilder();

                    while ((line = in.readLine()) != null) {
                        content.append(line);
                        content.append(System.lineSeparator());
                    }
                }
                return content.toString();
            }


        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return url;
    }
}
