/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.UIDFolder;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import org.apache.geronimo.javamail.store.imap.ACL;
import org.apache.geronimo.javamail.store.imap.IMAPMessage;
import org.apache.geronimo.javamail.store.imap.IMAPStore;
import org.apache.geronimo.javamail.store.imap.Rights;
import org.apache.geronimo.javamail.store.imap.connection.IMAPConnection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFlags;
import org.apache.geronimo.javamail.store.imap.connection.IMAPListResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPMailboxStatus;
import org.apache.geronimo.javamail.store.imap.connection.IMAPSizeResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUid;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponseHandler;

public class IMAPFolder
extends Folder
implements UIDFolder,
IMAPUntaggedResponseHandler {
    protected static final char UNDETERMINED = '\u0000';
    protected Session session;
    protected Map messageCache;
    protected Map uidCache;
    protected char separator;
    protected String fullname;
    protected String name;
    protected boolean folderOpen = false;
    protected int folderType;
    protected boolean subscribed = false;
    protected int nextMessageID = 1;
    protected int maxSequenceNumber = 0;
    protected int recentMessages = -1;
    protected int unseenMessages = 0;
    protected long uidValidity = 0L;
    protected long uidNext = 0L;
    protected Flags permanentFlags;
    protected Flags availableFlags;
    protected IMAPMailboxStatus cachedStatus;
    protected IMAPListResponse listInfo;
    protected long statusCacheTimeout;
    protected long lastStatusTimeStamp;
    protected IMAPConnection currentConnection;

    protected IMAPFolder(IMAPStore store, String fullname, char separator) {
        super(store);
        this.session = store.getSession();
        this.fullname = fullname;
        this.separator = separator;
        this.statusCacheTimeout = store.statusCacheTimeout;
    }

    public String getName() {
        if (this.name == null) {
            int lastLevel = -1;
            try {
                lastLevel = this.fullname.lastIndexOf(this.getSeparator());
            }
            catch (MessagingException messagingException) {
                // empty catch block
            }
            this.name = lastLevel == -1 ? this.fullname : this.fullname.substring(lastLevel + 1);
        }
        return this.name;
    }

    public String getFullName() {
        return this.fullname;
    }

    public Folder getParent() throws MessagingException {
        char separator = this.getSeparator();
        int lastLevel = this.fullname.lastIndexOf(separator);
        if (lastLevel == -1) {
            return ((IMAPStore)this.store).getDefaultFolder();
        }
        return new IMAPFolder((IMAPStore)this.store, this.fullname.substring(0, lastLevel), separator);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean exists() throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            boolean bl = this.checkExistance(connection);
            return bl;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    private boolean checkExistance(IMAPConnection connection) throws MessagingException {
        List responses = connection.list("", this.fullname);
        this.listInfo = this.findListResponse(responses, this.getMailBoxName());
        if (this.listInfo == null) {
            return false;
        }
        this.folderType = 0;
        if (!this.listInfo.noinferiors) {
            this.folderType |= 2;
        }
        if (!this.listInfo.noselect) {
            this.folderType |= 1;
        }
        this.separator = this.listInfo.separator;
        if (this.separator == '\u0000') {
            this.separator = (char)47;
        }
        return true;
    }

    public synchronized Folder[] list(String pattern) throws MessagingException {
        return this.filterFolders(pattern, false);
    }

    public synchronized Folder[] listSubscribed(String pattern) throws MessagingException {
        return this.filterFolders(pattern, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized char getSeparator() throws MessagingException {
        if (this.separator == '\u0000') {
            IMAPConnection connection = this.getConnection();
            try {
                List responses = connection.list("", this.fullname);
                IMAPListResponse info = this.findListResponse(responses, this.fullname);
                if (info == null) {
                    this.separator = (char)47;
                } else {
                    this.separator = info.separator;
                    if (this.separator == '\u0000') {
                        this.separator = (char)47;
                    }
                }
            }
            finally {
                this.releaseConnection(connection);
            }
        }
        return this.separator;
    }

    public int getType() throws MessagingException {
        this.checkFolderValidity();
        return this.folderType;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean create(int newType) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            String newPath = this.fullname;
            if ((newType & 1) == 0) {
                newPath = this.fullname + this.separator;
            }
            try {
                connection.createMailbox(newPath);
                boolean reallyCreated = this.checkExistance(connection);
                this.notifyFolderListeners(1);
                boolean bl = reallyCreated;
                return bl;
            }
            catch (MessagingException e) {
                boolean bl = false;
                this.releaseConnection(connection);
                return bl;
            }
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public synchronized boolean isSubscribed() {
        try {
            IMAPConnection connection = this.getConnection();
            try {
                List responses = connection.listSubscribed("", this.fullname);
                IMAPListResponse response = this.findListResponse(responses, this.fullname);
                if (response == null) {
                    boolean bl = false;
                    return bl;
                }
                boolean bl = !response.noselect;
                return bl;
            }
            finally {
                this.releaseConnection(connection);
            }
        }
        catch (MessagingException messagingException) {
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void setSubscribed(boolean flag) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            if (flag) {
                connection.subscribe(this.fullname);
            } else {
                connection.unsubscribe(this.fullname);
            }
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    public synchronized boolean hasNewMessages() throws MessagingException {
        this.checkFolderValidity();
        this.refreshStatus(true);
        return this.recentMessages > 0;
    }

    public Folder getFolder(String name) throws MessagingException {
        this.checkFolderValidity();
        if (!this.holdsFolders()) {
            throw new MessagingException("Folder " + this.fullname + " cannot hold subfolders");
        }
        char separator = this.getSeparator();
        return new IMAPFolder((IMAPStore)this.store, this.fullname + separator + name, separator);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean delete(boolean recurse) throws MessagingException {
        boolean i;
        this.checkClosed();
        if (recurse) {
            Folder[] subfolders = this.list();
            for (i = false; i < subfolders.length; i += 1) {
                subfolders[i].delete(true);
            }
        }
        IMAPConnection connection = this.getConnection();
        try {
            connection.deleteMailbox(this.fullname);
            this.listInfo = null;
            this.notifyFolderListeners(2);
            i = true;
            return i;
        }
        catch (MessagingException messagingException) {
        }
        finally {
            this.releaseConnection(connection);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean renameTo(Folder f) throws MessagingException {
        this.checkClosed();
        this.checkFolderValidity();
        IMAPConnection connection = this.getConnection();
        try {
            connection.renameMailbox(this.fullname, f.getFullName());
            this.refreshStatus(false);
            this.notifyFolderRenamedListeners(f);
            boolean bl = true;
            return bl;
        }
        catch (MessagingException messagingException) {
        }
        finally {
            this.releaseConnection(connection);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void open(int mode) throws MessagingException {
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkClosed();
            this.currentConnection = ((IMAPStore)this.store).getFolderConnection(this);
            this.currentConnection.addResponseHandler(this);
            this.mode = mode;
            try {
                IMAPMailboxStatus status = this.currentConnection.openMailbox(this.fullname, mode == 1);
                if (status.mode != mode && mode == 2) {
                    throw new ReadOnlyFolderException(this, "Cannot open READ_ONLY folder in READ_WRITE mode");
                }
                this.cachedStatus = status;
                this.lastStatusTimeStamp = System.currentTimeMillis();
                this.mode = status.mode;
                this.maxSequenceNumber = status.messages;
                this.recentMessages = status.recentMessages;
                this.uidValidity = status.uidValidity;
                this.uidNext = status.uidNext;
                this.availableFlags = status.availableFlags;
                this.permanentFlags = status.permanentFlags;
                this.messageCache = new HashMap();
                this.uidCache = new HashMap();
                this.folderOpen = true;
                this.notifyConnectionListeners(1);
            }
            finally {
                this.releaseConnection(this.currentConnection);
            }
        }
    }

    public synchronized void close(boolean expunge) throws MessagingException {
        this.checkOpen();
        this.cleanupFolder(expunge, false);
    }

    protected void cleanupFolder(boolean expunge, boolean disconnected) throws MessagingException {
        this.folderOpen = false;
        this.uidCache = null;
        this.messageCache = null;
        if (this.currentConnection != null) {
            if (disconnected) {
                this.currentConnection.setClosed();
            } else {
                if (this.mode == 2 && !expunge) {
                    this.currentConnection.openMailbox(this.fullname, true);
                }
                this.currentConnection.closeMailbox();
            }
            this.currentConnection.removeResponseHandler(this);
            ((IMAPStore)this.store).releaseFolderConnection(this, this.currentConnection);
            this.currentConnection = null;
        }
        this.notifyConnectionListeners(3);
    }

    public boolean isOpen() {
        return this.folderOpen;
    }

    public synchronized Flags getPermanentFlags() {
        if (this.permanentFlags != null) {
            return new Flags(this.permanentFlags);
        }
        return null;
    }

    public synchronized int getMessageCount() throws MessagingException {
        this.checkFolderValidity();
        this.refreshStatus(false);
        return this.maxSequenceNumber;
    }

    public synchronized int getNewMessageCount() throws MessagingException {
        this.checkFolderValidity();
        this.refreshStatus(false);
        return this.recentMessages;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized int getUnreadMessageCount() throws MessagingException {
        this.checkFolderValidity();
        if (!this.folderOpen) {
            this.refreshStatus(false);
        } else {
            FlagTerm criteria = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            IMAPConnection connection = this.getConnection();
            try {
                int[] matches = connection.searchMailbox(criteria);
                this.unseenMessages = matches == null ? 0 : matches.length;
            }
            finally {
                this.releaseConnection(connection);
            }
        }
        return this.unseenMessages;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized int getDeletedMessageCount() throws MessagingException {
        this.checkFolderValidity();
        if (!this.folderOpen) {
            return -1;
        }
        FlagTerm criteria = new FlagTerm(new Flags(Flags.Flag.DELETED), true);
        IMAPConnection connection = this.getConnection();
        try {
            int[] matches = connection.searchMailbox(criteria);
            int n = matches == null ? 0 : matches.length;
            return n;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    public synchronized Message getMessage(int msgNum) throws MessagingException {
        this.checkOpen();
        this.checkMessageValidity(msgNum);
        Integer messageKey = new Integer(msgNum);
        Message message = (Message)this.messageCache.get(messageKey);
        if (message == null) {
            message = new IMAPMessage(this, (IMAPStore)this.store, this.nextMessageID++, msgNum);
            this.messageCache.put(messageKey, message);
        }
        return message;
    }

    public synchronized Message[] getMessages(int start2, int end) throws MessagingException {
        this.checkOpen();
        Message[] messageRange = new Message[end - start2 + 1];
        for (int i = 0; i < messageRange.length; ++i) {
            messageRange[i] = this.getMessage(start2 + i);
        }
        return messageRange;
    }

    public synchronized void appendMessages(Message[] msgs) throws MessagingException {
        this.checkFolderValidity();
        for (int i = 0; i < msgs.length; ++i) {
            Message msg = msgs[i];
            this.appendMessage(msg);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void fetch(Message[] messages, FetchProfile profile) throws MessagingException {
        ArrayList<Message> fetchSet = new ArrayList<Message>();
        for (int i = 0; i < messages.length; ++i) {
            Message msg = messages[i];
            if (!((IMAPMessage)msg).evaluateFetch(profile)) continue;
            fetchSet.add(msg);
        }
        if (fetchSet.isEmpty()) {
            return;
        }
        IMAPConnection connection = this.getConnection();
        try {
            IMAPFolder iMAPFolder = this;
            synchronized (iMAPFolder) {
                String messageSet = this.generateMessageSet(fetchSet);
                List responses = connection.fetch(messageSet, profile);
                for (int i = 0; i < responses.size(); ++i) {
                    IMAPFetchResponse response = (IMAPFetchResponse)responses.get(i);
                    Message msg = this.getMessage(response.getSequenceNumber());
                    if (msg == null) continue;
                    ((IMAPMessage)msg).updateMessageInformation(response);
                }
            }
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setFlags(Message[] messages, Flags flags, boolean set) throws MessagingException {
        ArrayList<Message> updatedMessages = new ArrayList<Message>();
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            this.checkOpenReadWrite();
            if (!this.availableFlags.contains(flags)) {
                throw new MessagingException("The IMAP server does not support changing of this flag set");
            }
            String messageSet = this.generateMessageSet(messages);
            if (messageSet == null) {
                return;
            }
            IMAPConnection connection = this.getConnection();
            try {
                List responses = connection.setFlags(messageSet, flags, set);
                for (int i = 0; i < responses.size(); ++i) {
                    IMAPFetchResponse response = (IMAPFetchResponse)responses.get(i);
                    Message message = this.getMessage(response.sequenceNumber);
                    if (message == null) continue;
                    ((IMAPMessage)message).updateMessageInformation(response);
                    updatedMessages.add(message);
                }
            }
            finally {
                this.releaseConnection(connection);
            }
        }
        for (int i = 0; i < updatedMessages.size(); ++i) {
            Message message = (Message)updatedMessages.get(i);
            this.notifyMessageChangedListeners(1, message);
        }
    }

    public synchronized void setFlags(int start2, int end, Flags flags, boolean value) throws MessagingException {
        Message[] msgs = new Message[end - start2 + 1];
        for (int i = start2; i <= end; ++i) {
            msgs[i] = this.getMessage(i);
        }
        this.setFlags(msgs, flags, value);
    }

    public synchronized void setFlags(int[] ids, Flags flags, boolean value) throws MessagingException {
        Message[] msgs = new Message[ids.length];
        for (int i = 0; i < ids.length; ++i) {
            msgs[i] = this.getMessage(ids[i]);
        }
        this.setFlags(msgs, flags, value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void copyMessages(Message[] messages, Folder folder) throws MessagingException {
        if (this.getStore() != folder.getStore()) {
            super.copyMessages(messages, folder);
            return;
        }
        String messageSet = this.generateMessageSet(messages);
        if (messageSet == null) {
            return;
        }
        IMAPConnection connection = this.getConnection();
        try {
            connection.copyMessages(messageSet, folder.getFullName());
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Message[] expunge() throws MessagingException {
        this.checkOpen();
        this.checkReadWrite();
        IMAPConnection connection = this.getConnection();
        List expunges = null;
        try {
            expunges = connection.expungeMailbox();
        }
        finally {
            this.releaseConnection(connection);
        }
        Message[] messages = new Message[expunges.size()];
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            for (int i = 0; i < expunges.size(); ++i) {
                IMAPSizeResponse response = (IMAPSizeResponse)expunges.get(i);
                messages[i] = this.expungeMessage(response.getSize());
            }
        }
        if (messages.length > 0) {
            this.notifyMessageRemovedListeners(true, messages);
        }
        return messages;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Message[] search(SearchTerm term) throws MessagingException {
        this.checkOpen();
        IMAPConnection connection = this.getConnection();
        try {
            int[] messageNumbers = connection.searchMailbox(term);
            Message[] messageArray = this.resolveMessages(messageNumbers);
            return messageArray;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Message[] search(SearchTerm term, Message[] messages) throws MessagingException {
        this.checkOpen();
        String messageSet = this.generateMessageSet(messages);
        if (messageSet == null) {
            return new Message[0];
        }
        IMAPConnection connection = this.getConnection();
        try {
            int[] messageNumbers = connection.searchMailbox(messageSet, term);
            Message[] messageArray = this.resolveMessages(messageNumbers);
            return messageArray;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    public synchronized long getUIDValidity() throws MessagingException {
        this.refreshStatus(true);
        return this.uidValidity;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Message getMessageByUID(long uid) throws MessagingException {
        this.checkOpen();
        Long key = new Long(uid);
        Map map = this.messageCache;
        synchronized (map) {
            Message msg = (Message)this.uidCache.get(key);
            if (msg != null) {
                return msg;
            }
        }
        IMAPConnection connection = this.getConnection();
        try {
            IMAPUid imapuid = connection.getSequenceNumberForUid(uid);
            if (imapuid == null) {
                Message message = null;
                return message;
            }
            Message message = this.retrieveMessageByUid(key, imapuid.messageNumber);
            return message;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Message[] getMessagesByUID(long start2, long end) throws MessagingException {
        this.checkOpen();
        IMAPConnection connection = this.getConnection();
        try {
            List uids = connection.getSequenceNumbersForUids(start2, end);
            Message[] msgs = new Message[uids.size()];
            for (int i = 0; i < msgs.length; ++i) {
                IMAPUid uid = (IMAPUid)uids.get(i);
                msgs[i] = this.retrieveMessageByUid(new Long(uid.uid), uid.messageNumber);
            }
            Message[] messageArray = msgs;
            return messageArray;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    public synchronized Message[] getMessagesByUID(long[] ids) throws MessagingException {
        this.checkOpen();
        Message[] msgs = new Message[ids.length];
        for (int i = 0; i < msgs.length; ++i) {
            msgs[i] = this.getMessageByUID(ids[i]);
        }
        return msgs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized long getUID(Message message) throws MessagingException {
        this.checkMessageFolder(message);
        IMAPMessage msg = (IMAPMessage)message;
        if (msg.getUID() != -1L) {
            return msg.getUID();
        }
        IMAPConnection connection = this.getConnection();
        try {
            IMAPUid imapuid = connection.getUidForSequenceNumber(msg.getMessageNumber());
            if (imapuid == null) {
                long l = -1L;
                return l;
            }
            this.addToUidCache(new Long(imapuid.uid), this.getMessage(imapuid.messageNumber));
            long l = imapuid.uid;
            return l;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected synchronized Message retrieveMessageByUid(Long key, int msgNumber) throws MessagingException {
        Map map = this.messageCache;
        synchronized (map) {
            Message msg = (Message)this.uidCache.get(key);
            if (msg != null) {
                return msg;
            }
            msg = this.getMessage(msgNumber);
            this.addToUidCache(key, msg);
            return msg;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void addToUidCache(Long key, Message msg) {
        Map map = this.messageCache;
        synchronized (map) {
            ((IMAPMessage)msg).setUID(key);
            this.uidCache.put(key, msg);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected synchronized void appendMessage(Message msg) throws MessagingException {
        Date date = msg.getReceivedDate();
        if (date == null) {
            date = msg.getSentDate();
        }
        Flags flags = msg.getFlags();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            msg.writeTo(out);
        }
        catch (IOException e) {
            // empty catch block
        }
        IMAPConnection connection = this.getConnection();
        try {
            connection.appendMessage(this.getFullName(), date, flags, out.toByteArray());
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected synchronized Folder[] filterFolders(String pattern, boolean subscribed) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        String root = this.fullname + this.getSeparator();
        List responses = null;
        try {
            responses = subscribed ? connection.listSubscribed(root, pattern) : connection.list(root, pattern);
        }
        finally {
            this.releaseConnection(connection);
        }
        ArrayList<IMAPFolder> folders = new ArrayList<IMAPFolder>();
        for (int i = 0; i < responses.size(); ++i) {
            IMAPListResponse response = (IMAPListResponse)responses.get(i);
            if (response.mailboxName.equals(root)) continue;
            IMAPFolder folder = new IMAPFolder((IMAPStore)this.store, response.mailboxName, response.separator);
            folders.add(folder);
        }
        return folders.toArray(new Folder[folders.size()]);
    }

    protected synchronized boolean holdsFolders() throws MessagingException {
        this.checkFolderValidity();
        return (this.folderType & 2) != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void checkMessageValidity(int messageNumber) throws MessagingException {
        if (messageNumber < 1) {
            throw new MessagingException("Invalid message number for IMAP folder: " + messageNumber);
        }
        if (messageNumber <= this.maxSequenceNumber) {
            return;
        }
        IMAPConnection connection = this.getConnection();
        IMAPFolder iMAPFolder = this;
        synchronized (iMAPFolder) {
            try {
                connection.updateMailboxStatus();
            }
            finally {
                this.releaseConnection(connection);
            }
        }
        if (messageNumber > this.maxSequenceNumber) {
            throw new MessagingException("Message " + messageNumber + " does not exist on server");
        }
    }

    protected void checkOpen() throws IllegalStateException {
        if (!this.folderOpen) {
            throw new IllegalStateException("Folder is not Open");
        }
    }

    protected void checkClosed() throws IllegalStateException {
        if (this.folderOpen) {
            throw new IllegalStateException("Folder is Open");
        }
    }

    protected void checkReadWrite() throws IllegalStateException {
        if (this.mode != 2) {
            throw new IllegalStateException("Folder is opened READY_ONLY");
        }
    }

    protected void checkOpenReadWrite() throws IllegalStateException {
        this.checkOpen();
        this.checkReadWrite();
    }

    public void notifyMessageChangedListeners(int type, Message m4) {
        super.notifyMessageChangedListeners(type, m4);
    }

    protected synchronized IMAPConnection getConnection() throws MessagingException {
        if (this.currentConnection == null) {
            IMAPConnection connection = ((IMAPStore)this.store).getFolderConnection(this);
            connection.addResponseHandler(this);
            return connection;
        }
        return this.currentConnection;
    }

    protected void releaseConnection(IMAPConnection connection) throws MessagingException {
        connection.processPendingResponses();
        if (this.currentConnection == null || connection != this.currentConnection) {
            connection.removeResponseHandler(this);
            ((IMAPStore)this.store).releaseFolderConnection(this, connection);
        }
    }

    synchronized IMAPConnection getMessageConnection() throws MessagingException {
        if (this.currentConnection == null) {
            throw new FolderClosedException(this, "No Folder connections available");
        }
        return this.currentConnection;
    }

    void releaseMessageConnection(IMAPConnection connection) throws MessagingException {
        this.releaseConnection(connection);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void refreshStatus(boolean force) throws MessagingException {
        if (this.cachedStatus != null) {
            long age;
            if (!force && this.statusCacheTimeout > 0L && (age = System.currentTimeMillis() - this.lastStatusTimeStamp) < this.statusCacheTimeout) {
                return;
            }
            this.cachedStatus = null;
        }
        IMAPConnection connection = this.getConnection();
        try {
            this.cachedStatus = connection.getMailboxStatus(this.fullname);
            this.lastStatusTimeStamp = System.currentTimeMillis();
        }
        finally {
            this.releaseConnection(connection);
        }
        this.maxSequenceNumber = this.cachedStatus.messages;
        this.recentMessages = this.cachedStatus.recentMessages;
        this.unseenMessages = this.cachedStatus.unseenMessages;
        this.uidValidity = this.cachedStatus.uidValidity;
    }

    protected synchronized Message expungeMessage(int sequenceNumber) throws MessagingException {
        IMAPMessage expungedMessage = (IMAPMessage)this.getMessage(sequenceNumber);
        expungedMessage.setExpunged(true);
        long uid = expungedMessage.getUID();
        if (uid >= 0L) {
            this.uidCache.remove(new Long(uid));
        }
        HashMap newCache = new HashMap();
        for (Integer key : this.messageCache.keySet()) {
            int index = key;
            if (index < sequenceNumber) {
                newCache.put(key, this.messageCache.get(key));
                continue;
            }
            if (index <= sequenceNumber) continue;
            IMAPMessage message = (IMAPMessage)this.messageCache.get(key);
            message.setSequenceNumber(index - 1);
            newCache.put(new Integer(index - 1), message);
        }
        this.messageCache = newCache;
        --this.maxSequenceNumber;
        return expungedMessage;
    }

    protected Message[] resolveMessages(int[] messageNumbers) throws MessagingException {
        if (messageNumbers == null) {
            return new Message[0];
        }
        Message[] messages = new Message[messageNumbers.length];
        for (int i = 0; i < messageNumbers.length; ++i) {
            messages[i] = this.getMessage(messageNumbers[i]);
        }
        return messages;
    }

    protected String generateMessageSet(List messages) throws MessagingException {
        Message[] msgs = messages.toArray(new Message[messages.size()]);
        return this.generateMessageSet(msgs);
    }

    protected String generateMessageSet(Message[] messages) throws MessagingException {
        StringBuffer set = new StringBuffer();
        for (int i = 0; i < messages.length; ++i) {
            IMAPMessage start2 = (IMAPMessage)messages[i];
            if (start2.isExpunged()) continue;
            if (set.length() != 0) {
                set.append(',');
            }
            set.append(Integer.toString(start2.getSequenceNumber()));
            int endIndex = -1;
            int previousSequence = start2.getSequenceNumber();
            for (int j = i + 1; j < messages.length; ++j) {
                IMAPMessage message = (IMAPMessage)messages[j];
                if (message.isExpunged()) continue;
                if (message.getSequenceNumber() != previousSequence + 1) break;
                ++previousSequence;
                endIndex = j;
            }
            if (endIndex == -1) continue;
            i = endIndex;
            set.append(':');
            set.append(Integer.toString(((IMAPMessage)messages[endIndex]).getSequenceNumber()));
        }
        if (set.length() == 0) {
            return null;
        }
        return set.toString();
    }

    protected void checkFolderValidity() throws MessagingException {
        if (this.listInfo == null && !this.exists()) {
            throw new FolderNotFoundException(this, "Folder " + this.fullname + " not found on server");
        }
    }

    protected void checkMessageFolder(Message msg) throws MessagingException {
        if (msg.getFolder() != this) {
            throw new NoSuchElementException("Message is not within the target Folder");
        }
    }

    protected IMAPListResponse findListResponse(List responses, String name) {
        for (int i = 0; i < responses.size(); ++i) {
            IMAPListResponse response = (IMAPListResponse)responses.get(i);
            if (!response.mailboxName.equals(name)) continue;
            return response;
        }
        return null;
    }

    protected String getMailBoxName() {
        return this.fullname;
    }

    public boolean handleResponse(IMAPUntaggedResponse response) {
        if (response.isKeyword("EXISTS")) {
            int oldCount = this.maxSequenceNumber;
            this.maxSequenceNumber = ((IMAPSizeResponse)response).getSize();
            if (oldCount < this.maxSequenceNumber) {
                try {
                    Message[] messages = this.getMessages(oldCount + 1, this.maxSequenceNumber);
                    this.notifyMessageAddedListeners(messages);
                }
                catch (MessagingException e) {
                    // empty catch block
                }
            }
            return true;
        }
        if (response.isKeyword("EXPUNGE")) {
            int messageNumber = ((IMAPSizeResponse)response).getSize();
            try {
                Message message = this.expungeMessage(messageNumber);
                this.notifyMessageRemovedListeners(false, new Message[]{message});
            }
            catch (MessagingException e) {
                // empty catch block
            }
            return true;
        }
        if (response.isKeyword("RECENT")) {
            this.recentMessages = ((IMAPSizeResponse)response).getSize();
            return true;
        }
        if (response.isKeyword("FETCH")) {
            IMAPFetchResponse fetch = (IMAPFetchResponse)response;
            IMAPFlags flags = (IMAPFlags)fetch.getDataItem(9);
            if (flags != null) {
                try {
                    IMAPMessage message = (IMAPMessage)this.getMessage(fetch.sequenceNumber);
                    if (message != null) {
                        message.updateMessageInformation(fetch);
                    }
                    this.notifyMessageChangedListeners(1, message);
                }
                catch (MessagingException e) {
                    // empty catch block
                }
                return true;
            }
        } else if (response.isKeyword("BYE")) {
            try {
                this.cleanupFolder(false, true);
            }
            catch (MessagingException messagingException) {
                // empty catch block
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void removeACL(ACL acl) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            connection.removeACLRights(this.fullname, acl);
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void addACL(ACL acl) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            connection.setACLRights(this.fullname, acl);
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void addRights(ACL acl) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            connection.addACLRights(this.fullname, acl);
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void removeRights(ACL acl) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            connection.removeACLRights(this.fullname, acl);
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Rights[] listRights(String name) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            Rights[] rightsArray = connection.listACLRights(this.fullname, name);
            return rightsArray;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Rights myRights() throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            Rights rights = connection.getMyRights(this.fullname);
            return rights;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Quota[] getQuota() throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            Quota[] quotaArray = connection.fetchQuotaRoot(this.fullname);
            return quotaArray;
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void setQuota(Quota quota) throws MessagingException {
        IMAPConnection connection = this.getConnection();
        try {
            connection.setQuota(quota);
        }
        finally {
            this.releaseConnection(connection);
        }
    }

    public synchronized String[] getAttributes() throws MessagingException {
        if (this.listInfo == null && !this.exists()) {
            return null;
        }
        return (String[])this.listInfo.attributes.clone();
    }

    public static class FetchProfileItem
    extends FetchProfile.Item {
        public static final FetchProfileItem HEADERS = new FetchProfileItem("HEADERS");
        public static final FetchProfileItem SIZE = new FetchProfileItem("SIZE");

        protected FetchProfileItem(String name) {
            super(name);
        }
    }
}

