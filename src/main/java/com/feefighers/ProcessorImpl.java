package com.feefighers;

import java.util.Map;

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
	public PaymentMethod load(Map<String, String> values) {
		
		return null;
	}		
	
	@Override
	public PaymentMethod save(PaymentMethod paymentMethod) {
		http.put("/payment_methods/" + paymentMethod.getPaymentMethodToken() + ".xml", paymentMethod.toXml());
		return paymentMethod;
	}	

	@Override
	public PaymentMethod retain(PaymentMethod paymentMethod) {
		return executePaymentMethod("retain", paymentMethod);
	}

	@Override
	public PaymentMethod redact(PaymentMethod paymentMethod) {
		return executePaymentMethod("redact", paymentMethod);
	}	
	
	@Override
	public Transaction purchase(String paymentMethodToken, double amount,
			Options options) {
		return executeTransaction(TransactionRequestType.purchase, paymentMethodToken, amount, options);
	}

	@Override
	public Transaction authorize(String paymentMethodToken, double amount,
			Options options) {
		return executeTransaction(TransactionRequestType.authorize, paymentMethodToken, amount, options);
	}
	
	protected Transaction executeTransaction(TransactionRequestType type, String paymentMethodToken, double amount,
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
	
	protected PaymentMethod executePaymentMethod(String action, PaymentMethod paymentMethod) {		
		final String url = "payment_methods/" + paymentMethod.getId() + "/" + action + ".xml";
		final String requestXml = paymentMethod.toXml();
		
		final String responseXml = http.post(url, requestXml);
		
		final PaymentMethod ret = PaymentMethod.fromXml(responseXml);
		return ret;
	}

}
