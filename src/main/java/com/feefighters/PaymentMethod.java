package com.feefighters;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.feefighters.util.XmlMarshaller;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("payment_method")
public class PaymentMethod implements Serializable {

	private static final long serialVersionUID = 1L;

	@XStreamAlias("payment_method_token")
	private String paymentMethodToken;

	@XStreamAlias("created_at")
	private Date createdAt;

	@XStreamAlias("updated_at")
	private Date updatedAt;

	@XStreamAlias("custom")
	private String custom;

	@XStreamAlias("is_retained")
	private Boolean retained;

	@XStreamAlias("is_redacted")
	private Boolean redacted;

	@XStreamAlias("is_sensitive_data_valid")
	private Boolean sensitiveDataValid;

    @XStreamAlias("is_expiration_valid")
    private Boolean expirationValid;

	@XStreamAlias("messages")
	private MessageList messageList = new MessageList();

	@XStreamAlias("last_four_digits")
	private String lastFourDigits;

	@XStreamAlias("card_type")
	private String cardType;

	@XStreamAlias("first_name")
	private String firstName;

	@XStreamAlias("last_name")
	private String lastName;

    @XStreamAlias("card_number")
    private String cardNumber;

    @XStreamAlias("cvv")
    private String cvv;

	@XStreamAlias("expiry_month")
	private Integer expiryMonth;

	@XStreamAlias("expiry_year")
	private Integer expiryYear;

	@XStreamAlias("address_1")
	private String address1;

	@XStreamAlias("address_2")
	private String address2;

	@XStreamAlias("city")
	private String city;

	@XStreamAlias("state")
	private String state;

	@XStreamAlias("zip")
	private String zip;

	@XStreamAlias("country")
	private String country;

	static {
		XmlMarshaller.registerModelClass(PaymentMethod.class);
	}

	public String toXml() {
		return XmlMarshaller.toXml(this);
	}

	public static PaymentMethod fromXml(String xml) {
		return (PaymentMethod) XmlMarshaller.fromXml(xml);
	}

	public static PaymentMethod fromValueMap(Map<String, String> map) {
		PaymentMethod ret = new PaymentMethod();
		ret.setAddress1(getValue(map, "address1", "address_1"));
		ret.setAddress2(getValue(map, "address2", "address_2"));
		ret.setCardType(getValue(map, "cardType", "card_type"));
		ret.setCity(getValue(map, "city"));
		ret.setExpiryMonth(getIntegerValue(map, "expiryMonth", "expiry_month"));
		ret.setExpiryYear(getIntegerValue(map, "expiryYear", "expiryYear"));
		ret.setFirstName(getValue(map, "firstName", "firstName"));
		ret.setLastName(getValue(map, "lastName", "last_name"));
		ret.setPaymentMethodToken(getValue(map, "paymentMethodToken", "payment_method_token"));
		ret.setState(getValue(map, "state"));
		ret.setZip(getValue(map, "zip"));
		return ret;
	}

	protected static Integer getIntegerValue(Map<String, String> map, String key1, String key2) {
		return NumberUtils.createInteger(getValue(map, key1, key2));
	}

	protected static String getValue(Map<String, String> map, String key1, String key2) {
		return map.get(key1) != null ? map.get(key1) : map.get(key2);
	}

	protected static String getValue(Map<String, String> map, String key) {
		return map.get(key);
	}

	public String getId() {
		return getPaymentMethodToken();
	}

	public String getPaymentMethodToken() {
		return paymentMethodToken;
	}

	public void setPaymentMethodToken(String paymentMethodToken) {
		this.paymentMethodToken = paymentMethodToken;
	}

	public Date getCreatedAt() {
		return createdAt != null ? new Date(createdAt.getTime()) : null;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt != null ? new Date(createdAt.getTime()) : null;
	}

	public Date getUpdatedAt() {
		return updatedAt != null ? new Date(updatedAt.getTime()) : null;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt != null ? new Date(updatedAt.getTime()) : null;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public Boolean getRetained() {
		return retained;
	}

	public void setRetained(Boolean retained) {
		this.retained = retained;
	}

	public Boolean getRedacted() {
		return redacted;
	}

	public void setRedacted(Boolean redacted) {
		this.redacted = redacted;
	}

	public Boolean getSensitiveDataValid() {
		return sensitiveDataValid;
	}

	public void setSensitiveDataValid(Boolean sensitiveDataValid) {
		this.sensitiveDataValid = sensitiveDataValid;
	}

	public MessageList getMessageList() {
		return messageList;
	}

	public String getLastFourDigits() {
		return lastFourDigits;
	}

	public void setLastFourDigits(String lastFourDigits) {
		this.lastFourDigits = lastFourDigits;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getCvv() {
		return cvv;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(Integer expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public Integer getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(Integer expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
			.append("messageList", messageList)
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


}
