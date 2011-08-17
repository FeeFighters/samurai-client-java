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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result
				+ ((messageClass == null) ? 0 : messageClass.hashCode());
		result = prime * result
				+ ((messageSubClass == null) ? 0 : messageSubClass.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (messageClass == null) {
			if (other.messageClass != null)
				return false;
		} else if (!messageClass.equals(other.messageClass))
			return false;
		if (messageSubClass == null) {
			if (other.messageSubClass != null)
				return false;
		} else if (!messageSubClass.equals(other.messageSubClass))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
