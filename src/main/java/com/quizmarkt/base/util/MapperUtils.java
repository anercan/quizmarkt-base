package com.quizmarkt.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author anercan
 */
public class MapperUtils {

    public static Map<String, String> getAttributeMapFromString(String attributesString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(attributesString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
