package com.feefighters;

import java.util.Map;

import com.feefighters.model.PaymentMethod;
import com.feefighters.model.Transaction;
import com.feefighters.model.TransactionOptions;

public interface Processor {
	
	PaymentMethod find(String paymentMethodToken);
	
	PaymentMethod load(Map<String, String> values);
	
	PaymentMethod save(PaymentMethod paymentMethod);
	
	PaymentMethod retain(PaymentMethod paymentMethod);
	
	PaymentMethod redact(PaymentMethod paymentMethod);
			
	Transaction purchase(String paymentMethodToken, double amount, TransactionOptions options);
	
	Transaction authorize(String paymentMethodToken, double amount, TransactionOptions options);
}
