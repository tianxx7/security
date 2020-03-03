package com.txx.security.config.db;

import com.txx.security.filters.VerificationCodeFilter;
import com.txx.security.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * 1.配置权限
 * 2.配置自定义登录页
 * 3.验证码过滤器
 * 通过用户验证前添加验证码过滤器的方式来验证
 * @author labvi
 * @version 1.0.0
 */
//@Configuration
public class DbWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomerUserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/login.html","/captcha.jpg").permitAll()
                .antMatchers("/product/**").hasRole("USER")
                .antMatchers("/pages/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.html")
                .loginProcessingUrl("/authentication/form")
                .defaultSuccessUrl("/hello")//登录成功后,返回的结果 例如登录成功后返回 /hello请求的结果
        .and().addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
