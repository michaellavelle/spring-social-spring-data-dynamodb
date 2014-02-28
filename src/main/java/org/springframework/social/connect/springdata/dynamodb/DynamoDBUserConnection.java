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
package org.springframework.social.connect.springdata.dynamodb;

import org.springframework.data.annotation.Id;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.springdata.UserConnection;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author Michael Lavelle
 */
@DynamoDBTable(tableName = "UserConnection")
public class DynamoDBUserConnection implements
		UserConnection<DynamoDBUserConnectionKey> {

	private String accessToken;
	private String displayName;
	private Long expireTime;
	private String imageUrl;
	private String profileUrl;

	@Id
	private DynamoDBUserConnectionKey userConnectionKey;

	public void setConnectionKey(ConnectionKey connectionKey) {
		if (userConnectionKey == null) {
			userConnectionKey = new DynamoDBUserConnectionKey(null,
					connectionKey.getProviderId(),
					connectionKey.getProviderUserId());
		} else {
			userConnectionKey.setConnectionKey(connectionKey);
		}
	}

	@DynamoDBHashKey(attributeName = "ConnectionKey")
	@DynamoDBMarshalling(marshallerClass = DynamoDBConnectionKeyMarshaller.class)
	public ConnectionKey getConnectionKey() {

		ConnectionKey key = userConnectionKey == null ? null
				: userConnectionKey.getConnectionKey();
		return key;
	}

	private int rank;
	private String refreshToken;
	private String secret;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@DynamoDBIndexRangeKey(attributeName = "ProviderId", globalSecondaryIndexName = "UserId-ProviderId-index")
	public String getProviderId() {
		String providerId = (userConnectionKey == null || userConnectionKey
				.getConnectionKey() == null) ? null : userConnectionKey
				.getConnectionKey().getProviderId();
		return providerId;
	}

	public void setProviderId(String providerId) {
		if (userConnectionKey == null) {
			userConnectionKey = new DynamoDBUserConnectionKey(null, providerId,
					null);
		} else {
			ConnectionKey connectionKey = userConnectionKey.getConnectionKey();
			if (connectionKey != null) {
				connectionKey = new ConnectionKey(providerId,
						connectionKey.getProviderUserId());
			}
			userConnectionKey.setConnectionKey(connectionKey);

		}
	}

	@DynamoDBRangeKey
	@DynamoDBIndexHashKey(attributeName = "UserId", globalSecondaryIndexName = "UserId-ProviderId-index")
	public String getUserId() {
		return userConnectionKey.getUserId();
	}

	public void setUserId(String userId) {

		if (userConnectionKey == null) {
			userConnectionKey = new DynamoDBUserConnectionKey(userId, null,
					null);
		} else {
			userConnectionKey.setUserId(userId);
		}

	}

	@Override
	@DynamoDBIgnore
	public String getProviderUserId() {
		return userConnectionKey != null
				&& userConnectionKey.getConnectionKey() != null ? userConnectionKey
				.getConnectionKey().getProviderUserId() : null;
	}

	@Override
	public void setProviderUserId(String providerUserId) {
		if (userConnectionKey == null) {
			userConnectionKey = new DynamoDBUserConnectionKey(null, null,
					providerUserId);
		} else {
			ConnectionKey connectionKey = userConnectionKey.getConnectionKey();
			if (connectionKey != null) {
				connectionKey = new ConnectionKey(
						connectionKey.getProviderId(), providerUserId);
			}
			userConnectionKey.setConnectionKey(connectionKey);

		}
	}

	@Override
	public void setUserConnectionKey(DynamoDBUserConnectionKey userConnectionKey) {
		this.userConnectionKey = userConnectionKey;
	}

}
