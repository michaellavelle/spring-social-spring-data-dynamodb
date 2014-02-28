package org.springframework.social.connect.springdata.dynamodb.test;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.social.connect.springdata.dynamodb.DynamoDBUserConnection;
import org.springframework.social.connect.springdata.dynamodb.DynamoDBUserConnectionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamoDBUserConnectionRepositoryForTesting extends
		DynamoDBUserConnectionRepository {

	@EnableScan
	List<DynamoDBUserConnection> findByProviderId(String providerId);
	
	@EnableScan
	void deleteAll();
	
	@EnableScan
	Iterable<DynamoDBUserConnection> findAll();


	
}

