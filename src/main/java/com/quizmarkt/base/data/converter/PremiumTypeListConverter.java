package com.quizmarkt.base.data.converter;

import com.quizmarkt.base.data.enums.PremiumType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class PremiumTypeListConverter implements AttributeConverter<List<PremiumType>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<PremiumType> premiumTypes) {
        return !CollectionUtils.isEmpty(premiumTypes) ? premiumTypes.toString() : "";
    }

    @Override
    public List<PremiumType> convertToEntityAttribute(String stringList) {
        if (StringUtils.isNotEmpty(stringList)) {
            stringList = stringList.replace("[", "").replace("]", "").replace(" ","");
            return Arrays.stream(stringList.split(SPLIT_CHAR)).map(PremiumType::valueOf).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}