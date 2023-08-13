package com.user.service;

import java.util.List;

import com.user.binding.ActivateAccount;
import com.user.binding.Login;
import com.user.binding.User;

public interface UserMnmtService {
	
	public boolean saveUser(User user);
     
	public boolean actAccount(ActivateAccount activateAccount);
	
	public List<User> getAllUsers ();
	
	public User getById(Integer userId);
	
	public boolean deleteById(Integer userId);
	
	public boolean changeAccounttStatus(Integer userId,String actSts);
	
	public String login(Login login);
	
	public String frgtpwd(String email);
}
