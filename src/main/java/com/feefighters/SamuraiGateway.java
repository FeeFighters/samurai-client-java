package com.feefighters;

import java.io.Serializable;

import com.feefighters.http.Http;

public class SamuraiGateway implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String merchantKey;
	private String merchantPassword;
	private String processorToken;
	
	private boolean debug;
		
	public SamuraiGateway(String merchantKey, String merchantPassword,
			String processorToken) {
		super();
		this.merchantKey = merchantKey;
		this.merchantPassword = merchantPassword;
		this.processorToken = processorToken;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public String getMerchantKey() {
		return merchantKey;
	}	
	
	public String getMerchantPassword() {
		return merchantPassword;
	}
	
	public String getProcessorToken() {
		return processorToken;
	}	
	
	public Processor processor() {
		return new ProcessorImpl(this, newHttpInstance());
	}
	
	public PaymentTransaction transaction() {
		return new PaymentTransactionImpl(newHttpInstance());
	}

	private Http newHttpInstance() {
		return new Http(merchantKey, merchantPassword, "https://api.samurai.feefighters.com/v1");
	}	
	
}
