package com.geek.builderpattern;

import com.mashape.unirest.http.HttpMethod;

/**
 * All APIs must extend this class and thus declare their endpoint and HTTP method.
 */
public abstract class AbstractHttpSpec {

    protected abstract String getEndpoint();
    protected abstract HttpMethod getHttpMethod();

}
