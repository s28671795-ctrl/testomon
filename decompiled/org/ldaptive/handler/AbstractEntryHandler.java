/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.handler;

import java.util.Set;
import java.util.stream.Collectors;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.transport.MessageFunctional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEntryHandler<T>
extends MessageFunctional.Function<SearchRequest, SearchResponse, T, T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void handleEntry(LdapEntry entry) {
        entry.setDn(this.handleDn(entry));
        this.handleAttributes(entry);
    }

    protected String handleDn(LdapEntry entry) {
        return entry.getDn();
    }

    protected void handleAttributes(LdapEntry entry) {
        for (LdapAttribute la : entry.getAttributes()) {
            this.handleAttribute(la);
        }
    }

    protected void handleAttribute(LdapAttribute attr) {
        if (attr != null) {
            attr.setName(this.handleAttributeName(attr.getName()));
            if (attr.isBinary()) {
                Set<byte[]> newValues = attr.getBinaryValues().stream().map(this::handleAttributeValue).collect(Collectors.toSet());
                attr.clear();
                attr.addBinaryValues(newValues);
            } else {
                Set<String> newValues = attr.getStringValues().stream().map(this::handleAttributeValue).collect(Collectors.toSet());
                attr.clear();
                attr.addStringValues(newValues);
            }
        }
    }

    protected String handleAttributeName(String name) {
        return name;
    }

    protected String handleAttributeValue(String value) {
        return value;
    }

    protected byte[] handleAttributeValue(byte[] value) {
        return value;
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }

    public abstract int hashCode();
}

