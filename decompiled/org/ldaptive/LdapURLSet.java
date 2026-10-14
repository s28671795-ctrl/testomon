/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.ldaptive.ConnectionStrategy;
import org.ldaptive.LdapURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LdapURLSet {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<LdapURL> urls = new ArrayList<LdapURL>();

    public LdapURLSet(ConnectionStrategy strategy, String ldapUrls) {
        strategy.populate(ldapUrls, this);
    }

    public List<LdapURL> getUrls() {
        ArrayList<LdapURL> l = new ArrayList<LdapURL>(this.getActiveUrls());
        if (this.hasInactiveUrls()) {
            l.addAll(this.getInactiveUrls());
        }
        return Collections.unmodifiableList(l);
    }

    public boolean hasActiveUrls() {
        return this.urls.stream().anyMatch(LdapURL::isActive);
    }

    public List<LdapURL> getActiveUrls() {
        return this.urls.stream().filter(LdapURL::isActive).collect(Collectors.toList());
    }

    public boolean hasInactiveUrls() {
        return this.urls.stream().anyMatch(u -> !u.isActive());
    }

    public List<LdapURL> getInactiveUrls() {
        return this.urls.stream().filter(u -> !u.isActive()).collect(Collectors.toList());
    }

    public int size() {
        return this.urls.size();
    }

    protected synchronized void populate(List<LdapURL> ldapUrls) {
        for (LdapURL url2 : this.urls) {
            LdapURL match = ldapUrls.stream().filter(u -> u.equals(url2)).findFirst().orElse(null);
            if (match == null || url2.isActive()) continue;
            match.deactivate();
        }
        this.urls.clear();
        this.urls.addAll(ldapUrls);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "active=" + this.getActiveUrls() + ", " + "inactive=" + this.getInactiveUrls() + "]";
    }
}

