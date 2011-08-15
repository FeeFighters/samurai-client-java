package com.feefighers;

import java.io.File;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrMatcher;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.feefighers.http.Http;
import com.feefighers.model.PaymentMethod;

public class PaymentMethodTest {

	@Test
	public void shouldGetPaymentMethodFromServer() throws Exception {
		final Properties config = new Properties();
		config.load(getClass().getResourceAsStream("/config.properties"));
		
		final Http http = new Http(null, null, "https://samurai.feefighters.com/v1");
		final String output = http.post("payment_methods","redirect_url=http://localhost&" +
				"merchant_key=" + config.getProperty("merchantKey") + "&" +
				"credit_card[first_name]=Scooby&custom=&credit_card[last_name]=Do&credit_card[city]=Mystery Van&" +
				"credit_card[state]=IL&credit_card[zip]=60607&credit_card[card_number]=4111111111111111&credit_card[cvv]=123&" +
				"credit_card[expiry_month]=04&credit_card[expiry_year]=2014&sandbox=true",
				"application/x-www-form-urlencoded");
		final Matcher matcher = Pattern.compile("payment_method_token=(.+)\\\"").matcher(output);
		matcher.find();
		final String paymentMethodToken = matcher.group(1);
		
		final SamuraiGateway gateway = new SamuraiGateway(config.getProperty("merchantKey"), 
				config.getProperty("merchantPassword"), config.getProperty("processorToken"));
						
		final PaymentMethod paymentMethod = gateway.processor().find(paymentMethodToken);
		
		Assert.assertNotNull(paymentMethod);
	}
	
}
