package com.github.sellers.urltester;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class UrlTesterMain {

    public static void main(String[] args) {

        Options options = new Options();
        Option urlsOption = new Option("url", null, true, "The Urls to try to contact over https");
        urlsOption.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(urlsOption);

        Option proxyHostOption = new Option("proxyHost", null, true, "The proxy host");
        proxyHostOption.setOptionalArg(true);
        options.addOption(proxyHostOption);
        Option proxyPortOption = new Option("proxyPort", null, true, "The proxy port");
        proxyPortOption.setOptionalArg(true);
        options.addOption(proxyPortOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (null != cmd.getOptionValue("proxyHost")) {
                System.setProperty("https.proxyHost", cmd.getOptionValue("proxyHost"));
                System.setProperty("java.net.useSystemProxies", "true");
            }
            if (null != cmd.getOptionValue("proxyPort")) {
                System.setProperty("https.proxyPort", cmd.getOptionValue("proxyPort"));
            }

            if (null != cmd.getOptionValues("url")) {
                for (String url : cmd.getOptionValues("url")) {
                    checkUrl(url);
                }
            } else {
                formatter.printHelp("url-tester", options);
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("url-tester", options);

            System.exit(1);
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
            System.out.println(String.format("%s threw an exception of %s", urlAddress, e.getMessage()));
            e.printStackTrace();
        }
    }

}
