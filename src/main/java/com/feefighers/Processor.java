package com.feefighers;

import com.feefighers.model.PaymentMethod;

public interface Processor {
	
	PaymentMethod find(String paymentMethodToken);
			
	
}
