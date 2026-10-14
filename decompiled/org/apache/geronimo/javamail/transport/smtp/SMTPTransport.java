/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.smtp;

import java.net.Socket;
import java.util.ArrayList;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.geronimo.javamail.transport.smtp.SMTPConnection;
import org.apache.geronimo.javamail.transport.smtp.SMTPMessage;
import org.apache.geronimo.javamail.transport.smtp.SMTPReply;
import org.apache.geronimo.javamail.transport.smtp.SMTPSendFailedException;
import org.apache.geronimo.javamail.util.ProtocolProperties;

public class SMTPTransport
extends Transport {
    protected static final String MAIL_SMTP_DSN_NOTIFY = "dsn.notify";
    protected static final String MAIL_SMTP_SENDPARTIAL = "sendpartial";
    protected static final String MAIL_SMTP_EXTENSION = "mailextension";
    protected static final String DEFAULT_MAIL_HOST = "localhost";
    protected static final int DEFAULT_MAIL_SMTP_PORT = 25;
    protected static final int DEFAULT_MAIL_SMTPS_PORT = 465;
    protected boolean sslConnection = false;
    protected ProtocolProperties props;
    protected SMTPConnection connection;
    protected SMTPReply lastServerResponse = null;

    public SMTPTransport(Session session, URLName name) {
        this(session, name, "smtp", 25, false);
    }

    protected SMTPTransport(Session session, URLName name, String protocol, int defaultPort, boolean sslConnection) {
        super(session, name);
        this.props = new ProtocolProperties(session, protocol, sslConnection, defaultPort);
        this.connection = new SMTPConnection(this.props);
    }

    public void connect(Socket socket) throws MessagingException {
        this.connection.connect(socket);
        super.connect();
    }

    protected boolean protocolConnect(String host2, int port, String username, String password) throws MessagingException {
        return this.connection.protocolConnect(host2, port, username, password);
    }

    public void sendMessage(Message message, Address[] addresses) throws MessagingException {
        if (!this.isConnected()) {
            throw new IllegalStateException("Not connected");
        }
        if (message == null) {
            throw new MessagingException("Null message");
        }
        if (!(message instanceof MimeMessage)) {
            throw new MessagingException("SMTP can only send MimeMessages");
        }
        if (addresses == null || addresses.length == 0) {
            throw new MessagingException("Null or empty address array");
        }
        boolean reportSuccess = this.getReportSuccess();
        boolean partialSends = false;
        if (message instanceof SMTPMessage) {
            partialSends = ((SMTPMessage)message).getSendPartial();
        }
        if (!partialSends) {
            partialSends = this.props.getBooleanProperty(MAIL_SMTP_SENDPARTIAL, false);
        }
        boolean haveGroup = false;
        for (int i = 0; i < addresses.length; ++i) {
            if (addresses[i] instanceof InternetAddress) {
                if (!((InternetAddress)addresses[i]).isGroup()) continue;
                haveGroup = true;
                continue;
            }
            throw new MessagingException("Illegal InternetAddress " + addresses[i]);
        }
        if (haveGroup) {
            addresses = this.expandGroups(addresses);
        }
        SMTPConnection.SendStatus[] stats = new SMTPConnection.SendStatus[addresses.length];
        Address[] sent = null;
        Address[] unsent = null;
        Address[] invalid = null;
        try {
            MessagingException successes;
            if (!this.connection.sendMailFrom(message)) {
                unsent = addresses;
                sent = new Address[]{};
                invalid = new Address[]{};
                this.notifyTransportListeners(2, sent, unsent, invalid, message);
                SMTPReply last = this.connection.getLastServerResponse();
                throw new SMTPSendFailedException("MAIL FROM", last.getCode(), last.getMessage(), null, sent, unsent, invalid);
            }
            String dsn = this.getDeliveryStatusNotification(message);
            boolean sendFailure = false;
            ArrayList<InternetAddress> sentAddresses = new ArrayList<InternetAddress>();
            ArrayList<InternetAddress> unsentAddresses = new ArrayList<InternetAddress>();
            ArrayList<InternetAddress> invalidAddresses = new ArrayList<InternetAddress>();
            block11: for (int i = 0; i < addresses.length; ++i) {
                SMTPConnection.SendStatus status;
                InternetAddress target = (InternetAddress)addresses[i];
                stats[i] = status = this.connection.sendRcptTo(target, dsn);
                switch (status.getStatus()) {
                    case 0: {
                        sentAddresses.add(target);
                        continue block11;
                    }
                    case 1: 
                    case 3: {
                        sendFailure = true;
                        invalidAddresses.add(target);
                        continue block11;
                    }
                    case 2: {
                        sendFailure = true;
                        unsentAddresses.add(target);
                    }
                }
            }
            if (sendFailure && (!partialSends || sentAddresses.isEmpty())) {
                unsentAddresses.addAll(sentAddresses);
                sent = new Address[]{};
                unsent = unsentAddresses.toArray(new Address[0]);
                invalid = invalidAddresses.toArray(new Address[0]);
                this.connection.resetConnection();
                MessagingException failures = this.generateExceptionChain(stats, false);
                throw new SMTPSendFailedException("MAIL TO", 0, "Invalid Address", failures, sent, unsent, invalid);
            }
            try {
                this.connection.sendData((MimeMessage)message);
            }
            catch (MessagingException e) {
                unsentAddresses.addAll(sentAddresses);
                sent = new Address[]{};
                unsent = unsentAddresses.toArray(new Address[0]);
                invalid = invalidAddresses.toArray(new Address[0]);
                this.notifyTransportListeners(2, sent, unsent, invalid, message);
                throw new SMTPSendFailedException("DATA", 0, "Send failure", e, sent, unsent, invalid);
            }
            sent = sentAddresses.toArray(new Address[0]);
            unsent = unsentAddresses.toArray(new Address[0]);
            invalid = invalidAddresses.toArray(new Address[0]);
            if (sendFailure) {
                this.notifyTransportListeners(3, sent, unsent, invalid, message);
                MessagingException failures = this.generateExceptionChain(stats, reportSuccess);
                throw new SMTPSendFailedException("MAIL TO", 0, "Invalid Address", failures, sent, unsent, invalid);
            }
            this.notifyTransportListeners(1, sent, unsent, invalid, message);
            if (reportSuccess && (successes = this.generateExceptionChain(stats, reportSuccess)) != null) {
                throw successes;
            }
        }
        catch (SMTPSendFailedException e) {
            throw e;
        }
        catch (MessagingException e) {
            this.notifyTransportListeners(2, sent, unsent, invalid, message);
            throw e;
        }
    }

    protected String getDeliveryStatusNotification(Message message) {
        String dsn = null;
        if (message instanceof SMTPMessage) {
            int options = ((SMTPMessage)message).getNotifyOptions();
            switch (options) {
                case 0: {
                    break;
                }
                case -1: {
                    dsn = "NEVER";
                    break;
                }
                case 1: {
                    dsn = "SUCCESS";
                    break;
                }
                case 2: {
                    dsn = "FAILURE";
                    break;
                }
                case 4: {
                    dsn = "DELAY";
                    break;
                }
                case 3: {
                    dsn = "SUCCESS,FAILURE";
                    break;
                }
                case 5: {
                    dsn = "SUCCESS,DELAY";
                    break;
                }
                case 6: {
                    dsn = "FAILURE,DELAY";
                    break;
                }
                case 7: {
                    dsn = "SUCCESS,FAILURE,DELAY";
                }
            }
        }
        if (dsn == null) {
            dsn = this.props.getProperty(MAIL_SMTP_DSN_NOTIFY);
        }
        return dsn;
    }

    public void close() throws MessagingException {
        super.close();
        this.connection.close();
    }

    protected MessagingException generateExceptionChain(SMTPConnection.SendStatus[] stats, boolean reportSuccess) {
        MessagingException current = null;
        for (int i = 0; i < stats.length; ++i) {
            MessagingException nextException;
            SMTPConnection.SendStatus status = stats[i];
            if (status == null || (nextException = stats[i].getException(reportSuccess)) == null) continue;
            if (current == null) {
                current = nextException;
                continue;
            }
            current.setNextException(nextException);
            current = nextException;
        }
        return current;
    }

    protected Address[] expandGroups(Address[] addresses) throws MessagingException {
        ArrayList<InternetAddress> expandedAddresses = new ArrayList<InternetAddress>();
        for (int i = 0; i < addresses.length; ++i) {
            InternetAddress address = (InternetAddress)addresses[i];
            if (!address.isGroup()) {
                expandedAddresses.add(address);
                continue;
            }
            InternetAddress[] groupAddresses = address.getGroup(true);
            for (int j = 1; j < groupAddresses.length; ++j) {
                expandedAddresses.add(groupAddresses[j]);
            }
        }
        return expandedAddresses.toArray(new Address[0]);
    }

    public String getLocalHost() throws MessagingException {
        return this.connection.getLocalHost();
    }

    public void setLocalHost(String localHost) {
        this.connection.setLocalHost(localHost);
    }

    public boolean getReportSuccess() {
        return this.connection.getReportSuccess();
    }

    public void setReportSuccess(boolean report) {
        this.connection.setReportSuccess(report);
    }

    public boolean getStartTLS() {
        return this.connection.getStartTLS();
    }

    public void setStartTLS(boolean start2) {
        this.connection.setStartTLS(start2);
    }

    public String getSASLRealm() {
        return this.connection.getSASLRealm();
    }

    public void setSASLRealm(String name) {
        this.connection.setSASLRealm(name);
    }
}

