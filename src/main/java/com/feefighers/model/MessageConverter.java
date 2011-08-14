package com.feefighers.model;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MessageConverter implements Converter {

	@Override
	public boolean canConvert(Class arg0) {
		return Message.class.equals(arg0);
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter writer,
			MarshallingContext arg2) {
		Message m = (Message) arg0;
		addAttributeIfNotNull(writer, "class", m.messageClass);
		addAttributeIfNotNull(writer, "subclass", m.messageSubClass);
		addAttributeIfNotNull(writer, "context", m.context);
		addAttributeIfNotNull(writer, "key", m.key);
		writer.setValue(m.value);		
	}	

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		Message message = new Message(reader.getAttribute("context"), reader.getAttribute("key"), reader.getValue());
		message.messageClass = reader.getAttribute("class");
		message.messageSubClass = reader.getAttribute("subclass");
		return message;
	}
	
	private void addAttributeIfNotNull(HierarchicalStreamWriter writer, String attr, String value) {
		if(value != null) {
			writer.addAttribute(attr, value);
		}
	}
}
