package com.feefighers;

import com.feefighers.model.TransactionOptions;
import com.feefighers.model.Transaction;

public interface PaymentTransaction {

	Transaction find(String transactionReferenceId);
	
	Transaction capture(Transaction transaction, Double amount, TransactionOptions options);
	
	Transaction voidOperation(Transaction transaction, TransactionOptions options);
	
	Transaction credit(Transaction transaction, Double amount, TransactionOptions options);
	
}
