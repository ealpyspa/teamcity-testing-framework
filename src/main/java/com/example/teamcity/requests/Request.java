package com.example.teamcity.requests;

import com.example.teamcity.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

public class Request {
    /**
     * Request is a class that describes changing parameters of request such as
     * specification, endpoint (relative URL, model).
     */
    protected final RequestSpecification spec;
    protected final Endpoint endpoint;

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
