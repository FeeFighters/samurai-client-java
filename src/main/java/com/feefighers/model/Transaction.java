package com.feefighers.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("transaction")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum TransactionRequestType {
		purchase, authorize
	}
	
	public enum TransactionType {
		Purchase, Authorize, Void, Capture, Credit
	}
	
	@XStreamAlias("reference_id") 
	private String referenceId; // response
	
	@XStreamAlias("transaction_token")
	private String transactionToken; // response
	
	@XStreamAlias("created_at")
	private Date createdAt; // response	

	@XStreamAlias("descriptor")
	private String descriptor; // request
	
	@XStreamAlias("custom")
	private String custom; // request	

	@XStreamAlias("billing_reference")
	private String billingReference; // request
	
	@XStreamAlias("customer_reference")
	private String customerReference; // request

	@XStreamAlias("type")
	private TransactionRequestType requestType; // request
	
	@XStreamAlias("transaction_type")
	private TransactionType responseType; // response	
		
	@XStreamAlias("amount")
	private String amount; // request

	@XStreamAlias("currency_code")
	private String currencyCode; // request		
	
	@XStreamAlias("processor_token")
	private String processorToken; // response
		
	@XStreamAlias("processor_response")
	private ProcessorResponse processorResponse; // response

	@XStreamAlias("payment_method")
	private PaymentMethod paymentMethod; // response
	
	@XStreamAlias("payment_method_token")
	private String paymentMethodToken; // request	
	
	static {
		XmlMarshaller.registerModelClass(Transaction.class);
	}
	
	public String toXml() {
		return XmlMarshaller.toXml(this);
	}
	
	public static Transaction fromXml(String xml) {
		return (Transaction) XmlMarshaller.fromXml(xml);
	}

	public String getId() {
		return getTransactionToken();
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public TransactionRequestType getRequestType() {
		return requestType;
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

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getTransactionToken() {
		return transactionToken;
	}

	public void setTransactionToken(String transactionToken) {
		this.transactionToken = transactionToken;
	}

	public Date getCreatedAt() {
		return createdAt != null ? new Date(createdAt.getTime()) : null;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt != null ? new Date(createdAt.getTime()) : null;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setRequestType(TransactionRequestType type) {
		this.requestType = type;
	}

	public ProcessorResponse getProcessorResponse() {
		return processorResponse;
	}
	
	public String getProcessorToken() {
		return processorToken;
	}
	
	public void setProcessorToken(String processorToken) {
		this.processorToken = processorToken;
	}
	
	public TransactionType getTransactionType() {
		return responseType;
	}
	
	public void setResponseType(TransactionType responseType) {
		this.responseType = responseType;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("amount", amount)
			.append("billingReference", billingReference)
			.append("createdAt", createdAt)
			.append("currencyCode", currencyCode)
			.append("custom", custom)
			.append("customerReference", customerReference)
			.append("descriptor", descriptor)
			.append("paymentMethod", paymentMethod)			
			.append("paymentMethodToken", paymentMethodToken)
			.append("processorResponse", processorResponse)
			.append("processorToken", processorToken)
			.append("referenceId", referenceId)
			.append("requestType", requestType)
			.append("responseType", responseType)
			.append("transactionToken", transactionToken)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(amount)
			.append(billingReference)
			.append(createdAt)
			.append(currencyCode)
			.append(custom)
			.append(customerReference)
			.append(descriptor)
			.append(paymentMethod)
			.append(paymentMethodToken)
			.append(processorResponse)
			.append(processorToken)
			.append(referenceId)
			.append(requestType)
			.append(responseType)
			.append(transactionToken)
			.toHashCode();	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Transaction other = (Transaction) obj;
		return new EqualsBuilder()
			.append(amount, other.amount)
			.append(billingReference, other.billingReference)
			.append(createdAt, other.createdAt)
			.append(currencyCode, other.currencyCode)
			.append(custom, other.custom)
			.append(customerReference, other.customerReference)
			.append(descriptor, other.descriptor)
			.append(paymentMethod, other.paymentMethod)
			.append(paymentMethodToken, other.paymentMethodToken)
			.append(processorResponse, other.processorResponse)
			.append(processorToken, other.processorToken)
			.append(referenceId, other.referenceId)
			.append(requestType, other.requestType)
			.append(responseType, other.responseType)
			.append(transactionToken, other.transactionToken)
			.isEquals();		
	}
}
