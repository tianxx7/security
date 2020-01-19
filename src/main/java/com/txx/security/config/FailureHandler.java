package com.txx.security.config;

import com.txx.security.common.ResponseData;
import com.txx.security.util.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author labvi
 * @version 1.0.0
 */
public class FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ServletOutputStream outputStream = response.getOutputStream();
        ResponseData fail = ResponseData.fail(exception.getMessage());
        outputStream.write(JsonUtils.getJsonError(fail).getBytes());
        try {
            outputStream.flush();
        } finally {
            outputStream.close();
        }
    }
}
