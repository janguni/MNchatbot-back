package com.petchatbot.service;

import com.petchatbot.domain.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendEmail(EmailDto emailDto, String title, int randomNumber) throws MessagingException {

        log.info("emailDto.receiveMail={}", emailDto.getReceiveMail());

        try {
            String text = "test";
            MimeMessage m = emailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(m, "UTF-8");
            h.setFrom("lightson23@naver.com");
            h.setTo(emailDto.getReceiveMail());
            h.setSubject(title);
            h.setText(makeEmailText(randomNumber));
            emailSender.send(m);

           
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public String makeEmailText(int randomNumber){
        return "인증번호는 " + randomNumber + " 입니다.\n멍냥챗봇에 가입해 주셔서 감사합니다.";
    }
}

