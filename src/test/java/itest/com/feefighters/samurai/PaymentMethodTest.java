package itest.com.feefighters.samurai;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import com.feefighters.samurai.SamuraiGateway;
import com.feefighters.samurai.PaymentMethod;
import com.feefighters.samurai.http.HttpException;
import com.feefighters.samurai.util.PaymentMethodOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PaymentMethodTest {

    private HashMap<String, String> params;
    private HashMap<String, String> updateParams;

    @BeforeTest
    public void beforeAll() throws IOException {
        Properties config = new Properties();
        config.load(getClass().getResourceAsStream("/config.properties"));
        SamuraiGateway.configure(config);
    }

	@BeforeMethod
	public void beforeEach() {
        params = new HashMap<String, String>();
        params.put("firstName", "FirstName");
        params.put("lastName", "LastName");
        params.put("address1", "123 Main St.");
        params.put("address2", "Apt #3");
        params.put("city", "Chicago");
        params.put("state", "IL");
        params.put("zip", "10101");
        params.put("cardNumber", "4111-1111-1111-1111");
        params.put("cvv", "123");
        params.put("expiryMonth", "3");
        params.put("expiryYear", "2015");
        
        updateParams = new HashMap<String, String>();
        updateParams.put("firstName", "FirstNameX");
        updateParams.put("lastName", "LastNameX");
        updateParams.put("address1", "123 Main St.X");
        updateParams.put("address2", "Apt #3X");
        updateParams.put("city", "ChicagoX");
        updateParams.put("state", "IL");
        updateParams.put("zip", "10101");
        updateParams.put("cardNumber", "5454-5454-5454-5454");
        updateParams.put("cvv", "456");
        updateParams.put("expiryMonth", "5");
        updateParams.put("expiryYear", "2016");
	}

    @Test
    public void S2SCreateShouldBeSuccessful() {
        PaymentMethod pm = PaymentMethod.create(params);

        Assert.assertEquals((boolean)pm.sensitiveDataValid, true);
        Assert.assertEquals((boolean)pm.expirationValid, true);
        Assert.assertEquals(pm.firstName, params.get("firstName"));
        Assert.assertEquals(pm.lastName, params.get("lastName"));
        Assert.assertEquals(pm.address1, params.get("address1"));
        Assert.assertEquals(pm.address2, params.get("address2"));
        Assert.assertEquals(pm.city, params.get("city"));
        Assert.assertEquals(pm.state, params.get("state"));
        Assert.assertEquals(pm.zip, params.get("zip"));
        Assert.assertEquals(pm.lastFourDigits, params.get("cardNumber").replaceAll("-", "").substring(12, 16));
        Assert.assertEquals((int)pm.expiryMonth, Integer.parseInt(params.get("expiryMonth")));
        Assert.assertEquals((int)pm.expiryYear, Integer.parseInt(params.get("expiryYear")));
    }
    @Test
    public void S2SCreateShouldBeSuccessfulUsingOptions() {
        PaymentMethodOptions options = PaymentMethodOptions.fromMap(params);
        PaymentMethod pm = PaymentMethod.create(options);

        Assert.assertEquals((boolean)pm.sensitiveDataValid, true);
        Assert.assertEquals((boolean)pm.expirationValid, true);
        Assert.assertEquals(pm.firstName, params.get("firstName"));
        Assert.assertEquals(pm.lastName, params.get("lastName"));
        Assert.assertEquals(pm.address1, params.get("address1"));
        Assert.assertEquals(pm.address2, params.get("address2"));
        Assert.assertEquals(pm.city, params.get("city"));
        Assert.assertEquals(pm.state, params.get("state"));
        Assert.assertEquals(pm.zip, params.get("zip"));
        Assert.assertEquals(pm.lastFourDigits, params.get("cardNumber").replaceAll("-", "").substring(12, 16));
        Assert.assertEquals((int)pm.expiryMonth, Integer.parseInt(params.get("expiryMonth")));
        Assert.assertEquals((int)pm.expiryYear, Integer.parseInt(params.get("expiryYear")));
    }

    @Test
    public void S2SCreateFailOnInputCardNumberShouldReturnIsBlank() {
        params.put("cardNumber", "");
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.card_number").get(0), "The card number was blank.");
    }
    @Test
    public void S2SCreateFailOnInputCardNumberShouldReturnTooShort() {
        params.put("cardNumber", "4111-1");
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.card_number").get(0), "The card number was too short.");
    }
    @Test
    public void S2SCreateFailOnInputCardNumberShouldReturnTooLong() {
        params.put("cardNumber", "4111-1111-1111-1111-11");
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.card_number").get(0), "The card number was too long.");
    }
    @Test
    public void S2SCreateFailOnInputCardNumberShouldReturnFailedChecksum() {
        params.put("cardNumber", "4111-1111-1111-1234");
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.card_number").get(0), "The card number was invalid.");
    }
    @Test
    public void S2SCreateFailOnInputCvvShouldReturnTooShort() {
        params.put("cvv", "1");
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.cvv").get(0), "The CVV was too short.");
    }
    @Test
    public void S2SCreateFailOnInputCvvShouldReturnTooLong() {
        params.put("cvv", "111111");
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.cvv").get(0), "The CVV was too long.");
    }
    @Test
    public void S2SCreateFailOnInputCvvShouldReturnNotNumeric() {
        params.put("cvv", "abcd");
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.cvv").get(0), "The CVV was invalid.");
    }
//    @Test
//    public void S2SCreateFailOnInputExpiryMonthShouldReturnIsBlank() {
//        params.put("expiryMonth", "");
//        PaymentMethod pm = PaymentMethod.create(params);
//        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
//        Assert.assertEquals(pm.errors.get("input.expiry_month").get(0), "The expiration month was blank.");
//    }
//    @Test
//    public void S2SCreateFailOnInputExpiryMonthShouldReturnIsInvalid() {
//        params.put("expiryMonth", "abcd");
//        PaymentMethod pm = PaymentMethod.create(params);
//        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
//        Assert.assertEquals(pm.errors.get("input.expiry_month").get(0), "The expiration month was invalid.");
//    }
//    @Test
//    public void S2SCreateFailOnInputExpiryYearShouldReturnIsBlank() {
//        params.put("expiryYear", "");
//        PaymentMethod pm = PaymentMethod.create(params);
//        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
//        Assert.assertEquals(pm.errors.get("input.expiry_year").get(0), "The expiration year was blank.");
//    }
//    @Test
//    public void S2SCreateFailOnInputExpiryYearShouldReturnIsInvalid() {
//        params.put("expiryYear", "abcd");
//        PaymentMethod pm = PaymentMethod.create(params);
//        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
//        Assert.assertEquals(pm.errors.get("input.expiry_year").get(0), "The expiration year was invalid.");
//    }

    @Test
    public void S2SUpdateShouldBeSuccessful() {
        PaymentMethod pm = PaymentMethod.create(params);
        pm.updateAttributes(updateParams);
        pm.save();
        pm = PaymentMethod.find(pm.paymentMethodToken);

        Assert.assertEquals((boolean)pm.sensitiveDataValid, true);
        Assert.assertEquals((boolean)pm.expirationValid, true);
        Assert.assertEquals(pm.firstName, updateParams.get("firstName"));
        Assert.assertEquals(pm.lastName, updateParams.get("lastName"));
        Assert.assertEquals(pm.address1, updateParams.get("address1"));
        Assert.assertEquals(pm.address2, updateParams.get("address2"));
        Assert.assertEquals(pm.city, updateParams.get("city"));
        Assert.assertEquals(pm.state, updateParams.get("state"));
        Assert.assertEquals(pm.zip, updateParams.get("zip"));
        Assert.assertEquals(pm.lastFourDigits, updateParams.get("cardNumber").replaceAll("-", "").substring(12, 16));
        Assert.assertEquals((int)pm.expiryMonth, Integer.parseInt(updateParams.get("expiryMonth")));
        Assert.assertEquals((int)pm.expiryYear, Integer.parseInt(updateParams.get("expiryYear")));
    }
    @Test
    public void S2SUpdateShouldBeSuccessfulWithPartialData() {
        PaymentMethod pm = PaymentMethod.create(params);
        updateParams.remove("address2");
        updateParams.remove("city");
        pm.updateAttributes(updateParams);
        pm.save();
        pm = PaymentMethod.find(pm.paymentMethodToken);

        Assert.assertEquals((boolean)pm.sensitiveDataValid, true);
        Assert.assertEquals((boolean)pm.expirationValid, true);
        Assert.assertEquals(pm.firstName, updateParams.get("firstName"));
        Assert.assertEquals(pm.lastName, updateParams.get("lastName"));
        Assert.assertEquals(pm.address1, updateParams.get("address1"));
        Assert.assertEquals(pm.address2, params.get("address2"));
        Assert.assertEquals(pm.city, params.get("city"));
        Assert.assertEquals(pm.state, updateParams.get("state"));
        Assert.assertEquals(pm.zip, updateParams.get("zip"));
        Assert.assertEquals(pm.lastFourDigits, updateParams.get("cardNumber").replaceAll("-", "").substring(12, 16));
        Assert.assertEquals((int)pm.expiryMonth, Integer.parseInt(updateParams.get("expiryMonth")));
        Assert.assertEquals((int)pm.expiryYear, Integer.parseInt(updateParams.get("expiryYear")));
    }

    @Test
    public void S2SUpdateShouldBeSuccessfulPreservingSensitiveData() {
        PaymentMethod pm = PaymentMethod.create(params);
        updateParams.put("cardNumber", "****-****-****-****");
        updateParams.put("cvv", "***");
        pm.updateAttributes(updateParams);
        pm.save();
        pm = PaymentMethod.find(pm.paymentMethodToken);

        Assert.assertEquals((boolean)pm.sensitiveDataValid, true);
        Assert.assertEquals((boolean)pm.expirationValid, true);
        Assert.assertEquals(pm.firstName, updateParams.get("firstName"));
        Assert.assertEquals(pm.lastName, updateParams.get("lastName"));
        Assert.assertEquals(pm.address1, updateParams.get("address1"));
        Assert.assertEquals(pm.address2, updateParams.get("address2"));
        Assert.assertEquals(pm.city, updateParams.get("city"));
        Assert.assertEquals(pm.state, updateParams.get("state"));
        Assert.assertEquals(pm.zip, updateParams.get("zip"));
        Assert.assertEquals(pm.lastFourDigits, params.get("cardNumber").replaceAll("-", "").substring(12, 16));
        Assert.assertEquals((int)pm.expiryMonth, Integer.parseInt(updateParams.get("expiryMonth")));
        Assert.assertEquals((int)pm.expiryYear, Integer.parseInt(updateParams.get("expiryYear")));
    }

    @Test
    public void S2SUpdateFailOnInputCardNumberShouldReturnTooShort() {
        updateParams.put("cardNumber", "4111-1");
        PaymentMethod pm = PaymentMethod.create(params);
        pm.updateAttributes(updateParams);
        pm.save();
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.card_number").get(0), "The card number was too short.");
    }
    @Test
    public void S2SUpdateFailOnInputCardNumberShouldReturnTooLong() {
        updateParams.put("cardNumber", "4111-1111-1111-1111-11");
        PaymentMethod pm = PaymentMethod.create(params);
        pm.updateAttributes(updateParams);
        pm.save();
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.card_number").get(0), "The card number was too long.");
    }
    @Test
    public void S2SUpdateFailOnInputCardNumberShouldReturnFailedChecksum() {
        updateParams.put("cardNumber", "4111-1111-1111-1234");
        PaymentMethod pm = PaymentMethod.create(params);
        pm.updateAttributes(updateParams);
        pm.save();
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.card_number").get(0), "The card number was invalid.");
    }
    @Test
    public void S2SUpdateFailOnInputCvvShouldReturnTooShort() {
        updateParams.put("cvv", "1");
        PaymentMethod pm = PaymentMethod.create(params);
        pm.updateAttributes(updateParams);
        pm.save();
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.cvv").get(0), "The CVV was too short.");
    }
    @Test
    public void S2SUpdateFailOnInputCvvShouldReturnTooLong() {
        updateParams.put("cvv", "111111");
        PaymentMethod pm = PaymentMethod.create(params);
        pm.updateAttributes(updateParams);
        pm.save();
        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
        Assert.assertEquals(pm.errors.get("input.cvv").get(0), "The CVV was too long.");
    }
//    @Test
//    public void S2SUpdateFailOnInputExpiryMonthShouldReturnIsBlank() {
//        updateParams.put("expiryMonth", "");
//        PaymentMethod pm = PaymentMethod.create(params);
//        pm.updateAttributes(updateParams);
//        pm.save();
//        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
//        Assert.assertEquals(pm.errors.get("input.expiry_month").get(0), "The expiration month was blank.");
//    }
//    @Test
//    public void S2SUpdateFailOnInputExpiryMonthShouldReturnIsInvalid() {
//        updateParams.put("expiryMonth", "abcd");
//        PaymentMethod pm = PaymentMethod.create(params);
//        pm.updateAttributes(updateParams);
//        pm.save();
//        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
//        Assert.assertEquals(pm.errors.get("input.expiry_month").get(0), "The expiration month was invalid.");
//    }
//    @Test
//    public void S2SUpdateFailOnInputExpiryYearShouldReturnIsBlank() {
//        updateParams.put("expiryYear", "");
//        PaymentMethod pm = PaymentMethod.create(params);
//        pm.updateAttributes(updateParams);
//        pm.save();
//        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
//        Assert.assertEquals(pm.errors.get("input.expiry_year").get(0), "The expiration year was blank.");
//    }
//    @Test
//    public void S2SUpdateFailOnInputExpiryYearShouldReturnIsInvalid() {
//        updateParams.put("expiryYear", "abcd");
//        PaymentMethod pm = PaymentMethod.create(params);
//        pm.updateAttributes(updateParams);
//        pm.save();
//        pm = PaymentMethod.find(pm.paymentMethodToken);
//        Assert.assertEquals((boolean)pm.sensitiveDataValid, false);
//        Assert.assertEquals(pm.errors.get("input.expiry_year").get(0), "The expiration year was invalid.");
//    }

    @Test
    public void FindShouldBeSuccessful() {
        PaymentMethod pm = PaymentMethod.create(params);

        Assert.assertEquals((boolean)pm.sensitiveDataValid, true);
        Assert.assertEquals((boolean)pm.expirationValid, true);
        Assert.assertEquals(pm.firstName, params.get("firstName"));
        Assert.assertEquals(pm.lastName, params.get("lastName"));
        Assert.assertEquals(pm.address1, params.get("address1"));
        Assert.assertEquals(pm.address2, params.get("address2"));
        Assert.assertEquals(pm.city, params.get("city"));
        Assert.assertEquals(pm.state, params.get("state"));
        Assert.assertEquals(pm.zip, params.get("zip"));
        Assert.assertEquals(pm.lastFourDigits, params.get("cardNumber").replaceAll("-", "").substring(12, 16));
        Assert.assertEquals((int)pm.expiryMonth, Integer.parseInt(params.get("expiryMonth")));
        Assert.assertEquals((int)pm.expiryYear, Integer.parseInt(params.get("expiryYear")));
    }
    @Test
    public void FindShouldFailOnAnInvalidToken() {
        Exception exception = null;
        try {
            PaymentMethod.find("abc123");
        } catch (HttpException ex) {
            exception = ex;
        }
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception.getMessage().contains("Couldn't find PaymentMethod with token = abc123"));
    }

    @Test
    public void RedactShouldBeSuccessful() {
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.redacted, false);
        pm.redact();
        Assert.assertEquals((boolean)pm.redacted, true);

        Assert.assertEquals((boolean)pm.sensitiveDataValid, true);
        Assert.assertEquals((boolean)pm.expirationValid, true);
        Assert.assertEquals(pm.firstName, params.get("firstName"));
        Assert.assertEquals(pm.lastName, params.get("lastName"));
        Assert.assertEquals(pm.address1, params.get("address1"));
        Assert.assertEquals(pm.address2, params.get("address2"));
        Assert.assertEquals(pm.city, params.get("city"));
        Assert.assertEquals(pm.state, params.get("state"));
        Assert.assertEquals(pm.zip, params.get("zip"));
        Assert.assertEquals(pm.lastFourDigits, params.get("cardNumber").replaceAll("-", "").substring(12, 16));
        Assert.assertEquals((int)pm.expiryMonth, Integer.parseInt(params.get("expiryMonth")));
        Assert.assertEquals((int)pm.expiryYear, Integer.parseInt(params.get("expiryYear")));
    }

    @Test
    public void RetainShouldBeSuccessful() {
        PaymentMethod pm = PaymentMethod.create(params);
        Assert.assertEquals((boolean)pm.retained, false);
        pm.retain();
        Assert.assertEquals((boolean)pm.retained, true);

        Assert.assertEquals((boolean)pm.sensitiveDataValid, true);
        Assert.assertEquals((boolean)pm.expirationValid, true);
        Assert.assertEquals(pm.firstName, params.get("firstName"));
        Assert.assertEquals(pm.lastName, params.get("lastName"));
        Assert.assertEquals(pm.address1, params.get("address1"));
        Assert.assertEquals(pm.address2, params.get("address2"));
        Assert.assertEquals(pm.city, params.get("city"));
        Assert.assertEquals(pm.state, params.get("state"));
        Assert.assertEquals(pm.zip, params.get("zip"));
        Assert.assertEquals(pm.lastFourDigits, params.get("cardNumber").replaceAll("-", "").substring(12, 16));
        Assert.assertEquals((int)pm.expiryMonth, Integer.parseInt(params.get("expiryMonth")));
        Assert.assertEquals((int)pm.expiryYear, Integer.parseInt(params.get("expiryYear")));
    }
}
