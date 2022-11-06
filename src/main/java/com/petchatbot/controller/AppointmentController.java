package com.petchatbot.controller;

import com.petchatbot.config.ResponseMessage;
import com.petchatbot.config.StatusCode;
import com.petchatbot.domain.dto.MedicalFormDto;
import com.petchatbot.domain.requestAndResponse.AppointmentInfoRes;
import com.petchatbot.domain.requestAndResponse.AppointmentListRes;
import com.petchatbot.domain.requestAndResponse.DefaultRes;
import com.petchatbot.domain.requestAndResponse.ExpectDiagListRes;
import com.petchatbot.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/appointment/appointmentList/{petSerial}")
    public ResponseEntity<List<AppointmentListRes>> getAppointments(@PathVariable("petSerial") int petSerial) {
        List<AppointmentListRes> appointments = appointmentService.getAppointments(petSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_APPOINTMENT_LIST, appointments), HttpStatus.OK);
    }


    @GetMapping("/appointment/{appointmentSerial}")
    public ResponseEntity<AppointmentInfoRes> getAppointmentInfo(@PathVariable("appointmentSerial") int appointmentSerial) {
        AppointmentInfoRes appointmentInfo = appointmentService.getAppointmentInfo(appointmentSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_GET_APPOINTMENT_INFO, appointmentInfo), HttpStatus.OK);
    }

    @DeleteMapping("/appointment/delete/{appointmentSerial}")
    public ResponseEntity<AppointmentInfoRes> deleteAppointment(@PathVariable("appointmentSerial") int appointmentSerial) {
        appointmentService.deleteAppointment(appointmentSerial);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_DELETE_APPOINTMENT), HttpStatus.OK);
    }

}
