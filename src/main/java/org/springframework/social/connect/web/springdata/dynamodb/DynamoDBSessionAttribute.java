package org.springframework.social.connect.web.springdata.dynamodb;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.social.connect.web.springdata.SessionAttribute;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="SessionAttribute")
public class DynamoDBSessionAttribute implements SessionAttribute<DynamoDBSessionAttributeId> {
	
	@Id
	private DynamoDBSessionAttributeId sessionAttributeId;
	
	private Serializable value;
	
	@DynamoDBHashKey(attributeName="SessionId")
	public String getSessionId() {
		return sessionAttributeId == null ? null :sessionAttributeId.getSessionId();
	}
	public void setSessionId(String sessionId) {
		if (sessionAttributeId == null)
		{
			sessionAttributeId = new DynamoDBSessionAttributeId();
		}
		this.sessionAttributeId.setSessionId(sessionId);
	}
	@DynamoDBRangeKey(attributeName="AttributeName")
	public String getAttributeName() {
		return sessionAttributeId == null ? null :sessionAttributeId.getAttributeName();
	}
	public void setAttributeName(String attributeName) {
		if (sessionAttributeId == null)
		{
			sessionAttributeId = new DynamoDBSessionAttributeId();
		}
		this.sessionAttributeId.setAttributeName(attributeName);
	}
	
	@DynamoDBMarshalling(marshallerClass=DynamoDBSessionAttributeValueMarshaller.class)
	public Serializable getValue()
	{
		return value;
	}
	
	public void setValue(Serializable value) {
		this.value = value;
	}
	@Override
	public void setSessionAttributeId(
			DynamoDBSessionAttributeId sessionAttributeId) {
		this.sessionAttributeId = sessionAttributeId;
	}
	
}
