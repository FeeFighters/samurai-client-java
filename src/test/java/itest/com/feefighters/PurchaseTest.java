package itest.com.feefighters;

import static itest.com.feefighters.support.PaymentMethodHelper.createPaymentMethod;
import static itest.com.feefighters.support.PaymentMethodHelper.newPaymentMethodRequest;
import static itest.com.feefighters.support.PaymentMethodHelper.newPaymentMethodRequestWithInvalidCreditCard;

import java.io.IOException;
import java.util.Properties;

import com.feefighters.Transaction;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.feefighters.SamuraiGateway;

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
		String paymentMethodToken = createPaymentMethod(newPaymentMethodRequest());
		
		Transaction transaction = gateway.processor().purchase(paymentMethodToken, 100, null);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(transaction.getTransactionType(), Transaction.TransactionType.Purchase);
		Assert.assertNotNull(transaction.getProcessorResponse());
		Assert.assertNotNull(transaction.getProcessorResponse().getSuccess());
		Assert.assertTrue(transaction.getProcessorResponse().getSuccess());
	}
	
	@Test
	public void shouldAuthorizeMethodPaymentWithValidCreditCardAndReceiveTransaction() throws Exception {
		String paymentMethodToken = createPaymentMethod(newPaymentMethodRequest());
		
		Transaction transaction = gateway.processor().authorize(paymentMethodToken, 100, null);
		Assert.assertNotNull(transaction);
		Assert.assertEquals(transaction.getTransactionType(), Transaction.TransactionType.Authorize);
		Assert.assertNotNull(transaction.getProcessorResponse());
		Assert.assertNotNull(transaction.getProcessorResponse().getSuccess());
		Assert.assertTrue(transaction.getProcessorResponse().getSuccess());		
	}
	
	@Test
	public void shouldNotAuthorizeMethodPaymentWithInvalidCreditCard() throws Exception {
		String paymentMethodToken = createPaymentMethod(newPaymentMethodRequestWithInvalidCreditCard());
		
		Transaction transaction = gateway.processor().authorize(paymentMethodToken, 100, null);
		Assert.assertFalse(transaction.getProcessorResponse().getSuccess());
	}	
	
}
