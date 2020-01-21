package com.report.adapter.swapi.client.vo;

import com.report.common.vo.NonBlankName;
import lombok.NonNull;

public class EndpointName extends NonBlankName {
    public EndpointName(final String name) {
        super(name);
    }

    public String generatePathForBaseUrl(@NonNull final String baseUrl) {
        return baseUrl + this.getRaw() + '/';
    }
}