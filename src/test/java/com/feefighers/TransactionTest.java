package com.feefighers;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.feefighers.model.PaymentMethod;

public class TransactionTest {

	@Test
	public void shouldGetPaymentMethodFromServer() throws Exception {
		final Properties config = new Properties();
		config.load(getClass().getResourceAsStream("/config.properties"));
//		
		// given
		final SamuraiGateway gateway = new SamuraiGateway(config.getProperty("merchantKey"), 
				config.getProperty("merchantPassword"), config.getProperty("processorToken"));
		final String xml = IOUtils.toString(getClass().getResourceAsStream("model/payment_method.xml"));		
		
		// when
		final PaymentMethod paymentMethod = gateway.processor().find("56f0c882c4565da74d2a748e");
		final String paymentMethodXml = paymentMethod.toXml();
		
		Assert.assertEquals(xml.trim(), paymentMethodXml.trim());
	}
}
