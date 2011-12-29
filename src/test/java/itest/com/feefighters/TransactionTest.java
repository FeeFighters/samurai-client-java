package itest.com.feefighters;

import static itest.com.feefighters.support.TransactionHelper.createPurchase;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.feefighters.SamuraiGateway;
import com.feefighters.model.Transaction;

public class TransactionTest {
	private SamuraiGateway gateway;

	@BeforeTest
	public void createNewPaymentMethod() throws IOException {
		final Properties config = new Properties();
		config.load(getClass().getResourceAsStream("/config.properties"));

		gateway = new SamuraiGateway(config.getProperty("merchantKey"), 
				config.getProperty("merchantPassword"), config.getProperty("processorToken"));
	}
	
	@Test
	public void shouldFindTransaction() throws Exception {
		Transaction transaction = createPurchase();
		
		Assert.assertNotNull(transaction);
		Assert.assertNotNull(transaction.getReferenceId());
		
		Transaction foundTransaction = gateway.transaction().find(transaction.getReferenceId());
		Assert.assertNotNull(foundTransaction);
	}
	
	@Test
	public void shouldTransactionVoidSetTransactionTypeToVoid() throws Exception {
		Transaction transaction = createPurchase();
		
		Transaction returnedTranscation = gateway.transaction().voidOperation(transaction, null);
		
		Assert.assertNotNull(returnedTranscation);
		Assert.assertNotNull(returnedTranscation.getTransactionType());
		Assert.assertEquals(returnedTranscation.getTransactionType(), Transaction.TransactionType.Credit);
	}	
	
	@Test
	public void shouldTransactionCaputreSetTransactionTypeToCapture() throws Exception {
		Transaction transaction = createPurchase();
		
		Transaction returnedTranscation = gateway.transaction().capture(transaction, null, null);
		
		Assert.assertNotNull(returnedTranscation);
		Assert.assertNotNull(returnedTranscation.getTransactionType());
		Assert.assertEquals(returnedTranscation.getTransactionType(), Transaction.TransactionType.Capture);
	}
	
	@Test
	public void shouldTransactionCreditSetTransactionTypeToCredit() throws Exception {
		Transaction transaction = createPurchase();
		
		Transaction returnedTranscation = gateway.transaction().credit(transaction, null, null);
		
		Assert.assertNotNull(returnedTranscation);
		Assert.assertNotNull(returnedTranscation.getTransactionType());
		Assert.assertEquals(returnedTranscation.getTransactionType(), Transaction.TransactionType.Credit);
	}	

	@Test
	public void shouldTransactionReverseSetTransactionTypeToCredit() throws Exception {
		Transaction transaction = createPurchase();

		Transaction returnedTranscation = gateway.transaction().reverse(transaction, null, null);

		Assert.assertNotNull(returnedTranscation);
		Assert.assertNotNull(returnedTranscation.getTransactionType());
		Assert.assertEquals(returnedTranscation.getTransactionType(), Transaction.TransactionType.Credit);
	}
}
