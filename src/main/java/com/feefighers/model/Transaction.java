package com.feefighers.model;

import java.io.Serializable;
import java.util.Date;

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

	public Transaction(TransactionRequestType type) {
		this.requestType = type;
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

//	public TransactionRequestType getRequestType() {
//		return requestType;
//	}	

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
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
			.append("amount", this.amount)
			.append("billingReference", this.billingReference)
			.append("currencyCode", this.currencyCode)
			.append("custom", this.custom)
			.append("customerReference", this.customerReference)
			.append("descriptor", this.descriptor)
			.append("paymentMethodToken", this.paymentMethodToken)
			.append("processorToken", this.processorToken)
			.append("referenceId", this.referenceId)
			.append("transactionToken", this.transactionToken)
			.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime
				* result
				+ ((billingReference == null) ? 0 : billingReference.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result
				+ ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((custom == null) ? 0 : custom.hashCode());
		result = prime
				* result
				+ ((customerReference == null) ? 0 : customerReference
						.hashCode());
		result = prime * result
				+ ((descriptor == null) ? 0 : descriptor.hashCode());
		result = prime * result
				+ ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
		result = prime
				* result
				+ ((paymentMethodToken == null) ? 0 : paymentMethodToken
						.hashCode());
		result = prime
				* result
				+ ((processorResponse == null) ? 0 : processorResponse
						.hashCode());
		result = prime * result
				+ ((processorToken == null) ? 0 : processorToken.hashCode());
		result = prime * result
				+ ((referenceId == null) ? 0 : referenceId.hashCode());
		result = prime * result
				+ ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result
				+ ((responseType == null) ? 0 : responseType.hashCode());
		result = prime
				* result
				+ ((transactionToken == null) ? 0 : transactionToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (billingReference == null) {
			if (other.billingReference != null)
				return false;
		} else if (!billingReference.equals(other.billingReference))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (custom == null) {
			if (other.custom != null)
				return false;
		} else if (!custom.equals(other.custom))
			return false;
		if (customerReference == null) {
			if (other.customerReference != null)
				return false;
		} else if (!customerReference.equals(other.customerReference))
			return false;
		if (descriptor == null) {
			if (other.descriptor != null)
				return false;
		} else if (!descriptor.equals(other.descriptor))
			return false;
		if (paymentMethod == null) {
			if (other.paymentMethod != null)
				return false;
		} else if (!paymentMethod.equals(other.paymentMethod))
			return false;
		if (paymentMethodToken == null) {
			if (other.paymentMethodToken != null)
				return false;
		} else if (!paymentMethodToken.equals(other.paymentMethodToken))
			return false;
		if (processorResponse == null) {
			if (other.processorResponse != null)
				return false;
		} else if (!processorResponse.equals(other.processorResponse))
			return false;
		if (processorToken == null) {
			if (other.processorToken != null)
				return false;
		} else if (!processorToken.equals(other.processorToken))
			return false;
		if (referenceId == null) {
			if (other.referenceId != null)
				return false;
		} else if (!referenceId.equals(other.referenceId))
			return false;
		if (requestType != other.requestType)
			return false;
		if (responseType != other.responseType)
			return false;
		if (transactionToken == null) {
			if (other.transactionToken != null)
				return false;
		} else if (!transactionToken.equals(other.transactionToken))
			return false;
		return true;
	}
}
