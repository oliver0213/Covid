package com.example.codejava;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, Long>
{
    List<Patient> findByFullnameLikeOrEmailLike(String key1,
                                                String key2);

    Optional<Patient> findByEmail(String patientId);
}
