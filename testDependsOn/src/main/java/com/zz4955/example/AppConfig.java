package com.zz4955.example;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.zz4955.example")
public class AppConfig {

    @Bean(initMethod = "initialize")
    @DependsOn("eventListener")
    public EventPublisherBean eventPublisherBean() {
        return new EventPublisherBean();
    }

    @Bean(name = "eventListener", initMethod = "initialize")
    // @Lazy
    public EventListenerBean eventListenerBean() {
        return new EventListenerBean();
    }

    public static void main(String... strings) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
