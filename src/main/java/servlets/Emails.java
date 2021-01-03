package servlets;//es necesario usar la libreria jakarta.mail.java

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Emails {
    private final Properties props;


    public Emails() {
        props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        //username: correo de origen
        /*el correo de origen debe tener habilitado en el apartado seguridad
        el acceso para aplicaciones poco seguras
         */
        props.put("from", "pruebasdejalea@gmail.com");
        props.put("username", "pruebasdejalea@gmail.com");
        props.put("password", "Kotto9Contra12");
    }

    public void enviar(String to, String subject, String content) throws MessagingException {
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(props.getProperty("from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);
        Transport.send(message);

        System.out.println("Mensaje enviado!");
    }

    public void enviarCorreo(String correo, String subject, String Cont) throws MessagingException {
        Emails emails = new Emails();
        //correo de destino, motivo, contenido del correo
        emails.enviar(correo, subject,Cont);
    }
}