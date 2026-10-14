/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.nntp;

import java.util.ArrayList;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.NewsAddress;
import org.apache.geronimo.javamail.transport.nntp.NNTPConnection;
import org.apache.geronimo.javamail.transport.nntp.NNTPReply;
import org.apache.geronimo.javamail.util.ProtocolProperties;

public class NNTPTransport
extends Transport {
    protected static final String NNTP_FROM = "from";
    protected static final int DEFAULT_NNTP_PORT = 119;
    protected static final int DEFAULT_NNTP_SSL_PORT = 563;
    protected ProtocolProperties props;
    protected NNTPConnection connection;

    public NNTPTransport(Session session, URLName name) {
        this(session, name, "nntp-post", 119, false);
    }

    protected NNTPTransport(Session session, URLName name, String protocol, int defaultPort, boolean sslConnection) {
        super(session, name);
        this.props = new ProtocolProperties(session, protocol, sslConnection, defaultPort);
        this.connection = new NNTPConnection(this.props);
    }

    protected boolean protocolConnect(String host2, int port, String username, String password) throws MessagingException {
        return this.connection.protocolConnect(host2, port, username, password);
    }

    public void sendMessage(Message message, Address[] addresses) throws MessagingException {
        String defaultFrom;
        if (!this.isConnected()) {
            throw new IllegalStateException("Not connected");
        }
        if (!this.connection.isPostingAllowed()) {
            throw new MessagingException("Posting disabled for host server");
        }
        if (message == null) {
            throw new MessagingException("Null message");
        }
        if (!(message instanceof MimeMessage)) {
            throw new MessagingException("NNTP can only send MimeMessages");
        }
        Object from = null;
        Address[] fromAddresses = message.getFrom();
        if ((fromAddresses == null || fromAddresses.length == 0) && (defaultFrom = this.props.getProperty(NNTP_FROM)) == null) {
            message.setFrom(new InternetAddress(defaultFrom));
        }
        if (addresses == null || addresses.length == 0) {
            throw new MessagingException("Null or empty address array");
        }
        boolean haveGroup = false;
        for (int i = 0; i < addresses.length; ++i) {
            if (addresses[i] instanceof NewsAddress) continue;
            throw new MessagingException("Illegal NewsAddress " + addresses[i]);
        }
        ArrayList<Address> sentAddresses = new ArrayList<Address>();
        ArrayList<Address> unsentAddresses = new ArrayList<Address>();
        ArrayList<Address> invalidAddresses = new ArrayList<Address>();
        boolean sendFailure = false;
        for (int i = 0; i < addresses.length; ++i) {
            try {
                NNTPReply reply = this.connection.selectGroup(((NewsAddress)addresses[i]).getNewsgroup());
                if (reply.getCode() != 211) {
                    invalidAddresses.add(addresses[i]);
                    sendFailure = true;
                    continue;
                }
                this.connection.sendPost(message);
                sentAddresses.add(addresses[i]);
                continue;
            }
            catch (MessagingException e) {
                unsentAddresses.add(addresses[i]);
                sendFailure = true;
            }
        }
        Address[] sent = sentAddresses.toArray(new Address[0]);
        Address[] unsent = unsentAddresses.toArray(new Address[0]);
        Address[] invalid = invalidAddresses.toArray(new Address[0]);
        if (sendFailure) {
            if (sent.length == 0) {
                this.notifyTransportListeners(2, sent, unsent, invalid, message);
            } else {
                this.notifyTransportListeners(3, sent, unsent, invalid, message);
            }
            throw new MessagingException("Error posting NNTP message");
        }
        this.notifyTransportListeners(1, sent, unsent, invalid, message);
    }

    public void close() throws MessagingException {
        super.close();
        this.connection.close();
    }
}

