package com.geek.builderpattern.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Builder
@Getter
@Setter
@Accessors(chain = true)
public class Address {

    private int houseNo;
    private String firstLine;
    private String county;
    private String postCode;

}
