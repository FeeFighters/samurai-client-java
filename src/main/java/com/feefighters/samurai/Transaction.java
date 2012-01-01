package com.feefighters.samurai;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import com.feefighters.samurai.http.HttpException;
import com.feefighters.samurai.util.TransactionOptions;
import com.feefighters.samurai.util.XmlMarshaller;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("transaction")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 2L;
	
	public enum TransactionType {
		Purchase, Authorize, Void, Capture, Credit, Reverse
	}
	
	@XStreamAlias("reference_id") 
	public String referenceId; // response
	
	@XStreamAlias("transaction_token")
	public String transactionToken; // response
	
	@XStreamAlias("created_at")
	public Date createdAt; // response

	@XStreamAlias("description")
	public String description; // request

    @XStreamAlias("descriptor_name")
    public String descriptorName; // request

    @XStreamAlias("descriptor_phone")
    public String descriptorPhone; // request
	
	@XStreamAlias("custom")
	public String custom; // request

	@XStreamAlias("billing_reference")
	public String billingReference; // request
	
	@XStreamAlias("customer_reference")
	public String customerReference; // request

	@XStreamAlias("transaction_type")
	public TransactionType type; // response
		
	@XStreamAlias("amount")
	public String amount; // request

	@XStreamAlias("currency_code")
	public String currencyCode; // request
	
	@XStreamAlias("processor_token")
	public String processorToken; // response
		
	@XStreamAlias("processor_response")
	public ProcessorResponse processorResponse; // response

	@XStreamAlias("payment_method")
	public PaymentMethod paymentMethod; // response
	
	@XStreamAlias("payment_method_token")
	public String paymentMethodToken; // request

    @XStreamAlias("success")
    public Boolean success; // response

    @XStreamOmitField
    public HashMap<String, List<String>> errors = new HashMap<String, List<String>>();
    @XStreamOmitField
    public SamuraiGateway gateway;

	static {
		XmlMarshaller.registerModelClass(Transaction.class);
	}

    public Transaction() {
        currencyCode = "USD";
    }
    public Transaction(TransactionOptions options) {
        this();
        this.updateAttributes(options);
    }

    public static Transaction find(String referenceId) throws HttpException {
        String response = SamuraiGateway.defaultGateway().http().get(getTransactionUrl(referenceId));
        Transaction newTx = Transaction.fromXml(response);
        newTx.gateway = SamuraiGateway.defaultGateway();
        return newTx;
    }
    
	public String toXml() {
		return XmlMarshaller.toXml(this);
	}
	public static Transaction fromXml(String xml) {
        Transaction t = new Transaction();
        t.loadFromXml(xml);
        return t;
	}
    public void loadFromXml(String xml) {
        XmlMarshaller.fromXml(xml, this);
        processResponseMessages();
    }

    public void updateAttributes(TransactionOptions options) {
        if (options.paymentMethodToken != null) { paymentMethodToken = options.paymentMethodToken; }
        if (options.description != null) { description = options.description; }
        if (options.descriptorName != null) { descriptorName = options.descriptorName; }
        if (options.descriptorPhone != null) { descriptorPhone = options.descriptorPhone; }
        if (options.currencyCode != null) { currencyCode = options.currencyCode; }
        if (options.customerReference != null) { customerReference = options.customerReference; }
        if (options.billingReference != null) { billingReference = options.billingReference; }
        if (options.custom != null) { custom = options.custom; }
    }
    public void updateAttributes(Map<String, String> map) {
        updateAttributes(TransactionOptions.fromMap(map));
    }

    private void processResponseMessages() {
        if (errors == null) { errors = new HashMap<String, List<String>>(); }
        if (processorResponse != null) {
            Collections.sort(processorResponse.messages.getList(), new MessageComparator());
            for (Message msg : processorResponse.messages.getList()) {
                if (errors.get(msg.context)==null) { errors.put(msg.context, new ArrayList<String>()); }
                if (errors.get(msg.context).isEmpty()) {
                    errors.get(msg.context).add(msg.description);
                }
            }
        }
    }

    public Transaction capture() {
        return capture(new BigDecimal(this.amount));
    }
    public Transaction capture(String amount) {
        return capture(new BigDecimal(amount));
    }
    public Transaction capture(Double amount) {
        return capture(new BigDecimal(amount));
    }
    public Transaction capture(BigDecimal amount) {
        return execute(TransactionType.Capture, amount);
    }

    public Transaction voidTransaction() {
        return execute(TransactionType.Void, null);
    }

    public Transaction credit() {
        return capture(new BigDecimal(this.amount));
    }
    public Transaction credit(String amount) {
        return capture(new BigDecimal(amount));
    }
    public Transaction credit(Double amount) {
        return capture(new BigDecimal(amount));
    }
    public Transaction credit(BigDecimal amount) {
        return execute(TransactionType.Credit, amount);
    }

    public Transaction reverse() {
        return capture(new BigDecimal(this.amount));
    }
    public Transaction reverse(String amount) {
        return capture(new BigDecimal(amount));
    }
    public Transaction reverse(Double amount) {
        return capture(new BigDecimal(amount));
    }
    public Transaction reverse(BigDecimal amount) {
        return execute(TransactionType.Reverse, amount);
    }

    protected Transaction execute(TransactionType type, BigDecimal amount) {
        String payload = "";
        if (type == TransactionType.Capture || type == TransactionType.Credit || type == TransactionType.Reverse) {
            payload = "<amount>"+amount.toString()+"</amount>";
        }
        String response = gateway.http().post(this.transactionUrl(type), payload);
        return Transaction.fromXml(response);
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
			.append("description", description)
			.append("descriptorName", descriptorName)
			.append("descriptorPhone", descriptorPhone)
			.append("paymentMethod", paymentMethod)			
			.append("paymentMethodToken", paymentMethodToken)
			.append("processorResponse", processorResponse)
			.append("processorToken", processorToken)
			.append("referenceId", referenceId)
			.append("responseType", type)
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
			.append(description)
			.append(descriptorName)
			.append(descriptorPhone)
			.append(paymentMethod)
			.append(paymentMethodToken)
			.append(processorResponse)
			.append(processorToken)
			.append(referenceId)
			.append(type)
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
			.append(description, other.description)
			.append(descriptorName, other.descriptorName)
			.append(descriptorPhone, other.descriptorPhone)
			.append(paymentMethod, other.paymentMethod)
			.append(paymentMethodToken, other.paymentMethodToken)
			.append(processorResponse, other.processorResponse)
			.append(processorToken, other.processorToken)
			.append(referenceId, other.referenceId)
			.append(type, other.type)
			.append(transactionToken, other.transactionToken)
			.isEquals();		
	}

    protected String transactionUrl(TransactionType type) { return "/transactions/"+this.transactionToken+"/"+type.name().toLowerCase()+".xml"; }
    protected static String getTransactionUrl(String id) { return "/transactions/"+id+".xml"; }
}
