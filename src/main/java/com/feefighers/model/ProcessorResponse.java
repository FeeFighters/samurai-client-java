package com.feefighers.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("processor_response")
public class ProcessorResponse {
	@XStreamAlias("success")
	private Boolean success;
	
	@XStreamAlias("messages")
	private MessageList messageList = new MessageList();
	
	@XStreamAlias("avs_result_code")
	private String avsResultCode;
	
	public boolean getSuccess() {
		return success;
	}
	
	public MessageList getMessageList() {
		return messageList;
	}
	
	public String getAvsResultCode() {
		return avsResultCode;
	}
}
