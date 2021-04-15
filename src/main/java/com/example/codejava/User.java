package com.example.codejava;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "users")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/* This is to enter user name */
	@Column(name = "fullname", nullable = false, length = 20)
	private String Name;

	@Column(name = "email", nullable = false, unique = true, length = 45)
	private String email;

	@Column(name = "phone", nullable = false, unique = false)
	private String phone;

	@Column(name = "address", nullable = false, length = 255)
	private String address;

	@Column(nullable = false, length = 64)
	private String password;

	@Column(name = "DOB", nullable = true, length = 255)
	private String dob;

	@Column(name = "created_time", updatable = false)
	private Date created_time;

	private boolean enabled;

	@Column(name = "verification_code", updatable = false)
	private String verificationCode;

	@Column(name = "reset_password_token", nullable = true, length = 255)
	private String resetPasswordToken;


	@Transient
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}




	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}


	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode)
	{
		this.verificationCode = verificationCode;
	}

	public String getResetPasswordToken()
	{
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken)
	{
		this.resetPasswordToken = resetPasswordToken;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
