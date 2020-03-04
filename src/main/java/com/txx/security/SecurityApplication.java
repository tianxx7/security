package com.txx.security;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/*
* todo UserDetailsServiceAutoConfiguration的影响
* */

@SpringBootApplication(
        exclude = {RedisAutoConfiguration.class}
)
@MapperScan("com.txx.security.mapper")
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    /*@Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(1));
        return RedisCacheManager.builder(factory).cacheDefaults(configuration).build();
    }*/

    @Bean
    public Producer captcha(){
        Properties properties = new Properties();
        //图片宽度,高度
        properties.setProperty("kaptcha.image.width","150");
        properties.setProperty("kaptcha.image.height","40");
        //字符集
        properties.setProperty("kaptcha.textproducer.char.string","0123456789");
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.border","no");
        properties.setProperty("kaptcha.border.color","105,179,90");
        properties.setProperty("kaptcha.textproducer.font.size","30");
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;

    }

}
