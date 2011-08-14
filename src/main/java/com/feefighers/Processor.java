package com.feefighers;

import com.feefighers.model.PaymentMethod;
import com.feefighers.model.Options;

public interface Processor {
	
	PaymentMethod find(String paymentMethodToken);
			
	String purchase(String paymentMethodToken, double amount, Options options);
	
	String authorize(String paymentMethodToken, double amount, Options options);
}
