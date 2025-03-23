package com.coopbank.admin.administrative_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "soa")
public class SoaPropertiesConfig {
    private KeyStoreProperties keyStore;
    private IPRSProperties iprs;
//    private CustomerBlacklistProperties blacklist;
    private CustomerSanctionDetailsProperties sanctionDetails;
    
    @Getter
    @Setter
    @NoArgsConstructor
    public abstract static class AbstractSoaProperties {
        private String endpoint;
        private String username;
        private String password;
        private String systemCode;
        private String soapCallback;
        
		@Override
		public String toString() {
			return "AbstractSoaProperties [endpoint=" + endpoint + ", username=" + username + ", password=[PROTECTED], systemCode=" + systemCode + ", soapCallback=" + soapCallback + "]";
		}
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class KeyStoreProperties {
        private Resource resource;
        private String password;
		@Override
		public String toString() {
			return "KeyStoreProperties [resource=" + resource + ", password=[PROTECTED]]";
		}
            }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class IPRSProperties extends AbstractSoaProperties {
    	private String clientUsername;
    	private String clientPassword;

    	@Override
		public String toString() {
    		return "IPRSProperties [endpoint=" + super.getEndpoint() + ", username=" + super.getUsername()
					+ ", password=[PROTECTED], systemCode=" + super.getSystemCode() + ", soapCallback=" + super.getSoapCallback() 
					+ ", clientUsername=" + clientUsername + ", clientPassword=[PROTECTED]]";
		}
    }
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    public static class CustomerBlacklistProperties extends AbstractSoaProperties {
//
//    	@Override
//		public String toString() {
//    		return "CustomerBlacklistProperties [endpoint=" + super.getEndpoint() + ", username=" + super.getUsername()
//					+ ", password=[PROTECTED], systemCode=" + super.getSystemCode() + ", soapCallback=" + super.getSoapCallback() + "]";
//		}
//    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CustomerSanctionDetailsProperties extends AbstractSoaProperties {
    	private String template;

    	@Override
		public String toString() {
    		return "CustomerSanctionDetailsProperties [endpoint=" + super.getEndpoint() + ", username=" + super.getUsername()
					+ ", password=[PROTECTED], systemCode=" + super.getSystemCode() + ", soapCallback="
    				+ super.getSoapCallback() + ", template=" + template + "]";
		}
    }
}
