/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import org.apache.geronimo.javamail.authentication.AuthenticatorFactory;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.apache.geronimo.javamail.transport.smtp.MalformedSMTPReplyException;
import org.apache.geronimo.javamail.transport.smtp.SMTPAddressFailedException;
import org.apache.geronimo.javamail.transport.smtp.SMTPAddressSucceededException;
import org.apache.geronimo.javamail.transport.smtp.SMTPMessage;
import org.apache.geronimo.javamail.transport.smtp.SMTPReply;
import org.apache.geronimo.javamail.util.CountingOutputStream;
import org.apache.geronimo.javamail.util.MIMEOutputStream;
import org.apache.geronimo.javamail.util.MailConnection;
import org.apache.geronimo.javamail.util.ProtocolProperties;
import org.apache.geronimo.mail.util.Base64;
import org.apache.geronimo.mail.util.XText;

public class SMTPConnection
extends MailConnection {
    protected static final String MAIL_SMTP_QUITWAIT = "quitwait";
    protected static final String MAIL_SMTP_EXTENSION = "mailextension";
    protected static final String MAIL_SMTP_EHLO = "ehlo";
    protected static final String MAIL_SMTP_ALLOW8BITMIME = "allow8bitmime";
    protected static final String MAIL_SMTP_REPORT_SUCCESS = "reportsuccess";
    protected static final String MAIL_SMTP_STARTTLS_ENABLE = "starttls.enable";
    protected static final String MAIL_SMTP_AUTH = "auth";
    protected static final String MAIL_SMTP_FROM = "from";
    protected static final String MAIL_SMTP_DSN_RET = "dsn.ret";
    protected static final String MAIL_SMTP_SUBMITTER = "submitter";
    protected static final int DEFAULT_NNTP_PORT = 119;
    protected SMTPReply lastServerResponse = null;
    protected boolean reportSuccess;
    protected boolean serverTLS = false;
    protected boolean useTLS = false;
    protected boolean use8bit = false;

    public SMTPConnection(ProtocolProperties props) {
        super(props);
        this.reportSuccess = props.getBooleanProperty(MAIL_SMTP_REPORT_SUCCESS, false);
        this.useTLS = props.getBooleanProperty(MAIL_SMTP_STARTTLS_ENABLE, false);
        this.use8bit = props.getBooleanProperty(MAIL_SMTP_ALLOW8BITMIME, false);
    }

    public boolean protocolConnect(String host2, int port, String username, String password) throws MessagingException {
        boolean mustAuthenticate = this.props.getBooleanProperty(MAIL_SMTP_AUTH, false);
        if (mustAuthenticate && (username == null || password == null)) {
            this.debugOut("Failing connection for missing authentication information");
            return false;
        }
        super.protocolConnect(host2, port, username, password);
        try {
            this.getConnection();
            if (!this.getWelcome()) {
                this.debugOut("Error getting welcome message");
                throw new MessagingException("Error in getting welcome msg");
            }
            if (!this.sendHandshake()) {
                this.debugOut("Error getting processing handshake message");
                throw new MessagingException("Error in saying EHLO to server");
            }
            if (!this.processAuthentication()) {
                this.debugOut("User authentication failure");
                throw new AuthenticationFailedException("Error authenticating with server");
            }
        }
        catch (IOException e) {
            this.debugOut("I/O exception establishing connection", e);
            throw new MessagingException("Connection error", e);
        }
        this.debugOut("Successful connection");
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void close() throws MessagingException {
        if (this.socket == null) {
            return;
        }
        try {
            this.sendQuit();
        }
        finally {
            this.closeServerConnection();
        }
    }

    public String toString() {
        return "SMTPConnection host: " + this.serverHost + " port: " + this.serverPort;
    }

    protected boolean sendMailFrom(Message message) throws MessagingException {
        SMTPReply line;
        int estimate;
        String from = null;
        if (message instanceof SMTPMessage) {
            from = ((SMTPMessage)message).getEnvelopeFrom();
        }
        if (from == null || from.length() == 0) {
            from = this.props.getProperty(MAIL_SMTP_FROM);
        }
        if (from == null || from.length() == 0) {
            Address[] fromAddresses = message.getFrom();
            if (fromAddresses != null && fromAddresses.length > 0) {
                from = ((InternetAddress)fromAddresses[0]).getAddress();
            } else {
                InternetAddress local = InternetAddress.getLocalAddress(this.session);
                if (local != null) {
                    from = local.getAddress();
                }
            }
        }
        if (from == null || from.length() == 0) {
            throw new MessagingException("no FROM address");
        }
        StringBuffer command = new StringBuffer();
        command.append("MAIL FROM: ");
        command.append(this.fixEmailAddress(from));
        if (this.supportsExtension("8BITMIME") && (this.use8bit || message instanceof SMTPMessage && ((SMTPMessage)message).getAllow8bitMIME())) {
            command.append(" BODY=8BITMIME");
            if (this.convertTransferEncoding((MimeMessage)message)) {
                message.saveChanges();
            }
        }
        if (this.supportsExtension("SIZE") && (estimate = this.getSizeEstimate(message)) > 0) {
            command.append(" SIZE=" + estimate);
        }
        if (this.supportsExtension("DSN")) {
            String returnNotification = null;
            if (message instanceof SMTPMessage) {
                switch (((SMTPMessage)message).getReturnOption()) {
                    case 1: {
                        returnNotification = "FULL";
                        break;
                    }
                    case 2: {
                        returnNotification = "HDRS";
                    }
                }
            }
            if (returnNotification == null) {
                returnNotification = this.props.getProperty(MAIL_SMTP_DSN_RET);
            }
            if (returnNotification != null) {
                command.append(" RET=");
                command.append(returnNotification);
            }
        }
        if (this.supportsExtension("AUTH")) {
            String submitter = null;
            if (message instanceof SMTPMessage) {
                submitter = ((SMTPMessage)message).getSubmitter();
            }
            if (submitter == null) {
                submitter = this.props.getProperty(MAIL_SMTP_SUBMITTER);
            }
            if (submitter != null) {
                command.append(" AUTH=");
                try {
                    command.append(new String(XText.encode(submitter.getBytes("US-ASCII")), "US-ASCII"));
                }
                catch (UnsupportedEncodingException e) {
                    throw new MessagingException("Invalid submitter value " + submitter);
                }
            }
        }
        String extension = null;
        if (message instanceof SMTPMessage) {
            extension = ((SMTPMessage)message).getMailExtension();
        }
        if (extension == null) {
            extension = this.props.getProperty(MAIL_SMTP_EXTENSION);
        }
        if (extension != null && extension.length() != 0) {
            command.append(' ');
            command.append(extension);
        }
        return (line = this.sendCommand(command.toString())).getCode() == 250;
    }

    protected boolean convertTransferEncoding(MimePart bodyPart) {
        boolean converted = false;
        try {
            if (bodyPart.isMimeType("multipart/")) {
                MimeMultipart parts = (MimeMultipart)bodyPart.getContent();
                for (int i = 0; i < parts.getCount(); ++i) {
                    converted = converted && this.convertTransferEncoding((MimePart)((Object)parts.getBodyPart(i)));
                }
            } else {
                String encoding = bodyPart.getEncoding();
                if (encoding != null && ((encoding = encoding.toLowerCase()).equals("quoted-printable") || encoding.equals("base64")) && this.isValid8bit(bodyPart.getInputStream())) {
                    bodyPart.setContent(bodyPart.getContent(), bodyPart.getContentType());
                    bodyPart.setHeader("Content-Transfer-Encoding", "8bit");
                    converted = true;
                }
            }
        }
        catch (MessagingException e) {
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return converted;
    }

    protected boolean getWelcome() throws MessagingException {
        SMTPReply line = this.getReply();
        return !line.isError();
    }

    protected int getSizeEstimate(Message msg) {
        try {
            CountingOutputStream outputStream2 = new CountingOutputStream();
            MIMEOutputStream mimeOut = new MIMEOutputStream(outputStream2);
            msg.writeTo(mimeOut);
            mimeOut.forceTerminatingLineBreak();
            mimeOut.flush();
            return outputStream2.getCount();
        }
        catch (IOException e) {
            return 0;
        }
        catch (MessagingException e) {
            return 0;
        }
    }

    protected void sendData(MimeMessage msg) throws MessagingException {
        SMTPReply line = this.sendCommand("DATA");
        if (line.isError()) {
            throw new MessagingException("Error issuing SMTP 'DATA' command: " + line);
        }
        try {
            MIMEOutputStream mimeOut = new MIMEOutputStream(this.outputStream);
            msg.writeTo(mimeOut, new String[]{"Bcc", "Content-Length"});
            mimeOut.writeSMTPTerminator();
            mimeOut.flush();
        }
        catch (IOException e) {
            throw new MessagingException(e.toString());
        }
        catch (MessagingException e) {
            throw new MessagingException(e.toString());
        }
        try {
            line = new SMTPReply(this.receiveLine(600000));
        }
        catch (MalformedSMTPReplyException e) {
            throw new MessagingException(e.toString());
        }
        catch (MessagingException e) {
            throw new MessagingException(e.toString());
        }
        if (line.isError()) {
            throw new MessagingException("Error issuing SMTP 'DATA' command: " + line);
        }
    }

    protected void sendQuit() throws MessagingException {
        if (this.props.getBooleanProperty(MAIL_SMTP_QUITWAIT, true)) {
            this.sendCommand("QUIT");
        } else {
            this.sendLine("QUIT");
        }
    }

    public SendStatus sendRcptTo(InternetAddress addr, String dsn) throws MessagingException {
        StringBuffer command = new StringBuffer();
        command.append("RCPT TO: ");
        command.append(this.fixEmailAddress(addr.getAddress()));
        if (dsn != null) {
            command.append(" NOTIFY=");
            command.append(dsn);
        }
        String commandString = command.toString();
        SMTPReply line = this.sendCommand(commandString);
        switch (line.getCode()) {
            case 250: 
            case 251: {
                return new SendStatus(0, addr, commandString, line);
            }
            case 501: 
            case 503: 
            case 550: 
            case 551: 
            case 553: {
                return new SendStatus(1, addr, commandString, line);
            }
            case 421: 
            case 450: 
            case 451: 
            case 452: 
            case 552: {
                return new SendStatus(2, addr, commandString, line);
            }
        }
        return new SendStatus(3, addr, commandString, line);
    }

    protected SMTPReply sendCommand(String data) throws MessagingException {
        this.sendLine(data);
        return this.getReply();
    }

    protected void sendLine(String data) throws MessagingException {
        if (this.socket == null || !this.socket.isConnected()) {
            throw new MessagingException("no connection");
        }
        try {
            this.outputStream.write(data.getBytes("ISO8859-1"));
            this.outputStream.write(13);
            this.outputStream.write(10);
            this.outputStream.flush();
        }
        catch (IOException e) {
            throw new MessagingException(e.toString());
        }
    }

    protected String receiveLine() throws MessagingException {
        return this.receiveLine(300000);
    }

    protected SMTPReply getReply() throws MessagingException {
        try {
            this.lastServerResponse = new SMTPReply(this.receiveLine());
            while (this.lastServerResponse.isContinued()) {
                this.lastServerResponse.addLine(this.receiveLine());
            }
        }
        catch (MalformedSMTPReplyException e) {
            throw new MessagingException(e.toString());
        }
        catch (MessagingException e) {
            throw e;
        }
        return this.lastServerResponse;
    }

    public SMTPReply getLastServerResponse() {
        return this.lastServerResponse;
    }

    protected String receiveLine(int delayMillis) throws MessagingException {
        if (this.socket == null || !this.socket.isConnected()) {
            throw new MessagingException("no connection");
        }
        int timeout2 = 0;
        try {
            String line;
            int c;
            timeout2 = this.socket.getSoTimeout();
            this.socket.setSoTimeout(delayMillis);
            StringBuffer buff = new StringBuffer();
            boolean crFound = false;
            boolean lfFound = false;
            while ((c = this.inputStream.read()) != -1 && !crFound && !lfFound) {
                if (c == 13) {
                    crFound = true;
                    continue;
                }
                if (c == 10) {
                    lfFound = true;
                    continue;
                }
                buff.append((char)c);
            }
            String string = line = buff.toString();
            return string;
        }
        catch (SocketException e) {
            throw new MessagingException(e.toString());
        }
        catch (IOException e) {
            throw new MessagingException(e.toString());
        }
        finally {
            try {
                this.socket.setSoTimeout(timeout2);
            }
            catch (SocketException e) {}
        }
    }

    protected String fixEmailAddress(String mail) {
        if (mail.charAt(0) == '<') {
            return mail;
        }
        return "<" + mail + ">";
    }

    protected boolean sendHandshake() throws MessagingException {
        boolean useEhlo = this.props.getBooleanProperty(MAIL_SMTP_EHLO, true);
        if (useEhlo) {
            if (!this.sendEhlo()) {
                this.sendHelo();
            }
        } else {
            this.sendHelo();
        }
        if (this.useTLS) {
            if (!this.serverTLS) {
                throw new MessagingException("Server doesn't support required transport level security");
            }
            this.getConnectedTLSSocket();
            if (!this.sendEhlo()) {
                throw new MessagingException("Failure sending EHLO command to SMTP server");
            }
        }
        return true;
    }

    protected void getConnectedTLSSocket() throws MessagingException {
        this.debugOut("Attempting to negotiate STARTTLS with server " + this.serverHost);
        SMTPReply line = this.sendCommand("STARTTLS");
        if (line.getCode() != 220) {
            this.debugOut("STARTTLS command rejected by SMTP server " + this.serverHost);
            throw new MessagingException("Unable to make TLS server connection");
        }
        this.debugOut("STARTTLS command accepted");
        super.getConnectedTLSSocket();
    }

    protected boolean sendEhlo() throws MessagingException {
        this.sendLine("EHLO " + this.getLocalHost());
        SMTPReply reply = this.getReply();
        if (reply.getCode() != 250) {
            return false;
        }
        this.capabilities = new HashMap();
        this.authentications = new ArrayList();
        List lines = reply.getLines();
        for (int i = 1; i < lines.size(); ++i) {
            this.processExtension((String)lines.get(i));
        }
        return true;
    }

    protected void sendHelo() throws MessagingException {
        this.capabilities = new HashMap();
        this.authentications = new ArrayList();
        this.sendLine("HELO " + this.getLocalHost());
        SMTPReply line = this.getReply();
        if (line.getCode() != 250) {
            throw new MessagingException("Failure sending HELO command to SMTP server");
        }
    }

    public boolean getStartTLS() {
        return this.useTLS;
    }

    public void setStartTLS(boolean start2) {
        this.useTLS = start2;
    }

    protected void processExtension(String extension) {
        this.debugOut("Processing extension " + extension);
        String extensionName = extension.toUpperCase();
        String argument = "";
        int delimiter = extension.indexOf(32);
        if (delimiter != -1) {
            extensionName = extension.substring(0, delimiter).toUpperCase();
            argument = extension.substring(delimiter + 1);
        }
        this.capabilities.put(extensionName, argument);
        if (extensionName.equals("AUTH")) {
            if (argument == null) {
                this.authentications.add("LOGIN");
            } else {
                StringTokenizer tokenizer = new StringTokenizer(argument);
                while (tokenizer.hasMoreTokens()) {
                    String mechanism = tokenizer.nextToken().toUpperCase();
                    this.authentications.add(mechanism);
                }
            }
        } else if (extensionName.equals("AUTH=LOGIN")) {
            this.authentications.add("LOGIN");
        } else if (extensionName.equals("STARTTLS")) {
            this.serverTLS = true;
        }
    }

    public String extensionParameter(String name) {
        if (this.capabilities != null) {
            return (String)this.capabilities.get(name);
        }
        return null;
    }

    public boolean supportsExtension(String name) {
        return this.extensionParameter(name) != null;
    }

    protected boolean processAuthentication() throws MessagingException {
        SMTPReply line;
        StringBuffer command;
        if (!this.props.getBooleanProperty(MAIL_SMTP_AUTH, false)) {
            return true;
        }
        if (this.username == null || this.password == null) {
            return false;
        }
        ClientAuthenticator authenticator = this.getSaslAuthenticator();
        if (authenticator == null) {
            throw new MessagingException("Unable to obtain SASL authenticator");
        }
        if (this.debug) {
            this.debugOut("Authenticating for user: " + this.username + " using " + authenticator.getMechanismName());
        }
        if (authenticator.hasInitialResponse()) {
            command = new StringBuffer();
            command.append("AUTH ");
            command.append(authenticator.getMechanismName());
            command.append(" ");
            try {
                command.append(new String(Base64.encode(authenticator.evaluateChallenge(null)), "US-ASCII"));
            }
            catch (UnsupportedEncodingException e) {
                // empty catch block
            }
            this.sendLine(command.toString());
        } else {
            command = new StringBuffer();
            command.append("AUTH ");
            command.append(authenticator.getMechanismName());
            this.sendLine(command.toString());
        }
        while (true) {
            try {
                line = new SMTPReply(this.receiveLine());
            }
            catch (MalformedSMTPReplyException e) {
                throw new MessagingException(e.toString());
            }
            catch (MessagingException e) {
                throw e;
            }
            if (line.getCode() == 235) {
                this.debugOut("Successful SMTP authentication");
                return true;
            }
            if (line.getCode() != 334) break;
            if (authenticator.isComplete()) {
                return false;
            }
            try {
                byte[] challenge = Base64.decode(line.getMessage().getBytes("ISO8859-1"));
                this.sendLine(new String(Base64.encode(authenticator.evaluateChallenge(challenge)), "US-ASCII"));
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {}
        }
        if (this.debug) {
            this.debugOut("Authentication failure " + line);
        }
        return false;
    }

    protected ClientAuthenticator getSaslAuthenticator() {
        return AuthenticatorFactory.getAuthenticator(this.props, this.selectSaslMechanisms(), this.serverHost, this.username, this.password, this.authid, this.realm);
    }

    protected boolean isValid8bit(InputStream inStream) {
        try {
            int ch;
            int lineLength = 0;
            while ((ch = inStream.read()) >= 0) {
                if (ch == 0) {
                    return false;
                }
                if (ch == 13) {
                    ch = inStream.read();
                    if (ch != 10) {
                        return false;
                    }
                    lineLength = 0;
                    continue;
                }
                if (++lineLength <= 998) continue;
                return false;
            }
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public void resetConnection() throws MessagingException {
        SMTPReply last = this.lastServerResponse;
        SMTPReply line = this.sendCommand("RSET");
        if (line.getCode() != 250) {
            this.close();
        }
        this.lastServerResponse = last;
    }

    public boolean getReportSuccess() {
        return this.reportSuccess;
    }

    public void setReportSuccess(boolean report) {
        this.reportSuccess = report;
    }

    public static class SendStatus {
        public static final int SUCCESS = 0;
        public static final int INVALID_ADDRESS = 1;
        public static final int SEND_FAILURE = 2;
        public static final int GENERAL_ERROR = 3;
        int status;
        InternetAddress address;
        String cmd;
        SMTPReply reply;

        public SendStatus(int s2, InternetAddress a, String c, SMTPReply r) {
            this.cmd = c;
            this.status = s2;
            this.address = a;
            this.reply = r;
        }

        public int getStatus() {
            return this.status;
        }

        public InternetAddress getAddress() {
            return this.address;
        }

        public SMTPReply getReply() {
            return this.reply;
        }

        public String getCommand() {
            return this.cmd;
        }

        public MessagingException getException(boolean reportSuccess) {
            if (this.status != 0) {
                return new SMTPAddressFailedException(this.address, this.cmd, this.reply.getCode(), this.reply.getMessage());
            }
            if (reportSuccess) {
                return new SMTPAddressSucceededException(this.address, this.cmd, this.reply.getCode(), this.reply.getMessage());
            }
            return null;
        }
    }
}

