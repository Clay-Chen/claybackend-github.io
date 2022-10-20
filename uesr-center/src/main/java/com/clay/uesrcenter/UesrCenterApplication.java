package com.clay.uesrcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication

@MapperScan("com.clay.uesrcenter.mapper")
@EnableScheduling
public class UesrCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UesrCenterApplication.class, args);
    }

}
