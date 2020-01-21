package com.report.adapter.swapi.client.converter;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;

public class StringUrlToLongIdConverter extends AbstractConverter<String, Long> {
    @Override
    protected Long convert(String url) {
        if(StringUtils.isBlank(url)) {
            return null;
        }

        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];

        if(StringUtils.isNumeric(lastPart)) {
            return Long.parseLong(lastPart);
        }

        return null;
    }
}