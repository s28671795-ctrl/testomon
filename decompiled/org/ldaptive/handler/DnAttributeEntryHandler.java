/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.handler;

import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.handler.AbstractEntryHandler;
import org.ldaptive.handler.LdapEntryHandler;

public class DnAttributeEntryHandler
extends AbstractEntryHandler<LdapEntry>
implements LdapEntryHandler {
    private static final int HASH_CODE_SEED = 823;
    private String dnAttributeName = "entryDN";
    private boolean addIfExists;

    public String getDnAttributeName() {
        return this.dnAttributeName;
    }

    public void setDnAttributeName(String name) {
        this.dnAttributeName = name;
    }

    public boolean isAddIfExists() {
        return this.addIfExists;
    }

    public void setAddIfExists(boolean b) {
        this.addIfExists = b;
    }

    @Override
    public LdapEntry apply(LdapEntry entry) {
        this.handleEntry(entry);
        return entry;
    }

    @Override
    protected void handleAttributes(LdapEntry entry) {
        if (entry.getAttribute(this.dnAttributeName) == null) {
            entry.addAttributes(new LdapAttribute(this.dnAttributeName, entry.getDn()));
        } else if (this.addIfExists) {
            entry.getAttribute(this.dnAttributeName).addStringValues(entry.getDn());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof DnAttributeEntryHandler) {
            DnAttributeEntryHandler v = (DnAttributeEntryHandler)o;
            return LdapUtils.areEqual(this.addIfExists, v.addIfExists) && LdapUtils.areEqual(this.dnAttributeName, v.dnAttributeName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(823, this.addIfExists, this.dnAttributeName);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "dnAttributeName=" + this.dnAttributeName + ", " + "addIfExists=" + this.addIfExists + "]";
    }
}

