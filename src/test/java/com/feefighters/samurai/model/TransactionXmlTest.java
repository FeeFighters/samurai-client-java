package com.feefighters.samurai.model;

import java.io.IOException;

import com.feefighters.samurai.Transaction;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionXmlTest {

	@Test
	public void shouldSerializeEmptyTransaction() {
		// given
		final Transaction emptyTransaction = new Transaction();
		
		// when
		final String xml = emptyTransaction.toXml();
		final Transaction transaction = Transaction.fromXml(xml);
		
		// then
		Assert.assertEquals(transaction.amount, 			emptyTransaction.amount);
		Assert.assertEquals(transaction.billingReference, 	emptyTransaction.billingReference);
		Assert.assertEquals(transaction.currencyCode, 		emptyTransaction.currencyCode);
		Assert.assertEquals(transaction.custom, 			emptyTransaction.custom);
		Assert.assertEquals(transaction.customerReference, 	emptyTransaction.customerReference);
		Assert.assertEquals(transaction.description, 		emptyTransaction.description);
		Assert.assertEquals(transaction.descriptorName, 	emptyTransaction.descriptorName);
		Assert.assertEquals(transaction.descriptorPhone,	emptyTransaction.descriptorPhone);
		Assert.assertEquals(transaction.paymentMethodToken,	emptyTransaction.paymentMethodToken);
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
