package com.geek.builderpattern;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

@Builder
@Getter
@Setter
@Accessors(chain = true)
public class HttpHeader {

    @Singular private Map<String, String> headers;
}
