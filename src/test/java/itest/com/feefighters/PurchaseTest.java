package itest.com.feefighters;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.feefighers.SamuraiGateway;
import com.feefighers.http.Http;
import com.feefighers.model.PaymentMethod;
import com.feefighers.model.Transaction;

public class PurchaseTest {

	private String paymentMethodToken;
	private SamuraiGateway gateway;

	@BeforeTest
	public void createNewPaymentMethod() throws IOException {
		final Properties config = new Properties();
		config.load(getClass().getResourceAsStream("/config.properties"));
		
		final Http http = new Http(null, null, "https://samurai.feefighters.com/v1");
		final String output = http.post("payment_methods","redirect_url=http://localhost&" +
				"merchant_key=" + config.getProperty("merchantKey") + "&" +
				"credit_card[first_name]=Scooby&custom=&credit_card[last_name]=Do&credit_card[city]=Mystery Van&" +
				"credit_card[state]=IL&credit_card[zip]=60607&credit_card[card_number]=4111111111111111&credit_card[cvv]=123&" +
				"credit_card[expiry_month]=04&credit_card[expiry_year]=2014", 
				"application/x-www-form-urlencoded");
		final Matcher matcher = Pattern.compile("payment_method_token=(.+)\\\"").matcher(output);
		matcher.find();
		paymentMethodToken = matcher.group(1);
		
		gateway = new SamuraiGateway(config.getProperty("merchantKey"), 
				config.getProperty("merchantPassword"), config.getProperty("processorToken"));
	}
	
	@Test
	public void shouldGetPaymentMethodFromServer() throws Exception {
		final PaymentMethod paymentMethod = gateway.processor().find(paymentMethodToken);
		
		Transaction transaction = gateway.processor().purchase(paymentMethodToken, 10, null);
		Assert.assertNotNull(transaction);
	}
	
}
