package com.taotao.service.core;

import com.sgcai.zookeeper.lib.DistributedLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Q on 2017/7/10.
 * <p>
 * ApplicationConfig
 */
@Configuration
public class ApplicationConfig {

    @Value("${zookeeper.registry.host}")
    private String ZOOKEEPER_HOST;

    @Bean
    public DistributedLock DistributedLock() throws Exception {
        return new DistributedLock(ZOOKEEPER_HOST, "taotao-service-core");
    }
}
