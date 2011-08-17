package com.feefighers.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("processor_response")
public class ProcessorResponse {
	@XStreamAlias("success")
	private Boolean success;
	
	@XStreamAlias("messages")
	private MessageList messageList = new MessageList();
	
	@XStreamAlias("avs_result_code")
	private String avsResultCode;
	
	public Boolean getSuccess() {
		return success;
	}
	
	public MessageList getMessageList() {
		return messageList;
	}
	
	public String getAvsResultCode() {
		return avsResultCode;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avsResultCode == null) ? 0 : avsResultCode.hashCode());
		result = prime * result
				+ ((messageList == null) ? 0 : messageList.hashCode());
		result = prime * result + ((success == null) ? 0 : success.hashCode());
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
		ProcessorResponse other = (ProcessorResponse) obj;
		if (avsResultCode == null) {
			if (other.avsResultCode != null)
				return false;
		} else if (!avsResultCode.equals(other.avsResultCode))
			return false;
		if (messageList == null) {
			if (other.messageList != null)
				return false;
		} else if (!messageList.equals(other.messageList))
			return false;
		if (success == null) {
			if (other.success != null)
				return false;
		} else if (!success.equals(other.success))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("success", this.success)
			.append("messageList", this.messageList)
			.append("avsResultCode", this.avsResultCode)
			.toString();
	}
}
