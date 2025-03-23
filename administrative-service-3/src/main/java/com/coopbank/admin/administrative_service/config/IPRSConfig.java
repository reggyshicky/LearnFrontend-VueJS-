package com.coopbank.admin.administrative_service.config;

import com.coopbank.admin.administrative_service.client.IPRSClient;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ToString
public class IPRSConfig {
	private final SoaPropertiesConfig config;
    
    private final String contextPath = "com.coopbank.admin.administrative_service.ws.client.customer.iprs";
    private final BasicSoapClientConfig soaConfig;
    
	public IPRSConfig(SoaPropertiesConfig config) {
		soaConfig = new BasicSoapClientConfig(
				contextPath,
				config.getIprs().getUsername(),
				config.getIprs().getPassword(),
				config.getKeyStore().getResource(), 
				config.getKeyStore().getPassword());
		this.config = config;
	}
	
    @Bean
    public IPRSClient iprsClient() {
		var endpoint = config.getIprs().getEndpoint();
		var systemCode = config.getIprs().getSystemCode();
		var soapCallback = config.getIprs().getSoapCallback();
		var clientUsername = config.getIprs().getClientUsername();
		var clientPass = config.getIprs().getClientPassword();
		
		IPRSClient client = new IPRSClient(endpoint, systemCode, soapCallback, clientUsername, clientPass);
        client.setDefaultUri(endpoint.trim());
        client.setMarshaller(soaConfig.marshaller());
        client.setUnmarshaller(soaConfig.marshaller());
        client.setMessageSender(endpoint.trim().toLowerCase().startsWith("https://") ?
        		soaConfig.httpsUrlConnectionMessageSender() : soaConfig.httpComponentsMessageSender());
        return client;    	
    }
}
