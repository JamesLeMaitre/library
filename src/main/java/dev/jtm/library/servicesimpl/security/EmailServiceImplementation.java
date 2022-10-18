package dev.jtm.library.servicesimpl.security;

import com.sun.mail.smtp.SMTPTransport;
import dev.jtm.library.services.security.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static dev.jtm.library.security.utils.constants.EmailConstant.*;
import static dev.jtm.library.security.utils.constants.JavaConstant.API_BASE_URL;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Service
public class EmailServiceImplementation implements EmailService {

    @Override
    public void sendNewEmail(String username, String email, String subject) throws MessagingException {
        Message message = createMail(username, email, subject);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }



    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);

        return Session.getInstance(properties, null);
    }

    private Message createMail(String username, String email, String subject) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FORM_EMAIL));
        message.addRecipients(TO, InternetAddress.parse(email, false));
        message.addRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSentDate(new Date());
        message.setSubject(subject);
        String link = "http://localhost:8250"+API_BASE_URL+"enabled/"+username+"/account";
        message.setContent(message, APPLICATION_JSON_VALUE);
       // message.setText(buildEmail(username,link));
        String msg = "Hello " +username+ "\n" +"http://localhost:8250"+API_BASE_URL+"enabled/"+username+"/account" + "\n\n"+ "Click here to activate your account " ;
        message.setText(msg);

        message.saveChanges();
        return message;
    }

    @Override
    public String buildEmail(String username, String link) {

        return "<!DOCTYPE html><html lang=3D\"en\"><head><meta name=3D\"format-detection\" cont=\n";
    }

}

