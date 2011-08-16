package com.feefighers.model;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionXmlTest {

	@Test
	public void shouldSerializeEmptyTransaction() {
		// given
		final Transaction emptyTransaction = new Transaction(null);
		
		// when
		final String xml = emptyTransaction.toXml();
		final Transaction transaction = Transaction.fromXml(xml);
		
		// then
		Assert.assertEquals(transaction.getAmount(), 				emptyTransaction.getAmount());
		Assert.assertEquals(transaction.getBillingReference(), 		emptyTransaction.getBillingReference());
		Assert.assertEquals(transaction.getCurrencyCode(), 			emptyTransaction.getCurrencyCode());
		Assert.assertEquals(transaction.getCustom(), 				emptyTransaction.getCustom());
		Assert.assertEquals(transaction.getCustomerReference(), 	emptyTransaction.getCustomerReference());
		Assert.assertEquals(transaction.getDescriptor(), 			emptyTransaction.getDescriptor());
		Assert.assertEquals(transaction.getPaymentMethodToken(),	emptyTransaction.getPaymentMethodToken());
		Assert.assertEquals(transaction.getRequestType(), 					emptyTransaction.getRequestType());
	}
	

	@Test
	public void shouldDeserializeTransaction() throws IOException {
		// given
		final String xml = IOUtils.toString(getClass().getResourceAsStream("transaction.xml"));
		
		// when
		final Transaction object = Transaction.fromXml(xml);
		final String outputXml = object.toXml(); 
		
		// then
		Assert.assertEquals(outputXml.trim(), xml.trim());
	}	
}
