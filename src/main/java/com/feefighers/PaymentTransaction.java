package com.feefighers;

import com.feefighers.model.Options;
import com.feefighers.model.Transaction;

public interface PaymentTransaction {

	Transaction capture(String transactionToken, double amount, Options options);
	
	Transaction voidOperation(String transactionToken, Options options);
	
	Transaction credit(String transactionToken, double amount, Options options);
	
}
