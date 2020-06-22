package com.github.sellers.urltester;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class UrlTesterMain {

    public static void main(String[] args) {

        if (0 == args.length) {
            System.out.println("Example usage:");
            System.out.println("java -jar url-tester-0.0.1-SNAPSHOT-jar-with-dependencies.jar www.google.ca");

        } else {
            for (String url : args) {
                checkUrl(url);
            }
        }
    }

    private static void checkUrl(String urlAddress) {
        CloseableHttpClient client = HttpClients.createDefault();

        try {
            HttpGet method = new HttpGet("https://" + urlAddress);
            try (CloseableHttpResponse response = client.execute(method)) {
                int statusCode = response.getStatusLine().getStatusCode();

                System.out.println(String.format("%s has a response of %s", urlAddress, statusCode));
            } finally {
                method.releaseConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
