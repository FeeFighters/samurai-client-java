package com.feefighers.model;

import java.util.HashMap;
import java.util.Map;


public class Options {

	private Map<String, String> options = new HashMap<String, String>();
	
	public Options add(String name, String value) {
		options.put(name, value);
		return this;
	}
	
	public String get(String name) {
		return options.get(name);		
	}
	
	public Map<String, String> getMap() {
		return options;
	}
}
