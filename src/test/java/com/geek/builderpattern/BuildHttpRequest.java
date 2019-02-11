package com.geek.builderpattern;

import com.geek.builderpattern.pojo.Address;
import com.geek.builderpattern.pojo.Customer;
import org.junit.Test;

/**
 * Example: building and sending an API request
 */
public class BuildHttpRequest {


    @Test
    public void theTest() {

        new DemoAPI()
                .to("http://myserver")
                .usingHeaders(HttpHeader.builder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build())
                .forEndpoint()
                .createCustomer(getCustomer())
                .send();
    }

    private Customer getCustomer() {
        return Customer.builder()
                .firstName("Jack")
                .surname("Jones")
                .dob("01/01/1980")
                .address(Address.builder()
                        .houseNo(150)
                        .firstLine("Daisy Lane")
                        .county("The Shire")
                        .postCode("AB1 CD4")
                        .build())
                .build();
    }
}
