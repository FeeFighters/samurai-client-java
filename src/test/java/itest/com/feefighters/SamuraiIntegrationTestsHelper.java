package itest.com.feefighters;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.feefighers.http.Http;

public class SamuraiIntegrationTestsHelper {

	public static String createPaymentMethodWithValidCreditCard() throws IOException {
		return createPaymentMethod("4111111111111111");
	}
	
	public static String createPaymentMethodWithInvalidCreditCard() throws IOException {
		return createPaymentMethod("4242424242424242");
	}
	
	protected static String createPaymentMethod(String creditCard) throws IOException {
		final Properties config = new Properties();
		config.load(SamuraiIntegrationTestsHelper.class.getResourceAsStream("/config.properties"));		
		
		final Http http = new Http(null, null, "https://samurai.feefighters.com/v1");
		final String output = http.post("payment_methods","redirect_url=http://localhost" +
				"&merchant_key=" + config.getProperty("merchantKey") + 
				"&credit_card[first_name]=Joe" +
				"&custom=" +
				"&credit_card[last_name]=FeeFighter" +
//				"&credit_card[city]=Mystery Van" +
//				"&credit_card[state]=IL" +
//				"&credit_card[zip]=60607" +
				"&credit_card[card_number]=" + creditCard +
				"&credit_card[cvv]=123" +
				"&credit_card[expiry_month]=01" +
				"&credit_card[expiry_year]=2011", 
				"application/x-www-form-urlencoded");
		final Matcher matcher = Pattern.compile("payment_method_token=(.+)\\\"").matcher(output);
		matcher.find();
		String paymentMethodToken = matcher.group(1);
		return paymentMethodToken;
	}
}
