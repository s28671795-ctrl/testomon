/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.mail.Folder;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.nntp.NNTPFolder;
import org.apache.geronimo.javamail.store.nntp.NNTPGroupFolder;
import org.apache.geronimo.javamail.store.nntp.NNTPStore;
import org.apache.geronimo.javamail.store.nntp.newsrc.NNTPNewsrcGroup;
import org.apache.geronimo.javamail.transport.nntp.NNTPReply;
import org.apache.geronimo.mail.util.SessionUtil;

public class NNTPRootFolder
extends NNTPFolder {
    protected static final String NNTP_LISTALL = "mail.nntp.listall";

    protected NNTPRootFolder(NNTPStore store, String name, String fullName) {
        super(store);
        this.name = name;
        this.fullName = fullName;
    }

    public synchronized Folder[] list(String pattern) throws MessagingException {
        pattern = pattern.replace('%', '*');
        if (SessionUtil.getBooleanProperty(NNTP_LISTALL, false)) {
            return this.filterActiveGroups(pattern);
        }
        return this.filterSubscribedGroups(pattern);
    }

    public Folder[] listSubscribed(String pattern) throws MessagingException {
        pattern = pattern.replace('%', '*');
        return this.filterSubscribedGroups(pattern);
    }

    protected Folder[] filterActiveGroups(String pattern) throws MessagingException {
        NNTPReply reply = this.connection.sendCommand("LIST ACTIVE " + pattern, 215);
        if (reply.getCode() == 500) {
            return this.filterAllGroups(pattern);
        }
        if (reply.getCode() != 215) {
            throw new MessagingException("Error retrieving group list from NNTP server: " + reply);
        }
        List groups2 = reply.getData();
        Folder[] folders = new Folder[groups2.size()];
        for (int i = 0; i < groups2.size(); ++i) {
            folders[i] = this.getFolder(this.getGroupName((String)groups2.get(i)));
        }
        return folders;
    }

    protected Folder[] filterAllGroups(String pattern) throws MessagingException {
        NNTPReply reply = this.connection.sendCommand("LIST", 215);
        if (reply.getCode() != 215) {
            throw new MessagingException("Error retrieving group list from NNTP server: " + reply);
        }
        List groups2 = reply.getData();
        WildmatMatcher matcher = new WildmatMatcher(pattern);
        ArrayList<Folder> folders = new ArrayList<Folder>();
        for (int i = 0; i < groups2.size(); ++i) {
            String name = this.getGroupName((String)groups2.get(i));
            if (!matcher.matches(name)) continue;
            folders.add(this.getFolder(name));
        }
        return folders.toArray(new Folder[0]);
    }

    protected Folder[] filterSubscribedGroups(String pattern) throws MessagingException {
        Iterator groups2 = ((NNTPStore)this.store).getNewsrcGroups();
        WildmatMatcher matcher = new WildmatMatcher(pattern);
        ArrayList<Folder> folders = new ArrayList<Folder>();
        while (groups2.hasNext()) {
            NNTPNewsrcGroup group = (NNTPNewsrcGroup)groups2.next();
            if (!group.isSubscribed() || !matcher.matches(group.getName())) continue;
            folders.add(this.getFolder(group.getName()));
        }
        return folders.toArray(new Folder[0]);
    }

    protected String getGroupName(String response) {
        int blank = response.indexOf(32);
        return response.substring(0, blank).trim();
    }

    public int getType() throws MessagingException {
        return 2;
    }

    public Folder getFolder(String name) throws MessagingException {
        return new NNTPGroupFolder(this, (NNTPStore)this.store, name, ((NNTPStore)this.store).getNewsrcGroup(name));
    }

    class WildmatMatcher {
        List matchSections = new ArrayList();
        boolean matchAny = false;
        String exactMatch = null;
        String firstSection = null;
        String lastSection = null;

        public WildmatMatcher(String pattern) {
            int section = 0;
            if (pattern.equals("*")) {
                this.matchAny = true;
                return;
            }
            int wildcard = pattern.indexOf(42);
            if (wildcard == -1) {
                this.exactMatch = pattern;
                return;
            }
            if (!pattern.startsWith("*")) {
                this.firstSection = pattern.substring(0, wildcard);
                section = wildcard + 1;
                if (section >= pattern.length()) {
                    return;
                }
            }
            while (section < pattern.length()) {
                wildcard = pattern.indexOf(42, section);
                if (wildcard == -1) {
                    this.lastSection = pattern.substring(section);
                    return;
                }
                if (wildcard == section) {
                    ++section;
                    continue;
                }
                this.matchSections.add(pattern.substring(section, wildcard));
                section = wildcard + 1;
            }
        }

        public boolean matches(String name) {
            if (this.matchAny) {
                return true;
            }
            if (this.exactMatch != null) {
                return this.exactMatch.equals(name);
            }
            int span = 0;
            if (this.firstSection != null) {
                if (!name.startsWith(this.firstSection)) {
                    return false;
                }
                span = this.firstSection.length();
            }
            for (int i = 1; i < this.matchSections.size(); ++i) {
                String nextMatch = (String)this.matchSections.get(i);
                int nextLocation = name.indexOf(nextMatch, span);
                if (nextLocation == -1) {
                    return false;
                }
                span = nextMatch.length() + nextLocation;
            }
            if (this.lastSection != null) {
                if (name.length() - span < this.lastSection.length()) {
                    return false;
                }
                return name.endsWith(this.lastSection);
            }
            return true;
        }
    }
}

