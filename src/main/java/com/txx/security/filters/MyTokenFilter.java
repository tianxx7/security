package com.txx.security.filters;

import com.txx.security.service.CustomerUserDetailsService;
import com.txx.security.util.JWTUtils;
import com.txx.security.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author labvi
 * @version 1.0.0
 */
@Component
public class MyTokenFilter extends GenericFilter {
    private final static String AUTH_TOKEN_HEADER_NAME = "token";
    @Autowired
    private CustomerUserDetailsService userDetailsService;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            String authToken = request.getHeader(AUTH_TOKEN_HEADER_NAME);
            if (StringUtils.hasText(authToken)) {
                JWTUtils.JWTResult jwtResult = JWTUtils.getInstance().checkToken(authToken);
                if (jwtResult.isStatus()) {
                    System.out.println("token is ok");
                    //从中解析用户
                    UserDetails userDetails = JsonUtils.toBean(authToken, UserDetails.class);
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
