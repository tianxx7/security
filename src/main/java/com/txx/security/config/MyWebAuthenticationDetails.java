package com.txx.security.config;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
    private boolean imageCodeIsRight;
    private String imageCode;
    private String savedCode;
    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.imageCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        this.savedCode = (String) session.getAttribute("captcha");
        if (!StringUtils.isEmpty(this.savedCode)) {
            session.removeAttribute("captcha");
            if (!StringUtils.isEmpty(imageCode)
                    && imageCode.equals(savedCode)) {
                this.imageCodeIsRight = true;
            }
        }
    }

    public boolean isImageCodeIsRight() {
        return imageCodeIsRight;
    }
}
