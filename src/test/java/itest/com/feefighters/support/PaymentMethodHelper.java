package itest.com.feefighters.support;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.feefighters.http.Http;

public class PaymentMethodHelper {

	public static PaymentMethodRequest newPaymentMethodRequest() throws IOException {
		return new PaymentMethodRequest();
	}

	public static PaymentMethodRequest newPaymentMethodRequestWithInvalidCreditCard() throws IOException {
		PaymentMethodRequest req = new PaymentMethodRequest();
		req.cardNumber = "1234123412341234";
		return req;
	}

    public static class PaymentMethodRequest {
		public String firstName = "Joe";
        public String custom = "";
        public String lastName = "FeeFighter";
        public String cardNumber = "4111111111111111";
        public String cvv = "123";
        public String expiryMonth = "01";
        public String expiryYear = "2014";
        public String city = "Mystery Van";
        public String state = "IL";
        public String zip = "60607";
	}

	public static String createPaymentMethod(PaymentMethodRequest req) throws IOException {
		final Properties config = new Properties();
		config.load(PaymentMethodHelper.class.getResourceAsStream("/config.properties"));

		final Http http = new Http(null, null, "https://api.samurai.feefighters.com/v1");
		final String body = "redirect_url=http://localhost"
				+ "&merchant_key=" + config.getProperty("merchantKey")
				+ "&custom=" + req.firstName
                + "&sandbox=" + true
				+ "&credit_card[first_name]=" + req.firstName
				+ "&credit_card[last_name]=" + req.lastName
				+ "&credit_card[city]=" + req.city
				+ "&credit_card[state]=" + req.state
				+ "&credit_card[zip]=" + req.zip
				+ "&credit_card[card_number]=" + req.cardNumber
				+ "&credit_card[cvv]=" + req.cvv
				+ "&credit_card[expiry_month]=" + req.expiryMonth
				+ "&credit_card[expiry_year]=" + req.expiryYear;
		final String output = http.post("payment_methods/tokenize.json", body, "application/x-www-form-urlencoded");
		final Matcher matcher = Pattern.compile("payment_method_token\":\"(.+)\"").matcher(output);
		matcher.find();
        return matcher.group(1);
	}

}
