/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.nntp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.authentication.AuthenticatorFactory;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.apache.geronimo.javamail.transport.nntp.NNTPReply;
import org.apache.geronimo.javamail.util.MIMEOutputStream;
import org.apache.geronimo.javamail.util.MailConnection;
import org.apache.geronimo.javamail.util.ProtocolProperties;
import org.apache.geronimo.mail.util.Base64;

public class NNTPConnection
extends MailConnection {
    protected static final char CR = '\r';
    protected static final char LF = '\n';
    protected static final int DEFAULT_NNTP_PORT = 119;
    protected boolean postingAllowed = true;
    protected boolean authInfoUserAllowed = false;
    protected boolean authInfoSaslAllowed = false;
    protected NNTPReply lastServerResponse = null;
    protected String welcomeString = null;
    protected BufferedReader reader;
    protected PrintWriter writer;

    public NNTPConnection(ProtocolProperties props) {
        super(props);
    }

    public boolean protocolConnect(String host2, int port, String username, String password) throws MessagingException {
        super.protocolConnect(host2, port, username, password);
        this.getConnection();
        this.getWelcome();
        return true;
    }

    protected void getConnection() throws MessagingException {
        try {
            super.getConnection();
        }
        catch (IOException e) {
            throw new MessagingException("Unable to obtain a connection to the NNTP server", e);
        }
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.inputStream, "ISO8859-1"));
            this.writer = new PrintWriter(new OutputStreamWriter((OutputStream)new BufferedOutputStream(this.outputStream), "ISO8859-1"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
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
            this.reader = null;
            this.writer = null;
        }
    }

    public String toString() {
        return "NNTPConnection host: " + this.serverHost + " port: " + this.serverPort;
    }

    public void getWelcome() throws MessagingException {
        NNTPReply line = this.getReply();
        if (line.isError()) {
            throw new MessagingException("Error connecting to news server: " + line.getMessage());
        }
        this.postingAllowed = line.getCode() == 200;
        this.welcomeString = line.getMessage();
        this.getExtensions();
    }

    public void sendQuit() throws MessagingException {
        this.sendLine("QUIT");
    }

    public NNTPReply selectGroup(String name) throws MessagingException {
        return this.sendCommand("GROUP " + name);
    }

    protected void getExtensions() throws MessagingException {
        NNTPReply reply = this.sendCommand("LIST EXTENSIONS", 202);
        if (reply.getCode() != 202) {
            return;
        }
        this.capabilities = new HashMap();
        this.authentications = new ArrayList();
        List extensions = reply.getData();
        for (int i = 0; i < extensions.size(); ++i) {
            this.processExtension((String)extensions.get(i));
        }
    }

    protected void processExtension(String extension) {
        block5: {
            String argument;
            String extensionName;
            block4: {
                extensionName = extension.toUpperCase();
                argument = "";
                int delimiter = extension.indexOf(32);
                if (delimiter != -1) {
                    extensionName = extension.substring(0, delimiter).toUpperCase();
                    argument = extension.substring(delimiter + 1);
                }
                this.capabilities.put(extensionName, argument);
                if (!extensionName.equals("AUTHINFO")) break block4;
                StringTokenizer tokenizer = new StringTokenizer(argument);
                while (tokenizer.hasMoreTokens()) {
                    String mechanism = tokenizer.nextToken().toUpperCase();
                    if (mechanism.equals("SASL")) {
                        this.authInfoSaslAllowed = true;
                        continue;
                    }
                    if (!mechanism.equals("USER")) continue;
                    this.authInfoUserAllowed = true;
                }
                break block5;
            }
            if (!extensionName.equals("SASL")) break block5;
            StringTokenizer tokenizer = new StringTokenizer(argument);
            while (tokenizer.hasMoreTokens()) {
                String mechanism = tokenizer.nextToken().toUpperCase();
                this.authentications.add(mechanism);
            }
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

    public synchronized void sendPost(Message msg) throws MessagingException {
        NNTPReply line = this.sendCommand("POST");
        if (line.getCode() != 340) {
            throw new MessagingException("Server rejected POST command: " + line);
        }
        try {
            MIMEOutputStream mimeOut = new MIMEOutputStream(this.outputStream);
            msg.writeTo(mimeOut);
            mimeOut.writeSMTPTerminator();
            mimeOut.flush();
        }
        catch (IOException e) {
            throw new MessagingException("I/O error posting message", e);
        }
        catch (MessagingException e) {
            throw new MessagingException("Exception posting message", e);
        }
        line = new NNTPReply(this.receiveLine());
        if (line.getCode() != 240) {
            throw new MessagingException("Server rejected POST command: " + line);
        }
    }

    public synchronized NNTPReply sendCommand(String command, int success) throws MessagingException {
        NNTPReply reply = this.sendCommand(command);
        if (reply.getCode() == success) {
            reply.retrieveData(this.reader);
        }
        return reply;
    }

    public NNTPReply sendCommand(String data) throws MessagingException {
        this.sendLine(data);
        NNTPReply reply = this.getReply();
        if (reply.getCode() == 480 || reply.getCode() == 450) {
            this.debugOut("Authentication required received from server.");
            this.processAuthentication(reply.getCode());
            this.sendLine(data);
            reply = this.getReply();
        }
        return reply;
    }

    public NNTPReply sendAuthCommand(String data) throws MessagingException {
        this.sendLine(data);
        return this.getReply();
    }

    public void sendLine(String data) throws MessagingException {
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

    public NNTPReply getReply() throws MessagingException {
        this.lastServerResponse = new NNTPReply(this.receiveLine());
        return this.lastServerResponse;
    }

    public String getLastServerResponse() {
        if (this.lastServerResponse == null) {
            return "";
        }
        return this.lastServerResponse.getReply();
    }

    public String receiveLine() throws MessagingException {
        if (this.socket == null || !this.socket.isConnected()) {
            throw new MessagingException("no connection");
        }
        try {
            String line = this.reader.readLine();
            if (line == null) {
                throw new MessagingException("Unexpected end of stream");
            }
            return line;
        }
        catch (IOException e) {
            throw new MessagingException("Error reading from server", e);
        }
    }

    protected void processAuthentication(int request) throws MessagingException {
        if (this.username == null || this.password == null) {
            throw new MessagingException("Server requires user authentication");
        }
        if (request == 450) {
            this.processAuthinfoSimple();
        } else if (!this.processSaslAuthentication()) {
            this.processAuthinfoUser();
        }
    }

    protected void processAuthinfoSimple() throws MessagingException {
        NNTPReply reply = this.sendAuthCommand("AUTHINFO SIMPLE");
        if (reply.getCode() != 350) {
            throw new MessagingException("Error authenticating with server using AUTHINFO SIMPLE");
        }
        reply = this.sendAuthCommand(this.username + " " + this.password);
        if (reply.getCode() != 250) {
            throw new MessagingException("Error authenticating with server using AUTHINFO SIMPLE");
        }
    }

    protected boolean processSaslAuthentication() throws MessagingException {
        if (!this.authInfoSaslAllowed) {
            return false;
        }
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
        NNTPReply line;
        StringBuffer command;
        this.debugOut("Authenticating for user: " + this.username + " using " + authenticator.getMechanismName());
        if (authenticator.hasInitialResponse()) {
            command = new StringBuffer();
            command.append("AUTHINFO SASL ");
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
            command.append("AUTHINFO SASL");
            command.append(authenticator.getMechanismName());
            this.sendLine(command.toString());
        }
        while (true) {
            if ((line = this.getReply()).getCode() == 250 || line.getCode() == 251) {
                this.debugOut("Successful SMTP authentication");
                return true;
            }
            if (line.getCode() != 350) break;
            if (authenticator.isComplete()) {
                this.debugOut("Extra authentication challenge " + line);
                return false;
            }
            try {
                byte[] challenge = Base64.decode(line.getMessage().getBytes("ISO8859-1"));
                this.sendLine(new String(Base64.encode(authenticator.evaluateChallenge(challenge)), "US-ASCII"));
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {}
        }
        this.debugOut("Authentication failure " + line);
        return false;
    }

    protected void processAuthinfoUser() throws MessagingException {
        if (!this.authInfoUserAllowed) {
            return;
        }
        NNTPReply reply = this.sendAuthCommand("AUTHINFO USER " + this.username);
        if (reply.getCode() == 250) {
            return;
        }
        if (reply.getCode() != 350) {
            throw new MessagingException("Error authenticating with server using AUTHINFO USER: " + reply);
        }
        reply = this.sendAuthCommand("AUTHINFO PASS " + this.password);
        if (reply.getCode() != 250) {
            throw new MessagingException("Error authenticating with server using AUTHINFO SIMPLE");
        }
    }

    public boolean isPostingAllowed() {
        return this.postingAllowed;
    }

    public String getWelcomeString() {
        return this.welcomeString;
    }
}

