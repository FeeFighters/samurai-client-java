package com.feefighers;

import com.feefighers.http.Http;
import com.feefighers.model.Options;
import com.feefighers.model.PaymentMethod;
import com.feefighers.model.Transaction;
import com.feefighers.model.Transaction.TransactionRequestType;

public class ProcessorImpl implements Processor {

	private Http http;
	private SamuraiGateway gateway;

	public ProcessorImpl(SamuraiGateway gateway, Http http) {
		this.http = http;
		this.gateway = gateway;
	}
	
	@Override
	public PaymentMethod find(String paymentMethodToken) {
		String xml = http.get("/payment_methods/" + paymentMethodToken + ".xml");
		return PaymentMethod.fromXml(xml);
	}
	
	@Override
	public boolean save(PaymentMethod paymentMethod) {
		http.put("/payment_methods/" + paymentMethod.getPaymentMethodToken() + ".xml", paymentMethod.toXml());
		return true;
	}	

	@Override
	public Transaction purchase(String paymentMethodToken, double amount,
			Options options) {
		return execute(TransactionRequestType.purchase, paymentMethodToken, amount, options);
	}

	@Override
	public Transaction authorize(String paymentMethodToken, double amount,
			Options options) {
		return execute(TransactionRequestType.authorize, paymentMethodToken, amount, options);
	}
	
	protected Transaction execute(TransactionRequestType type, String paymentMethodToken, double amount,
			Options options) {
		Transaction transaction = TransactionHelper.generateTransaction(options);
		transaction.setPaymentMethodToken(paymentMethodToken);
		transaction.setAmount(String.valueOf(amount));
		
		final String url = "processors/" + gateway.getProcessorToken() + "/" +
				type.name().toLowerCase() + ".xml"; 
		String xml = http.post(url, transaction.toXml());
		
		Transaction ret = Transaction.fromXml(xml);
		return ret;
	}

}
