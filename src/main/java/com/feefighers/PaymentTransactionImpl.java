package com.feefighers;

import com.feefighers.http.Http;
import com.feefighers.model.Options;
import com.feefighers.model.Transaction;

public class PaymentTransactionImpl implements PaymentTransaction {

	private Http http;
	private SamuraiGateway gateway;

	public PaymentTransactionImpl(SamuraiGateway gateway, Http http) {
		this.http = http;
		this.gateway = gateway;
	}
	
	@Override
	public Transaction find(String transactionReferenceId) {
		String xml = http.get("/transactions/" + transactionReferenceId + ".xml");
		return Transaction.fromXml(xml);
	}	

	@Override
	public Transaction capture(Transaction transaction, Double amount, Options options) {
		return execute("capture", transaction, amount, options);
	}

	@Override
	public Transaction voidOperation(Transaction transaction, Options options) {
		return execute("void", transaction, null, options);
	}

	@Override
	public Transaction credit(Transaction transaction, Double amount, Options options) {
		return execute("credit", transaction, amount, options);
	}

	protected Transaction execute(String action, Transaction transaction, Double amount,
			Options options) {
		Transaction transactionRequest = TransactionHelper.generateTransactionAndSetOptions(options, false);
		if(amount != null) {
			transactionRequest.setAmount(String.valueOf(amount));
		} else {
			transactionRequest.setAmount(transaction.getAmount());
		}
		
		final String url = "transactions/" +  transaction.getId() + "/" +
				action + ".xml"; 
		String xml = http.post(url, transactionRequest.toXml());
		
		Transaction transactionResponse = Transaction.fromXml(xml);
		return transactionResponse;
	}
}
