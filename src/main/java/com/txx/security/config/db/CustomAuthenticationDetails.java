package com.txx.security.config.db;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author labvi
 * @version 1.0.0
 */
public class CustomAuthenticationDetails extends WebAuthenticationDetails {
    private String session_captcha;
    private String user_captcha;
    private boolean isCaptcha;

    public CustomAuthenticationDetails(HttpServletRequest request) {
        super(request);
        HttpSession session = request.getSession();
        this.session_captcha = (String)session.getAttribute("captcha");
        this.user_captcha = request.getParameter("captcha");
        if(!StringUtils.isEmpty(session_captcha)){
            session.removeAttribute("captcha");
            if (!StringUtils.isEmpty(user_captcha) &&
            session_captcha.equals(user_captcha)) {
                this.isCaptcha = true;
            }
        }
    }

    public boolean isCaptcha() {
        return isCaptcha;
    }
}
