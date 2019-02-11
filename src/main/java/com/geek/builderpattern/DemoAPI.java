package com.geek.builderpattern;

import com.geek.builderpattern.pojo.Customer;
import com.google.gson.Gson;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import lombok.AccessLevel;
import lombok.Getter;


public class DemoAPI {

    @Getter(AccessLevel.PROTECTED) private HttpHeader headers;
    @Getter(AccessLevel.PROTECTED) private String server;
    @Getter(AccessLevel.PROTECTED) private AbstractHttpSpec body;

    public DemoAPI usingHeaders(final HttpHeader httpHeader) {
        headers = httpHeader;
        return this;
    }

    public DemoAPI to(String server) {
        this.server = server;
        return this;
    }

    public DemoAPI forEndpoint() {
        // dummy method for readability purposes
        return this;
    }

    public DemoAPI createCustomer(Customer customer) {
        body = customer;
        return this;
    }

    public void send() {
        String url = server + body.getEndpoint();
        HttpRequest request;

        switch (body.getHttpMethod()) {

            case GET: POST: DELETE:
                //todo still to implement
                break;
            case PUT:
                String json = new Gson().toJson(body);
                request = new HttpRequestWithBody(body.getHttpMethod(), url);
                request.headers(headers.getHeaders());
                RequestBodyEntity bodyEntity = ((HttpRequestWithBody) request).body(json);

                System.out.println("h");
                break;
        }

    }
}
