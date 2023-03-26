package net.tfg.sharedlife.service.mail;


import net.tfg.sharedlife.common.ErrorMessages;
import net.tfg.sharedlife.controller.auth.AuthControllerImpl;
import net.tfg.sharedlife.exception.DataIncorrectException;
import net.tfg.sharedlife.service.user.UserService;
import net.tfg.sharedlife.service.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService{
    private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    UserService userService;

    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    private boolean sendGmail(String receiver, String topic, String body) {
        logger.info("Starting the process of sending and email for recovery the password");
        boolean enviado = false;
        String remitente = "franricoperez1997@gmail.com";
        String claveemail = "letckhjxrddfidvn";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.user", remitente);
        properties.put("mail.smtp.clave", claveemail);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("franricoperez1997@gmail.com")); // crear un corre, en plan, sharedlifeadministracion@gmail.com
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver)); // receiver
            message.setSubject(topic);
            message.setText(body);
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", "franricoperez1997@gmail.com", claveemail);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            enviado = true;
        } catch (MessagingException e) {
            System.out.println(e);
        }
        return enviado;
    }

    @Override
    public boolean sendGmail(String receiver) {
        String message = "";
        String topic = "RECUPERACIÓN DE CONTRASEÑA: Shared Life";
        String psw = userService.generateNewPassword(receiver);
        message = "Esta es tu nueva contraseña, recuerda actualizarla una vez que hayas logrado acceder: "+ psw;
        boolean res = userService.updatePassword(passwordEncoder.encode(psw), receiver);
        if(!res){
            throw new DataIncorrectException(ErrorMessages.USER_NOT_FOUND);
        }
        return this.sendGmail(receiver, topic, message);
    }
}
