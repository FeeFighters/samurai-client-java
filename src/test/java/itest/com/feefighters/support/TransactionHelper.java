package itest.com.feefighters.support;

import static itest.com.feefighters.support.PaymentMethodHelper.createPaymentMethod;
import static itest.com.feefighters.support.PaymentMethodHelper.newPaymentMethodRequest;

import java.io.IOException;
import java.util.Properties;

import com.feefighters.SamuraiGateway;
import com.feefighters.Transaction;

public class TransactionHelper {

	public static Transaction createPurchase() throws IOException {
		final Properties config = new Properties();
		config.load(TransactionHelper.class.getResourceAsStream("/config.properties"));

		SamuraiGateway gateway = new SamuraiGateway(config.getProperty("merchantKey"), 
				config.getProperty("merchantPassword"), config.getProperty("processorToken"));		
		
		String paymentMethodToken = createPaymentMethod(newPaymentMethodRequest());
		
		Transaction transaction = gateway.processor().purchase(paymentMethodToken, 10.0, null);
		return transaction;
	}
}
