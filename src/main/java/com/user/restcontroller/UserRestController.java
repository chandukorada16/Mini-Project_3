package com.user.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.binding.ActivateAccount;
import com.user.binding.Login;
import com.user.binding.User;
import com.user.service.UserMnmtService;

@RestController
public class UserRestController {

	@Autowired
	private UserMnmtService service;

	@PostMapping("/user")
	public ResponseEntity<String> userRegistration(@RequestBody User user) {
		boolean saveUser = service.saveUser(user);
		if (saveUser) {
			return new ResponseEntity<String>("Registration Succesfull", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Registration Failed", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/activate")
	public ResponseEntity<String> activateAcsercount(@RequestBody ActivateAccount activateAccount) {
		boolean actAccount = service.actAccount(activateAccount);
		if (actAccount) {
			return new ResponseEntity<>("Account activated", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("invalid Temporary Password", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allUsers = service.getAllUsers();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserbyId(@PathVariable Integer userId) {
		User user = service.getById(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteById(@PathVariable Integer userId) {
		boolean deleteById = service.deleteById(userId);
		if (deleteById) {
			return new ResponseEntity<String>("Deleted Succesfullly", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/actStatus/{userId}/{status}")
	public ResponseEntity<String> activateStatus(@PathVariable Integer userId, String status) {
		boolean changeAccounttStatus = service.changeAccounttStatus(userId, status);
		if (changeAccounttStatus) {
			return new ResponseEntity<String>("Account status changed Succesfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Account status change Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginPage(@RequestBody Login login) {
		String login2 = service.login(login);
		return new ResponseEntity<String>(login2, HttpStatus.OK);

	}

	@GetMapping("/fwtPas/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email) {
		String frgtpwd = service.frgtpwd(email);
		return new ResponseEntity<String>(frgtpwd, HttpStatus.OK);

	}
}
