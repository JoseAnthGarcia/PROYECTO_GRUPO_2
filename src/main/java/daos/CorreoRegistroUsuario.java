package daos;

public class CorreoRegistroUsuario {
/*
    private final Properties props;
    public EmailContrasenia() {
        props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        //username: correo de origen
        /*el correo de origen debe tener habilitado en el apartado seguridad
        el acceso para aplicaciones poco seguras
         */
 /*       props.put("from", "pruebasdejalea@gmail.com");
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
        String contenido="Usted se ha registrado en la plataforma 'Mi Bodega'" +
                "Para continuar con el registro acceda al link de confirmación. ";
        //correo de destino, motivo, contenido del correo
        emails.enviar("a20181563@pucp.edu.pe", "Continua con el registro de la bodega!",contenido);
    }*/
}
