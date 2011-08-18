package com.feefighers;

import java.util.Map;

import com.feefighers.model.TransactionOptions;
import com.feefighers.model.PaymentMethod;
import com.feefighers.model.Transaction;

public interface Processor {
	
	PaymentMethod find(String paymentMethodToken);
	
	PaymentMethod load(Map<String, String> values);
	
	PaymentMethod save(PaymentMethod paymentMethod);
	
	PaymentMethod retain(PaymentMethod paymentMethod);
	
	PaymentMethod redact(PaymentMethod paymentMethod);
			
	Transaction purchase(String paymentMethodToken, double amount, TransactionOptions options);
	
	Transaction authorize(String paymentMethodToken, double amount, TransactionOptions options);
}
