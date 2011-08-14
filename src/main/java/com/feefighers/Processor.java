package com.feefighers;

import com.feefighers.model.PaymentMethod;
import com.feefighers.model.Options;

public interface Processor {
	
	PaymentMethod find(String paymentMethodToken);
			
	void purchase(String paymentMethodToken, double amount, Options options);
	
	void authorize(String paymentMethodToken, double amount, Options options);
}
