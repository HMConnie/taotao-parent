package com.taotao.service.boot;


import com.taotao.service.core.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by Q on 2017/7/10.
 * <p>
 * main
 */


@ComponentScan(basePackages = {"com.taotao.service.core"})
@ImportResource({"classpath:applicationContext.xml"
        , "classpath:database-config.xml", "classpath:dubbo-provider.xml","classpath:redis-config.xml"
})
@ContextConfiguration(classes = ApplicationConfig.class)
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
}
