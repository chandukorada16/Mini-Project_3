package com.user.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.user.binding.ActivateAccount;
import com.user.binding.Login;
import com.user.binding.User;
import com.user.emailutils.EmailUtils;
import com.user.entity.UserMaster;
import com.user.repository.UserMngRepo;

@Service
public class UserMnmtServiceImpl implements UserMnmtService {

	@Autowired
	private UserMngRepo repo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean saveUser(User user) {

		UserMaster entity = new UserMaster();
		BeanUtils.copyProperties(user, entity);

		entity.setPassword(generatePwd());
		entity.setAccStatus("In-Active");
		UserMaster save = repo.save(entity);

		String subject = "Your Registration is Success";
		String fileName = "REG-EMAIL-BODY.txt";
		String body = readEmailBody(entity.getFullName(), entity.getPassword(), fileName);
		emailUtils.sendEmail(user.getEmail(), subject, body);

		return save.getUserId() != null;

	}

	@Override
	public boolean actAccount(ActivateAccount activateAccount) {
		UserMaster entity = new UserMaster();
		entity.setEmail(activateAccount.getEmail());
		entity.setPassword(activateAccount.getTemppwd());

		Example<UserMaster> of = Example.of(entity);

		List<UserMaster> findAll = repo.findAll(of);

		if (findAll.isEmpty()) {
			return false;
		} else {
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(activateAccount.getNewPwd());
			userMaster.setAccStatus("Active");
			repo.save(userMaster);
		}
		return true;
	}

	@Override
	public List<User> getAllUsers() {
		List<UserMaster> entites = repo.findAll();

		List<User> users = new ArrayList<>();

		for (UserMaster entity : entites) {
			User user = new User();
			BeanUtils.copyProperties(entity, user);
			users.add(user);
		}
		return users;

	}

	@Override
	public User getById(Integer userId) {

		Optional<UserMaster> findById = repo.findById(userId);
		User user = new User();
		if (findById.isPresent()) {
			UserMaster userMaster = findById.get();
			BeanUtils.copyProperties(userMaster, user);
			return user;
		}

		return null;
	}

	@Override
	public boolean deleteById(Integer userId) {
		try {
			repo.deleteById(userId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean changeAccounttStatus(Integer userId, String actSts) {
		Optional<UserMaster> findById = repo.findById(userId);
		if (findById.isPresent()) {
			UserMaster userMaster = findById.get();
			userMaster.setAccStatus(actSts);
			repo.save(userMaster);
			return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {

		UserMaster entity = new UserMaster();
		entity.setEmail(login.getEmail());
		entity.setPassword(login.getPassword());

		Example<UserMaster> example = Example.of(entity);
		List<UserMaster> findAll = repo.findAll(example);

		if (findAll.isEmpty()) {
			return "Invalid credentails";
		} else {
			UserMaster userMaster = findAll.get(0);
			if (userMaster.getAccStatus().equals("Active")) {
				return "Succes";
			} else {
				return "Account not Activated";
			}
		}

		/*
		 * UserMaster entity = repo.findEmailAndPassword(login.getEmail(),
		 * login.getPassword()); if (entity == null) { return "invalid Credentials"; }
		 * else { if (entity.getAccStatus().equals("Active")) { return "Succes"; } else
		 * { return "Account not Activated"; } }
		 */

	}

	@Override
	public String frgtpwd(String email) {

		UserMaster entity = repo.findByEmail(email);
		if (entity == null) {
			return "invalid email";
		}
		String subject = "Forgot Password";
		String fileName = "RECOVER-PWD-BODY.txt";
		String body = readEmailBody(entity.getFullName(), entity.getPassword(), fileName);

		boolean sendEmail = emailUtils.sendEmail(entity.getEmail(), subject, body);

		if (sendEmail) {
			return "Password sent to your registered email";
		}
		return "Exception Occured";
	}

	private String generatePwd() {
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String alphanumeric = upperAlphabet + lowerAlphabet + numbers;
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int lenght = 6;

		for (int i = 0; i < lenght; i++) {
			int index = random.nextInt(alphanumeric.length());
			char randomChar = alphanumeric.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}

	private String readEmailBody(String fullName, String pwd, String fileName) {
		String url = "";
		String mailBody = null;
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer buffer = new StringBuffer();
			String line = br.readLine();
			while (line != null) {
				buffer.append(line);
				line = br.readLine();
			}
			br.close();
			mailBody = buffer.toString();
			mailBody = mailBody.replace("{FULL NAME}", fullName);
			mailBody = mailBody.replace("{TEMP-PWD}", pwd);
			mailBody = mailBody.replace("{URL}", url);
			mailBody = mailBody.replace("{PWD}", pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}
}
