package com.txx.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;


//@EnableRedisHttpSession
//@Configuration
public class HttpSessionConfig {

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    //SpringSessionBackedSessionRegistry是session为Spring Security提供的用于在集群环境下控制会话并发的会话注册表实现类
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

}


















