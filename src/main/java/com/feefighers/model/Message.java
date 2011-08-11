package com.feefighers.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("message")
@XStreamConverter(MessageConverter.class)
public class Message {

//	@XStreamAlias("class")
//	@XStreamAsAttribute
	@XStreamOmitField
	String messageClass;
	
//	@XStreamAsAttribute
	@XStreamOmitField
	String context;
	
	@XStreamOmitField
//	@XStreamAsAttribute
	String key;
	
	String value;
	
	static {
		XmlMarshaller.registerModelClass(Message.class);
	}
	
	public Message(String messageClass, String context, String key, String value) {
		super();
		this.messageClass = messageClass;
		this.context = context;
		this.key = key;
		this.value = value;
	}

	
}