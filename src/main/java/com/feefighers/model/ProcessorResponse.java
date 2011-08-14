package com.feefighers.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("processor_response")
public class ProcessorResponse {
	@XStreamAlias("success")
	private Boolean success;
	
	@XStreamAlias("messages")
	private MessageList messageList = new MessageList();
}
