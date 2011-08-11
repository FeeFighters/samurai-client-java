package com.feefighers.model;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.mapper.CannotResolveClassException;

public class PaymentMethodXmlTest {
	
	
	
	@Test
	public void shouldDeserializePaymentMethod() throws IOException {
		// given
		final String xml = IOUtils.toString(getClass().getResourceAsStream("payment_method.xml"));
		
		// when
		final PaymentMethod paymentMethod = PaymentMethod.fromXml(xml);
		final String outputXml = paymentMethod.toXml(); 
		
		// then
		Assert.assertEquals(outputXml.trim(), xml.trim());
	}		
}
