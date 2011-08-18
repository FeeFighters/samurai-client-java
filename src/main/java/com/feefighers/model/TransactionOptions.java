package com.feefighers.model;

import com.feefighers.model.Transaction.TransactionRequestType;

public class TransactionOptions {

	private TransactionRequestType type;
	private Double amount;
	private String paymentMethodToken;
	private String descriptor;
	private String custom;
	private String customerReference;
	private String billingReference;
	private String currencyCode;
	
	public TransactionRequestType getType() {
		return type;
	}
	public void setType(TransactionRequestType type) {
		this.type = type;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPaymentMethodToken() {
		return paymentMethodToken;
	}
	public void setPaymentMethodToken(String paymentMethodToken) {
		this.paymentMethodToken = paymentMethodToken;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}
	public String getBillingReference() {
		return billingReference;
	}
	public void setBillingReference(String billingReference) {
		this.billingReference = billingReference;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	
}
