package org.springframework.social.connect.web.springdata.dynamodb;

import java.io.Serializable;

import org.springframework.social.connect.web.springdata.AbstractSpringDataSessionStrategy;



public class DynamoDBSessionStrategy extends
		AbstractSpringDataSessionStrategy<DynamoDBSessionAttributeId, DynamoDBSessionAttribute> {

	@Override
	protected DynamoDBSessionAttributeId createSessionAttributeId(
			String sessionId, String name) {
		DynamoDBSessionAttributeId id = new DynamoDBSessionAttributeId();
		id.setSessionId(sessionId);
		id.setAttributeName(name);
		return id;
		}

	@Override
	protected DynamoDBSessionAttribute createSessionAttribute(
			DynamoDBSessionAttributeId sessionAttributeId, Object value) {
		DynamoDBSessionAttribute attribute = new DynamoDBSessionAttribute();
		attribute.setSessionAttributeId(sessionAttributeId);
		attribute.setValue((Serializable)value);
		return attribute;
	}

}
