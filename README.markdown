Samurai Java Client Library
===========================

Build requirements
------------------

To build the library you need the following:

* Java SDK 6
* Maven 3.x (http://maven.apache.org)

Build steps
-----------

In the directory with the library sources invoke the following command:

    mvn install
    
This will compile the Java classes, execute the tests and pack all the library files into jar file:
    
    target/samurai-1.0.jar

You can also create a single jar file with all the library dependencies by invoking the following command:
    
    mvn assembly:assembly
    
This will result in creating file:
    
    target/samurai-1.0-jar-with-dependencies.jar    

API usage
---------

### Classpath

After compiling the library as described in the previous point, you need to add the Samurai Java Client Library to your project's classpath.

If you use Maven you can achieve that by adding the following dependency to your `pom.xml`:
 
    <dependency>
      <groupId>com.feefighers</groupId>
      <artifactId>samurai</artifactId>
      <version>1.0</version>
    </dependency>    
    
### Gateway

To use the library you have to create a new instance of `com.feefighers.SamuraiGateway`.

    com.feefighers.SamuraiGateway gateway = new om.feefighers.SamuraiGateway(merchantKey, merchantPassword, processorToken);

### Payment Methods

A Payment Method is created each time a user stores their billing information in Samurai.

#### Creating a Payment Method

o let your customers create a Payment Method, place a credit card
entry form on your site like the one below.

    <form action="https://samurai.feefighters.com/v1/payment_methods" method="POST">
      <fieldset>
        <input name="redirect_url" type="hidden" value="http://yourdomain.com/anywhere" />
        <input name="merchant_key" type="hidden" value="[Your Merchant Key]" />

        <!-- Before populating the ÔcustomÕ parameter, remember to escape reserved xml characters
             like <, > and & into their safe counterparts like &lt;, &gt; and &amp; -->
        <input name="custom" type="hidden" value="Any value you want us to save with this payment method" />

        <label for="credit_card_first_name">First name</label>
        <input id="credit_card_first_name" name="credit_card[first_name]" type="text" />

        <label for="credit_card_last_name">Last name</label>
        <input id="credit_card_last_name" name="credit_card[last_name]" type="text" />

        <label for="credit_card_address_1">Address 1</label>
        <input id="credit_card_address_1" name="credit_card[address_1]" type="text" />

        <label for="credit_card_address_2">Address 2</label>
        <input id="credit_card_address_2" name="credit_card[address_2]" type="text" />

        <label for="credit_card_city">City</label>
        <input id="credit_card_city" name="credit_card[city]" type="text" />

        <label for="credit_card_state">State</label>
        <input id="credit_card_state" name="credit_card[state]" type="text" />

        <label for="credit_card_zip">Zip</label>
        <input id="credit_card_zip" name="credit_card[zip]" type="text" />

        <label for="credit_card_card_type">Card Type</label>
        <select id="credit_card_card_type" name="credit_card[card_type]">
          <option value="visa">Visa</option>
          <option value="master">MasterCard</option>
        </select>

        <label for="credit_card_card_number">Card Number</label>
        <input id="credit_card_card_number" name="credit_card[card_number]" type="text" />

        <label for="credit_card_verification_value">Security Code</label>
        <input id="credit_card_verification_value" name="credit_card[cvv]" type="text" />

        <label for="credit_card_month">Expires on</label>
        <input id="credit_card_month" name="credit_card[expiry_month]" type="text" />
        <input id="credit_card_year" name="credit_card[expiry_year]" type="text" />

        <button type="submit">Submit Payment</button>
      </fieldset>
    </form>

After the form submits to Samurai, the user's browser will be returned to the
URL that you specify in the redirect_url field, with an additional query
parameter containing the `payment_method_token`. You should save the
`payment_method_token` and use it from this point forward.


#### Fetching a Payment Method

To retrieve the payment method and ensure that the sensitive data is valid:

    com.feefighers.model.PaymentProcessor pm = gateway.processor().find(payment_method_token);
    pm.getSensitiveDataValid();

**NB:** Samurai will not validate any non-sensitive data so it is up to your
application to perform any additional validation on the payment_method.

#### Updating a Payment Method

You can update the payment method by directly updating its properties and then saving the object:

    pm.setFirstName("Graeme");
    gateway.processor().save(pm);
    
#### Retaining and Redacting a Payment Method

Unless you create a transaction on a payment method right away, that payment
method will be purged from Samurai. If you want to hang on to a payment method
for a while before making an authorization or purchase on it, you must retain it:

    gateway.processor().retain(pm);
    
If you are finished with a payment method that you have either previously retained
or done one or more transactions with, you may redact the payment method. This
removes any sensitive information from Samurai related to the payment method,
but it still keeps the transaction data for reference. No further transactions
can be processed on a redacted payment method.

    gateway.processor().redact(pm);
    
    
### Processing Transactions

Your application needs to be prepared to track several identifiers. The paymentMethodToken
identifies a payment method stored in Samurai. Each transaction processed
has a transaction_token that identifies a group of transactions (initiated with
a purchase or authorization) and a referenceId that identifies the specific
transaction.

#### Purchases and Authorizations

When you want to start to process a new purchase or authorization on a payment
method, Samurai needs to know which of your processors you want to use. You can
initiate a purchase (if your processor supports it) or an authorization against
a processor by:
    
    com.feefighers.model.Transaction purchase = gateway.processor().purchase(payment_method_token, amount, options);
    String purchaseReferenceId = purchase.getReferenceId(); // save this value, you can find the transaction with it later

An authorization is created the same way:

    com.feefighers.model.Transaction authorization = gateway.processor().authorize(payment_method_token, amount, options);
    String authorizationReferenceId = purchase.getReferenceId(); // save this value, you can find the transaction with it later    

You can specify options for either transaction type. 
Options is a `com.feefighers.model.TransactionOptions` instance that may contain:

* descriptor: a string description of the charge
* billingReference: a string reference for the transaction
* customerReference: a string that identifies the customer to your application
* custom: a custom value that Samurai will store but not forward to the processor

#### Capturing an Authorization

An authorization only puts a hold on the funds that you specified. It won't
capture the money. You'll need to call capture on the authorization to do this.
    
    com.feefighers.model.Transaction authorization = gateway.transaction().find(authorizationReferenceId);
    com.feefighers.model.Transaction capture = gateway.transaction().capture(authorization, amount, options);
    
#### Voiding a Transaction

A transaction that was recently created can be voided, if is has not been
settled. A transaction that has settled has already deposited funds into your
merchant account.

    com.feefighers.model.Transaction transaction = gateway.transaction().find(purchaseReferenceId);
    com.feefighers.model.Transaction voidTransaction = gateway.transaction().voidOperation(transaction, options);
    

#### Crediting a Transaction

Once a captured authorization or purchase has settled, you need to credit the
transaction if you want to reverse a charge.

    com.feefighers.model.Transaction purchase = gateway.transaction().find(purchaseReferenceId);
    com.feefighers.model.Transaction credit = gateway.transaction().credit(purchase, amount, options);    
    