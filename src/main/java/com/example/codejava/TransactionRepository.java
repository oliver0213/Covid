package com.example.codejava;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TransactionRepository extends JpaRepository<Transaction, Long>
{

    List<Transaction> findByPatientIdLikeOrUserFullnameLike(String key, String key1);

    List<Transaction> findByTestCenterAndDateAndTimeslot(String testCenter, String date, String time);

    int deleteByTestCenterAndDateAndTimeslot(String testCenter, String date, String time);
}
