package org.freedom.econnoisseur.announcement.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类.
 *
 * @author Levi Qian
 * @since v0.0.1
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan("org.freedom.econnoisseur.announcement")
@EnableAutoConfiguration
public class Application {

    /**
     *  Spring Boot 启动方法.
     * @param args 输入参数
     */
    public static void main(final String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}
