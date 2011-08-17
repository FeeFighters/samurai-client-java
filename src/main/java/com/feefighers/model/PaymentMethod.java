package com.feefighers.model;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("payment_method")
public class PaymentMethod {
	
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
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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
			.append("address1", this.address1)
			.append("address2", this.address2)
			.append("cardType", this.cardType)
			.append("city", this.city)
			.append("country", this.country)
			.append("custom", this.custom)
			.append("firstName", this.firstName)
			.append("lastFourDigits", this.lastFourDigits)
			.append("lastName", this.lastName)
			.append("paymentMethodToken", this.paymentMethodToken)
			.append("state", this.state)
			.append("zip", this.zip)
			.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result
				+ ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((custom == null) ? 0 : custom.hashCode());
		result = prime * result
				+ ((expiryMonth == null) ? 0 : expiryMonth.hashCode());
		result = prime * result
				+ ((expiryYear == null) ? 0 : expiryYear.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastFourDigits == null) ? 0 : lastFourDigits.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((messageList == null) ? 0 : messageList.hashCode());
		result = prime
				* result
				+ ((paymentMethodToken == null) ? 0 : paymentMethodToken
						.hashCode());
		result = prime * result
				+ ((redacted == null) ? 0 : redacted.hashCode());
		result = prime * result
				+ ((retained == null) ? 0 : retained.hashCode());
		result = prime
				* result
				+ ((sensitiveDataValid == null) ? 0 : sensitiveDataValid
						.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		PaymentMethod other = (PaymentMethod) obj;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (cardType == null) {
			if (other.cardType != null)
				return false;
		} else if (!cardType.equals(other.cardType))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (custom == null) {
			if (other.custom != null)
				return false;
		} else if (!custom.equals(other.custom))
			return false;
		if (expiryMonth == null) {
			if (other.expiryMonth != null)
				return false;
		} else if (!expiryMonth.equals(other.expiryMonth))
			return false;
		if (expiryYear == null) {
			if (other.expiryYear != null)
				return false;
		} else if (!expiryYear.equals(other.expiryYear))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastFourDigits == null) {
			if (other.lastFourDigits != null)
				return false;
		} else if (!lastFourDigits.equals(other.lastFourDigits))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (messageList == null) {
			if (other.messageList != null)
				return false;
		} else if (!messageList.equals(other.messageList))
			return false;
		if (paymentMethodToken == null) {
			if (other.paymentMethodToken != null)
				return false;
		} else if (!paymentMethodToken.equals(other.paymentMethodToken))
			return false;
		if (redacted == null) {
			if (other.redacted != null)
				return false;
		} else if (!redacted.equals(other.redacted))
			return false;
		if (retained == null) {
			if (other.retained != null)
				return false;
		} else if (!retained.equals(other.retained))
			return false;
		if (sensitiveDataValid == null) {
			if (other.sensitiveDataValid != null)
				return false;
		} else if (!sensitiveDataValid.equals(other.sensitiveDataValid))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	
	
}
