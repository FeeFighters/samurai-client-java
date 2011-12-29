package itest.com.feefighters;

import static itest.com.feefighters.support.PaymentMethodHelper.createPaymentMethod;
import static itest.com.feefighters.support.PaymentMethodHelper.newPaymentMethodRequest;
import static itest.com.feefighters.support.PaymentMethodHelper.PaymentMethodRequest;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.feefighters.SamuraiGateway;
import com.feefighters.model.PaymentMethod;

public class PaymentMethodTest {

	private Properties config;
	private String paymentMethodToken;
	private SamuraiGateway gateway;
	private PaymentMethodRequest paymentMethodRequest;
	
	@BeforeMethod
	public void createNewPaymentMethod() throws IOException {
		config = new Properties();
		config.load(getClass().getResourceAsStream("/config.properties"));

		paymentMethodRequest = newPaymentMethodRequest();
		paymentMethodToken = createPaymentMethod(paymentMethodRequest);
		
		gateway = new SamuraiGateway(config.getProperty("merchantKey"), 
				config.getProperty("merchantPassword"), config.getProperty("processorToken"));		
	}
	
	@Test
	public void shouldGetPaymentMethodFromServer() throws Exception {	
		final PaymentMethod paymentMethod = gateway.processor().find(paymentMethodToken);
		
		Assert.assertNotNull(paymentMethod);
		Assert.assertEquals(paymentMethod.getFirstName(), paymentMethodRequest.firstName);
	}
	
	@Test
	public void shouldUpdatePaymentMethod() throws Exception {
		final PaymentMethod paymentMethod = gateway.processor().find(paymentMethodToken);
		paymentMethod.setAddress1("1");
		paymentMethod.setAddress2("2");
		paymentMethod.setCity("4");
		paymentMethod.setCountry("5");
		paymentMethod.setCustom("6");
		paymentMethod.setFirstName("7");
		paymentMethod.setLastName("9");
		paymentMethod.setState("10");
		paymentMethod.setZip("11");

		final PaymentMethod updatedPaymentMethod = gateway.processor().save(paymentMethod);
        Assert.assertNotNull(updatedPaymentMethod);
		paymentMethod.setUpdatedAt(updatedPaymentMethod.getUpdatedAt());
        paymentMethod.setCreatedAt(updatedPaymentMethod.getCreatedAt());
        paymentMethod.setPaymentMethodToken(updatedPaymentMethod.getPaymentMethodToken());
		Assert.assertEquals(updatedPaymentMethod, paymentMethod);

		final PaymentMethod modifiedPaymentMethod = gateway.processor().find(updatedPaymentMethod.getPaymentMethodToken());
		Assert.assertNotNull(modifiedPaymentMethod);
		paymentMethod.setUpdatedAt(modifiedPaymentMethod.getUpdatedAt());
        paymentMethod.setCreatedAt(modifiedPaymentMethod.getCreatedAt());
        paymentMethod.setPaymentMethodToken(modifiedPaymentMethod.getPaymentMethodToken());
		Assert.assertEquals(modifiedPaymentMethod, paymentMethod);
	}
	
	@Test
	public void shouldRedactPaymentMethod() throws Exception {
		final PaymentMethod paymentMethod = gateway.processor().find(paymentMethodToken);		
		Assert.assertNotNull(paymentMethod.getRedacted());
		Assert.assertFalse(paymentMethod.getRedacted());
		
		final PaymentMethod returnedPaymentMethod = gateway.processor().redact(paymentMethod);		
		Assert.assertNotNull(returnedPaymentMethod);
		Assert.assertNotNull(returnedPaymentMethod.getRedacted());
		Assert.assertTrue(returnedPaymentMethod.getRedacted());
	}
	
	@Test
	public void shouldRetainPaymentMethod() throws Exception {
		final PaymentMethod paymentMethod = gateway.processor().find(paymentMethodToken);		
		Assert.assertNotNull(paymentMethod.getRetained());
		Assert.assertFalse(paymentMethod.getRetained());
		
		final PaymentMethod returnedPaymentMethod = gateway.processor().retain(paymentMethod);		
		Assert.assertNotNull(returnedPaymentMethod);
		Assert.assertNotNull(returnedPaymentMethod.getRetained());
		Assert.assertTrue(returnedPaymentMethod.getRetained());
	}
}
