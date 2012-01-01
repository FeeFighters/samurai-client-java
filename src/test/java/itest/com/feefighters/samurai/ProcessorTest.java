package itest.com.feefighters.samurai;

import com.feefighters.samurai.PaymentMethod;
import com.feefighters.samurai.Processor;
import com.feefighters.samurai.SamuraiGateway;
import com.feefighters.samurai.Transaction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Properties;

public class ProcessorTest {
    private HashMap<String, String> paymentMethodParams, params;
    private PaymentMethod defaultPaymentMethod;
    String rand;

    @BeforeTest
    public void beforeAll() throws IOException {
        Properties config = new Properties();
        config.load(getClass().getResourceAsStream("/config.properties"));
        SamuraiGateway.configure(config);
    }

    @BeforeMethod
    public void beforeEach() {
        paymentMethodParams = new HashMap<String, String>();
        paymentMethodParams.put("firstName", "FirstName");
        paymentMethodParams.put("lastName", "LastName");
        paymentMethodParams.put("address1", "123 Main St.");
        paymentMethodParams.put("address2", "Apt #3");
        paymentMethodParams.put("city", "Chicago");
        paymentMethodParams.put("state", "IL");
        paymentMethodParams.put("zip", "10101");
        paymentMethodParams.put("cardNumber", "4111-1111-1111-1111");
        paymentMethodParams.put("cvv", "123");
        paymentMethodParams.put("expiryMonth", "3");
        paymentMethodParams.put("expiryYear", "2015");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);

        params = new HashMap<String, String>();
        params.put("description", "description");
        params.put("descriptorName", "descriptor_name");
        params.put("descriptorPhone", "descriptor_phone");
        params.put("custom", "custom_data");
        params.put("billingReference", "ABC123"+rand);
        params.put("customerReference", "customer (123)");
        params.put("currencyCode", "USD");

        SecureRandom r = new SecureRandom();
        rand = new BigInteger(130, r).toString(32);
    }

    @Test
    public void TheProcessorShouldReturnTheDefaultProcessor() {
        Processor theProcessor = Processor.theProcessor();

        Assert.assertNotNull(theProcessor);
        Assert.assertEquals(SamuraiGateway.defaultGateway().processorToken, theProcessor.processorToken);
    }

    @Test
    public void NewProcessorShouldReturnAProcessor() {
        Processor processor = new Processor("abc123");

        Assert.assertNotNull(processor);
        Assert.assertEquals("abc123", processor.processorToken);
    }

    @Test
    public void PurchaseShouldBeSuccessful() {
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, 100.0, params);

        Assert.assertTrue( transaction.success );
        Assert.assertEquals(transaction.description, params.get("description"));
        Assert.assertEquals(transaction.descriptorName, params.get("descriptorName"));
        Assert.assertEquals(transaction.descriptorPhone, params.get("descriptorPhone"));
        Assert.assertEquals(transaction.custom, params.get("custom"));
        Assert.assertEquals(transaction.billingReference, params.get("billingReference"));
        Assert.assertEquals(transaction.customerReference, params.get("customerReference"));
        Assert.assertEquals(transaction.currencyCode, params.get("currencyCode"));
    }

    @Test
    public void PurchaseShouldSetDefaultCurrencyCode() {
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, 100.0);

        Assert.assertTrue( transaction.success );
        Assert.assertEquals(transaction.currencyCode, "USD");
    }
    
    @Test
    public void PurchaseFailuresShouldReturnProcessorTransactionDeclined() {
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "1.02", params);
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("processor.transaction").get(0), "The card was declined.");
    }
    @Test
    public void PurchaseFailuresShouldReturnInputAmountInvalid() {
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "1.10", params);
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("input.amount").get(0), "The transaction amount was invalid.");
    }
    /*
    @Test
    public void PurchaseFailuresShouldReturnInputCardNumberFailedChecksum() {
        defaultPaymentMethod = PaymentMethod.Create(defaultPayload.Merge(new PaymentMethodPayload() { CardNumber = "1234123412341234" }));
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken,
                                                          "1.00", new TransactionPayload() { billingReference = rand });
        Assert.assertFalse(transaction.success);
        Assert.assertEquals( new List<string>(){"The card number was invalid."}, transaction.Errors["input.card_number"] );
    }
    @Test
    public void PurchaseFailuresShouldReturnInputCardNumberInvalid() {
        defaultPaymentMethod = PaymentMethod.Create(defaultPayload.Merge(new PaymentMethodPayload() { CardNumber = "5105105105105100" }));
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken,
                                                          "1.00", new TransactionPayload() { billingReference = rand });
        Assert.assertFalse(transaction.success);
        Assert.assertEquals( new List<string>(){"The card number was invalid."}, transaction.Errors["input.card_number"] );
    }
    */

    @Test
    public void PurchaseCvvResponsesShouldReturnProcessorCvvResultCodeM() {
        paymentMethodParams.put("cvv", "111");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.cvvResultCode, "M" );
    }
    @Test
    public void PurchaseCvvResponsesShouldReturnProcessorCvvResultCodeN() {
        paymentMethodParams.put("cvv", "222");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.cvvResultCode, "N" );
    }
    @Test
    public void PurchaseAvsResponsesShouldReturnProcessorAvsResultCodeY() {
        paymentMethodParams.put("address1", "1000 1st Av");
        paymentMethodParams.put("address2", "");
        paymentMethodParams.put("zip", "10101");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.avsResultCode, "Y" );
    }
    @Test
    public void PurchaseAvsResponsesShouldReturnProcessorAvsResultCodeZ() {
        paymentMethodParams.put("address1", "");
        paymentMethodParams.put("address2", "");
        paymentMethodParams.put("zip", "10101");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.avsResultCode, "Z" );
    }
    @Test
    public void PurchaseAvsResponsesShouldReturnProcessorAvsResultCodeN() {
        paymentMethodParams.put("address1", "123 Main St.");
        paymentMethodParams.put("address2", "");
        paymentMethodParams.put("zip", "60610");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.avsResultCode, "N" );
    }

    @Test
    public void AuthorizeShouldBeSuccessful() {
        Transaction transaction = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue(transaction.success);
        Assert.assertEquals(transaction.description, params.get("description"));
        Assert.assertEquals(transaction.descriptorName, params.get("descriptorName"));
        Assert.assertEquals(transaction.descriptorPhone, params.get("descriptorPhone"));
        Assert.assertEquals(transaction.custom, params.get("custom"));
        Assert.assertEquals(transaction.billingReference, params.get("billingReference"));
        Assert.assertEquals(transaction.customerReference, params.get("customerReference"));
    }

    @Test
    public void AuthorizeFailuresShouldReturnProcessorTransactionDeclined() {
        Transaction transaction = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "1.02", params);
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("processor.transaction").get(0), "The card was declined.");
    }
    @Test
    public void AuthorizeFailuresShouldReturnInputAmountInvalid() {
        Transaction transaction = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "1.10", params);
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("input.amount").get(0), "The transaction amount was invalid.");
    }
    /*
    @Test
    public void AuthorizeFailuresShouldReturnInputCardNumberFailedChecksum() {
        defaultPaymentMethod = PaymentMethod.Create(defaultPayload.Merge(new PaymentMethodPayload() { CardNumber = "1234123412341234" }));
        Transaction transaction = Processor.theProcessor().Authorize(defaultPaymentMethod.paymentMethodToken,
                                                           "1.00", new TransactionPayload() { billingReference = rand });
        Assert.assertFalse(transaction.success);
        Assert.assertEquals( new List<string>(){"The card number was invalid."}, transaction.Errors["input.card_number"] );
    }
    @Test
    public void AuthorizeFailuresShouldReturnInputCardNumberInvalid() {
        defaultPaymentMethod = PaymentMethod.Create(defaultPayload.Merge(new PaymentMethodPayload() { CardNumber = "5105105105105100" }));
        Transaction transaction = Processor.theProcessor().Authorize(defaultPaymentMethod.paymentMethodToken,
                                                           "1.00", new TransactionPayload() { billingReference = rand });
        Assert.assertFalse(transaction.success);
        Assert.assertEquals( new List<string>(){"The card number was invalid."}, transaction.Errors["input.card_number"] );
    }
    */

    @Test
    public void AuthorizeCvvResponsesShouldReturnProcessorCvvResultCodeM() {
        paymentMethodParams.put("cvv", "111");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.cvvResultCode, "M" );
    }
    @Test
    public void AuthorizeCvvResponsesShouldReturnProcessorCvvResultCodeN() {
        paymentMethodParams.put("cvv", "222");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.cvvResultCode, "N" );
    }
    @Test
    public void AuthorizeAvsResponsesShouldReturnProcessorAvsResultCodeY() {
        paymentMethodParams.put("address1", "1000 1st Av");
        paymentMethodParams.put("address2", "");
        paymentMethodParams.put("zip", "10101");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.avsResultCode, "Y" );
    }
    @Test
    public void AuthorizeAvsResponsesShouldReturnProcessorAvsResultCodeZ() {
        paymentMethodParams.put("address1", "");
        paymentMethodParams.put("address2", "");
        paymentMethodParams.put("zip", "10101");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.avsResultCode, "Z" );
    }
    @Test
    public void AuthorizeAvsResponsesShouldReturnProcessorAvsResultCodeN() {
        paymentMethodParams.put("address1", "123 Main St.");
        paymentMethodParams.put("address2", "");
        paymentMethodParams.put("zip", "60610");
        defaultPaymentMethod = PaymentMethod.create(paymentMethodParams);
        Transaction transaction = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, 100.0, params);
        Assert.assertTrue( transaction.success );
        Assert.assertEquals( transaction.processorResponse.avsResultCode, "N" );
    }


}
