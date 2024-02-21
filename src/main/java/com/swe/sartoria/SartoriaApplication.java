package com.swe.sartoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SartoriaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SartoriaApplication.class, args);
        SartoriaApplication app = context.getBean(SartoriaApplication.class);
        app.run();
    }

    public void run() {

    }


}
