package com.petchatbot.service;

import com.petchatbot.domain.dto.MemberDto;
import com.petchatbot.domain.model.Member;
import com.petchatbot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    // 기존 회원인지 체크
    @Override
    public boolean isExistingMember(String email){
        Member findMember = memberRepository.findByMemberEmail(email);

        if (findMember == null){return false;}
        else {return true;}
    }


    // 회원가입
    @Override
    public void join(MemberDto memberDto){
        log.info("join member email= {}, password={}", memberDto.getMemberEmail(), memberDto.getMemberPassword());
        String memberEmail = memberDto.getMemberEmail();
        String memberPassword = memberDto.getMemberPassword() ;
        Member member = new Member(memberEmail, memberPassword);
        memberRepository.save(member);
    }

    // 비밀번호 변경
    @Transactional

    public void changePassword(MemberDto memberDto){
        Member findMember = memberRepository.findByMemberEmail(memberDto.getMemberEmail());
        if (memberDto.getMemberPassword() == null){
            throw new IllegalStateException("비밀번호가 null");
        }
        String rawPassword = memberDto.getMemberPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        findMember.changePassword(encodedPassword);
        log.info("changedMember email={}, password={}", findMember.getMemberEmail(), findMember.getMemberPassword());
    }





}
