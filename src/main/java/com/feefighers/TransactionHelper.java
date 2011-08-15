package com.feefighers;

import org.apache.commons.lang.StringUtils;

import com.feefighers.model.Options;
import com.feefighers.model.Transaction;
import com.feefighers.model.Transaction.TransactionType;

public class TransactionHelper {

	public static Transaction generateTransaction(Options options) {
		Transaction transaction = new Transaction(TransactionType.Purchase);
		
		if(options != null) {
			if(options.get("amount") != null) {
				transaction.setAmount(String.valueOf(options.get("amount")));
			}
			transaction.setPaymentMethodToken(options.get("payment_method_token"));						
			
			transaction.setDescriptor(options.get("descriptor"));
			transaction.setCustom(options.get("custom"));
			transaction.setCustomerReference(options.get("customer_reference"));
			transaction.setBillingReference(options.get("billing_reference"));
		}
		
		if(StringUtils.isBlank(transaction.getCurrencyCode())) {
			transaction.setCurrencyCode("USD");
		}		
		
		return transaction;
	}
}
