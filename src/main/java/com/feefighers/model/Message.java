package com.feefighers.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("message")
@XStreamConverter(MessageConverter.class)
public class Message {
	
	private String messageClass;

	private String messageSubClass;

	private String context;

	private String key;
	
	private String value;
	
	static {
		XmlMarshaller.registerModelClass(Message.class);
	}
	
	public Message(String context, String key, String value) {
		super();
		this.context = context;
		this.key = key;
		this.value = value;
	}

	public String getMessageClass() {
		return messageClass;
	}

	public String getMessageSubClass() {
		return messageSubClass;
	}

	public String getContext() {
		return context;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}
	
	public void setMessageSubClass(String messageSubClass) {
		this.messageSubClass = messageSubClass;
	}
	
	@Override
	public String toString() {		
		return new ToStringBuilder(this)
			.append("messageClass", this.messageClass)
			.append("messageSubClass", this.messageSubClass)
			.append("context", this.context)
			.append("key", this.key)
			.append("value", this.value)
			.toString();
	}
}
