package com.neo.bookameetingroom.services;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailUtility extends Thread {

	private String subject;
	private String mailBody;
	private String to;
	
	public MailUtility() {

	}

	public MailUtility(String subject, String mailBody, String to) {
		this.subject = subject;
		this.mailBody = mailBody;
		this.to = to;
	}

	@Override
	public void run() {

		sendMail();

	}

	private void sendMail() {

		JavaMailSenderImpl j = null;

		Session session = null;
		Properties props = System.getProperties();

		// props.put("http.proxyHost", "10.0.60.33");
		props.put("http.proxyPort", "465");
		props.put("http.proxyUser", "shailesh.mhadaye@neosofttech.com");
		props.put("http.proxyPassword", "shaileshm12");
		props.put("mail.smtp.host", "webmail.wwindia.com");

		
		try {

			Message message = new MimeMessage(session);
			message.addHeader("Content-type", "text/HTML; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8bit");

			message.setFrom(new InternetAddress("shailesh.mhadaye@neosofttech.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(mailBody, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);

			message.setContent(multipart);
			Transport.send(message);
		}catch(SendFailedException e) {
			e.printStackTrace();
		}catch (MessagingException e) {
			System.out.println("Mail send error");
			throw new RuntimeException(e);
		}
	}

}
