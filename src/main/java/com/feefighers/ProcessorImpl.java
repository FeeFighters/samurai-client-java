package com.feefighers;

import java.util.Map;

import com.feefighers.http.Http;
import com.feefighers.model.TransactionOptions;
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
		String xml = http.get(getPaymentMethodUrl(paymentMethodToken));
		return PaymentMethod.fromXml(xml);
	}
	
	@Override
	public PaymentMethod load(Map<String, String> values) {
		return PaymentMethod.fromValueMap(values);
	}		
	
	@Override
	public PaymentMethod save(PaymentMethod paymentMethod) {
		http.put(getPaymentMethodUrl(paymentMethod.getId()), paymentMethod.toXml());
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
			TransactionOptions options) {
		return executeTransaction(TransactionRequestType.purchase, paymentMethodToken, amount, options);
	}

	@Override
	public Transaction authorize(String paymentMethodToken, double amount,
			TransactionOptions options) {
		return executeTransaction(TransactionRequestType.authorize, paymentMethodToken, amount, options);
	}
	
	protected Transaction executeTransaction(TransactionRequestType type, String paymentMethodToken, double amount,
			TransactionOptions options) {
		final Transaction transaction = TransactionHelper.generateTransactionAndSetOptions(options, true);
		transaction.setPaymentMethodToken(paymentMethodToken);
		transaction.setAmount(String.valueOf(amount));
		
		final String url = "processors/" + gateway.getProcessorToken() + "/" +
				type.name().toLowerCase() + ".xml"; 
		final String xml = http.post(url, transaction.toXml());
		
		return Transaction.fromXml(xml);
	}
	
	protected PaymentMethod executePaymentMethod(String action, PaymentMethod paymentMethod) {		
		final String url = getPaymentMethodUrl(paymentMethod.getId(), action);
		final String requestXml = paymentMethod.toXml();
		
		final String responseXml = http.post(url, requestXml);
		
		return PaymentMethod.fromXml(responseXml);
	}

	protected String getPaymentMethodUrl(String id) {
		return "/payment_methods/" + id + ".xml";
	}
	
	protected String getPaymentMethodUrl(String id, String action) {
		return "payment_methods/" + id + "/" + action + ".xml";
	}
}
