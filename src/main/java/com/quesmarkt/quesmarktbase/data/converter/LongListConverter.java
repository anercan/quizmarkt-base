package com.quesmarkt.quesmarktbase.data.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> longList) {
        return longList != null ? longList.toString() : "";
    }

    @Override
    public List<Long> convertToEntityAttribute(String longListString) {
        return StringUtils.isEmpty(longListString) ?
                Arrays.stream(longListString.split(SPLIT_CHAR)).map(Long::parseLong).collect(Collectors.toList())
                : emptyList();
    }
}