package com.coopbank.admin.administrative_service.config;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import com.coopbank.admin.administrative_service.util.BasicAuthHttpsConnectionMessageSender;
import lombok.ToString;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class BasicSoapClientConfig {
	private final String contextPath;
	private final String basicAuthUsername;
	private final String basicAuthPassword;
	
	private final Resource keyStore;
	private final String keystorePassword;
	
    public BasicSoapClientConfig(String contextPath, String basicAuthUsername, String basicAuthPassword,
			Resource keyStore, String keystorePassword) {
    	
		this.contextPath = contextPath.trim();
		this.basicAuthUsername = basicAuthUsername.trim();
		this.basicAuthPassword = basicAuthPassword;
		this.keyStore = keyStore;
		this.keystorePassword = keystorePassword;
	}

	public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(contextPath);
        return marshaller;
    }

    public HttpsUrlConnectionMessageSender httpsUrlConnectionMessageSender() {    	
        HttpsUrlConnectionMessageSender webServiceMessageSender =
            new BasicAuthHttpsConnectionMessageSender(this.basicAuthUsername, this.basicAuthPassword);
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            Resource keystore = this.keyStore;
            String keyStorePassword = this.keystorePassword;
            
            ks.load(keystore.getInputStream(), keyStorePassword.toCharArray());
            keystore.getInputStream().close();
            
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(ks, keyStorePassword.toCharArray());
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(keystore.getInputStream(), keyStorePassword.toCharArray());
            keystore.getInputStream().close();
            
            TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(ts);
            webServiceMessageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
            webServiceMessageSender.setTrustManagers(trustManagerFactory.getTrustManagers());
            webServiceMessageSender.setHostnameVerifier((hostname, sslSession) -> true);
        } catch (Exception e) {
        	log.error("Error occurred: ", e);
        }
        return webServiceMessageSender;
    }

    public HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        // set the basic authorization credentials
        httpComponentsMessageSender.setCredentials(usernamePasswordCredentials());
        return httpComponentsMessageSender;
    }

    public UsernamePasswordCredentials usernamePasswordCredentials() {
        return new UsernamePasswordCredentials(this.basicAuthUsername, this.basicAuthPassword);
    }
}
