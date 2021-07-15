package com.in28minutes.microservices.camelmsa.routes.d;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestApiConsumerRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().host("127.0.0.1").port(8081);

        from("timer:rest-api-consumer?period=10000")
                .to("rest:get:/api/v1/ebooks/21")
                .log("${body}");
    }
}
