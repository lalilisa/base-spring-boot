package  com.n10.webbook.mail;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

@Component("message")
public class SendMail {
    @Value("${admin-mail}")
    private String SUPPORT_EMAIL;
    @Autowired
    private OAuthMail oAuthMail;

    public SendMail() {

    }

    @Bean
    public void configSendMail() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.auth.mechanisms", "XOAUTH2");
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.debug", false);
        ((MailSenderImplment) this.mailSender).setHost("smtp.gmail.com");
        ((MailSenderImplment) this.mailSender).setPort(587);
        ((MailSenderImplment) this.mailSender).setUsername(SUPPORT_EMAIL);
        ((MailSenderImplment) this.mailSender).setPassword(oAuthMail.getAccessToken());
        ((MailSenderImplment) this.mailSender).setJavaMailProperties(properties);
    }

    @Autowired
    private JavaMailSender mailSender;

    public void sendMessage(String from, String to, String subject, String msg) throws MessagingException {
        MimeMessage mimeMessage = ((MailSenderImplment) this.mailSender).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        mimeMessage.setContent(msg, "text/html");
        helper.setTo(to);
        helper.setSubject("Test send HTML email");
        this.mailSender.send(mimeMessage);
    }

    public void sendMessageWithAttachment(String from, String to, String subject, String msg, InputStream inputStream) throws MessagingException {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(msg);
            ByteArrayResource byteArrayResource= new ByteArrayResource(IOUtils.toByteArray(inputStream));
            message.addAttachment("sss",byteArrayResource);
        };
        try {
            this.mailSender.send(preparator);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}