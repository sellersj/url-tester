# url-tester
Tests access to a url using http client 4.x

## Example usage
Without a proxy
`java -jar target/url-tester-0.0.1-SNAPSHOT-jar-with-dependencies.jar -url www.google.ca`

When running behind a proxy
`java -jar target/url-tester-0.0.1-SNAPSHOT-jar-with-dependencies.jar -proxyHost=myProxyServer -proxyPort=80 www.tsn.ca`
