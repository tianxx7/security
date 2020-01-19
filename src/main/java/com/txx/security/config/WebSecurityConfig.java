package com.txx.security.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.txx.security.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

/**
 * @author labvi
 * @version 1.0.0
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomerUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.
                authorizeRequests()
                .antMatchers("/product/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin();*/
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/product/**").hasAuthority("USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/login.html").permitAll()
                .antMatchers("/captcha.jpg").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.html")
                .loginProcessingUrl("/authentication/form");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles("USER");*/
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /*没用*/
    /*@Bean
    public RoleVoter roleVoter(){
        RoleVoter roleVoter = new RoleVoter();
        //默认角色前缀是ROLE_
        roleVoter.setRolePrefix("");
        return roleVoter;
    }*/


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
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
