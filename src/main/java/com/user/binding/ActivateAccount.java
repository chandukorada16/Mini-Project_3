package com.user.binding;

import lombok.Data;

@Data
public class ActivateAccount {
	
	private String email;
	
	private String temppwd;
	
	private String newPwd;
	
	private String confrmPwd;

}
