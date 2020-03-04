package com.txx.security.config.db;

import com.txx.security.filters.VerificationCodeFilter;
import com.txx.security.provider.MyAuthenticationProvider;
import com.txx.security.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 1.配置权限
 * 2.配置自定义登录页
 * 3.验证码过滤器
 * 自定义authenticationProvider
 * @author labvi
 * @version 1.0.0
 */
//@Configuration
public class NoFilterDbWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

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
                .authenticationDetailsSource(authenticationDetailsSource)
                .defaultSuccessUrl("/hello");
                 //登录成功后,返回的结果 例如登录成功后返回 /hello请求的结果
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new MyAuthenticationProvider(userDetailsService,new BCryptPasswordEncoder());
    }
}
