/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.handler;

import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.ad.SecurityIdentifier;
import org.ldaptive.ad.handler.AbstractBinaryAttributeHandler;
import org.ldaptive.handler.LdapEntryHandler;

public class ObjectSidHandler
extends AbstractBinaryAttributeHandler<LdapEntry>
implements LdapEntryHandler {
    private static final int HASH_CODE_SEED = 1801;
    private static final String ATTRIBUTE_NAME = "objectSid";

    public ObjectSidHandler() {
        this.setAttributeName(ATTRIBUTE_NAME);
    }

    public ObjectSidHandler(String attrName) {
        this.setAttributeName(attrName);
    }

    @Override
    public LdapEntry apply(LdapEntry entry) {
        this.handleEntry(entry);
        return entry;
    }

    @Override
    protected String convertValue(byte[] value) {
        return SecurityIdentifier.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof ObjectSidHandler && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1801, this.getAttributeName());
    }
}

