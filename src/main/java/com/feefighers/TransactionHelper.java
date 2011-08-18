package com.feefighers;

import org.apache.commons.lang.StringUtils;

import com.feefighers.model.TransactionOptions;
import com.feefighers.model.Transaction;

public final class TransactionHelper {

	private TransactionHelper() {
		
	}
	
	public static Transaction generateTransactionAndSetOptions(TransactionOptions options, boolean defaultCurrency) {
		Transaction transaction = new Transaction();
		
		if(options != null) {
			transaction.setRequestType(options.getType());
			transaction.setAmount(String.valueOf(options.getAmount()));
			transaction.setPaymentMethodToken(options.getPaymentMethodToken());						
			
			transaction.setDescriptor(options.getDescriptor());
			transaction.setCustom(options.getCustom());
			transaction.setCustomerReference(options.getCustomerReference());
			transaction.setBillingReference(options.getBillingReference());
			transaction.setCurrencyCode(options.getCurrencyCode());
		}
		
		if(defaultCurrency && StringUtils.isBlank(transaction.getCurrencyCode())) {
			transaction.setCurrencyCode("USD");
		}		
		
		return transaction;
	}
}
