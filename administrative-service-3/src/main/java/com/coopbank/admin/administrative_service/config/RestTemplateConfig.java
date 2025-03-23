package com.coopbank.admin.administrative_service.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;


@Configuration
public class RestTemplateConfig {

//    @Bean
//    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//        var sslContext = SSLContextBuilder.create().loadTrustMaterial(acceptingTrustStrategy).build();
//
//        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
//        	    sslContext,
//        	    new String[] { "TLSv1.2" }, // Specify allowed protocols
//        	    null, // Use default cipher suites
//        	    HttpsSupport.getDefaultHostnameVerifier());
//
//        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
//        	    .setSSLSocketFactory(sslSocketFactory)
//        	    .build();
//
//        CloseableHttpClient httpClient = HttpClients.custom()
//        	    .setConnectionManager(connectionManager)
//        	    .build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory =
//                new HttpComponentsClientHttpRequestFactory();
//
//        requestFactory.setHttpClient(httpClient);
//
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        return restTemplate;
//    }

        @Value("${soa.keyStore.resource}")
        private String keystoreResource;

        @Value("${soa.keyStore.password}")
        private String keystorePassword;

        @Bean
        public RestTemplate restTemplate() throws Exception {
            // Load keystore from the specified resource

            Resource resource = new ClassPathResource(keystoreResource.replace("classpath:", ""));

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(resource.getInputStream(), keystorePassword.toCharArray());

            // Create SSL context with the keystore
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(keyStore, null)
                    .build();

            // Create connection manager with SSL context and disable hostname verification
            PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                            .setSslContext(sslContext)
                            .setHostnameVerifier((hostname, session) -> true) // Disable hostname verification
                            .setTlsVersions("TLSv1.2")
                            .build())
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);

            return new RestTemplate(requestFactory);
        }
}
