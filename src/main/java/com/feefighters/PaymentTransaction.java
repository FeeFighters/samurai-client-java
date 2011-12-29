package com.feefighters;

import com.feefighters.model.Transaction;
import com.feefighters.model.TransactionOptions;

public interface PaymentTransaction {

	Transaction find(String transactionReferenceId);
	
	Transaction capture(Transaction transaction, Double amount, TransactionOptions options);
	
	Transaction voidOperation(Transaction transaction, TransactionOptions options);
	
	Transaction credit(Transaction transaction, Double amount, TransactionOptions options);
	
	Transaction reverse(Transaction transaction, Double amount, TransactionOptions options);
}
