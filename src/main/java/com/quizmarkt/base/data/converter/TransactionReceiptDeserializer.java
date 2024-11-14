package com.quizmarkt.base.data.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizmarkt.base.data.request.TransactionReceipt;

import java.io.IOException;

public class TransactionReceiptDeserializer extends JsonDeserializer<TransactionReceipt> {
    @Override
    public TransactionReceipt deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String jsonString = parser.getText();
        return new ObjectMapper().readValue(jsonString, TransactionReceipt.class);
    }
}