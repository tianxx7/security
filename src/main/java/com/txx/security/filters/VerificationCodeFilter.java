package com.txx.security.filters;

import com.txx.security.exception.VerificationCodeException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用于校验验证码
 * OncePerRequestFilter 可以确保一次请求只会通过一次该过滤器
 */
public class VerificationCodeFilter extends OncePerRequestFilter {
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //非登录请求不校验验证码
        if (!"/authentication/form".equals(httpServletRequest.getRequestURI())){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        } else {
            try {
                //校验验证码
                verificationCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            } catch (VerificationCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }

        }
    }
    private static final String CAPTCHA ="captcha";
    private void verificationCode(HttpServletRequest httpServletRequest) {
        String requestCode = httpServletRequest.getParameter(CAPTCHA);
        HttpSession session = httpServletRequest.getSession();
        String savedCode = (String)session.getAttribute(CAPTCHA);
        if (!StringUtils.isEmpty(savedCode)){
            session.removeAttribute(CAPTCHA);
        }
        //
        if (StringUtils.isEmpty(requestCode)
                || StringUtils.isEmpty(savedCode)
                || !requestCode.equals(savedCode)){
            throw new VerificationCodeException();
        }
    }
}




