package com.petchatbot.service;

import com.petchatbot.domain.dto.EmailDto;
import com.petchatbot.domain.dto.HospitalApplyDto;
import com.petchatbot.domain.model.MedicalForm;
import com.petchatbot.domain.model.Neutralization;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.repository.MedicalFormRepository;
import com.petchatbot.repository.PetRepository;
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

import static com.petchatbot.domain.model.Neutralization.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final PetRepository petRepository;
    private final MedicalFormRepository medicalFormRepository;


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

    public void sendEmailToHospital(EmailDto emailDto, String title, HospitalApplyDto hospitalApplyDto) throws MessagingException {

        try {
            String text = "test";
            MimeMessage m = emailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(m, "UTF-8");
            h.setFrom("lightson23@naver.com");
            h.setTo(emailDto.getReceiveMail());
            h.setSubject(title);
            h.setText(makeEmailText(hospitalApplyDto));
            emailSender.send(m);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private String makeEmailText(int randomNumber){
        return "인증번호는 " + randomNumber + " 입니다.\n멍냥챗봇에 가입해 주셔서 감사합니다.";
    }

    private String makeEmailText(HospitalApplyDto dto){

        int petSerial = dto.getPetSerial();
        int medicalSerial = dto.getMedicalSerial();
        Pet findPet = petRepository.findByPetSerial(petSerial);
        MedicalForm findMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalSerial);

        String isNeuter; // 중성화 여부
        if (findPet.getPetNeutralization() == NEUTER) isNeuter = "네";
        else isNeuter = "아니요";

        String isHypersensitivity; // 약 섭취 후 과민반응 여부
        if (findMedicalForm.isMedicalFormQ4()) isHypersensitivity="네";
        else isHypersensitivity = "아니요";

        String isSurgeryOrAnesthesia; // 수출 또는 마취 여부
        if (findMedicalForm.isMedicalFormQ5()) isSurgeryOrAnesthesia="네";
        else isSurgeryOrAnesthesia = "아니요";

        String isExcessiveExercise; // 과도한 운동 여부
        if (findMedicalForm.isMedicalFormQ6()) isExcessiveExercise="네";
        else isExcessiveExercise = "아니요";

        String isCostRequest;
        if (dto.isApptBill()) isCostRequest = "네";
        else isCostRequest = "아니요";

        return "안녕하세요. 멍냥챗봇 입니다.\n" +
                "해당병원에 " + dto.getApptMemberName() + "님이 상담신청을 하셨습니다.\n\n" +
                "<상담신청인 정보>\n" +
                "    - 이름: " + dto.getApptMemberName() + "\n" +
                "    - 전화번호: " + dto.getApptMemberTel() + "\n\n\n" +
                "<반려동물 정보>\n" +
                "    - 이름: " + findPet.getPetName() + "\n" +
                "    - 나이: " + findPet.getPetAge() + "\n" +
                "    - 축종: " + findPet.getPetSpecies() + "\n" +
                "    - 품종: " + findPet.getPetBreed() + "\n" +
                "    - 성별: " + findPet.getPetGender() + "\n" +
                "    - 중성화 여부: " + findPet.getPetNeutralization() + "\n" +
                "    - 복용중인약: " + findMedicalForm.getMedicalFormQ3() + "\n" +
                "    - 약 섭취 후 과민반응 여부: " + isHypersensitivity+ "\n" +
                "    - 수술 또는 마취 여부: " + isSurgeryOrAnesthesia + "\n" +
                "    - 과도한 운동 여부: " + isExcessiveExercise + "\n" +
                "    - 반려동물 이미지: " + dto.getApptImage() + "\n" +
                "    - 기타 특이사항: " + findMedicalForm.getMedicalFormQ7() + "\n\n\n" +
                "<상담내용>\n" +
                "    - 상담신청 이유: " + dto.getApptReason() + "\n" +
                "    - 예상비용 요청여부: " + isCostRequest + "\n\n\n" +
                "감사합니다 - 멍냥챗봇";

    }
}

