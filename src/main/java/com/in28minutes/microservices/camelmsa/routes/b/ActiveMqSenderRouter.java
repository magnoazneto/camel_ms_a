package com.in28minutes.microservices.camelmsa.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //timer
//        from("timer:active-mq-timer?period=10000")
//            .transform().constant("My message for ActiveMQ")
//            .log("${body}")
//            .to("activemq:my-activemq-queue");
        //queue
        from("file:files")
                .routeId("file-input-route")
                .choice()
                    .when(simple("${file:ext} ends with 'xml'"))
                        .log("XML FILE")
                    .when(simple("${file:ext} ends with 'json'"))
                        .log("JSON FILE")

                .log("${body}")
                .to("kafka:myKafkaTopic");
//        from("file:files/xml")
//                .log("${body}")
//                .to("activemq:my-activemq-xml-queue");
    }
}
