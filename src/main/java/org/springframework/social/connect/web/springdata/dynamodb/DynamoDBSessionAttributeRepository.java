package org.springframework.social.connect.web.springdata.dynamodb;

import org.springframework.social.connect.web.springdata.SessionAttributeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamoDBSessionAttributeRepository extends SessionAttributeRepository<DynamoDBSessionAttributeId,DynamoDBSessionAttribute> {
}
