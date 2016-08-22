package com.test.mail;

import com.test.entity.Utilisateur;
import com.test.lifecycle.DaoFactory;
import com.test.lifecycle.UtilisateurDaoImpl;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//  http://www.codejava.net/java-ee/jsp/sending-e-mail-with-jsp-servlet-and-javamail
// http://www.java2s.com/Code/Java/Network-Protocol/SendingMailUsingSockets.htm

public class SendMail {

    private String host = "smtp.gmail.com";    //   "SENDER-EMAIL-SMTP-ADDRESS"
    private String userName = ""; // "SENDER-EMAIL-SMTP-USERNAME" 
    private String password = ""; // "SENDER-EMAIL-SMTP-PASSWORD" 
    private String port = "587";// "SENDER-EMAIL-SMTP-PORT" 
    private String starttls = "true"; //"SENDER-EMAIL-SMTP-STARTTLS" // https://en.wikipedia.org/wiki/Opportunistic_TLS
    private String socketFactoryClass = "";  //"SENDER-EMAIL-SMTP-SOCKET-CLASS" 
    private String fallback = "";// "SENDER-EMAIL-SMTP-ALLOW-FALLBACK"  

    public SendMail() {

    }

    public  void sendMail(Utilisateur utilisateur)   {
        // reads form fields
        String emailTo = utilisateur.getLogin();
        String subject = "subject";
        
        
        String ip = "";
        String loopbackIP = "";
        
        try {
            ip = InetAddress.getLocalHost().getHostAddress().toString();
            loopbackIP = InetAddress.getLoopbackAddress().getHostAddress();
            
        }catch (UnknownHostException e)
        {
            e.printStackTrace();
        }


        String body = "Valider avec cette URL = " + "http://"+ip+":8080/test_mvc_crud/ServletController?commande=mail_create&login="+emailTo;
        
        
        System.out.println("content = " + body);
        String resultMessage = "";

        try {
            sendMailAdvance(emailTo, subject, body);
            resultMessage = "le msg a été envoyé";
        } catch (Exception ex) {
            ex.printStackTrace();
            resultMessage = "erreur: " + ex.getMessage();
        }
    }

    public boolean sendMailAdvance(String emailTo, String subject, String body) {

        // https://javamail.java.net/nonav/docs/api/com/sun/mail/smtp/package-summary.html
        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.user", userName);
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.debug", "true");

            if (!"".equals(port)) {
                properties.put("mail.smtp.port", port);
                properties.put("mail.smtp.socketFactory.port", port);
            }

            if (!"".equals(starttls)) {
                properties.put("mail.smtp.starttls.enable", starttls);
            }

            if (!"".equals(socketFactoryClass)) {
                properties.put("mail.smtp.socketFactory.class", socketFactoryClass);
            }

            if (!"".equals(fallback)) {
                properties.put("mail.smtp.socketFactory.fallback", fallback);
            }

            //Session session = Session.getDefaultInstance(props, null);
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("loginMail-smtp", password);
                }
            });

            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            msg.setSubject(subject);
            msg.setText(body, "ISO-8859-1");
            msg.setSentDate(new Date());
            msg.setHeader("content-Type", "text/html;charset=\"ISO-8859-1\"");
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            // msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("Sent message successfully....");
            return true;
        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        }
    }

    
    
    ///cette methode permet de tester cette unite de test ( test unitaire)
    public static void main(String[] args) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin("");
        utilisateur.setPassword("passwordMailTo");
        utilisateur.setNom("nom");
        utilisateur.setPrenom("prenom");

        new SendMail().sendMail(utilisateur);
    }

    public void sendMailPwdperdu(String emailTo) {
        
          sendMailAdvance(emailTo, "votre mot de passe", "mot de passe ="+DaoFactory.getInstance().getUtilisateurDao().pwdPerdu(emailTo));
 
    }

}
