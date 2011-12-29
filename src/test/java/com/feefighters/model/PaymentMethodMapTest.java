package com.feefighters.model;

import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.feefighters.PaymentMethod;
import com.feefighters.util.XmlMarshaller;
import org.apache.commons.beanutils.PropertyUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentMethodMapTest {
	@Test
	public void shouldSerializePaymentMethod() throws Exception {
		// given		
		final SimpleDateFormat sdf =  new SimpleDateFormat(XmlMarshaller.DEFAULT_DATE_FORMAT);
		final Map<String, String> map = new HashMap<String, String>();
		
		final PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setPaymentMethodToken("56f0c882c4565da74d2a748e");
		paymentMethod.setFirstName("Joe");
		paymentMethod.setLastName("FeeFighter");
		paymentMethod.setExpiryMonth(1);
		paymentMethod.setExpiryYear(2011);
		
		for(PropertyDescriptor property : PropertyUtils.getPropertyDescriptors(paymentMethod)) {
			String name = property.getName();
			Object value = PropertyUtils.getProperty(paymentMethod, name);
			if(value == null) {
				continue;
			}
			map.put(name, String.valueOf(value)); 
		}		
				
		// when
		final PaymentMethod createdPaymentMethod = PaymentMethod.fromValueMap(map);
		
		// then
		Assert.assertEquals(createdPaymentMethod, paymentMethod);		
	}	
}
