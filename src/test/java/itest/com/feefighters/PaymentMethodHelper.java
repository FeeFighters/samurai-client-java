package itest.com.feefighters;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.feefighers.http.Http;

public class PaymentMethodHelper {

	public static PaymentMethodRequest newPaymentMethodRequest() throws IOException {
		return new PaymentMethodRequest();
	}
	
	public static PaymentMethodRequest newPaymentMethodRequestWithInvalidCreditCard() throws IOException {
		PaymentMethodRequest req = new PaymentMethodRequest();
		req.cardNumber = "4242424242424242";
		return req;
	}
	
	protected static class PaymentMethodRequest {
		String firstName = "Joe";
		String custom = "";
		String lastName = "FeeFighter";
		String cardNumber = "4111111111111111";
		String cvv = "123";
		String expiryMonth = "01";
		String expiryYear = "2014";
	}
	
	protected static String createPaymentMethod(PaymentMethodRequest req) throws IOException {
		final Properties config = new Properties();
		config.load(PaymentMethodHelper.class.getResourceAsStream("/config.properties"));		
		
		final Http http = new Http(null, null, "https://samurai.feefighters.com/v1");
		final String body = "redirect_url=http://localhost" 
				+ "&merchant_key=" + config.getProperty("merchantKey") 
				+ "&credit_card[first_name]=" + req.firstName
				+ "&custom=" + req.firstName
				+ "&credit_card[last_name]=" + req.lastName
//				+ "&credit_card[city]=Mystery Van" +
//				+ "&credit_card[state]=IL" +
//				+ "&credit_card[zip]=60607" +
				+ "&credit_card[card_number]=" + req.cardNumber
				+ "&credit_card[cvv]=" + req.cvv
				+ "&credit_card[expiry_month]=" + req.expiryMonth
				+ "&credit_card[expiry_year]=" + req.expiryYear;
		final String output = http.post("payment_methods", body,"application/x-www-form-urlencoded");
		final Matcher matcher = Pattern.compile("payment_method_token=(.+)\\\"").matcher(output);
		matcher.find();
		String paymentMethodToken = matcher.group(1);
		return paymentMethodToken;
	}
}
