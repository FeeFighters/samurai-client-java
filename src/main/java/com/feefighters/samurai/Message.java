package com.feefighters.samurai;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.feefighters.samurai.util.MessageConverter;
import com.feefighters.samurai.util.XmlMarshaller;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("message")
@XStreamConverter(MessageConverter.class)
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public String subclass;
	public String context;
	public String key;
	public String description;
	
	static {
		XmlMarshaller.registerModelClass(Message.class);
	}

	public Message(String subclass, String context, String key, String description) {
		super();
        this.subclass = subclass;
		this.context = context;
		this.key = key;
        
        String lookup = subclass+" "+context+" "+key;
		this.description = defaultResponseMappings.get(lookup);
        if (this.description == null) {
            this.description = description == null ? "" : description;
        }
	}

	@Override
	public String toString() {		
		return new ToStringBuilder(this)
            .append("subclass", this.subclass)
			.append("context", this.context)
			.append("key", this.key)		
			.append("description", this.description)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
            .append(subclass)
			.append(context)
			.append(key)
			.append(description)
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
		final Message other = (Message) obj;
		return new EqualsBuilder()
            .append(subclass, other.subclass)
			.append(context, other.context)
			.append(key, other.key)
			.append(description, other.description)
			.isEquals();
	}

    public static Map<String, String> defaultResponseMappings;
    static {
        HashMap<String, String> map = new HashMap<String, String>();

        // Transaction Responses
        map.put("info processor.transaction success"      , "The transaction was successful.");
        map.put("error processor.transaction declined"    , "The card was declined.");
        map.put("error processor.issuer call"             , "Call the card issuer for further instructions.");
        map.put("error processor.issuer unavailable"      , "The authorization did not respond within the alloted time.");
        map.put("error input.card_number invalid"         , "The card number was invalid.");
        map.put("error input.expiry_month invalid"        , "The expiration date month was invalid, or prior to today.");
        map.put("error input.expiry_year invalid"         , "The expiration date year was invalid, or prior to today.");
        map.put("error processor.pin invalid"             , "The PIN number is incorrect.");
        map.put("error input.amount invalid"              , "The transaction amount was invalid.");
        map.put("error processor.transaction declined_insufficient_funds" , "The transaction was declined due to insufficient funds.");
        map.put("error processor.network_gateway merchant_invalid"        , "The Merchant Number is incorrect.");
        map.put("error input.merchant_login invalid"      , "The merchant ID is not valid or active.");
        map.put("error input.store_number invalid"        , "Invalid Store Number.");
        map.put("error processor.bank_info invalid"       , "Invalid banking information.");
        map.put("error processor.transaction not_allowed" , "This transaction type is not allowed.");
        map.put("error processor.transaction type_invalid"    , "Requested transaction type is not allowed for this card/merchant.");
        map.put("error processor.transaction method_invalid"  , "The requested transaction could not be performed for this merchant.");
        map.put("error input.amount exceeds_limit"            , "The maximum transaction amount was exceeded.");
        map.put("error input.cvv invalid"                     , "The CVV code was not correct.");
        map.put("error processor.network_gateway communication_error"     , "There was a fatal communication error.");
        map.put("error processor.network_gateway unresponsive"            , "The processing network is temporarily unavailable.");
        map.put("error processor.network_gateway merchant_invalid"        , "The merchant number is not on file.");

        // AVS Responses
        map.put("info processor.avs_result_code 0"  , "No response.");
        map.put("info processor.avs_result_code Y"  , "The address and 5-digit ZIP match.");
        map.put("info processor.avs_result_code Z"  , "The 5-digit ZIP matches, the address does not.");
        map.put("info processor.avs_result_code X"  , "The address and 9-digit ZIP match.");
        map.put("info processor.avs_result_code A"  , "The address matches, the ZIP does not.");
        map.put("info processor.avs_result_code E"  , "There was an AVS error, or the data was illegible.");
        map.put("info processor.avs_result_code R"  , "The AVS request timed out.");
        map.put("info processor.avs_result_code S"  , "The issuer does not support AVS.");
        map.put("info processor.avs_result_code F"  , "The street addresses and postal codes match.");
        map.put("info processor.avs_result_code N"  , "The address and ZIP do not match.");

        // CVV Responses
        map.put("error input.cvv declined" , "The CVV code was not correct.");

        // Input validations
        map.put("error input.card_number is_blank"        , "The card number was blank.");
        map.put("error input.card_number not_numeric"     , "The card number was invalid.");
        map.put("error input.card_number too_short"       , "The card number was too short.");
        map.put("error input.card_number too_long"        , "The card number was too long.");
        map.put("error input.card_number failed_checksum" , "The card number was invalid.");
        map.put("error input.card_number is_invalid"      , "The card number was invalid.");
        map.put("error input.cvv is_blank"                , "The CVV was blank.");
        map.put("error input.cvv not_numeric"             , "The CVV was invalid.");
        map.put("error input.cvv too_short"               , "The CVV was too short.");
        map.put("error input.cvv too_long"                , "The CVV was too long.");
        map.put("error input.expiry_month is_blank"       , "The expiration month was blank.");
        map.put("error input.expiry_month not_numeric"    , "The expiration month was invalid.");
        map.put("error input.expiry_month is_invalid"     , "The expiration month was invalid.");
        map.put("error input.expiry_year is_blank"        , "The expiration year was blank.");
        map.put("error input.expiry_year not_numeric"     , "The expiration year was invalid.");
        map.put("error input.expiry_year is_invalid"      , "The expiration year was invalid.");

        defaultResponseMappings = map;
    }

}

