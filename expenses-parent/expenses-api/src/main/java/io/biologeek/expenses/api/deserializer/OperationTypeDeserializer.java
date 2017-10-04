package io.biologeek.expenses.api.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.biologeek.expenses.api.beans.OperationType;

public class OperationTypeDeserializer  extends JsonDeserializer<OperationType>{

	@Override
	public OperationType deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		String chain = arg0.getText();
		for (OperationType val : OperationType.values()) {
			if (chain.contains(val.getName()))
				return val;
		}
		return null;
	}

}
