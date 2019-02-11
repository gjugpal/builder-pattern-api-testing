package com.geek.builderpattern.pojo;

import com.geek.builderpattern.AbstractHttpSpec;
import com.mashape.unirest.http.HttpMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Builder
@Getter
@Setter
@Accessors(chain = true)
public class Customer extends AbstractHttpSpec {

    private String firstName;
    private String surname;
    private String dob;
    private Address address;

    protected String getEndpoint() {
        return "/create";
    }

    protected HttpMethod getHttpMethod() {
        return HttpMethod.PUT;
    }
}
