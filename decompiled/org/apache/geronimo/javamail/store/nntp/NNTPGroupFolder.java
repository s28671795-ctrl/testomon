/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.mail.FetchProfile;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.nntp.NNTPFolder;
import org.apache.geronimo.javamail.store.nntp.NNTPMessage;
import org.apache.geronimo.javamail.store.nntp.NNTPRootFolder;
import org.apache.geronimo.javamail.store.nntp.NNTPStore;
import org.apache.geronimo.javamail.store.nntp.newsrc.NNTPNewsrcGroup;
import org.apache.geronimo.javamail.transport.nntp.NNTPReply;

public class NNTPGroupFolder
extends NNTPFolder {
    protected int firstArticle = -1;
    protected int lastArticle = -1;
    Map articles;
    NNTPNewsrcGroup groupInfo;

    protected NNTPGroupFolder(NNTPRootFolder parent, NNTPStore store, String name, NNTPNewsrcGroup groupInfo) {
        super(store);
        this.name = name;
        this.fullName = name;
        this.parent = parent;
        this.groupInfo = groupInfo;
    }

    private void updateGroupStats() throws MessagingException {
        NNTPReply reply = this.connection.sendCommand("GROUP " + this.name);
        if (reply.getCode() == 411) {
            throw new FolderNotFoundException(this, "Folder does not exist on server: " + reply);
        }
        if (reply.getCode() != 211) {
            throw new MessagingException("Error requesting group information: " + reply);
        }
        StringTokenizer tokenizer = new StringTokenizer(reply.getMessage());
        if (tokenizer.hasMoreTokens()) {
            String count = tokenizer.nextToken();
            try {
                this.messageCount = Integer.parseInt(count);
            }
            catch (NumberFormatException e) {
                // empty catch block
            }
        }
        if (tokenizer.hasMoreTokens()) {
            String first = tokenizer.nextToken();
            try {
                this.firstArticle = Integer.parseInt(first);
            }
            catch (NumberFormatException e) {
                // empty catch block
            }
        }
        if (tokenizer.hasMoreTokens()) {
            String last = tokenizer.nextToken();
            try {
                this.lastArticle = Integer.parseInt(last);
            }
            catch (NumberFormatException e) {
                // empty catch block
            }
        }
    }

    public boolean exists() throws MessagingException {
        try {
            this.updateGroupStats();
            return true;
        }
        catch (FolderNotFoundException e) {
            return false;
        }
    }

    public boolean hasNewMessages() throws MessagingException {
        int oldLast = this.lastArticle;
        this.updateGroupStats();
        return this.lastArticle > oldLast;
    }

    protected void openFolder() throws MessagingException {
        this.updateGroupStats();
        this.articles = new HashMap();
    }

    public void closeFolder() throws MessagingException {
        this.articles = null;
    }

    public Message getMessage(int msgNum) throws MessagingException {
        this.checkOpen();
        Integer key = new Integer(msgNum);
        NNTPMessage message = (NNTPMessage)this.articles.get(key);
        if (message != null) {
            return message;
        }
        this.updateGroupStats();
        NNTPReply reply = this.connection.sendCommand("STAT " + Integer.toString(msgNum));
        if (reply.getCode() != 223) {
            throw new MessagingException("Error retrieving article from NNTP server: " + reply);
        }
        String response = reply.getMessage();
        int idStart = response.indexOf(60);
        int idEnd = response.indexOf(62);
        message = new NNTPMessage(this, (NNTPStore)this.store, msgNum, response.substring(idStart, idEnd + 1));
        this.articles.put(key, message);
        return message;
    }

    public Message[] getMessages() throws MessagingException {
        this.checkOpen();
        NNTPReply reply = this.connection.sendCommand("XHDR Message-ID " + Integer.toString(this.firstArticle) + "-" + Integer.toString(this.lastArticle), 221);
        ArrayList<Message> messages = new ArrayList<Message>();
        if (reply.getCode() == 221) {
            List lines = reply.getData();
            for (int i = 0; i < lines.size(); ++i) {
                String line = (String)lines.get(i);
                try {
                    int pos = line.indexOf(32);
                    int articleID = Integer.parseInt(line.substring(0, pos));
                    String messageID = line.substring(pos + 1);
                    Integer key = new Integer(articleID);
                    Message message = (Message)this.articles.get(key);
                    if (message == null) {
                        message = new NNTPMessage(this, (NNTPStore)this.store, key, messageID);
                        this.articles.put(key, message);
                    }
                    messages.add(message);
                    continue;
                }
                catch (NumberFormatException e) {
                    // empty catch block
                }
            }
        } else {
            for (int i = this.firstArticle; i <= this.lastArticle; ++i) {
                try {
                    messages.add(this.getMessage(i));
                    continue;
                }
                catch (MessagingException e) {
                    // empty catch block
                }
            }
        }
        return messages.toArray(new Message[0]);
    }

    public void fetch(Message[] msgs, FetchProfile fp) throws MessagingException {
        this.checkOpen();
        for (int i = 0; i < msgs.length; ++i) {
            Message msg = msgs[i];
            if (msg == null || !(msg instanceof NNTPMessage)) continue;
            if (fp.contains(FetchProfile.Item.ENVELOPE) && fp.contains(FetchProfile.Item.CONTENT_INFO)) {
                ((NNTPMessage)msg).loadArticle();
                continue;
            }
            if (fp.contains(FetchProfile.Item.ENVELOPE)) {
                ((NNTPMessage)msg).loadHeaders();
                continue;
            }
            if (!fp.contains(FetchProfile.Item.CONTENT_INFO)) continue;
            ((NNTPMessage)msg).loadContent();
        }
    }

    public boolean isSubscribed() {
        return this.groupInfo.isSubscribed();
    }

    public void setSubscribed(boolean flag) {
        this.groupInfo.setSubscribed(flag);
    }

    public boolean isSeen(int article) {
        return this.groupInfo.isArticleSeen(article);
    }

    public void setSeen(int article, boolean flag) {
        if (flag) {
            this.groupInfo.markArticleSeen(article);
        } else {
            this.groupInfo.markArticleUnseen(article);
        }
    }
}

