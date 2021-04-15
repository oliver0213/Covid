package com.example.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);


	public User findByResetPasswordToken(String token);

	@Query("SELECT u FROM User u WHERE u.id = 1 ")
	public User findMemberById();

	@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
	public User findByVerificationCode(String code);

	@Transactional
	@Modifying
	@Query("UPDATE User u set u.Name = ?1, u.email = ?2, u.phone = ?3, u.address = ?4 where u.id = ?5")
	void updateUser(String name, String email, String phone, String add, Long userid);
	
}