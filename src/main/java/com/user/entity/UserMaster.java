package com.user.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class UserMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String fullName;

	private String email;

	private Long mobileNo;

	private String gender;

	private LocalDate dob;

	private Long ssn;

	private String password;

	private String accStatus;

	@CreationTimestamp
	@Column(name = "createDate", updatable = false)
	private LocalDate createDate;

	@UpdateTimestamp
	@Column(name = "updateDate", insertable = false)
	private LocalDate updateDate;

	private String createdBy;

	private String updatedBy;

}
