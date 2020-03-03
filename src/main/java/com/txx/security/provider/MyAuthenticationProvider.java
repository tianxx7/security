package com.txx.security.provider;

import com.txx.security.config.db.CustomAuthenticationDetails;
import com.txx.security.exception.VerificationCodeException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author labvi
 * @version 1.0.0
 */
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(encoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        CustomAuthenticationDetails authentication1 = (CustomAuthenticationDetails) authentication.getDetails();
        if (!authentication1.isCaptcha()) {
            throw new VerificationCodeException();
        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
