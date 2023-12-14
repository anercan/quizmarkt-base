package com.quesmarkt.quesmarktbase.data.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> longList) {
        return !CollectionUtils.isEmpty(longList) ? longList.toString() : "";
    }

    @Override
    public List<Long> convertToEntityAttribute(String longListString) {
        if (StringUtils.isNotEmpty(longListString)) {
            longListString = longListString.replace("[", "").replace("]", "").replace(" ","");
            return Arrays.stream(longListString.split(SPLIT_CHAR)).map(Long::parseLong).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}