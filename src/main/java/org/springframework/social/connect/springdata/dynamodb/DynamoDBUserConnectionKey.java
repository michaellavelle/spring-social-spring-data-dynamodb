package org.springframework.social.connect.springdata.dynamodb;

/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @author Michael Lavelle
 */
import java.io.Serializable;

import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.springdata.UserConnectionKey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

/**
 * The unique business key for a {@link UserConnection} instance. A composite key
 * that consists of the hash-key of ConnectionKey ( providerId (e.g. "facebook") plus providerUserId (e.g.
 * "125660") ) and range-key of userId. 
 * 
 * @author Michael Lavelle
 */
@SuppressWarnings("serial")
public final class DynamoDBUserConnectionKey implements Serializable,
		UserConnectionKey {

	private ConnectionKey connectionKey;

	private String userId;

	/**
	 * Creates a new {@link ConnectionKey}.
	 * 
	 * @param providerId
	 *            the id of the provider e.g. facebook
	 * @param providerUserId
	 *            id of the provider user account e.g. '125660'
	 */
	public DynamoDBUserConnectionKey(String userId, String providerId,
			String providerUserId) {
		this.connectionKey = new ConnectionKey(providerId, providerUserId);
		this.userId = userId;
	}

	public void setConnectionKey(ConnectionKey connectionKey) {
		this.connectionKey = connectionKey;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@DynamoDBHashKey(attributeName = "ConnectionKey")
	@DynamoDBMarshalling(marshallerClass = DynamoDBConnectionKeyMarshaller.class)
	public ConnectionKey getConnectionKey() {
		return connectionKey;
	}

	@DynamoDBRangeKey(attributeName = "UserId")
	public String getUserId() {
		return userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((connectionKey == null) ? 0 : connectionKey.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		DynamoDBUserConnectionKey other = (DynamoDBUserConnectionKey) obj;
		if (connectionKey == null) {
			if (other.connectionKey != null)
				return false;
		} else if (!connectionKey.equals(other.connectionKey))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserConnectionKey [connectionKey=" + connectionKey
				+ ", userId=" + userId + "]";
	}

	@Override
	public String getProviderId() {
		return connectionKey == null ? null : connectionKey.getProviderId();
	}

	@Override
	public String getProviderUserId() {
		return connectionKey == null ? null : connectionKey.getProviderUserId();
	}

	// object identity

}
