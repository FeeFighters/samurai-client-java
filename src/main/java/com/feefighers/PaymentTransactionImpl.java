package com.feefighers;

import com.feefighers.http.Http;
import com.feefighers.model.Options;
import com.feefighers.model.Transaction;
import com.feefighers.model.Transaction.TransactionType;

public class PaymentTransactionImpl implements PaymentTransaction {

	private Http http;
	private SamuraiGateway gateway;

	public PaymentTransactionImpl(SamuraiGateway gateway, Http http) {
		this.http = http;
		this.gateway = gateway;
	}

	@Override
	public Transaction capture(String transactionToken, double amount, Options options) {
		return execute("capture", transactionToken, amount, options);
	}

	@Override
	public Transaction voidOperation(String transactionToken, Options options) {
		return execute("void", transactionToken, null, options);
	}

	@Override
	public Transaction credit(String transactionToken, double amount, Options options) {
		return execute("credit", transactionToken, amount, options);
	}

	protected Transaction execute(String action, String transactionToken, Double amount,
			Options options) {
		Transaction transaction = TransactionHelper.generateTransaction(options);
		if(amount != null) {
			transaction.setAmount(String.valueOf(amount));
		}
		transaction.setTransactionToken(transactionToken);
		
		final String url = "transactions/" +  transactionToken + "/" +
				action + ".xml"; 
		String xml = http.post(url, transaction.toXml());
		
		Transaction ret = Transaction.fromXml(xml);
		return ret;
	}
}
