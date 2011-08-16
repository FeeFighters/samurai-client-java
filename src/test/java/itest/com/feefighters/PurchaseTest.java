package itest.com.feefighters;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.feefighers.SamuraiGateway;
import com.feefighers.http.Http;
import com.feefighers.model.Transaction;

public class PurchaseTest {

	private SamuraiGateway gateway;

	@BeforeTest
	public void createNewPaymentMethod() throws IOException {
		final Properties config = new Properties();
		config.load(getClass().getResourceAsStream("/config.properties"));

		gateway = new SamuraiGateway(config.getProperty("merchantKey"), 
				config.getProperty("merchantPassword"), config.getProperty("processorToken"));
	}
	
	@Test
	public void shouldPurchaseMethodPaymentWithValidCreditCardAndReceiveTransaction() throws Exception {
		String paymentMethodToken = SamuraiIntegrationTestsHelper.createPaymentMethodWithValidCreditCard();
		
		Transaction transaction = gateway.processor().purchase(paymentMethodToken, 10, null);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(transaction.getResponseType(), Transaction.TransactionResponseType.Purchase);
		Assert.assertNotNull(transaction.getProcessorResponse());
		Assert.assertNotNull(transaction.getProcessorResponse().getSuccess());
		Assert.assertTrue(transaction.getProcessorResponse().getSuccess());
	}
	
	@Test
	public void shouldAuthorizeMethodPaymentWithValidCreditCardAndReceiveTransaction() throws Exception {
		String paymentMethodToken = SamuraiIntegrationTestsHelper.createPaymentMethodWithValidCreditCard();
		
		Transaction transaction = gateway.processor().authorize(paymentMethodToken, 10, null);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(transaction.getResponseType(), Transaction.TransactionResponseType.Authorize);
		Assert.assertNotNull(transaction.getProcessorResponse());
		Assert.assertNotNull(transaction.getProcessorResponse().getSuccess());
		Assert.assertTrue(transaction.getProcessorResponse().getSuccess());		
	}
	
	@Test
	public void shouldNotAuthorizeMethodPaymentWithInValidCreditCard() throws Exception {
		Http.enableDebug();
		String paymentMethodToken = SamuraiIntegrationTestsHelper.createPaymentMethodWithInvalidCreditCard();
		
		Transaction transaction = gateway.processor().authorize(paymentMethodToken, 10, null);
		Assert.assertFalse(transaction.getProcessorResponse().getSuccess());		
	}	
	
	
}
