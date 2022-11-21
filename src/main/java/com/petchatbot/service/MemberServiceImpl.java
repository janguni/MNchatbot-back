package com.petchatbot.service;

import com.petchatbot.domain.dto.EmailDto;
import com.petchatbot.domain.dto.MemberDto;
import com.petchatbot.domain.model.Member;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.repository.MemberRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PetRepository petRepository;
    private final PetService petService;


    /**
     * 이메일 중복 확인 (회원가입 시)
     * @param email
     * @return 중복이라면 true
     */
    @Override
    public boolean isExistingMember(String email){
        Member findMember = memberRepository.findByMemberEmail(email);
        if (findMember == null){return false;}
        else {return true;}
    }


    @Override
    public void join(MemberDto memberDto){
        String memberEmail = memberDto.getMemberEmail();
        String memberPassword = memberDto.getMemberPassword() ;
        Member member = new Member(memberEmail, memberPassword);
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void changePassword(MemberDto memberDto){
        Member findMember = memberRepository.findByMemberEmail(memberDto.getMemberEmail());

        // 비밀번호 암호화
        String rawPassword = memberDto.getMemberPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        findMember.changePassword(encodedPassword);
    }

    // 회원탈퇴
    @Transactional
    @Override
    public void withdrawal(EmailDto emailDto) {
        String memberEmail = emailDto.getReceiveMail();
        Member findMember = memberRepository.findByMemberEmail(memberEmail);
        List<Pet> pets = petRepository.findByMember(findMember);
        for (Pet p : pets) {
            petService.petDelete(p.getPetSerial());
        }
        memberRepository.delete(findMember);
    }
}
