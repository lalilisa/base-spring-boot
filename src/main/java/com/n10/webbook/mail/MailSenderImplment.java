
package  com.n10.webbook.mail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

//Mail Service
@Service
public class MailSenderImplment extends JavaMailSenderImpl implements JavaMailSender {


}
