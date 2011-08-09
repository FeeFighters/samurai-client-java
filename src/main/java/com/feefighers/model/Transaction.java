package com.feefighers.model;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamer;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String amount;
	private String type;
	private String paymentMethodToken;
	private String currencyCode;
	private String descriptor;
	private String custom;
	private String customerReference;
	private String billingReference;
	
	private static XStream xstream = new XStream(new StaxDriver()); 
	
	static {
		xstream.alias("transaction", Transaction.class);
	}
	
	public String toXml() {
		return xstream.toXML(this);
	}
	
	public static Transaction fromXml(String xml) {
		return (Transaction) xstream.fromXML(xml);
	}

	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentMethodToken() {
		return paymentMethodToken;
	}

	public void setPaymentMethodToken(String paymentMethodToken) {
		this.paymentMethodToken = paymentMethodToken;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	
}
