package net.thumtack.onlineshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class FileConfig {
    public static int maxNameLength;
    public static int minPasswordLength;

    @Value("${max.name}")
    public void setMaxNameLength(int max) {
        maxNameLength = max;
    }

    @Value("${min.password}")
    public void setMinPasswordLength(int min) {
        minPasswordLength = min;
    }
}
