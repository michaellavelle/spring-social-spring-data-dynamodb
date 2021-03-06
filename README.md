spring-social-spring-data-dynamodb
==================================

A UsersConnectionRepository/ConnectionRepository implementation using DynamoDB for persistence as an alternative to the JDBC versions in spring-social-core. 

Also include is a DynamoDB-backed SessionStategy implementation which can be wired into Spring Social's ConnectController, ProviderSignInController and SocialSignin Spring-Social-Security components

# Quick Start #

## Add DynamoDB Support ##

- 1 Download the jar though Maven:


```xml
<repository>
	<id>opensourceagility-snapshots</id>
	<url>http://repo.opensourceagility.com/snapshots</url
</repository>
```

```xml
<dependency>
  <groupId>org.springframework.social</groupId>
  <artifactId>spring-social-spring-data-dynamodb</artifactId>
  <version>1.1.0-SNAPSHOT</version>
</dependency>
```

- 2 Setup DynamoDB configuration as well as enabling Spring Social Spring Data DynamoDB repository support.

Configure "basePackages" to be the packages for the relevant dynamodb repositories

( "org.springframework.social.connect.springdata.dynamodb" for DynamoDB-backed connection repository, or

  "org.springframework.social.connect.web.springdata.dynamodb for DynamoDB-backed SessionStrategy ) 

```java
@Configuration
@EnableDynamoDBRepositories(basePackages = "org.springframework.social.connect.springdata.dynamodb")
public class DynamoDBConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(
				amazonAWSCredentials());
		if (StringUtils.isNotEmpty(amazonDynamoDBEndpoint)) {
			amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
		}
		return amazonDynamoDB;
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
	}

}
```

or in xml...

```xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jdbc="http://www.springframework.org/schema/jdbc"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa"
       xmlns:dynamodb="http://docs.socialsignin.org/schema/data/dynamodb"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://docs.socialsignin.org/schema/data/dynamodb
                           http://docs.socialsignin.org/schema/data/dynamodb/spring-dynamodb.xsd">

  <bean id="amazonDynamoDB" class="com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient">
    <constructor-arg ref="amazonAWSCredentials" />
    <property name="endpoint" value="${amazon.dynamodb.endpoint}" />
  </bean>
  
  <bean id="amazonAWSCredentials" class="com.amazonaws.auth.BasicAWSCredentials">
    <constructor-arg value="${amazon.aws.accesskey}" />
    <constructor-arg value="${amazon.aws.secretkey}" />
  </bean>
  
  <dynamodb:repositories base-package="org.springframework.social.connect.springdata.dynamodb" amazon-dynamodb-ref="amazonDynamoDB" />
  
</beans>

```

## To use DynamoDB-backed UsersConnection Repository ##


- 1 Ensure you have enabled DynamoDB Repositories for the basePackage "org.springframework.social.connect.springdata.dynamodb"

- 2 Create a DynamoDB hash and range key table in AWS console:

a) With table name 'UserConnection' and with hash key attribute name "ConnectionKey" and range key attribute name "UserId"
b) With global secondary index "UserId-ProviderId-index" with hash key "UserId" and range key "ProviderId"

- 3 Create a DynamoDB JpaTemplate bean instance in your application context

```
@Bean
public JpaTemplate springDataTemplate()
{
	return new DynamoDBUserConnectionRepositoryJpaTemplateAdapter();
}
```
- 4 Replace the JdbcUsersConnectionRepository returned by the SocialConfigurer.getUsersConnectionrepository method of your SocialConfig with JpaUsersConnectionRepository,
wiring in your JpaTemplate bean

```
public class SocialConfig implements SocialConfigurer 
{
....
@Override
	public UsersConnectionRepository getUsersConnectionRepository(

		UsersConnectionRepository usersConnectionRepository = new JpaUsersConnectionRepository(
				springDataTemplate(), connectionFactoryLocator, Encryptors.noOpText());
                
		return usersConnectionRepository;
		
	}
```

## To use a DynamoDB-backed SessionStrategy ##

- 1 Ensure you have enabled DynamoDB Repositories for the basePackage "org.springframework.social.connect.web.springdata.dynamodb"

- 2 Configure DynamoDBSessionStrategy as a bean in your application context:

```
@Bean
public SessionStrategy sessionStrategy()
{
	return new DynamoDBSessionStrategy();
}

```


- 3 Configure a SessionIdSource bean in your application context, eg.

```
@Bean
public SessionIdSource sessionIdSource()
{
	return new CookieSessionIdSource();
}
```

- 4 Wire in your sessionStrategy bean into your ConnectController and ProviderSignInController beans
