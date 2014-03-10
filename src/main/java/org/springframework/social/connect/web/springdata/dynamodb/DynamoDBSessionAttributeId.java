package org.springframework.social.connect.web.springdata.dynamodb;

import org.springframework.social.connect.web.springdata.SessionAttributeId;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class DynamoDBSessionAttributeId  implements SessionAttributeId {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sessionId;
	private String attributeName;
	
	@DynamoDBHashKey(attributeName="SessionId")
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@DynamoDBRangeKey(attributeName="AttributeName")
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
}
