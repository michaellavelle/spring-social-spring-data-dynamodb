/*
 * Copyright 2011 the original author or authors.
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
package org.springframework.social.connect.springdata.dynamodb.test;

import org.apache.commons.lang3.StringUtils;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * @author Michael Lavelle
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = "org.springframework.social.connect.springdata.dynamodb.test")
@PropertySource("/environment.properties")
public class DynamoDBTestConfig {

	@Autowired
	private Environment environment;
	
	@Bean
	public AmazonDynamoDB amazonDynamoDB() {


		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(
				amazonAWSCredentials());
				
		String amazonDynamoDBEndpoint = environment.getProperty("amazon.dynamodb.endpoint");
		
		if (StringUtils.isNotEmpty(amazonDynamoDBEndpoint)) {
			amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
		}
		

		return amazonDynamoDB;

	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		
		String amazonAWSAccessKey = environment.getProperty("amazon.aws.accesskey");
		String amazonAWSSecretKey = environment.getProperty("amazon.aws.secretkey");
		return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
	}
	

}
