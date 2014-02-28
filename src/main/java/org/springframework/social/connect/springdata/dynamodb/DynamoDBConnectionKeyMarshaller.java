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
import org.springframework.social.connect.ConnectionKey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
/**
 * @author Michael Lavelle
 */
public class DynamoDBConnectionKeyMarshaller implements DynamoDBMarshaller<ConnectionKey> {

	@Override
	public String marshall(ConnectionKey connectionKey) {
		if (connectionKey == null) return null;
		return connectionKey.getProviderId() + "---" + connectionKey.getProviderUserId();
	}

	@Override
	public ConnectionKey unmarshall(Class<ConnectionKey> clazz, String value) {
		if (value == null) return null;
		String[] parts = value.split("---");
		return new ConnectionKey(parts[0],parts[1]);
	}

}
