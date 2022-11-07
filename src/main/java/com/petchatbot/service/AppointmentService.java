package com.petchatbot.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.petchatbot.domain.model.*;
import com.petchatbot.domain.requestAndResponse.AppointmentInfoRes;
import com.petchatbot.domain.requestAndResponse.AppointmentListRes;
import com.petchatbot.repository.AppointmentRepository;
import com.petchatbot.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.petchatbot.domain.model.Neutralization.NEUTER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<AppointmentListRes> getAppointments(int petSerial){
        Pet findPet = petRepository.findByPetSerial(petSerial);
        List<Appointment> appointments = appointmentRepository.findByPet(findPet);

        List<AppointmentListRes> appointmentsRes = new ArrayList<>();

        SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy.MM.dd");
        for (Appointment appt:appointments){
            int serial = appt.getAppointmentSerial();
            Partner partner = appt.getPartner();
            String partnerName = partner.getPnrName();
            Date date = appt.getAppointmentDate();
            String formattedDate = newDtFormat.format(date);
            AppointmentListRes appointmentRes = new AppointmentListRes(serial, partnerName, formattedDate);
            appointmentsRes.add(appointmentRes);
        }

        return appointmentsRes;
    }

    public AppointmentInfoRes getAppointmentInfo(int appointmentSerial) {
        Appointment findAppointment = appointmentRepository.findByAppointmentSerial(appointmentSerial);
        Date date = findAppointment.getAppointmentDate();
        SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = newDtFormat.format(date);

        String apptTime = findAppointment.getAppointmentTime();
        String time = apptTime.substring(0, 5); // hh:mm

        String memberName = findAppointment.getMemberName();
        String memberTel = findAppointment.getMemberTel();

        // 반려동물 정보
        Pet findPet = findAppointment.getPet();
        String petName = findPet.getPetName();
        int age = findPet.getPetAge();
        Species species = findPet.getPetSpecies();
        String breed = findPet.getPetBreed();
        PetGender gender = findPet.getPetGender();
        String isNeuter; // 중성화 여부
        if (findPet.getPetNeutralization() == NEUTER) isNeuter = "네";
        else isNeuter = "아니요";

        // 문진표 정보
        MedicalForm findMedicalForm = findAppointment.getMedicalForm();
        String underDisease; // 기저질환
        if (findMedicalForm.getMedicalFormQ2().equals("I")) underDisease = "내분비질환";
        else if (findMedicalForm.getMedicalFormQ2().equals("S")) underDisease = "피부질환";
        else if (findMedicalForm.getMedicalFormQ2().equals("M")) underDisease = "근골격계질환";
        else if (findMedicalForm.getMedicalFormQ2().equals("C")) underDisease = "순환기질환";
        else underDisease = "없음";

        String takingMedication = findMedicalForm.getMedicalFormQ4();
        String isHypersensitivity; // 약 섭취 후 과민반응 여부
        if (findMedicalForm.isMedicalFormQ3()) isHypersensitivity="네";
        else isHypersensitivity = "아니요";

        String isSurgeryOrAnesthesia; // 수출 또는 마취 여부
        if (findMedicalForm.isMedicalFormQ5()) isSurgeryOrAnesthesia="네";
        else isSurgeryOrAnesthesia = "아니요";

        String isExcessiveExercise; // 과도한 운동 여부
        if (findMedicalForm.isMedicalFormQ6()) isExcessiveExercise="네";
        else isExcessiveExercise = "아니요";

        String etc = findMedicalForm.getMedicalFormQ7();


        String apptReason = findAppointment.getAppointmentReason();

        String isCostRequest;
        if (findAppointment.isCostRequest()) isCostRequest = "네";
        else isCostRequest = "아니요";

        String imageName = findAppointment.getAppointmentImage();
        URL imageUrl;
        if (imageName == "noImageFile"){
            imageUrl = null;
        }
        else {
            imageUrl = amazonS3Client.getUrl(bucket, imageName);
        }

        AppointmentInfoRes res = new AppointmentInfoRes(formattedDate, time, memberName, memberTel, petName, age, species, breed, gender, isNeuter, underDisease, takingMedication, isHypersensitivity, isSurgeryOrAnesthesia, isExcessiveExercise, etc, apptReason,isCostRequest, imageUrl);

        return res;
    }

    public void deleteAppointment(int appointmentSerial){
        Appointment findAppointment = appointmentRepository.findByAppointmentSerial(appointmentSerial);
        appointmentRepository.delete(findAppointment);
    }



}
