package com.feefighters.samurai.model;

import com.feefighters.samurai.PaymentMethod;
import com.feefighters.samurai.util.XmlMarshaller;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentMethodMapTest {
	@Test
	public void shouldSerializePaymentMethod() throws Exception {
		// given
		final SimpleDateFormat sdf =  new SimpleDateFormat(XmlMarshaller.DEFAULT_DATE_FORMAT);
		final Map<String, String> map = new HashMap<String, String>();
		
		final PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.paymentMethodToken = "56f0c882c4565da74d2a748e";
		paymentMethod.firstName = "Joe";
		paymentMethod.lastName = "FeeFighter";
		paymentMethod.expiryMonth = 1;
		paymentMethod.expiryYear = 2011;
		
		for(Field field : PaymentMethod.class.getFields()) {
			String name = field.getName();
			Object value = field.get(paymentMethod);
			if(value == null) {
				continue;
			}
			map.put(name, String.valueOf(value)); 
		}		
				
		// when
		final PaymentMethod createdPaymentMethod = PaymentMethod.fromMap(map);
		
		// then
		Assert.assertEquals(createdPaymentMethod, paymentMethod);		
	}	
}
