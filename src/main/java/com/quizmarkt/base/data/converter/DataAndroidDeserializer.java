package com.quizmarkt.base.data.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizmarkt.base.data.request.DataAndroid;

import java.io.IOException;

public class DataAndroidDeserializer extends JsonDeserializer<DataAndroid> {
    @Override
    public DataAndroid deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String jsonString = parser.getText();
        return new ObjectMapper().readValue(jsonString, DataAndroid.class);
    }
}