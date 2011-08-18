package com.feefighers;

import com.feefighers.http.Http;
import com.feefighers.model.TransactionOptions;
import com.feefighers.model.Transaction;

public class PaymentTransactionImpl implements PaymentTransaction {

	private Http http;

	public PaymentTransactionImpl(Http http) {
		this.http = http;
	}
	
	@Override
	public Transaction find(String transactionReferenceId) {
		final String xml = http.get("/transactions/" + transactionReferenceId + ".xml");
		return Transaction.fromXml(xml);
	}	

	@Override
	public Transaction capture(Transaction transaction, Double amount, TransactionOptions options) {
		return execute("capture", transaction, amount, options);
	}

	@Override
	public Transaction voidOperation(Transaction transaction, TransactionOptions options) {
		return execute("void", transaction, null, options);
	}

	@Override
	public Transaction credit(Transaction transaction, Double amount, TransactionOptions options) {
		return execute("credit", transaction, amount, options);
	}

	protected Transaction execute(String action, Transaction transaction, Double amount,
			TransactionOptions options) {
		final Transaction transactionRequest = TransactionHelper.generateTransactionAndSetOptions(options, false);
		if(amount != null) {
			transactionRequest.setAmount(String.valueOf(amount));
		} else {
			transactionRequest.setAmount(transaction.getAmount());
		}
		
		final String url = "transactions/" +  transaction.getId() + "/" +
				action + ".xml"; 
		final String xml = http.post(url, transactionRequest.toXml());
		
		return Transaction.fromXml(xml);
	}
}
