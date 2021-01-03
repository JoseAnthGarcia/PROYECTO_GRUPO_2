package daos;

//es necesario usar la libreria jakarta.mail.java
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailContrasenia {
    /*
    private final Properties props;

    public EmailContrasenia() {
        props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
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

    public static void main(String[] args) throws MessagingException {
        EmailContrasenia emails = new EmailContrasenia();
        String contenido="Se ha registrado la bodega (nombre) con RUC ()." +
                "Para continuar con el registro acceda al link http://localhost:8081/TareaLaboratorio8_war_exploded/AdminServlet?accion=definirContrasenia" +
                "y registre una contraseña ";
        //correo de destino, motivo, contenido del correo
        emails.enviar("a20181563@pucp.edu.pe", "Continua con el registro de la bodega!",contenido);
    }*/
}