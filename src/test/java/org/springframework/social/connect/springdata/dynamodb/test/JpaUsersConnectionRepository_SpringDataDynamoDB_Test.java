package org.springframework.social.connect.springdata.dynamodb.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.JpaUsersConnectionRepository;
import org.springframework.social.extension.connect.jdbc.AbstractUsersConnectionRepositoryTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
@Ignore("Ignoring integration test which requires a real DynamoDB instance to test against. Populate properties in the test environment.properties file and un-ignore this test to run")
public class JpaUsersConnectionRepository_SpringDataDynamoDB_Test extends
		AbstractUsersConnectionRepositoryTest<JpaUsersConnectionRepository> {

	@Autowired
	private JpaTemplate dataAccessor;

	@Autowired
	private DynamoDBUserConnectionRepositoryForTesting repository;

	@Autowired
	private AmazonDynamoDB client;

	protected Boolean checkIfProviderConnectionsExist(String providerId) {
		return repository.findByProviderId(providerId).size() > 0;

	}

	@Override
	protected JpaUsersConnectionRepository createUsersConnectionRepository() {

		return new JpaUsersConnectionRepository(dataAccessor,
				connectionFactoryRegistry, Encryptors.noOpText());

	}

	@Before
	public void setup() throws Exception {
		repository.deleteAll();
	}

	@After
	public void tearDown() throws Exception {
		repository.deleteAll();
	}

	@Override
	protected void insertConnection(String userId, String providerId,
			String providerUserId, int rank, String displayName,
			String profileUrl, String imageUrl, String accessToken,
			String secret, String refreshToken, Long expireTime) {
		dataAccessor.createRemoteUser(userId, providerId, providerUserId, rank,
				displayName, profileUrl, imageUrl, accessToken, secret, null,
				null);

	}

	@Override
	protected void setConnectionSignUpOnUsersConnectionRepository(
			JpaUsersConnectionRepository usersConnectionRepository,
			ConnectionSignUp connectionSignUp) {
		usersConnectionRepository.setConnectionSignUp(connectionSignUp);
	}

}
