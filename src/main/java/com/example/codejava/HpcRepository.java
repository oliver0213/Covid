package com.example.codejava;

import org.springframework.data.jpa.repository.JpaRepository;


public interface HpcRepository extends JpaRepository<Hcp, Long> {
    Hcp findByEmail(String email);

}
