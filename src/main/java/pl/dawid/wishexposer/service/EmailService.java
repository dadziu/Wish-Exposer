package pl.dawid.wishexposer.service;

import com.sun.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;
import pl.dawid.wishexposer.domain.Email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static pl.dawid.wishexposer.constant.EmailConstant.*;

@Service
public class EmailService {

    public void sendNewEmail(Email email) throws MessagingException, UnsupportedEncodingException {
        Message message = createEmail(email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    private Message createEmail(Email email) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
        message.setRecipients(TO, InternetAddress.parse(email.getEmail(), false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject("Wish Exposer - " + email.getTitle());
        message.setText(email.getMessage());
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
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
}
