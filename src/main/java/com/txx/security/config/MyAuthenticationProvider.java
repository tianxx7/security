package com.txx.security.config;

import com.txx.security.exception.VerificationCodeException;
import com.txx.security.service.CustomerUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 实现图形验证码的AuthenticationProvider
 */
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    public MyAuthenticationProvider(CustomerUserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
       this.setUserDetailsService(userDetailsService);
       this.setPasswordEncoder(passwordEncoder);
   }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //实现验证码的校验逻辑
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails)authentication.getDetails();
        if (!details.isImageCodeIsRight()) {
            throw new VerificationCodeException();
        }
        //完成用户名密码校验
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
