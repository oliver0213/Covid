package com.example.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TransactionAppRepository extends JpaRepository<TransactionApp, Long>

{
    @Query("SELECT u FROM TransactionApp u WHERE u.id = ?1")
    public TransactionApp findById(int id);

    @Query("SELECT u FROM TransactionApp u WHERE u.Patient_id = ?1 and u.Date <= CURRENT_DATE ")
    public List<TransactionApp> findByNamePastAppointments(String Name);

    @Query("SELECT u FROM TransactionApp u WHERE u.Patient_id = ?1 and u.Date > CURRENT_DATE ")
    public List<TransactionApp> findByNameFutureAppointments(String Name);

    @Query("SELECT u FROM TransactionApp u WHERE u.id = ?1 ")
    public TransactionApp findBytransactionId(long id);

    @Transactional
    @Modifying
    @Query("delete FROM TransactionApp u WHERE u.id = ?1 ")
    void  deleteBytransactionId(long id);

    @Query("SELECT u FROM TransactionApp u WHERE u.Patient_id = ?1 and u.Type = 'Test' and u.Date > CURRENT_DATE ")
    public TransactionApp findByTestFutureAppointments(String Name);

    @Query("SELECT u FROM TransactionApp u WHERE u.Patient_id = ?1 and u.Type = 'Vaccination' and u.Date > CURRENT_DATE ")
    public TransactionApp findByVaccineFutureAppointments(String Name);

    @Query("SELECT u FROM TransactionApp u WHERE u.Test_center = ?1 and u.Date = ?2 and u.Timeslot = ?3 and u.Type = 'Test' ")
    public List<TransactionApp> findIfSlotsAvailable(String Tc, String date, List<String> time);

    @Query("SELECT COUNT(u.id)  FROM TransactionApp u WHERE u.Test_center = ?1 and u.Date = ?2 and u.Timeslot = ?3 and u.Type = 'Test' ")
    public int CountSlots(String Tc, String date, String time);

    @Query("SELECT COUNT(u.id)  FROM TransactionApp u WHERE u.Test_center = ?1 and u.Date = ?2 and u.Timeslot = ?3 and u.Type = 'Vaccination' ")
    public int CountSlotsVac(String Tc, String date, String time);
}
