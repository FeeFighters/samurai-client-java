package com.feefighers;

import org.apache.commons.lang.StringUtils;

import com.feefighers.http.Http;
import com.feefighers.model.Options;
import com.feefighers.model.PaymentMethod;
import com.feefighers.model.Transaction;
import com.feefighers.model.Transaction.TransactionType;

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
	public String purchase(String paymentMethodToken, double amount,
			Options options) {
		return execute(TransactionType.Purchase, paymentMethodToken, amount, options);
	}

	@Override
	public String authorize(String paymentMethodToken, double amount,
			Options options) {
		return execute(TransactionType.authorize, paymentMethodToken, amount, options);
	}
	
	protected String execute(TransactionType type, String paymentMethodToken, double amount,
			Options options) {
		Transaction transaction = new Transaction(TransactionType.Purchase);		
		transaction.setAmount(String.valueOf(amount));
		transaction.setPaymentMethodToken(paymentMethodToken);
		
		if(options != null) {
			transaction.setDescriptor(options.get("descriptor"));
			transaction.setCustom(options.get("custom"));
			transaction.setCustomerReference(options.get("customer_reference"));
			transaction.setBillingReference(options.get("billing_reference"));
		}
		
		if(StringUtils.isBlank(transaction.getCurrencyCode())) {
			transaction.setCurrencyCode("USD");
		}
		
		return http.post("processors/" + gateway.getProcessorToken() + "/purchase.xml", transaction.toXml());		
	}

}
