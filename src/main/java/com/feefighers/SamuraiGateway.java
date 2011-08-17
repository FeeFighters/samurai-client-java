package com.feefighers;

import java.io.Serializable;

import com.feefighers.http.Http;

public class SamuraiGateway implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String merchantKey;
	private String merchantPassword;
	private String processorToken;
		
	public SamuraiGateway(String merchantKey, String merchantPassword,
			String processorToken) {
		super();
		this.merchantKey = merchantKey;
		this.merchantPassword = merchantPassword;
		this.processorToken = processorToken;
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
		return new ProcessorImpl(this, new Http(merchantKey, merchantPassword, "https://samurai.feefighters.com/v1"));
	}
	
	public PaymentTransaction transaction() {
		return new PaymentTransactionImpl(this, new Http(merchantKey, merchantPassword, "https://samurai.feefighters.com/v1"));
	}	
	
}
