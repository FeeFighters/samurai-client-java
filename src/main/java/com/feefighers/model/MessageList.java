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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		MessageList other = (MessageList) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
}
