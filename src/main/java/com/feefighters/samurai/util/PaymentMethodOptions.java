package com.feefighters.samurai.util;

import java.util.Map;

public class PaymentMethodOptions extends AttributeContainer  {

    public String paymentMethodToken;
    public String firstName;
    public String lastName;
    public String address1;
    public String address2;
    public String city;
    public String state;
    public String zip;
    public String country;
    public String cardNumber;
    public String cvv;
    public Integer expiryMonth;
    public Integer expiryYear;

    public static PaymentMethodOptions fromMap(Map<String, String> map) {
        PaymentMethodOptions options = new PaymentMethodOptions();
        options.paymentMethodToken = getValue(map, "paymentMethodToken");
        options.firstName = getValue(map, "firstName");
        options.lastName = getValue(map, "lastName");
        options.address1 = getValue(map, "address1");
        options.address2 = getValue(map, "address2");
        options.city = getValue(map, "city");
        options.state = getValue(map, "state");
        options.zip = getValue(map, "zip");
        options.country = getValue(map, "country");
        options.cardNumber = getValue(map, "cardNumber");
        options.cvv = getValue(map, "cvv");
        options.expiryMonth = getIntegerValue(map, "expiryMonth");
        options.expiryYear = getIntegerValue(map, "expiryYear");
        return options;
    }
}
