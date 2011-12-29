package com.feefighters;

import java.io.Serializable;

import com.feefighters.util.MessageConverter;
import com.feefighters.util.XmlMarshaller;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("message")
@XStreamConverter(MessageConverter.class)
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

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
			.append("context", this.context)
			.append("key", this.key)		
			.append("messageClass", this.messageClass)
			.append("messageSubClass", this.messageSubClass)
			.append("value", this.value)
			.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(context)
			.append(key)
			.append(messageClass)
			.append(messageSubClass)
			.append(value)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Message other = (Message) obj;
		return new EqualsBuilder()
			.append(context, other.context)
			.append(key, other.key)
			.append(messageClass, other.messageClass)
			.append(messageSubClass, other.messageSubClass)
			.append(value, other.value)
			.isEquals();
	}
	
	
}
