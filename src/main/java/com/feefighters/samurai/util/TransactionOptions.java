package com.feefighters.samurai.util;

import com.feefighters.samurai.Transaction;
import java.util.Map;

public class TransactionOptions extends AttributeContainer {

	public Transaction.TransactionType type;
    public String paymentMethodToken;
    public String description;
    public String descriptorName;
    public String descriptorPhone;
    public String custom;
    public String customerReference;
    public String billingReference;
    public String currencyCode;

    public static TransactionOptions fromMap(Map<String, String> map) {
        TransactionOptions options = new TransactionOptions();
        options.paymentMethodToken = getValue(map, "paymentMethodToken");
        options.description = getValue(map, "description");
        options.descriptorName = getValue(map, "descriptorName");
        options.descriptorPhone = getValue(map, "descriptorPhone");
        options.custom = getValue(map, "custom");
        options.customerReference = getValue(map, "customerReference");
        options.billingReference = getValue(map, "billingReference");
        options.currencyCode = getValue(map, "currencyCode");
        return options;
    }

}
