package com.user.emailutils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	private JavaMailSender mailSender;

	@Autowired
	public EmailUtils(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public boolean sendEmail(String to, String subject, String body) {

		boolean isMailSent = false;

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			mailSender.send(mimeMessage);

			isMailSent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMailSent;
	}

}