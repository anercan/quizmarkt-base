package com.quizmarkt.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */
public class MapperUtils {

    public static Map<String, String> getAttributeMapFromString(String attributesString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> listOfMaps = objectMapper.readValue(attributesString, new TypeReference<>() {
            });

            Map<String, String> attributes = new HashMap<>();
            for (Map<String, String> map : listOfMaps) {
                attributes.putAll(map);
            }
            return attributes;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
