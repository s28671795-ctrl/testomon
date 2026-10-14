/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils.Messaging;

import com.incoresoft.Utils.Messaging.MyMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MessagingUtils {
    public static boolean sendPhoto(String urlString, String imagePath, int counter) {
        String boundary = String.valueOf(System.currentTimeMillis());
        String LINE_FEED = "\r\n";
        try {
            int bytesRead;
            String charset = "UTF-8";
            URL url2 = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection)url2.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            OutputStream outputStream2 = con.getOutputStream();
            PrintWriter writer = new PrintWriter((Writer)new OutputStreamWriter(outputStream2, charset), true);
            File uploadFile = new File(imagePath);
            String fileName = uploadFile.getName();
            writer.append("--").append(boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"photo\"; filename=\"").append(fileName).append("\"");
            writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName));
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            FileInputStream inputStream2 = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream2.read(buffer)) != -1) {
                outputStream2.write(buffer, 0, bytesRead);
            }
            outputStream2.flush();
            inputStream2.close();
            writer.append(LINE_FEED).flush();
            writer.append("--").append(boundary).append("--");
            writer.close();
            int responseCode = 0;
            try {
                responseCode = con.getResponseCode();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (responseCode != 200 && counter > 0) {
                Thread.sleep(10000L);
                return MessagingUtils.sendPhoto(urlString, imagePath, counter - 1);
            }
            return true;
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendMsg(String method2, String urlString, String body2, int counter) {
        try {
            URL url2 = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection)url2.openConnection();
            con.setRequestProperty("User-Agent", "Mozzila Firefox");
            con.setRequestMethod(method2);
            if (method2.equals("POST") && body2 != null) {
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(body2);
                osw.flush();
                osw.close();
                os.close();
                con.connect();
            }
            int responseCode = 0;
            try {
                responseCode = con.getResponseCode();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (responseCode != 200 && counter > 0) {
                Thread.sleep(10000L);
                return MessagingUtils.sendMsg(method2, urlString, body2, counter - 1);
            }
            return true;
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendEmail(String server, String port, String from, String email, String subject, String text, File attachment, boolean html, int counter) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", server);
        props.put("mail.smtp.port", port);
        Session session = Session.getInstance(props, null);
        try {
            MyMessage message = new MyMessage(session);
            message.setFrom(new InternetAddress("user", from));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            if (html) {
                message.setContent(text, "text/html; charset=utf-8");
            } else {
                message.setText(text);
            }
            message.setSentDate(new Date());
            MimeMultipart multipart = new MimeMultipart("related");
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, "text/html");
            multipart.addBodyPart(messageBodyPart);
            if (attachment.exists()) {
                messageBodyPart = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(attachment);
                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setFileName(attachment.getName());
                multipart.addBodyPart(messageBodyPart);
            }
            message.setContent(multipart);
            Transport.send(message);
        }
        catch (UnsupportedEncodingException | MessagingException e) {
            try {
                if (counter > 0) {
                    Thread.sleep(10000L);
                    return MessagingUtils.sendEmail(server, port, from, email, subject, text, attachment, html, counter - 1);
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendSms(String text, String number, int counter) {
        return false;
    }
}

