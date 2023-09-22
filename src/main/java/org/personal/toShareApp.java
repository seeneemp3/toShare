package org.personal;

import org.apache.catalina.LifecycleException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class toShareApp {
    public static void main(String[] args) throws LifecycleException {
        SpringApplication.run(toShareApp.class, args);
    }
}