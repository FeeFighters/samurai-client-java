package com.feefighters.samurai.model;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.feefighters.samurai.Message;
import com.feefighters.samurai.PaymentMethod;
import com.feefighters.samurai.util.XmlMarshaller;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentMethodXmlTest {
	
	@Test
	public void shouldSerializePaymentMethod() throws Exception {
		// given
		final Message message = new Message("error", "input.cvv", "too_long", "");

		final SimpleDateFormat sdf =  new SimpleDateFormat(XmlMarshaller.DEFAULT_DATE_FORMAT);
		
		final PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.paymentMethodToken = "56f0c882c4565da74d2a748e";
		paymentMethod.createdAt = sdf.parse("2011-08-10 13:00:07 UTC");
		paymentMethod.updatedAt = sdf.parse("2011-08-10 13:00:07 UTC");
		paymentMethod.retained = false;
		paymentMethod.redacted = false;
		paymentMethod.sensitiveDataValid = false;
		paymentMethod.messages.getList().add(message);
		paymentMethod.lastFourDigits = "1111";
		paymentMethod.firstName = "Joe";
		paymentMethod.lastName = "FeeFighter";
		paymentMethod.expiryMonth = 1;
		paymentMethod.expiryYear = 2011;
		final String xml = IOUtils.toString(getClass().getResourceAsStream("payment_method_without_empty_nodes.xml"));		
				
		// when
		final String outputXml = paymentMethod.toXml();
		
		// then
		Assert.assertEquals(outputXml.trim(), xml.trim());		
	}	
	
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
