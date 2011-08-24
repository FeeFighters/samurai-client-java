package com.feefighters.model;

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
		addAttributeIfNotNull(writer, "class", m.getMessageClass());
		addAttributeIfNotNull(writer, "subclass", m.getMessageSubClass());
		addAttributeIfNotNull(writer, "context", m.getContext());
		addAttributeIfNotNull(writer, "key", m.getKey());
		writer.setValue(m.getValue());		
	}	

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		Message message = new Message(reader.getAttribute("context"), reader.getAttribute("key"), reader.getValue());
		message.setMessageClass(reader.getAttribute("class"));
		message.setMessageSubClass(reader.getAttribute("subclass"));
		return message;
	}
	
	private void addAttributeIfNotNull(HierarchicalStreamWriter writer, String attr, String value) {
		if(value != null) {
			writer.addAttribute(attr, value);
		}
	}
}
