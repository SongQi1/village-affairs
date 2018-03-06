package com.bocs.util;


import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailService {
	
	private JavaMailSender mailSender;
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMail(SimpleMailMessage mail){
		mailSender.send(mail);
	}
	public void sendMail(MimeMessage mail){
		mailSender.send(mail);
	}
	
	public void sendMail(List<String> receptMailAdds, String subject, String content) throws MessagingException{
		String receptAdds = "";
		if(receptMailAdds != null && receptMailAdds.size() > 0){
			for(String add : receptMailAdds){
				receptAdds += add +";";
			}
		}
		MimeMessage msg = createMsg(receptAdds, subject, content);
		mailSender.send(msg);
	}
	
	public void sendMail(String receptMailAdd, String subject, String content) throws MessagingException{
		MimeMessage msg = createMsg(receptMailAdd, subject, content);
		mailSender.send(msg);
	}
	
	/**
	 * 创建邮件
	 * @param receptMailAdd 收件者地址
	 * @param subject 主题
	 * @param content 内容
	 * @return
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public MimeMessage createMsg(String receptMailAdd, String subject, String content) throws MessagingException, AddressException {
		String[] mailAdds = null;
		if(receptMailAdd.indexOf(",") == -1){
			mailAdds = receptMailAdd.split(";");
		}else{
			mailAdds = receptMailAdd.split(",");
		}
		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(PropertiesUtils.getMailValue("mail.addr")));
		Address[] addresses = new Address[mailAdds.length];
		for(int i=0; i<mailAdds.length; i++ ){
			if(StringUtils.isNotBlank(mailAdds[i])){
				addresses[i] = new InternetAddress(mailAdds[i]);
			}
		}
		msg.setRecipients(Message.RecipientType.TO, addresses );
		msg.setSubject(subject);
		msg.setContent(content, "text/html;charset=GBK");
		return msg;
	}
	
}
