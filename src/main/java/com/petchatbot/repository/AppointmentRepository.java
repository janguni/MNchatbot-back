package com.petchatbot.repository;

import com.petchatbot.domain.model.Appointment;
import com.petchatbot.domain.model.MedicalForm;
import com.petchatbot.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Appointment findByAppointmentSerial(int appointmentSerial);
    List<Appointment> findByPet(Pet pet);
}
