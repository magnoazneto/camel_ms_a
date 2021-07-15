package com.in28minutes.microservices.camelmsa.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class TimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent loggingComponent;

    @Override
    public void configure() throws Exception {
        // timer
        // transformation
        // log
        // Exchange[ExchangePattern: InOnlym BodyType: null, Body: [Body is null]]
        from("timer:my-timer")
                .transform().constant("My Constant Value or Message")
//                .log("${body}")
                .bean(getCurrentTimeBean)
                .bean(loggingComponent)
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer");
    }
}

class SimpleLoggingProcessor implements Processor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessingComponent {}", exchange.getMessage().getBody());
    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is " + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void process(String message) {
        logger.info("SimpleLoggingProcessingComponent");
    }
}
