package com.example.merchantdemo.helperClass;

import android.os.AsyncTask;
import android.util.Log;
import java.util.Properties;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

public class SendMailTask {//extends AsyncTask<Void, Void, Void> {
//    private static final String TAG = "SendMailTask";
//
//    private final String username;
//    private final String password;
//    private final String recipientEmail;
//    private final String subject;
//    private final String messageBody;
//
//    public SendMailTask(String username, String password, String recipientEmail, String subject, String messageBody) {
//        this.username = username;
//        this.password = password;
//        this.recipientEmail = recipientEmail;
//        this.subject = subject;
//        this.messageBody = messageBody;
//    }
//
//    @Override
//    protected Void doInBackground(Void... voids) {
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
//            message.setSubject(subject);
//            message.setText(messageBody);
//
//            Transport.send(message);
//
//            Log.d(TAG, "Email sent successfully");
//
//        } catch (MessagingException e) {
//            Log.e(TAG, "Error sending email", e);
//        }
//
//        return null;
//    }
}