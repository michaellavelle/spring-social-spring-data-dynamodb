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
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.springdata.UserConnectionRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Michael Lavelle
 */
@Repository
public interface DynamoDBUserConnectionRepository extends
		UserConnectionRepository<DynamoDBUserConnectionKey,DynamoDBUserConnection> {

	List<DynamoDBUserConnection> findByConnectionKey(@Param("connectionKey") ConnectionKey connectionKey);
	
}
