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

public class TransactionTest {
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
        HashMap<String, String> paymentMethodParams = new HashMap<String, String>();
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

        SecureRandom r = new SecureRandom();
        rand = new BigInteger(130, r).toString(32);
    }

    @Test
    public void CaptureShouldBeSuccessful() {
        Transaction auth = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.0");
        Transaction transaction = auth.capture();
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void CaptureShouldBeSuccessfulForFullAmount() {
        Transaction auth = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.0");
        Transaction transaction = auth.capture("100.0");
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void CaptureShouldBeSuccessfulForPartialAmount() {
        Transaction auth = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.0");
        Transaction transaction = auth.capture("50.0");
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void CaptureFailuresShouldReturnProcessorTransactionInvalidWithDeclinedAuth() {
        Transaction auth = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.02");
        Transaction transaction = auth.capture();
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("processor.transaction").get(0), "This transaction type is not allowed.");
    }
    @Test
    public void CaptureFailuresShouldReturnProcessorTransactionDeclined() {
        Transaction auth = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = auth.capture("100.02");
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("processor.transaction").get(0), "The card was declined.");
    }
    @Test
    public void CaptureFailuresShouldReturnInputAmountInvalid() {
        Transaction auth = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = auth.capture("100.10");
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("input.amount").get(0), "The transaction amount was invalid.");
    }

    @Test
    public void ReverseOnCaptureShouldBeSuccessful() {
        Transaction purchase = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = purchase.reverse();
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void ReverseOnCaptureShouldBeSuccessfulForFullAmount() {
        Transaction purchase = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.0");
        Transaction transaction = purchase.reverse("100.0");
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void ReverseOnCaptureShouldBeSuccessfulForPartialAmount() {
        Transaction purchase = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.0");
        Transaction transaction = purchase.reverse("50.0");
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void ReverseOnAuthorizeShouldBeSuccessful() {
        Transaction purchase = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = purchase.reverse();
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void ReverseFailuresShouldReturnInputAmountInvalid() {
        Transaction auth = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = auth.reverse("100.10");
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("input.amount").get(0), "The transaction amount was invalid.");
    }

    @Test
    public void CreditOnCaptureShouldBeSuccessful() {
        Transaction purchase = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = purchase.credit();
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void CreditOnCaptureShouldBeSuccessfulForFullAmount() {
        Transaction purchase = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.0");
        Transaction transaction = purchase.credit("100.0");
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void CreditOnCaptureShouldBeSuccessfulForPartialAmount() {
        Transaction purchase = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.0");
        Transaction transaction = purchase.credit("50.0");
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void CreditOnAuthorizeShouldBeSuccessful() {
        Transaction purchase = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = purchase.credit();
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void CreditFailuresShouldReturnInputAmountInvalid() {
        Transaction auth = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = auth.credit("100.10");
        Assert.assertFalse(transaction.success);
        Assert.assertEquals(transaction.errors.get("input.amount").get(0), "The transaction amount was invalid.");
    }

    @Test
    public void VoidOnAuthorizedShouldBeSuccessful() {
        Transaction auth = Processor.theProcessor().authorize(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = auth.voidTransaction();
        Assert.assertTrue(transaction.success);
    }
    @Test
    public void VoidOnCapturedShouldBeSuccessful() {
        Transaction purchase = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "100.00");
        Transaction transaction = purchase.voidTransaction();
        Assert.assertTrue(transaction.success);
    }

    @Test
    public void FindShouldReturnATransaction() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("description", "description");
        params.put("descriptorName", "descriptor_name");
        params.put("descriptorPhone", "descriptor_phone");
        params.put("custom", "custom_data");
        params.put("billingReference", "ABC123"+rand);
        params.put("customerReference", "customer (123)");
        params.put("currencyCode", "USD");

        Transaction reference = Processor.theProcessor().purchase(defaultPaymentMethod.paymentMethodToken, "20.00", params);
        Transaction transaction = Transaction.find(reference.referenceId);

        Assert.assertEquals(transaction.transactionToken, reference.transactionToken);
        Assert.assertEquals(transaction.referenceId, reference.referenceId);
        Assert.assertEquals(transaction.description, "description");
        Assert.assertEquals(transaction.descriptorName, "descriptor_name");
        Assert.assertEquals(transaction.descriptorPhone, "descriptor_phone");
        Assert.assertEquals(transaction.custom, "custom_data");
        Assert.assertEquals(transaction.billingReference, "ABC123"+rand);
        Assert.assertEquals(transaction.customerReference, "customer (123)");
        Assert.assertEquals(transaction.type, Transaction.TransactionType.Purchase);
        Assert.assertEquals(transaction.amount, "20.0");
        Assert.assertEquals(transaction.currencyCode, "USD");
        Assert.assertEquals(transaction.processorToken, Processor.theProcessor().processorToken);
        Assert.assertNotNull(transaction.processorResponse);
        Assert.assertNotNull(transaction.paymentMethod);
    }
}
