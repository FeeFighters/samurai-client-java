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
	public String toString() {
		return new ToStringBuilder(this)
			.append("success", this.success)
			.append("messageList", this.messageList)
			.append("avsResultCode", this.avsResultCode)
			.toString();
	}
}
