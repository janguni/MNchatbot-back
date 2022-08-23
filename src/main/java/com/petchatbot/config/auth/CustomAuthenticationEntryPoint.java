package com.petchatbot.config.auth;

import com.google.gson.JsonObject;
import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(200);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("statusCode", StatusCode.UNAUTHORIZED);
        jsonObject.addProperty("responseMessage", ResponseMessage.INCORRECT_APPROACH);
        jsonObject.addProperty("data","");
        response.getWriter().print(jsonObject);
    }
}
