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

public class PaymentMethodTest {

	private Properties config;
	private String paymentMethodToken;
	private SamuraiGateway gateway;
	private PaymentMethod createdPaymentMethod;
	
	@BeforeTest
	public void createNewPaymentMethod() throws IOException {
		config = new Properties();
		config.load(getClass().getResourceAsStream("/config.properties"));
		
		createdPaymentMethod = new PaymentMethod();
		createdPaymentMethod.setFirstName("Scooby");
		
		final Http http = new Http(null, null, "https://samurai.feefighters.com/v1");		
		final String output = http.post("payment_methods","redirect_url=http://localhost&" +
				"merchant_key=" + config.getProperty("merchantKey") + "&" +
				"credit_card[first_name]=" + createdPaymentMethod.getFirstName() + 
				"&custom=&credit_card[last_name]=Do&credit_card[city]=Mystery Van&" +
				"credit_card[state]=IL&credit_card[zip]=60607&credit_card[card_number]=4111111111111111&credit_card[cvv]=123&" +
				"credit_card[expiry_month]=04&credit_card[expiry_year]=2014&sandbox=true",
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
		
		Assert.assertNotNull(paymentMethod);
		Assert.assertEquals(paymentMethod.getFirstName(), createdPaymentMethod.getFirstName());
	}
	
	@Test
	public void shouldUpdatePaymentMethod() throws Exception {
		final PaymentMethod paymentMethod = gateway.processor().find(paymentMethodToken);
		paymentMethod.setFirstName(paymentMethod.getFirstName() + paymentMethod.getFirstName());
		
		boolean success = gateway.processor().save(paymentMethod);
		Assert.assertTrue(success);
		
		final PaymentMethod modifiedPaymentMethod = gateway.processor().find(paymentMethodToken);
		Assert.assertNotNull(modifiedPaymentMethod);
		Assert.assertEquals(modifiedPaymentMethod.getFirstName(), paymentMethod.getFirstName());
	}
	
}
