package com.example.application;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/example")
public class ExampleResource {

    private final Meter computationReturnsFalse;

    public ExampleResource(final MetricRegistry mr) {
        this.computationReturnsFalse = mr.meter("computation-returns-false");
    }

    @Timed
    @GET
    public Response doSomething() {
        boolean result = computation();
        if(!result) {
            computationReturnsFalse.mark();
        }

        return Response.ok().build();
    }

    private boolean computation() {
        return false;
    }

}
