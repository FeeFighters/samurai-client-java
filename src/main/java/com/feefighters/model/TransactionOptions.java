package com.feefighters.model;

import com.feefighters.model.Transaction.TransactionRequestType;

public class TransactionOptions {

	private TransactionRequestType type;
	private Double amount;
	private String paymentMethodToken;
	private String description;
	private String descriptorName;
	private String descriptorPhone;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptorName() {
		return descriptorName;
	}
	public void setDescriptorName(String descriptorName) {
		this.descriptorName = descriptorName;
	}
	public String getDescriptorPhone() {
		return descriptorPhone;
	}
	public void setDescriptorPhone(String descriptorPhone) {
		this.descriptorPhone = descriptorPhone;
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
