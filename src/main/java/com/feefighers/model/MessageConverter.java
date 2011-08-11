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
		writer.addAttribute("class", m.messageClass);
		writer.addAttribute("context", m.context);
		writer.addAttribute("key", m.key);
		writer.setValue(m.value);
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		return new Message(reader.getAttribute("class"), reader.getAttribute("context"), reader.getAttribute("key"), reader.getValue());
	}
	
}
