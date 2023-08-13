package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.UserMaster;

public interface UserMngRepo extends JpaRepository<UserMaster, Integer> {

	//@Query("select*from UserMaster")
	public UserMaster findByEmailAndPassword(String email, String pwd);

	//@Query("select*from UserMaster")
	public UserMaster findByEmail(String email);

}
