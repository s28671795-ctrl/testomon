/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3.connection;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.authentication.AuthenticatorFactory;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.apache.geronimo.javamail.store.pop3.POP3Constants;
import org.apache.geronimo.javamail.store.pop3.connection.POP3ListResponse;
import org.apache.geronimo.javamail.store.pop3.connection.POP3Response;
import org.apache.geronimo.javamail.store.pop3.connection.POP3StatusResponse;
import org.apache.geronimo.javamail.util.CommandFailedException;
import org.apache.geronimo.javamail.util.MIMEInputReader;
import org.apache.geronimo.javamail.util.MailConnection;
import org.apache.geronimo.javamail.util.ProtocolProperties;
import org.apache.geronimo.mail.util.Base64;
import org.apache.geronimo.mail.util.Hex;

public class POP3Connection
extends MailConnection
implements POP3Constants {
    protected static final String MAIL_APOP_ENABLED = "apop.enable";
    protected static final String MAIL_AUTH_ENABLED = "auth.enable";
    protected static final String MAIL_RESET_QUIT = "rsetbeforequit";
    protected static final String MAIL_DISABLE_TOP = "disabletop";
    protected static final String MAIL_FORGET_TOP = "forgettopheaders";
    protected String greeting;
    protected boolean authEnabled;
    protected boolean apopEnabled;
    protected BufferedReader reader;
    protected PrintWriter writer;
    protected boolean closed;
    protected boolean loggedIn;
    protected boolean topDisabled = false;

    public POP3Connection(ProtocolProperties props) {
        super(props);
        this.authEnabled = props.getBooleanProperty(MAIL_AUTH_ENABLED, false);
        this.apopEnabled = props.getBooleanProperty(MAIL_APOP_ENABLED, false);
        this.topDisabled = props.getBooleanProperty(MAIL_DISABLE_TOP, false);
    }

    public boolean protocolConnect(String host2, int port, String authid, String realm, String username, String password) throws MessagingException {
        this.serverHost = host2;
        this.serverPort = port;
        this.realm = realm;
        this.authid = authid;
        this.username = username;
        this.password = password;
        try {
            this.getConnection();
            this.getWelcome();
            if (this.login()) {
                this.loggedIn = true;
                return true;
            }
            return false;
        }
        catch (IOException e) {
            if (this.debug) {
                this.debugOut("I/O exception establishing connection", e);
            }
            throw new MessagingException("Connection error", e);
        }
    }

    protected void getConnection() throws MessagingException {
        try {
            super.getConnection();
        }
        catch (IOException e) {
            throw new MessagingException("Unable to obtain a connection to the POP3 server", e);
        }
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.inputStream, "ISO8859-1"));
            this.writer = new PrintWriter(new OutputStreamWriter((OutputStream)new BufferedOutputStream(this.outputStream), "ISO8859-1"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
    }

    protected void getWelcome() throws IOException {
        this.greeting = this.reader.readLine();
    }

    public String toString() {
        return "POP3Connection host: " + this.serverHost + " port: " + this.serverPort;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void close() throws MessagingException {
        if (this.socket == null) {
            return;
        }
        try {
            this.logout();
        }
        finally {
            this.closeServerConnection();
            this.reader = null;
            this.writer = null;
        }
    }

    public void setClosed() {
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }

    protected POP3Response sendCommand(String cmd) throws MessagingException {
        return this.sendCommand(cmd, false);
    }

    protected POP3Response sendMultiLineCommand(String cmd) throws MessagingException {
        return this.sendCommand(cmd, true);
    }

    protected synchronized POP3Response sendCommand(String cmd, boolean multiLine) throws MessagingException {
        if (this.socket.isConnected()) {
            this.writer.write(cmd);
            this.writer.write("\r\n");
            this.writer.flush();
            POP3Response response = this.buildResponse(multiLine);
            if (response.isError()) {
                throw new CommandFailedException("Error issuing POP3 command: " + cmd);
            }
            return response;
        }
        throw new MessagingException("Connection to Mail Server is lost, connection " + this.toString());
    }

    protected POP3Response buildResponse(boolean isMultiLineResponse) throws MessagingException {
        String line;
        int status = 1;
        byte[] data = null;
        MIMEInputReader source2 = new MIMEInputReader(this.reader);
        try {
            line = this.reader.readLine();
        }
        catch (IOException e) {
            throw new MessagingException("Error in receving response");
        }
        if (line == null || line.trim().equals("")) {
            throw new MessagingException("Empty Response");
        }
        if (line.startsWith("+OK")) {
            status = 0;
            line = POP3Connection.removeStatusField(line);
            if (isMultiLineResponse) {
                data = this.getMultiLineResponse();
            }
        } else if (line.startsWith("-ERR")) {
            status = 1;
            line = POP3Connection.removeStatusField(line);
        } else if (line.startsWith("+")) {
            status = 2;
            line = POP3Connection.removeStatusField(line);
            if (isMultiLineResponse) {
                data = this.getMultiLineResponse();
            }
        } else {
            throw new MessagingException("Unexpected response: " + line);
        }
        return new POP3Response(status, line, data);
    }

    private static String removeStatusField(String line) {
        return line.substring(line.indexOf(" ") + 1);
    }

    private byte[] getMultiLineResponse() throws MessagingException {
        MIMEInputReader source2 = new MIMEInputReader(this.reader);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            OutputStreamWriter outWriter = new OutputStreamWriter((OutputStream)out, "ISO8859-1");
            char[] buffer = new char[500];
            try {
                int charsRead = -1;
                while ((charsRead = source2.read(buffer)) >= 0) {
                    outWriter.write(buffer, 0, charsRead);
                }
                outWriter.flush();
            }
            catch (IOException e) {
                throw new MessagingException("Error processing a multi-line response", e);
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        return out.toByteArray();
    }

    public byte[] retrieveMessageData(int sequenceNumber) throws MessagingException {
        POP3Response msgResponse = this.sendMultiLineCommand("RETR " + sequenceNumber);
        return msgResponse.getData();
    }

    public ByteArrayInputStream retrieveMessageHeaders(int sequenceNumber) throws MessagingException {
        POP3Response msgResponse = this.topDisabled ? this.sendMultiLineCommand("RETR " + sequenceNumber) : this.sendMultiLineCommand("TOP " + sequenceNumber + " 0");
        return msgResponse.getContentStream();
    }

    public int retrieveMessageSize(int sequenceNumber) throws MessagingException {
        POP3Response msgResponse = this.sendCommand("LIST " + sequenceNumber);
        POP3ListResponse list = new POP3ListResponse(msgResponse);
        return list.getSize();
    }

    public POP3StatusResponse retrieveMailboxStatus() throws MessagingException {
        return new POP3StatusResponse(this.sendCommand("STAT"));
    }

    public String retrieveMessageUid(int sequenceNumber) throws MessagingException {
        POP3Response msgResponse = this.sendCommand("UIDL " + sequenceNumber);
        String message = msgResponse.getFirstLine();
        return message.substring(message.indexOf(32) + 1).trim();
    }

    public void deleteMessage(int sequenceNumber) throws MessagingException {
        this.sendCommand("DELE " + sequenceNumber);
    }

    public void logout() throws MessagingException {
        if (!this.loggedIn) {
            return;
        }
        this.sendCommand("QUIT");
        this.loggedIn = false;
    }

    public void reset() throws MessagingException {
        if (this.props.getBooleanProperty(MAIL_RESET_QUIT, false)) {
            this.sendCommand("RSET");
        }
    }

    public void pingServer() throws MessagingException {
        this.sendCommand("NOOP");
    }

    public synchronized boolean login() throws MessagingException {
        if (this.authEnabled) {
            try {
                return this.processSaslAuthentication();
            }
            catch (MessagingException e) {
                // empty catch block
            }
        }
        if (this.apopEnabled) {
            try {
                return this.processAPOPAuthentication();
            }
            catch (MessagingException e) {
                // empty catch block
            }
        }
        try {
            return this.processLogin();
        }
        catch (MessagingException messagingException) {
            return false;
        }
    }

    public boolean processLogin() throws MessagingException {
        this.sendCommand("USER " + this.username);
        this.sendCommand("PASS " + this.password);
        return true;
    }

    public boolean processAPOPAuthentication() throws MessagingException {
        byte[] digest;
        int timeStart = this.greeting.indexOf(60);
        if (timeStart == -1) {
            throw new MessagingException("POP3 Server does not support APOP");
        }
        int timeEnd = this.greeting.indexOf(62);
        String timeStamp = this.greeting.substring(timeStart, timeEnd + 1);
        String digestPassword = timeStamp + this.password;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            digest = md.digest(digestPassword.getBytes("iso-8859-1"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new MessagingException("Unable to create MD5 digest", e);
        }
        catch (UnsupportedEncodingException e) {
            throw new MessagingException("Unable to create MD5 digest", e);
        }
        this.sendCommand("APOP " + this.username + " " + Hex.encode(digest));
        return true;
    }

    protected boolean processSaslAuthentication() throws MessagingException {
        ClientAuthenticator authenticator = this.getSaslAuthenticator();
        if (authenticator == null) {
            throw new MessagingException("Unable to obtain SASL authenticator");
        }
        return this.processLogin(authenticator);
    }

    protected ClientAuthenticator getSaslAuthenticator() {
        return AuthenticatorFactory.getAuthenticator(this.props, this.selectSaslMechanisms(), this.serverHost, this.username, this.password, this.authid, this.realm);
    }

    protected synchronized boolean processLogin(ClientAuthenticator authenticator) throws MessagingException {
        if (this.debug) {
            this.debugOut("Authenticating for user: " + this.username + " using " + authenticator.getMechanismName());
        }
        POP3Response response = this.sendCommand("AUTH " + authenticator.getMechanismName());
        while (response.isChallenge()) {
            byte[] challenge = response.decodeChallengeResponse();
            try {
                String responseString = new String(Base64.encode(authenticator.evaluateChallenge(challenge)), "US-ASCII");
                response = this.sendCommand(responseString);
            }
            catch (UnsupportedEncodingException ex) {}
        }
        return true;
    }

    protected List selectSaslMechanisms() {
        return this.getSaslMechanisms();
    }
}

