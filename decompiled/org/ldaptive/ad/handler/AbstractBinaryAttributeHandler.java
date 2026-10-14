/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.handler;

import java.util.stream.Stream;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.SearchRequest;
import org.ldaptive.handler.AbstractEntryHandler;

public abstract class AbstractBinaryAttributeHandler<T>
extends AbstractEntryHandler<T> {
    private static final int HASH_CODE_SEED = 1847;
    private String attributeName;

    public String getAttributeName() {
        return this.attributeName;
    }

    public void setAttributeName(String name) {
        this.attributeName = name;
    }

    @Override
    protected void handleAttributes(LdapEntry entry) {
        for (LdapAttribute la : entry.getAttributes()) {
            if (this.attributeName.equalsIgnoreCase(la.getName())) {
                if (la.isBinary()) {
                    LdapAttribute newAttr = new LdapAttribute();
                    newAttr.setName(la.getName());
                    for (byte[] b : la.getBinaryValues()) {
                        newAttr.addStringValues(this.convertValue(b));
                    }
                    entry.addAttributes(newAttr);
                    this.logger.debug("Processed attribute {}", (Object)newAttr);
                    this.handleAttribute(newAttr);
                    continue;
                }
                this.logger.warn("Attribute {} must be set as a binary attribute", (Object)this.attributeName);
                this.handleAttribute(la);
                continue;
            }
            this.handleAttribute(la);
        }
    }

    protected abstract String convertValue(byte[] var1);

    @Override
    public void setRequest(SearchRequest request) {
        String[] binaryAttrs = request.getBinaryAttributes();
        if (binaryAttrs != null) {
            boolean isAttrSet = Stream.of(binaryAttrs).anyMatch(a -> this.attributeName.equalsIgnoreCase((String)a));
            if (!isAttrSet) {
                request.setBinaryAttributes(LdapUtils.concatArrays(binaryAttrs, new String[][]{{this.attributeName}}));
            }
        } else {
            request.setBinaryAttributes(this.attributeName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbstractBinaryAttributeHandler) {
            AbstractBinaryAttributeHandler v = (AbstractBinaryAttributeHandler)o;
            return LdapUtils.areEqual(this.attributeName, v.attributeName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1847, this.attributeName);
    }
}

