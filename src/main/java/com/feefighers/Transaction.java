package com.feefighers;

import com.feefighers.model.Options;

public interface Transaction {

	void capture(double amount, Options options);
	
	void voidOperation(Options options);
	
	void credit(double amount, Options options);
	
}
