package com.petchatbot.service;

import com.amazonaws.services.s3.AmazonS3;
import com.petchatbot.domain.dto.EmailDto;
import com.petchatbot.domain.dto.HospitalApplyDto;
import com.petchatbot.domain.model.MedicalForm;
import com.petchatbot.domain.model.Neutralization;
import com.petchatbot.domain.model.Pet;
import com.petchatbot.repository.MedicalFormRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.EditorAwareTag;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
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

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 회원가입 시 인증코드 전송
     * @param emailDto (이메일)
     * @param title (이메일 제목)
     * @param randomNumber (인증코드)
     * @throws MessagingException
     */
    public void sendEmailForJoin(EmailDto emailDto, String title, int randomNumber) throws MessagingException {
        try {
            String message = "멍냥챗봇에 가입해 주셔서 감사합니다.\n";
            MimeMessage m = emailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(m, "UTF-8");
            h.setFrom("lightson23@naver.com");
            h.setTo(emailDto.getReceiveMail());
            h.setSubject(title);
            h.setText(makeEmailText(randomNumber,message));
            emailSender.send(m);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 비밀번호 변경 시 이메일 전송
     * @param emailDto (이메일)
     * @param title (이메일 제목)
     * @param randomNumber (인증코드)
     * @throws MessagingException
     */
    public void sendEmailForChangePw(EmailDto emailDto, String title, int randomNumber) throws MessagingException {
        try {
            String message = "앱으로 돌아가셔서 인증번호 입력 후 비밀번호 재설정을 완료해주세요.";
            MimeMessage m = emailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(m, "UTF-8");
            h.setFrom("lightson23@naver.com");
            h.setTo(emailDto.getReceiveMail());
            h.setSubject(title);
            h.setText(makeEmailText(randomNumber, message));
            emailSender.send(m);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 연계병원에게 상담신청 내용 이메일전송
     * @throws MessagingException
     */
    public void sendEmailToHospital(EmailDto emailDto, String title, HospitalApplyDto hospitalApplyDto, int petSerial, int medicalFormSerial, AmazonS3 amazonS3 , String s3ImageName) throws MessagingException {

        try {
            MimeMessage m = emailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(m, "UTF-8");
            h.setFrom("lightson23@naver.com");
            h.setTo(emailDto.getReceiveMail());
            h.setSubject(title);
            h.setText(makeEmailText(hospitalApplyDto,petSerial, medicalFormSerial, amazonS3,s3ImageName));
            emailSender.send(m);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    // 인증번호 문장 만들기
    private String makeEmailText(int randomNumber,String message){
        return "인증번호는 " + randomNumber + " 입니다.\n" + message;
    }

    // 상담신청 전체 내용 만들기
    private String makeEmailText(HospitalApplyDto dto, int petSerial, int medicalFormSerial,AmazonS3 amazonS3,String s3ImageName){

        Pet findPet = petRepository.findByPetSerial(petSerial);
        MedicalForm findMedicalForm = medicalFormRepository.findByMedicalFormSerial(medicalFormSerial);

        String isNeuter; // 중성화 여부
        if (findPet.getPetNeutralization() == NEUTER) isNeuter = "네";
        else isNeuter = "아니요";


        String underDisease; // 기저질환
        if (findMedicalForm.getMedicalFormQ2().equals("I")) underDisease = "내분비질환";
        else if (findMedicalForm.getMedicalFormQ2().equals("S")) underDisease = "피부질환";
        else if (findMedicalForm.getMedicalFormQ2().equals("M")) underDisease = "근골격계질환";
        else if (findMedicalForm.getMedicalFormQ2().equals("C")) underDisease = "순환기질환";
        else underDisease = "없음";

        String isSpecialNoteMedication; // 약에 대한 특이사항 여부
        String medication;
        if (findMedicalForm.isMedicalFormQ3()) {
            isSpecialNoteMedication="네";
            medication = findMedicalForm.getMedicalFormQ4();
        }
        else {
            isSpecialNoteMedication = "아니요";
            medication = "약에 대한 세부사항 없음";
        }


        String isSurgeryOrAnesthesia; // 수출 또는 마취 여부
        if (findMedicalForm.isMedicalFormQ5()) isSurgeryOrAnesthesia="네";
        else isSurgeryOrAnesthesia = "아니요";

        String isExcessiveExercise; // 과도한 운동 여부
        if (findMedicalForm.isMedicalFormQ6()) isExcessiveExercise="네";
        else isExcessiveExercise = "아니요";

        String isCostRequest; // 비용 요청 여부
        if (dto.getApptBill()=="true") isCostRequest = "네";
        else isCostRequest = "아니요";


        String image; // 이미지
        if (s3ImageName == "noImageFile"){
            image = "이미지가 첨부되지 않음";
        }
        else {
            image = amazonS3.getUrl(bucket, s3ImageName).toString(); // s3에서 이미지 파일 가져오기
        }

        return "안녕하세요. 멍냥챗봇 입니다.\n" +
                "해당병원에 " + dto.getApptMemberName() + "님이 상담신청을 하셨습니다.\n\n" +
                "<상담신청 일자>\n" +
                "    - 원하는 상담날짜 : " + dto.getApptDate() + "\n" +
                "    - 원하는 상담시간 : " + dto.getApptTime() + "\n\n\n" +
                "<상담신청인 정보>\n" +
                "    - 이름: " + dto.getApptMemberName() + "\n" +
                "    - 전화번호: " + dto.getApptMemberTel() + "\n\n\n" +
                "<반려동물 정보>\n" +
                "    - 이름: " + findPet.getPetName() + "\n" +
                "    - 나이: " + findPet.getPetAge() + "\n" +
                "    - 축종: " + findPet.getPetSpecies() + "\n" +
                "    - 품종: " + findPet.getPetBreed() + "\n" +
                "    - 성별: " + findPet.getPetGender() + "\n" +
                "    - 중성화 여부: " + isNeuter + "\n" +
                "    - 기저질환: " + underDisease + "\n" +
                "    - 약에 관한 특이사항 여부: " + isSpecialNoteMedication + "\n" +
                "    - 약 관련 내용: " + medication + "\n" +
                "    - 수술 또는 마취 여부: " + isSurgeryOrAnesthesia + "\n" +
                "    - 과도한 운동 여부: " + isExcessiveExercise + "\n" +
                "    - 반려동물 이미지: " + image + "\n" +
                "    - 기타 특이사항: " + findMedicalForm.getMedicalFormQ7() + "\n\n\n" +
                "<상담내용>\n" +
                "    - 상담신청 이유: " + dto.getApptReason() + "\n" +
                "    - 예상비용 요청여부: " + isCostRequest + "\n\n\n" +

                "감사합니다 - 멍냥챗봇";

    }
}

