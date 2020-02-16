package com.txx.security.config;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;


/**
 * @author labvi
 * @version 1.0.0
 */
@Configuration
public class CASWebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private AuthenticationProvider authenticationProvider;

   @Autowired
   private AuthenticationEntryPoint entryPoint;

   @Autowired
   private SingleSignOutFilter singleSignOutFilter;

    @Autowired
   private LogoutFilter requestSingleLogoutFilter;

   @Autowired
   private CasAuthenticationFilter casAuthenticationFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/login/cas","/favicon.ico","/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .addFilter(casAuthenticationFilter)
                .addFilterBefore(singleSignOutFilter,CasAuthenticationFilter.class)
                .addFilterBefore(requestSingleLogoutFilter,LogoutFilter.class);
    }

    //httpSession的事件监听,改用session提供的会话注册表

}
