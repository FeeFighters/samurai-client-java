package com.feefighers;

import com.feefighers.http.Http;
import com.feefighers.model.Options;
import com.feefighers.model.PaymentMethod;

public class ProcessorImpl implements Processor {

	private Http http;

	public ProcessorImpl(Http http) {
		this.http = http;
	}
	
	@Override
	public PaymentMethod find(String paymentMethodToken) {
		String xml = http.get("/payment_methods/" + paymentMethodToken + ".xml");
		return PaymentMethod.fromXml(xml);
	}

	@Override
	public void purchase(String paymentMethodToken, double amount,
			Options options) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void authorize(String paymentMethodToken, double amount,
			Options options) {
		// TODO Auto-generated method stub
		
	}

}
