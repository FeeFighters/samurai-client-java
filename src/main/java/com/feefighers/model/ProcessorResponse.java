package com.feefighers.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("processor_response")
public class ProcessorResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@XStreamAlias("success")
	private Boolean success;
	
	@XStreamAlias("messages")
	private MessageList messageList = new MessageList();
	
	@XStreamAlias("processor_data")
	private String processorData;
	
	@XStreamAlias("avs_result_code")
	private String avsResultCode;
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	
	public MessageList getMessageList() {
		return messageList;
	}
	
	public void setProcessorData(String processorData) {
		this.processorData = processorData;
	}
	
	public String getProcessorData() {
		return processorData;
	}
	
	public void setAvsResultCode(String avsResultCode) {
		this.avsResultCode = avsResultCode;
	}
	
	public String getAvsResultCode() {
		return avsResultCode;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(avsResultCode)
			.append(messageList)
			.append(processorData)
			.append(success)
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
		final ProcessorResponse other = (ProcessorResponse) obj;
		return new EqualsBuilder()
			.append(avsResultCode, other.avsResultCode)
			.append(messageList, other.messageList)
			.append(processorData, other.processorData)
			.append(success, other.success)
			.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("avsResultCode", this.avsResultCode)		
			.append("messageList", this.messageList)
			.append("processorData", this.processorData)
			.append("success", this.success)
			.toString();
	}
	
}
