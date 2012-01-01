package com.feefighters.samurai;

import com.feefighters.samurai.http.HttpException;
import com.feefighters.samurai.util.TransactionOptions;

import java.math.BigDecimal;
import java.util.HashMap;

public class Processor {
    private SamuraiGateway gateway;

    public String processorToken;

    public Processor() {
        this(SamuraiGateway.defaultGateway().processorToken);
    }
    public Processor(String processorToken) {
        this(processorToken, SamuraiGateway.defaultGateway());
    }
    public Processor(String processorToken, SamuraiGateway gateway) {
        this.processorToken = processorToken;
        this.gateway = gateway;
    }
    public static Processor theProcessor() {
        return new Processor();
    }

    public Transaction purchase(String paymentMethodToken, BigDecimal amount) {
        return purchase(paymentMethodToken, amount, new TransactionOptions());
    }
    public Transaction purchase(String paymentMethodToken, String amount) {
        return purchase(paymentMethodToken, new BigDecimal(amount), new TransactionOptions());
    }
    public Transaction purchase(String paymentMethodToken, double amount) {
        return purchase(paymentMethodToken, new BigDecimal(amount), new TransactionOptions());
    }
    public Transaction purchase(String paymentMethodToken, String amount, TransactionOptions options) {
        return purchase(paymentMethodToken, new BigDecimal(amount), options);
    }
    public Transaction purchase(String paymentMethodToken, double amount, TransactionOptions options) {
        return purchase(paymentMethodToken, new BigDecimal(amount), options);
    }
    public Transaction purchase(String paymentMethodToken, String amount, HashMap<String, String> options) {
        return purchase(paymentMethodToken, new BigDecimal(amount), TransactionOptions.fromMap(options));
    }
    public Transaction purchase(String paymentMethodToken, double amount, HashMap<String, String> options) {
        return purchase(paymentMethodToken, new BigDecimal(amount), TransactionOptions.fromMap(options));
    }
    public Transaction purchase(String paymentMethodToken, BigDecimal amount, HashMap<String, String> options) {
        return purchase(paymentMethodToken, amount, TransactionOptions.fromMap(options));
    }
    public Transaction purchase(String paymentMethodToken, BigDecimal amount, TransactionOptions options) {
        return executeTransaction(Transaction.TransactionType.Purchase, paymentMethodToken, amount, options);
    }

    public Transaction authorize(String paymentMethodToken, BigDecimal amount) {
        return authorize(paymentMethodToken, amount, new TransactionOptions());
    }
    public Transaction authorize(String paymentMethodToken, String amount) {
        return authorize(paymentMethodToken, new BigDecimal(amount), new TransactionOptions());
    }
    public Transaction authorize(String paymentMethodToken, double amount) {
        return authorize(paymentMethodToken, new BigDecimal(amount), new TransactionOptions());
    }
    public Transaction authorize(String paymentMethodToken, String amount, TransactionOptions options) {
        return authorize(paymentMethodToken, new BigDecimal(amount), options);
    }
    public Transaction authorize(String paymentMethodToken, double amount, TransactionOptions options) {
        return authorize(paymentMethodToken, new BigDecimal(amount), options);
    }
    public Transaction authorize(String paymentMethodToken, String amount, HashMap<String, String> options) {
        return authorize(paymentMethodToken, new BigDecimal(amount), TransactionOptions.fromMap(options));
    }
    public Transaction authorize(String paymentMethodToken, double amount, HashMap<String, String> options) {
        return authorize(paymentMethodToken, new BigDecimal(amount), TransactionOptions.fromMap(options));
    }
    public Transaction authorize(String paymentMethodToken, BigDecimal amount, HashMap<String, String> options) {
        return authorize(paymentMethodToken, amount, TransactionOptions.fromMap(options));
    }
    public Transaction authorize(String paymentMethodToken, BigDecimal amount, TransactionOptions options) {
        return executeTransaction(Transaction.TransactionType.Authorize, paymentMethodToken, amount, options);
    }

    protected Transaction executeTransaction(Transaction.TransactionType type,
                                             String paymentMethodToken,
                                             BigDecimal amount,
                                             TransactionOptions options) {
        final Transaction transaction = new Transaction(options);

        transaction.paymentMethodToken = paymentMethodToken;
        transaction.amount = String.valueOf(amount);

        try {
            final String url = "processors/" + gateway.processorToken + "/" + type.name().toLowerCase() + ".xml";
            final String xml = gateway.http().post(url, transaction.toXml());
            Transaction newTx = Transaction.fromXml(xml);
            newTx.gateway = this.gateway;
            return newTx;

        } catch (HttpException ex) {
            Transaction tx = new Transaction();
            tx.processorResponse = ProcessorResponse.fromErrorXml(ex.getMessage());
            return tx;
        }

    }

}
