/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.handler;

import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.ad.GlobalIdentifier;
import org.ldaptive.ad.handler.AbstractBinaryAttributeHandler;
import org.ldaptive.handler.LdapEntryHandler;

public class ObjectGuidHandler
extends AbstractBinaryAttributeHandler<LdapEntry>
implements LdapEntryHandler {
    private static final int HASH_CODE_SEED = 1823;
    private static final String ATTRIBUTE_NAME = "objectGUID";

    public ObjectGuidHandler() {
        this.setAttributeName(ATTRIBUTE_NAME);
    }

    public ObjectGuidHandler(String attrName) {
        this.setAttributeName(attrName);
    }

    @Override
    public LdapEntry apply(LdapEntry entry) {
        this.handleEntry(entry);
        return entry;
    }

    @Override
    protected String convertValue(byte[] value) {
        return GlobalIdentifier.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof ObjectGuidHandler && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1823, this.getAttributeName());
    }
}

