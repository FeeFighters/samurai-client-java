package com.feefighters.samurai;

import java.io.Serializable;
import java.util.*;

import com.feefighters.samurai.http.HttpException;
import com.feefighters.samurai.util.PaymentMethodOptions;
import com.feefighters.samurai.util.XmlMarshaller;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("payment_method")
public class PaymentMethod implements Serializable {
	private static final long serialVersionUID = 2L;

	@XStreamAlias("payment_method_token")
	public String paymentMethodToken;
    public String token() { return paymentMethodToken; }

	@XStreamAlias("created_at")
    public Date createdAt;

	@XStreamAlias("updated_at")
    public Date updatedAt;

	@XStreamAlias("custom")
    public String custom;

	@XStreamAlias("is_retained")
    public Boolean retained;

	@XStreamAlias("is_redacted")
    public Boolean redacted;

	@XStreamAlias("is_sensitive_data_valid")
    public Boolean sensitiveDataValid;

    @XStreamAlias("is_expiration_valid")
    public Boolean expirationValid;

	@XStreamAlias("first_name")
    public String firstName;

	@XStreamAlias("last_name")
    public String lastName;

    @XStreamAlias("address_1")
    public String address1;

    @XStreamAlias("address_2")
    public String address2;

    @XStreamAlias("city")
    public String city;

    @XStreamAlias("state")
    public String state;

    @XStreamAlias("zip")
    public String zip;

    @XStreamAlias("country")
    public String country;
    
    @XStreamAlias("card_number")
    public String cardNumber;

    @XStreamAlias("cvv")
    public String cvv;

	@XStreamAlias("expiry_month")
    public Integer expiryMonth;

	@XStreamAlias("expiry_year")
    public Integer expiryYear;

    @XStreamAlias("last_four_digits")
    public String lastFourDigits;

    @XStreamAlias("card_type")
    public String cardType;

    @XStreamAlias("messages")
    public MessageList messages = new MessageList();

    @XStreamOmitField
    public HashMap<String, List<String>> errors = new HashMap<String, List<String>>();
    @XStreamOmitField
    public SamuraiGateway gateway;

	static {
		XmlMarshaller.registerModelClass(PaymentMethod.class);
	}

    public PaymentMethod() {}
    public PaymentMethod(PaymentMethodOptions options) {
        this.updateAttributes(options);
    }

    public static PaymentMethod create(PaymentMethodOptions options) {
        try {
            PaymentMethod payload = new PaymentMethod(options);
            String response = SamuraiGateway.defaultGateway().http().post(postPaymentMethodUrl(), payload.toXml());
            PaymentMethod newPM = PaymentMethod.fromXml(response);
            newPM.gateway = SamuraiGateway.defaultGateway();
            return newPM;
        } catch (HttpException ex) {
            PaymentMethod newPM = new PaymentMethod();
            newPM.messages = MessageList.fromErrorXml(ex.getMessage());
            newPM.processResponseMessages();
            return newPM;
        }
    }
    public static PaymentMethod create(Map<String, String> map) {
        return PaymentMethod.create(PaymentMethodOptions.fromMap(map));
    }

    public static PaymentMethod find(String token) throws HttpException {
        String response = SamuraiGateway.defaultGateway().http().get(getPaymentMethodUrl(token));
        PaymentMethod newPM = PaymentMethod.fromXml(response);
        newPM.gateway = SamuraiGateway.defaultGateway();
        return newPM;
    }

	public static PaymentMethod fromXml(String xml) {
		PaymentMethod pm = new PaymentMethod();
        pm.loadFromXml(xml);
        return pm;
	}
    public void loadFromXml(String xml) {
        XmlMarshaller.fromXml(xml, this);
        processResponseMessages();
    }
    public String toXml() {
        return XmlMarshaller.toXml(this);
    }

	public static PaymentMethod fromMap(Map<String, String> map) {
		PaymentMethod ret = new PaymentMethod();
        ret.updateAttributes(map);
		return ret;
	}

    public void updateAttributes(PaymentMethodOptions options) {
        if (options.paymentMethodToken != null) { paymentMethodToken = options.paymentMethodToken; }
        if (options.firstName != null) { firstName = options.firstName; }
        if (options.lastName != null) { lastName = options.lastName; }
        if (options.address1 != null) { address1 = options.address1; }
        if (options.address2 != null) { address2 = options.address2; }
        if (options.city != null) { city = options.city; }
        if (options.state != null) { state = options.state; }
        if (options.zip != null) { zip = options.zip; }
        if (options.country != null) { country = options.country; }
        if (options.cardNumber != null) { cardNumber = options.cardNumber; }
        if (options.cvv != null) { cvv = options.cvv; }
        if (options.expiryMonth != null) { expiryMonth = options.expiryMonth; }
        if (options.expiryYear != null) { expiryYear = options.expiryYear; }
    }
    public void updateAttributes(Map<String, String> map) {
        updateAttributes(PaymentMethodOptions.fromMap(map));
    }

    public boolean save() {
        try {
            String response = gateway.http().put(this.putPaymentMethodUrl(), this.toXml());
            this.loadFromXml(response);
        } catch (HttpException ex) {
            this.messages = MessageList.fromErrorXml(ex.getMessage());
            processResponseMessages();
        }

        return this.errors.isEmpty();
    }

    public boolean redact() {
        String response = gateway.http().post(this.redactPaymentMethodUrl(), "");
        this.loadFromXml(response);
        return this.errors.isEmpty();
    }
    public boolean retain() {
        String response = gateway.http().post(this.retainPaymentMethodUrl(), "");
        this.loadFromXml(response);
        return this.errors.isEmpty();
    }

    private void processResponseMessages() {
        if (errors == null) { errors = new HashMap<String, List<String>>(); }
        Collections.sort(messages.getList(), new MessageComparator());
        for (Message msg : messages.getList()) {
            if (errors.get(msg.context)==null) { errors.put(msg.context, new ArrayList<String>()); }
            if (errors.get(msg.context).isEmpty()) {
                errors.get(msg.context).add(msg.description);
            }
        }
    }

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("address1", address1)
			.append("address2", address2)
			.append("cardType", cardType)
			.append("city", city)
			.append("country", country)
			.append("createdAt", createdAt)
			.append("custom", custom)
			.append("expiryMonth", expiryMonth)
			.append("expiryYear", expiryYear)
			.append("firstName", firstName)
			.append("lastFourDigits", lastFourDigits)
			.append("lastName", lastName)
			.append("messages", messages)
			.append("paymentMethodToken", paymentMethodToken)
			.append("redacted", redacted)
			.append("retained", retained)
			.append("sensitiveDataValid", sensitiveDataValid)
			.append("state", state)
			.append("updatedAt", updatedAt)
			.append("zip", zip)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(address1)
			.append(address2)
			.append(cardType)
			.append(city)
			.append(country)
			.append(createdAt)
			.append(custom)
			.append(expiryMonth)
			.append(expiryYear)
			.append(firstName)
			.append(lastFourDigits)
			.append(lastName)
			.append(paymentMethodToken)
			.append(redacted)
			.append(retained)
			.append(sensitiveDataValid)
			.append(state)
			.append(updatedAt)
			.append(zip)
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
		final PaymentMethod other = (PaymentMethod) obj;
		return new EqualsBuilder()
			.append(address1, other.address1)
			.append(address2, other.address2)
			.append(cardType, other.cardType)
			.append(city, other.city)
			.append(country, other.country)
			.append(createdAt, other.createdAt)
			.append(custom, other.custom)
			.append(expiryMonth, other.expiryMonth)
			.append(expiryYear, other.expiryYear)
			.append(firstName, other.firstName)
			.append(lastFourDigits, other.lastFourDigits)
			.append(lastName, other.lastName)
			.append(paymentMethodToken, other.paymentMethodToken)
			.append(redacted, other.redacted)
			.append(retained, other.retained)
			.append(sensitiveDataValid, other.sensitiveDataValid)
			.append(state, other.state)
			.append(updatedAt, other.updatedAt)
			.append(zip, other.zip)
			.isEquals();
	}

    protected static String postPaymentMethodUrl() { return "/payment_methods.xml"; }
    protected static String getPaymentMethodUrl(String token) { return "/payment_methods/"+token+".xml"; }
    protected String putPaymentMethodUrl() { return "/payment_methods/"+this.paymentMethodToken+".xml"; }
    protected String redactPaymentMethodUrl() { return "/payment_methods/"+this.paymentMethodToken+"/redact.xml"; }
    protected String retainPaymentMethodUrl() { return "/payment_methods/"+this.paymentMethodToken+"/retain.xml"; }
    

}

