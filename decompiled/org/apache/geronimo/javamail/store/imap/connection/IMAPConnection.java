/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Quota;
import javax.mail.internet.InternetHeaders;
import javax.mail.search.SearchTerm;
import org.apache.geronimo.javamail.authentication.AuthenticatorFactory;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.apache.geronimo.javamail.authentication.LoginAuthenticator;
import org.apache.geronimo.javamail.authentication.PlainAuthenticator;
import org.apache.geronimo.javamail.store.imap.ACL;
import org.apache.geronimo.javamail.store.imap.Rights;
import org.apache.geronimo.javamail.store.imap.connection.IMAPACLResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBody;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodyStructure;
import org.apache.geronimo.javamail.store.imap.connection.IMAPCapabilityResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPCommand;
import org.apache.geronimo.javamail.store.imap.connection.IMAPConnectionPool;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFlags;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFlagsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPInternetHeader;
import org.apache.geronimo.javamail.store.imap.connection.IMAPListRightsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPMailboxStatus;
import org.apache.geronimo.javamail.store.imap.connection.IMAPMessageText;
import org.apache.geronimo.javamail.store.imap.connection.IMAPMyRightsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPNamespaceResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPOkResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPPermanentFlagsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPQuotaResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseStream;
import org.apache.geronimo.javamail.store.imap.connection.IMAPSearchResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPSizeResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPStatusResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPTaggedResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUid;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponseHandler;
import org.apache.geronimo.javamail.util.CommandFailedException;
import org.apache.geronimo.javamail.util.InvalidCommandException;
import org.apache.geronimo.javamail.util.MailConnection;
import org.apache.geronimo.javamail.util.ProtocolProperties;
import org.apache.geronimo.mail.util.Base64;

public class IMAPConnection
extends MailConnection {
    protected static final String CAPABILITY_LOGIN_DISABLED = "LOGINDISABLED";
    protected IMAPConnectionPool pool;
    protected IMAPResponseStream reader;
    protected long lastAccess = 0L;
    protected LinkedList responseHandlers = new LinkedList();
    protected List queuedResponses = new LinkedList();
    protected boolean closed = false;

    public IMAPConnection(ProtocolProperties props, IMAPConnectionPool pool) {
        super(props);
        this.pool = pool;
    }

    public boolean protocolConnect(String host2, int port, String authid, String realm, String username, String password) throws MessagingException {
        this.serverHost = host2;
        this.serverPort = port;
        this.realm = realm;
        this.authid = authid;
        this.username = username;
        this.password = password;
        boolean preAuthorized = false;
        try {
            this.getConnection();
            this.getCapability();
            if (this.extractResponse("PREAUTH") != null) {
                preAuthorized = true;
            }
            this.processPendingResponses();
            if (!this.sslConnection && this.props.getBooleanProperty("starttls.enable", false) && this.hasCapability("STARTTLS")) {
                this.sendSimpleCommand("STARTTLS");
                this.getConnectedTLSSocket();
                this.reader = new IMAPResponseStream(this.inputStream);
                this.getCapability();
                if (this.extractResponse("PREAUTH") != null) {
                    preAuthorized = true;
                }
            }
            if (preAuthorized) {
                boolean bl = true;
                return bl;
            }
            boolean bl = this.login();
            return bl;
        }
        catch (IOException e) {
            if (this.debug) {
                this.debugOut("I/O exception establishing connection", e);
            }
            throw new MessagingException("Connection error", e);
        }
        finally {
            this.processPendingResponses();
        }
    }

    protected void updateLastAccess() {
        this.lastAccess = System.currentTimeMillis();
    }

    public boolean isStale(long timeout2) {
        return System.currentTimeMillis() - this.lastAccess > timeout2;
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
        }
    }

    protected void getConnection() throws IOException, MessagingException {
        super.getConnection();
        this.reader = new IMAPResponseStream(this.inputStream);
        this.updateLastAccess();
    }

    public void sendSimpleCommand(String data) throws MessagingException {
        IMAPCommand command = new IMAPCommand(data);
        this.sendSimpleCommand(command);
    }

    public void sendSimpleCommand(IMAPCommand data) throws MessagingException {
        this.sendCommand(data);
    }

    public IMAPTaggedResponse sendCommand(String data) throws MessagingException {
        IMAPCommand command = new IMAPCommand(data);
        return this.sendCommand(command);
    }

    public synchronized IMAPTaggedResponse sendCommand(IMAPCommand data) throws MessagingException {
        this.checkConnected();
        try {
            data.writeTo(this.outputStream, this);
            this.outputStream.flush();
            this.updateLastAccess();
            return this.receiveResponse();
        }
        catch (IOException e) {
            throw new MessagingException(e.toString(), e);
        }
    }

    public IMAPTaggedResponse sendLine(String data) throws MessagingException {
        try {
            return this.sendLine(data.getBytes("ISO8859-1"));
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public IMAPTaggedResponse sendLine(byte[] data) throws MessagingException {
        return this.sendLine(data, 0, data.length);
    }

    public synchronized IMAPTaggedResponse sendLine(byte[] data, int offset, int length) throws MessagingException {
        this.checkConnected();
        try {
            this.outputStream.write(data, offset, length);
            this.outputStream.write(13);
            this.outputStream.write(10);
            this.outputStream.flush();
            this.updateLastAccess();
            return this.receiveResponse();
        }
        catch (IOException e) {
            throw new MessagingException(e.toString(), e);
        }
    }

    public IMAPTaggedResponse receiveResponse() throws MessagingException {
        while (true) {
            IMAPResponse response;
            if ((response = this.reader.readResponse()) instanceof IMAPTaggedResponse) {
                this.updateLastAccess();
                IMAPTaggedResponse tagged = (IMAPTaggedResponse)response;
                if (tagged.isBAD()) {
                    throw new InvalidCommandException("Unexpected command IMAP command error");
                }
                if (tagged.isNO()) {
                    throw new CommandFailedException("Unexpected error executing IMAP command");
                }
                return tagged;
            }
            this.queuePendingResponse((IMAPUntaggedResponse)response);
        }
    }

    public void getCapability() throws MessagingException {
        this.sendCommand("CAPABILITY");
        IMAPCapabilityResponse response = (IMAPCapabilityResponse)this.extractResponse("CAPABILITY");
        this.capabilities = response.getCapabilities();
        this.authentications = response.getAuthentications();
    }

    public void logout() throws MessagingException {
        this.sendCommand("LOGOUT");
    }

    public void closeMailbox() throws MessagingException {
        this.sendCommand("CLOSE");
    }

    protected boolean login() throws MessagingException {
        if (this.username == null || this.password == null) {
            return false;
        }
        if (this.props.getBooleanProperty("sasl.enable", false) && this.processSaslAuthentication()) {
            return true;
        }
        if (!this.props.getBooleanProperty("auth.plain.disable", false) && this.supportsMechanism("PLAIN")) {
            return this.processPlainAuthentication();
        }
        if (!this.props.getBooleanProperty("auth.login.disable", false) && this.supportsMechanism("LOGIN")) {
            return this.processLoginAuthentication();
        }
        if (!this.hasCapability(CAPABILITY_LOGIN_DISABLED)) {
            return this.processLogin();
        }
        throw new MessagingException("No supported LOGIN methods enabled");
    }

    protected boolean processSaslAuthentication() throws MessagingException {
        ClientAuthenticator authenticator = this.getSaslAuthenticator();
        if (authenticator == null) {
            return false;
        }
        return this.processLogin(authenticator);
    }

    protected ClientAuthenticator getSaslAuthenticator() {
        return AuthenticatorFactory.getAuthenticator(this.props, this.selectSaslMechanisms(), this.serverHost, this.username, this.password, this.authid, this.realm);
    }

    protected boolean processPlainAuthentication() throws MessagingException {
        return this.processLogin(new PlainAuthenticator(this.username, this.password));
    }

    protected boolean processLoginAuthentication() throws MessagingException {
        return this.processLogin(new LoginAuthenticator(this.username, this.password));
    }

    protected boolean processLogin() throws MessagingException {
        IMAPCommand command = new IMAPCommand("LOGIN");
        command.appendAtom(this.username);
        command.appendAtom(this.password);
        try {
            this.sendCommand(command);
        }
        catch (CommandFailedException e) {
            return false;
        }
        return true;
    }

    protected synchronized boolean processLogin(ClientAuthenticator authenticator) throws MessagingException {
        if (this.debug) {
            this.debugOut("Authenticating for user: " + this.username + " using " + authenticator.getMechanismName());
        }
        IMAPCommand command = new IMAPCommand("AUTHENTICATE");
        command.appendAtom(authenticator.getMechanismName());
        try {
            IMAPTaggedResponse response = this.sendCommand(command);
            while (response.isContinuation()) {
                byte[] challenge = response.decodeChallengeResponse();
                response = this.sendLine(Base64.encode(authenticator.evaluateChallenge(challenge)));
            }
            return true;
        }
        catch (CommandFailedException e) {
            return false;
        }
    }

    public String getHost() {
        return this.serverHost;
    }

    public synchronized void addResponseHandler(IMAPUntaggedResponseHandler h2) {
        this.responseHandlers.add(h2);
    }

    public synchronized void removeResponseHandler(IMAPUntaggedResponseHandler h2) {
        this.responseHandlers.remove(h2);
    }

    public synchronized void queuePendingResponse(IMAPUntaggedResponse response) {
        this.queuedResponses.add(response);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void processPendingResponses() throws MessagingException {
        List pendingResponses = null;
        List handlerList = null;
        IMAPConnection iMAPConnection = this;
        synchronized (iMAPConnection) {
            if (this.queuedResponses.isEmpty()) {
                return;
            }
            pendingResponses = this.queuedResponses;
            this.queuedResponses = new LinkedList();
            handlerList = (List)this.responseHandlers.clone();
        }
        for (int i = 0; i < pendingResponses.size(); ++i) {
            IMAPUntaggedResponseHandler h2;
            IMAPUntaggedResponse response = (IMAPUntaggedResponse)pendingResponses.get(i);
            for (int j = 0; j < handlerList.size() && !(h2 = (IMAPUntaggedResponseHandler)handlerList.get(j)).handleResponse(response); ++j) {
            }
        }
    }

    public IMAPUntaggedResponse extractResponse(String type) {
        Iterator i = this.queuedResponses.iterator();
        while (i.hasNext()) {
            IMAPUntaggedResponse response = (IMAPUntaggedResponse)i.next();
            if (!response.isKeyword(type)) continue;
            i.remove();
            return response;
        }
        return null;
    }

    public List extractResponses(String type) {
        ArrayList<IMAPUntaggedResponse> responses = new ArrayList<IMAPUntaggedResponse>();
        Iterator i = this.queuedResponses.iterator();
        while (i.hasNext()) {
            IMAPUntaggedResponse response = (IMAPUntaggedResponse)i.next();
            if (!response.isKeyword(type)) continue;
            i.remove();
            responses.add(response);
        }
        return responses;
    }

    public List extractFetchResponses(int sequenceNumber) {
        ArrayList<IMAPUntaggedResponse> responses = new ArrayList<IMAPUntaggedResponse>();
        Iterator i = this.queuedResponses.iterator();
        while (i.hasNext()) {
            IMAPUntaggedResponse response = (IMAPUntaggedResponse)i.next();
            if (!response.isKeyword("FETCH")) continue;
            IMAPFetchResponse fetch = (IMAPFetchResponse)response;
            if (fetch.sequenceNumber != sequenceNumber) continue;
            i.remove();
            responses.add(response);
        }
        return responses;
    }

    protected IMAPFetchDataItem extractFetchDataItem(long sequenceNumber, int type) {
        Iterator i = this.queuedResponses.iterator();
        while (i.hasNext()) {
            IMAPFetchDataItem item;
            IMAPUntaggedResponse response = (IMAPUntaggedResponse)i.next();
            if (!response.isKeyword("FETCH")) continue;
            IMAPFetchResponse fetch = (IMAPFetchResponse)response;
            if ((long)fetch.sequenceNumber != sequenceNumber || (item = fetch.getDataItem(type)) == null) continue;
            i.remove();
            return item;
        }
        return null;
    }

    protected List extractFetchDataItems(int type) {
        Iterator i = this.queuedResponses.iterator();
        ArrayList<IMAPFetchResponse> items = new ArrayList<IMAPFetchResponse>();
        while (i.hasNext()) {
            IMAPFetchResponse fetch;
            IMAPFetchDataItem item;
            IMAPUntaggedResponse response = (IMAPUntaggedResponse)i.next();
            if (!response.isKeyword("FETCH") || (item = (fetch = (IMAPFetchResponse)response).getDataItem(type)) == null) continue;
            i.remove();
            items.add(fetch);
        }
        return items;
    }

    public void updateMailboxStatus() throws MessagingException {
        this.sendSimpleCommand("NOOP");
    }

    public synchronized boolean isAlive(long timeout2) {
        long lastUsed = System.currentTimeMillis() - this.lastAccess;
        if (lastUsed < timeout2) {
            return true;
        }
        try {
            this.sendSimpleCommand("NOOP");
            return true;
        }
        catch (MessagingException e) {
            return false;
        }
    }

    public synchronized List fetchEnvelope(int sequenceNumber) throws MessagingException {
        IMAPCommand command = new IMAPCommand("FETCH");
        command.appendInteger(sequenceNumber);
        command.startList();
        command.appendAtom("ENVELOPE INTERNALDATE RFC822.SIZE");
        command.endList();
        this.sendCommand(command);
        return this.extractFetchResponses(sequenceNumber);
    }

    public synchronized IMAPBodyStructure fetchBodyStructure(int sequenceNumber) throws MessagingException {
        IMAPCommand command = new IMAPCommand("FETCH");
        command.appendInteger(sequenceNumber);
        command.startList();
        command.appendAtom("BODYSTRUCTURE");
        command.endList();
        this.sendCommand(command);
        IMAPBodyStructure bodyStructure = (IMAPBodyStructure)this.extractFetchDataItem(sequenceNumber, 3);
        if (bodyStructure == null) {
            throw new MessagingException("No BODYSTRUCTURE information received from IMAP server");
        }
        return bodyStructure;
    }

    public synchronized InternetHeaders fetchHeaders(int sequenceNumber, String part) throws MessagingException {
        IMAPCommand command = new IMAPCommand("FETCH");
        command.appendInteger(sequenceNumber);
        command.startList();
        command.appendAtom("BODY.PEEK");
        command.appendBodySection(part, "HEADER");
        command.endList();
        this.sendCommand(command);
        IMAPInternetHeader header = (IMAPInternetHeader)this.extractFetchDataItem(sequenceNumber, 8);
        if (header == null) {
            throw new MessagingException("No HEADER information received from IMAP server");
        }
        return header.headers;
    }

    public synchronized IMAPMessageText fetchText(int sequenceNumber) throws MessagingException {
        IMAPCommand command = new IMAPCommand("FETCH");
        command.appendInteger(sequenceNumber);
        command.startList();
        command.appendAtom("BODY.PEEK");
        command.appendBodySection("TEXT");
        command.endList();
        this.sendCommand(command);
        IMAPMessageText text = (IMAPMessageText)this.extractFetchDataItem(sequenceNumber, 7);
        if (text == null) {
            throw new MessagingException("No TEXT information received from IMAP server");
        }
        return text;
    }

    public synchronized IMAPMessageText fetchBodyPartText(int sequenceNumber, String section) throws MessagingException {
        IMAPCommand command = new IMAPCommand("FETCH");
        command.appendInteger(sequenceNumber);
        command.startList();
        command.appendAtom("BODY.PEEK");
        command.appendBodySection(section, "TEXT");
        command.endList();
        this.sendCommand(command);
        IMAPMessageText text = (IMAPMessageText)this.extractFetchDataItem(sequenceNumber, 7);
        if (text == null) {
            throw new MessagingException("No TEXT information received from IMAP server");
        }
        return text;
    }

    public synchronized IMAPBody fetchBody(int sequenceNumber, String section) throws MessagingException {
        IMAPCommand command = new IMAPCommand("FETCH");
        command.appendInteger(sequenceNumber);
        command.startList();
        command.appendAtom("BODY.PEEK");
        command.appendBodySection(section, null);
        command.endList();
        this.sendCommand(command);
        IMAPBody body2 = (IMAPBody)this.extractFetchDataItem(sequenceNumber, 2);
        if (body2 == null) {
            throw new MessagingException("No BODY information received from IMAP server");
        }
        return body2;
    }

    public byte[] fetchContent(int sequenceNumber) throws MessagingException {
        IMAPMessageText text = this.fetchText(sequenceNumber);
        return text.getContent();
    }

    public byte[] fetchContent(int sequenceNumber, String section) throws MessagingException {
        if (section == null) {
            IMAPMessageText text = this.fetchText(sequenceNumber);
            return text.getContent();
        }
        IMAPBody body2 = this.fetchBody(sequenceNumber, section);
        return body2.getContent();
    }

    public synchronized List list(String mailbox, String pattern) throws MessagingException {
        IMAPCommand command = new IMAPCommand("LIST");
        command.appendEncodedString(mailbox);
        command.appendEncodedString(pattern);
        this.sendCommand(command);
        return this.extractResponses("LIST");
    }

    public List listSubscribed(String mailbox, String pattern) throws MessagingException {
        IMAPCommand command = new IMAPCommand("LSUB");
        command.appendEncodedString(mailbox);
        command.appendEncodedString(pattern);
        this.sendCommand(command);
        return this.extractResponses("LSUB");
    }

    public void subscribe(String mailbox) throws MessagingException {
        IMAPCommand command = new IMAPCommand("SUBSCRIBE");
        command.appendEncodedString(mailbox);
        this.sendSimpleCommand(command);
    }

    public void unsubscribe(String mailbox) throws MessagingException {
        IMAPCommand command = new IMAPCommand("UNSUBSCRIBE");
        command.appendEncodedString(mailbox);
        this.sendSimpleCommand(command);
    }

    public void createMailbox(String mailbox) throws MessagingException {
        IMAPCommand command = new IMAPCommand("CREATE");
        command.appendEncodedString(mailbox);
        this.sendSimpleCommand(command);
    }

    public void deleteMailbox(String mailbox) throws MessagingException {
        IMAPCommand command = new IMAPCommand("DELETE");
        command.appendEncodedString(mailbox);
        this.sendSimpleCommand(command);
    }

    public void renameMailbox(String oldName, String newName) throws MessagingException {
        IMAPCommand command = new IMAPCommand("RENAME");
        command.appendEncodedString(oldName);
        command.appendEncodedString(newName);
        this.sendSimpleCommand(command);
    }

    public synchronized IMAPMailboxStatus getMailboxStatus(String mailbox) throws MessagingException {
        IMAPCommand command = new IMAPCommand("STATUS");
        command.appendEncodedString(mailbox);
        command.append(" (MESSAGES RECENT UIDNEXT UIDVALIDITY UNSEEN)");
        this.sendCommand(command);
        IMAPMailboxStatus status = new IMAPMailboxStatus();
        status.mergeSizeResponses(this.extractResponses("EXISTS"));
        status.mergeSizeResponses(this.extractResponses("RECENT"));
        status.mergeOkResponses(this.extractResponses("UIDNEXT"));
        status.mergeOkResponses(this.extractResponses("UIDVALIDITY"));
        status.mergeOkResponses(this.extractResponses("UNSEEN"));
        status.mergeStatus((IMAPStatusResponse)this.extractResponse("STATUS"));
        status.mergeStatus((IMAPPermanentFlagsResponse)this.extractResponse("PERMANENTFLAGS"));
        return status;
    }

    public synchronized IMAPMailboxStatus openMailbox(String mailbox, boolean readOnly) throws MessagingException {
        IMAPCommand command = new IMAPCommand();
        if (readOnly) {
            command.appendAtom("EXAMINE");
        } else {
            command.appendAtom("SELECT");
        }
        command.appendEncodedString(mailbox);
        IMAPTaggedResponse response = this.sendCommand(command);
        IMAPMailboxStatus status = new IMAPMailboxStatus();
        int n = status.mode = readOnly ? 1 : 2;
        if (response.hasStatus("READ-ONLY")) {
            status.mode = 1;
        }
        status.mergeFlags((IMAPFlagsResponse)this.extractResponse("FLAGS"));
        status.mergeStatus((IMAPSizeResponse)this.extractResponse("EXISTS"));
        status.mergeStatus((IMAPSizeResponse)this.extractResponse("RECENT"));
        status.mergeStatus((IMAPOkResponse)this.extractResponse("UIDVALIDITY"));
        status.mergeStatus((IMAPOkResponse)this.extractResponse("UNSEEN"));
        status.mergeStatus((IMAPPermanentFlagsResponse)this.extractResponse("PERMANENTFLAGS"));
        return status;
    }

    public synchronized List expungeMailbox() throws MessagingException {
        this.sendCommand("EXPUNGE");
        return this.extractResponses("EXPUNGED");
    }

    public int[] searchMailbox(SearchTerm term) throws MessagingException {
        return this.searchMailbox("ALL", term);
    }

    public int[] searchMailbox(String messages, SearchTerm term) throws MessagingException {
        String charset = null;
        if (IMAPCommand.checkSearchEncoding(term)) {
            charset = "UTF-8";
        }
        return this.searchMailbox(messages, term, charset);
    }

    public synchronized int[] searchMailbox(String messages, SearchTerm term, String charset) throws MessagingException {
        IMAPCommand command = new IMAPCommand("SEARCH");
        if (charset != null) {
            command.appendAtom("CHARSET");
            command.appendAtom(charset);
        }
        command.appendSearchTerm(term, charset);
        command.appendAtom(messages);
        this.sendCommand(command);
        IMAPSearchResponse hits = (IMAPSearchResponse)this.extractResponse("SEARCH");
        return hits.messageNumbers;
    }

    public void appendMessage(String mailbox, Date messageDate, Flags messageFlags, byte[] messageData) throws MessagingException {
        IMAPCommand command = new IMAPCommand("APPEND");
        command.appendEncodedString(mailbox);
        if (messageFlags != null) {
            messageFlags.remove(Flags.Flag.RECENT);
            command.appendFlags(messageFlags);
        }
        if (messageDate != null) {
            command.appendDate(messageDate);
        }
        command.appendLiteral(messageData);
        this.sendSimpleCommand(command);
    }

    public synchronized Flags fetchFlags(int sequenceNumber) throws MessagingException {
        this.sendCommand("FETCH " + String.valueOf(sequenceNumber) + " (FLAGS)");
        IMAPFlags flags = (IMAPFlags)this.extractFetchDataItem(sequenceNumber, 9);
        return flags.flags;
    }

    public synchronized List setFlags(String messageSet, Flags flags, boolean set) throws MessagingException {
        IMAPCommand command = new IMAPCommand("STORE");
        command.appendAtom(messageSet);
        if (set) {
            command.appendAtom("+FLAGS");
        } else {
            command.appendAtom("-FLAGS");
        }
        command.appendFlags(flags);
        this.sendCommand(command);
        return this.extractFetchDataItems(9);
    }

    public synchronized Flags setFlags(int sequenceNumber, Flags flags, boolean set) throws MessagingException {
        IMAPCommand command = new IMAPCommand("STORE");
        command.appendInteger(sequenceNumber);
        if (set) {
            command.appendAtom("+FLAGS");
        } else {
            command.appendAtom("-FLAGS");
        }
        command.appendFlags(flags);
        this.sendCommand(command);
        IMAPFlags flagResponse = (IMAPFlags)this.extractFetchDataItem(sequenceNumber, 9);
        return flagResponse.flags;
    }

    public void copyMessages(String messageSet, String target) throws MessagingException {
        IMAPCommand command = new IMAPCommand("COPY");
        command.appendAtom(messageSet);
        command.appendEncodedString(target);
        this.sendSimpleCommand(command);
    }

    public synchronized IMAPUid getSequenceNumberForUid(long uid) throws MessagingException {
        IMAPCommand command = new IMAPCommand("UID FETCH");
        command.appendLong(uid);
        command.appendAtom("(UID)");
        this.sendCommand(command);
        List responses = this.extractResponses("FETCH");
        for (int i = 0; i < responses.size(); ++i) {
            IMAPFetchResponse response = (IMAPFetchResponse)responses.get(i);
            IMAPUid item = (IMAPUid)response.getDataItem(6);
            if (item != null && item.uid == uid) {
                return item;
            }
            this.queuePendingResponse(response);
        }
        return null;
    }

    public synchronized List getSequenceNumbersForUids(long start2, long end) throws MessagingException {
        IMAPCommand command = new IMAPCommand("UID FETCH");
        command.appendLong(start2);
        command.append(":");
        if (end != -1L) {
            command.appendLong(end);
        } else {
            command.append("*");
        }
        command.appendAtom("(UID)");
        this.sendCommand(command);
        List responses = this.extractResponses("FETCH");
        ArrayList<IMAPUid> uids = new ArrayList<IMAPUid>((int)(end - start2 + 1L));
        for (int i = 0; i < responses.size(); ++i) {
            IMAPFetchResponse response = (IMAPFetchResponse)responses.get(i);
            IMAPUid item = (IMAPUid)response.getDataItem(6);
            if (item != null) {
                uids.add(item);
                continue;
            }
            this.queuePendingResponse(response);
        }
        return uids;
    }

    public synchronized IMAPUid getUidForSequenceNumber(int sequenceNumber) throws MessagingException {
        IMAPCommand command = new IMAPCommand("FETCH");
        command.appendInteger(sequenceNumber);
        command.appendAtom("(UID)");
        this.sendCommand(command);
        return (IMAPUid)this.extractFetchDataItem(sequenceNumber, 6);
    }

    public synchronized IMAPNamespaceResponse getNamespaces() throws MessagingException {
        if (!this.hasCapability("NAMESPACE")) {
            return new IMAPNamespaceResponse();
        }
        this.sendCommand("NAMESPACE");
        return (IMAPNamespaceResponse)this.extractResponse("NAMESPACE");
    }

    public synchronized List fetch(String messageSet, FetchProfile profile) throws MessagingException {
        IMAPCommand command = new IMAPCommand("FETCH");
        command.appendAtom(messageSet);
        command.appendFetchProfile(profile);
        this.sendCommand(command);
        return this.extractResponses("FETCH");
    }

    public synchronized void setACLRights(String mailbox, ACL acl) throws MessagingException {
        IMAPCommand command = new IMAPCommand("SETACL");
        command.appendEncodedString(mailbox);
        command.appendACL(acl);
        this.sendSimpleCommand(command);
    }

    public synchronized void addACLRights(String mailbox, ACL acl) throws MessagingException {
        if (!this.hasCapability("ACL")) {
            throw new MethodNotSupportedException("ACL not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("SETACL");
        command.appendEncodedString(mailbox);
        command.appendACL(acl, "+");
        this.sendSimpleCommand(command);
    }

    public synchronized void removeACLRights(String mailbox, ACL acl) throws MessagingException {
        if (!this.hasCapability("ACL")) {
            throw new MethodNotSupportedException("ACL not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("SETACL");
        command.appendEncodedString(mailbox);
        command.appendACL(acl, "-");
        this.sendSimpleCommand(command);
    }

    public synchronized ACL[] getACLRights(String mailbox) throws MessagingException {
        if (!this.hasCapability("ACL")) {
            throw new MethodNotSupportedException("ACL not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("GETACL");
        command.appendEncodedString(mailbox);
        this.sendCommand(command);
        IMAPACLResponse response = (IMAPACLResponse)this.extractResponse("ACL");
        return response.acls;
    }

    public synchronized Rights getMyRights(String mailbox) throws MessagingException {
        if (!this.hasCapability("ACL")) {
            throw new MethodNotSupportedException("ACL not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("MYRIGHTS");
        command.appendEncodedString(mailbox);
        this.sendCommand(command);
        IMAPMyRightsResponse response = (IMAPMyRightsResponse)this.extractResponse("MYRIGHTS");
        return response.rights;
    }

    public synchronized Rights[] listACLRights(String mailbox, String name) throws MessagingException {
        if (!this.hasCapability("ACL")) {
            throw new MethodNotSupportedException("ACL not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("LISTRIGHTS");
        command.appendEncodedString(mailbox);
        command.appendString(name);
        this.sendCommand(command);
        IMAPListRightsResponse response = (IMAPListRightsResponse)this.extractResponse("LISTRIGHTS");
        return response.rights;
    }

    public synchronized void deleteACL(String mailbox, String name) throws MessagingException {
        if (!this.hasCapability("ACL")) {
            throw new MethodNotSupportedException("ACL not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("DELETEACL");
        command.appendEncodedString(mailbox);
        command.appendString(name);
        this.sendSimpleCommand(command);
    }

    public synchronized Quota[] fetchQuotaRoot(String mailbox) throws MessagingException {
        if (!this.hasCapability("QUOTA")) {
            throw new MethodNotSupportedException("QUOTA not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("GETQUOTAROOT");
        command.appendEncodedString(mailbox);
        this.sendCommand(command);
        this.extractResponse("QUOTAROOT");
        List responses = this.extractResponses("QUOTA");
        Quota[] quotas = new Quota[responses.size()];
        for (int i = 0; i < quotas.length; ++i) {
            IMAPQuotaResponse q = (IMAPQuotaResponse)responses.get(i);
            quotas[i] = q.quota;
        }
        return quotas;
    }

    public synchronized Quota[] fetchQuota(String root) throws MessagingException {
        if (!this.hasCapability("QUOTA")) {
            throw new MethodNotSupportedException("QUOTA not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("GETQUOTA");
        command.appendString(root);
        this.sendCommand(command);
        List responses = this.extractResponses("QUOTA");
        Quota[] quotas = new Quota[responses.size()];
        for (int i = 0; i < quotas.length; ++i) {
            IMAPQuotaResponse q = (IMAPQuotaResponse)responses.get(i);
            quotas[i] = q.quota;
        }
        return quotas;
    }

    public synchronized void setQuota(Quota quota) throws MessagingException {
        if (!this.hasCapability("QUOTA")) {
            throw new MethodNotSupportedException("QUOTA not available from this IMAP server");
        }
        IMAPCommand command = new IMAPCommand("GETQUOTA");
        command.appendQuota(quota);
        this.sendCommand(command);
        this.extractResponses("QUOTA");
    }

    public boolean hasCapability(String capability) {
        if (this.capabilities == null) {
            return false;
        }
        return this.capabilities.containsKey(capability);
    }

    public void setClosed() {
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }
}

