package com.coopbank.useraccessservice.client;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class GlobalMethod {

    private static HttpComponentsClientHttpRequestFactory requestFactory;

    public static synchronized HttpComponentsClientHttpRequestFactory getRequestFactory(
            String soaTrustStorePath, String soaTrustStorePassword) {
        if (requestFactory == null) {
            try {
                requestFactory = createRequestFactory(soaTrustStorePath, soaTrustStorePassword);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create HttpComponentsClientHttpRequestFactory", e);
            }
        }
        return requestFactory;
    }

    private static HttpComponentsClientHttpRequestFactory createRequestFactory(
            String soaTrustStorePath, String soaTrustStorePassword)
            throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, KeyManagementException {

        // Load the truststore
        KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (FileInputStream truststoreFile = new FileInputStream(soaTrustStorePath)) {
            truststore.load(truststoreFile, soaTrustStorePassword.toCharArray());
        }

        // Build SSLContext
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(truststore, null)
                .build();

        // Create SSLConnectionSocketFactory
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);

        // Create HttpClientConnectionManager
        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(socketFactory)
                .build();

        // Create HttpClient
        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        // Create and return HttpComponentsClientHttpRequestFactory
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
}
