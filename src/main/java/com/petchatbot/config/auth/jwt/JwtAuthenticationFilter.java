package com.petchatbot.config.auth.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.config.auth.PrincipalDetails;
import com.petchatbot.domain.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import com.auth0.jwt.JWT;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // 로그인 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);

            // 토큰 생성 (인증용 객체)
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getMemberEmail(), member.getMemberPassword());

            // 정상이면 authentication 객체를 반환
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;

        } catch (IOException e) {
            log.error("login 실패");
            throw new RuntimeException();
        }
    }

    // attemptAuthentication 함수가 정상적으로 동작하면 호출되는 함수
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        log.info("login 성공 이메일 = {}",principalDetails.getMember().getMemberEmail());

        // jwt 토큰 생성
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 30))) // jwt 토큰 30분 설정
                .withClaim("id", principalDetails.getMember().getMemberSerial())
                .withClaim("email", principalDetails.getMember().getMemberEmail())
                .sign(Algorithm.HMAC512("cos")); // secret key

        response.addHeader("Authorization", "Bearer "+jwtToken);
        setSuccessResponse(request, response);
    }

    // attemptAuthentication 함수가 정상적으로 동작하지 않으면 호출되는 함수
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("login 실패");
        setFailResponse(request, response);
    }

    private void setSuccessResponse(HttpServletRequest request, HttpServletResponse response) {
        try{
            response.setStatus(200);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("statusCode", StatusCode.OK);
            jsonObject.addProperty("responseMessage", ResponseMessage.LOGIN_SUCCESS);
            jsonObject.addProperty("data","");

            response.getWriter().print(jsonObject);
        } catch (IOException e){
            log.error("로그인 정리에서 오류");
        }
    }

    private void setFailResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            response.setStatus(200);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("statusCode", StatusCode.UNAUTHORIZED);
            jsonObject.addProperty("responseMessage", ResponseMessage.LOGIN_FAIL);
            jsonObject.addProperty("data","");
            response.getWriter().print(jsonObject);
        } catch (IOException e){
            log.error("로그인 실패 처리에서 오류");
        }
    }
}
