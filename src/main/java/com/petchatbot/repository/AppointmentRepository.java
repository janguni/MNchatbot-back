package com.petchatbot.repository;

import com.petchatbot.domain.model.Appointment;
import com.petchatbot.domain.model.MedicalForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
