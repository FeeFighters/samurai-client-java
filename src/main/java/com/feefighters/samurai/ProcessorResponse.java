package com.feefighters.samurai;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.feefighters.samurai.util.XmlMarshaller;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("processor_response")
public class ProcessorResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@XStreamAlias("success")
	public Boolean success;
	
	@XStreamAlias("messages")
    public MessageList messages = new MessageList();
	
	@XStreamAlias("processor_data")
    public String processorData;
	
	@XStreamAlias("avs_result_code")
    public String avsResultCode;

    @XStreamAlias("cvv_result_code")
    public String cvvResultCode;

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(avsResultCode)
			.append(messages)
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
			.append(messages, other.messages)
			.append(processorData, other.processorData)
			.append(success, other.success)
			.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("avsResultCode", this.avsResultCode)		
			.append("messageList", this.messages)
			.append("processorData", this.processorData)
			.append("success", this.success)
			.toString();
	}
	
    public static ProcessorResponse fromErrorXml(String errorXml) {
        ProcessorResponse processorResponse = new ProcessorResponse();
        Pattern pattern = Pattern.compile("<messages[^>]*>.*</messages>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(errorXml);
        matcher.find();
        errorXml = matcher.group();
        processorResponse.messages = (MessageList) XmlMarshaller.fromXml(errorXml);
        processorResponse.success = false;
        return processorResponse;
    }
    
}
