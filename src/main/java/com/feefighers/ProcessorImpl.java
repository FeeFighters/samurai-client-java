package com.feefighers;

import com.feefighers.http.Http;
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

}
