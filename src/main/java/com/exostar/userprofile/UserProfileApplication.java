package com.exostar.userprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan ({ "com.exostar.userprofile.controller", "com.exostar.userprofile.service", "com.exostar.userprofile.batch.configuration",
                "com.exostar.userprofile.entity","com.exostar.userprofile.dao"})
public class UserProfileApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserProfileApplication.class, args);
    }
}
