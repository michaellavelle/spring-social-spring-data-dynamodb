package org.springframework.social.connect.web.springdata.dynamodb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.codec.Base64;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

public class DynamoDBSessionAttributeValueMarshaller implements
		DynamoDBMarshaller<Serializable> {

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public String marshall(Serializable getterReturnResult) {
		return objectToString(getterReturnResult);
	}

	@Override
	public Serializable unmarshall(Class<Serializable> clazz, String obj) {
		return stringToObject(obj, clazz);
	}

	public String objectToString(Serializable object) {
	
			String encoded = null;
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						byteArrayOutputStream);
				objectOutputStream.writeObject(object);
				objectOutputStream.close();
				encoded = new String(Base64.encode(byteArrayOutputStream
						.toByteArray()));
			} catch (IOException e) {
				logger.error(e);
				throw new RuntimeException(e);
			}
			return encoded;
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T stringToObject(String string,
			Class<T> clazz) {
		byte[] bytes = Base64.decode(string.getBytes());
		T object = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new ByteArrayInputStream(bytes));
			object = (T) objectInputStream.readObject();
		} catch (IOException e) {
			logger.error(e);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		} catch (ClassCastException e) {
			logger.error(e);
		}
		return object;
	}

}
