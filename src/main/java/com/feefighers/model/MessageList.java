package com.feefighers.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class MessageList {

	@XStreamAlias("type")
	@XStreamAsAttribute
	private String type = "array";
	
	@XStreamImplicit
	private List<Message> list = new ArrayList<Message>();

	static {
		XmlMarshaller.registerModelClass(MessageList.class);
	}
	
	public List<Message> getList() {
		return list;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("type", this.type)
			.append("list", this.list)
			.toString();
	}
}
