package com.example.codejava;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AppointmentHcRepository extends JpaRepository<AppointmentHc, Long> {
    List<AppointmentHc> findByTestCenter(String name);


    Optional<AppointmentHc>  findByTestCenterAndDateAndTimeAndType(String testCenter, String date, String time, String type);
}
