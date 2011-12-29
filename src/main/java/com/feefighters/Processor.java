package com.feefighters;

import com.feefighters.util.TransactionOptions;

import java.util.Map;

public interface Processor {
	
	PaymentMethod find(String paymentMethodToken);
	
	PaymentMethod load(Map<String, String> values);
	
	PaymentMethod save(PaymentMethod paymentMethod);
	
	PaymentMethod retain(PaymentMethod paymentMethod);
	
	PaymentMethod redact(PaymentMethod paymentMethod);
			
	Transaction purchase(String paymentMethodToken, double amount, TransactionOptions options);
	
	Transaction authorize(String paymentMethodToken, double amount, TransactionOptions options);
}
