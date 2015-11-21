package com.example.application;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class ExampleApplication extends Application<ExampleConfiguration>{

    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }

    @Override
    public void run(ExampleConfiguration configuration, Environment environment) throws Exception {

        if(configuration.metricsEnabled()) {


            final Graphite graphite = new Graphite(new InetSocketAddress("graphite.example.com", 2003));

            final GraphiteReporter reporter = GraphiteReporter.forRegistry(environment.metrics())
                    .prefixedWith("prefix")
                    .convertRatesTo(TimeUnit.SECONDS)
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .filter(MetricFilter.ALL)
                    .build(graphite);
            reporter.start(5, TimeUnit.SECONDS);

            final ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(environment.metrics()).build();
            consoleReporter.start(5, TimeUnit.SECONDS);
        }

        final ExampleResource exampleResource = new ExampleResource(environment.metrics());
        environment.jersey().register(exampleResource);
    }
}
