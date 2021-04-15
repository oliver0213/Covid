
package com.example.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{


    @Query("SELECT DISTINCT u.Date FROM Appointment u where u.Type = 'Test' and u.Test_Center = ?1  and u.Date > CURRENT_DATE ")
    List<String> findDistinctDate(String testCenter);

    @Query("SELECT DISTINCT u.Time FROM Appointment u where u.Type = 'Test' and u.Test_Center = ?1 and u.Date = ?2 ")
    List<String> findDistinctTime(String testCenter, String testDate);

    @Query("SELECT u.Time FROM Appointment u where u.Type = 'Test' and u.Test_Center = ?1 and u.Date = ?2 ")
    List<String> findDistinctTestTime(String testCenter, String testDate);

    @Query("SELECT u.Number_of_Slots   FROM Appointment u where u.Type = 'Test' and u.Test_Center = ?1 and u.Date = ?2 and u.Time = ?3")
    Long findDistinctTestSlot(String testCenter, String testDate, String Time);

    @Query("SELECT u.Number_of_Slots   FROM Appointment u where u.Type = 'Vaccine' and u.Test_Center = ?1 and u.Date = ?2 and u.Time = ?3")
    Long findDistinctVaccineSlot(String testCenter, String testDate, String Time);

    @Query("SELECT DISTINCT u.Date FROM Appointment u where u.Type = 'Vaccine'")
    List<String> findDistinctvaccineDate();

    @Query("SELECT DISTINCT u.Date FROM Appointment u where u.Type = 'Vaccine' and u.Test_Center = ?1  and u.Date > CURRENT_DATE ")
    List<String> findDistinctVacDate(String testCenter);

    @Query("SELECT DISTINCT u.Time FROM Appointment u where u.Type = 'Vaccine' and u.Test_Center = ?1 and u.Date = ?2 ")
    List<String> findDistinctvacTime(String testCenter, String testDate);

}