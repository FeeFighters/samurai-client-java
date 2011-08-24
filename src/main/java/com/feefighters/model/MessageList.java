package com.feefighters.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class MessageList implements Serializable {

	private static final long serialVersionUID = 1L;

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
		return new HashCodeBuilder()
			.append(list)
			.append(type)
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
		final MessageList other = (MessageList) obj;
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.append(list, other.list)
			.append(type, other.type)
			.isEquals();
	}

	
}
