package com.example.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class ExampleConfiguration extends Configuration {

    @JsonProperty("metrics_enabled")
    private Boolean metricsEnabled = true;
    public Boolean metricsEnabled() {
        return metricsEnabled;
    }
}
