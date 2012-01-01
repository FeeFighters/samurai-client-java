package com.feefighters.samurai;

import java.io.Serializable;
import java.util.Properties;

import com.feefighters.samurai.http.Http;

public class SamuraiGateway implements Serializable {
	private static final long serialVersionUID = 2L;
    public static final String Version = "1.0.0";
	
	public String merchantKey;
    public String merchantPassword;
    public String processorToken;
    protected static Properties defaultProperties;
	
    public static void configure(Properties properties) {
        SamuraiGateway.defaultProperties = properties;
    }
    public static SamuraiGateway create(Properties properties) {
        return new SamuraiGateway(properties);
    }
    public static SamuraiGateway defaultGateway() {
        return SamuraiGateway.create(defaultProperties);
    }

    public SamuraiGateway(Properties properties) {
        this(
            properties.getProperty("merchantKey"),
            properties.getProperty("merchantPassword"),
            properties.getProperty("processorToken")
        );
    }
	public SamuraiGateway(String merchantKey, String merchantPassword, String processorToken) {
		super();
		this.merchantKey = merchantKey;
		this.merchantPassword = merchantPassword;
		this.processorToken = processorToken;
	}

    public Http http() {
        return newHttpInstance();
    }
    
	private Http newHttpInstance() {
		return new Http(merchantKey, merchantPassword, "https://api.samurai.feefighters.com/v1");
	}	
    
    
	
}
