package com.feefighers;

import com.feefighers.model.Options;
import com.feefighers.model.Transaction;

public interface PaymentTransaction {

	Transaction find(String transactionReferenceId);
	
	Transaction capture(Transaction transaction, Double amount, Options options);
	
	Transaction voidOperation(Transaction transaction, Options options);
	
	Transaction credit(Transaction transaction, Double amount, Options options);
	
}
