package com.geek.builderpattern;

import com.geek.builderpattern.pojo.Customer;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Example: deserializing an API response into a object and asserting values
 */
public class DeserializeJsonToJava {

    private static Customer customer;

    private static final String EXAMPLE_RESPONSE = "{\n" +
            "  \"firstName\": \"John\",\n" +
            "  \"surname\": \"Doe\",\n" +
            "  \"dob\": \"12/01/1981\",\n" +
            "  \"address\": {\n" +
            "    \"houseNo\": 12,\n" +
            "    \"firstLine\": \"Test Road\",\n" +
            "    \"county\": \"The Shire\",\n" +
            "    \"postCode\": \"TE5 1TG\"\n" +
            "  }\n" +
            "}";

    @BeforeClass
    public static void deserializeToJava() {
        customer = new Gson().fromJson(EXAMPLE_RESPONSE, Customer.class);

    }

    @Test
    public void shouldContainCorrectFirstName() {
        Assert.assertEquals("John", customer.getFirstName());
    }

    @Test
    public void shouldContainCorrectDOB() {
        Assert.assertEquals("12/01/1981", customer.getDob());
    }

    @Test
    public void shouldContainCorrectAddress() {
        Assert.assertEquals(12, customer.getAddress().getHouseNo());
        Assert.assertEquals("The Shire", customer.getAddress().getCounty());
    }

}
