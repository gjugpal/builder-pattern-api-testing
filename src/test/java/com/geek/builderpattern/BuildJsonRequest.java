package com.geek.builderpattern;

import com.geek.builderpattern.pojo.Address;
import com.geek.builderpattern.pojo.Customer;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

/**
 * Example: Unit test to validate that the Customer builder builds a correct JSON String
 */
public class BuildJsonRequest {

    @Test
    public void testBuildingJSONRequest() {

        Customer customer = Customer.builder()
                .firstName("John")
                .surname("Doe")
                .dob("12/01/1981")
                .address(Address.builder()
                        .houseNo(12)
                        .firstLine("Test Road")
                        .county("The Shire")
                        .postCode("TE5 1TG")
                        .build())
                .build();


        String jsonRequest = new Gson().toJson(customer);
        Assert.assertEquals("{\"firstName\":\"John\",\"surname\":\"Doe\",\"dob\":\"12/01/1981\",\"address\":{\"houseNo\":12,\"firstLine\":\"Test Road\",\"county\":\"The Shire\",\"postCode\":\"TE5 1TG\"}}", jsonRequest);

    }
}
