/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.handler;

import java.util.Arrays;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.handler.AbstractEntryHandler;
import org.ldaptive.handler.LdapEntryHandler;

public class MergeAttributeEntryHandler
extends AbstractEntryHandler<LdapEntry>
implements LdapEntryHandler {
    private static final int HASH_CODE_SEED = 827;
    private String mergeAttributeName;
    private String[] attributeNames;

    public String getMergeAttributeName() {
        return this.mergeAttributeName;
    }

    public void setMergeAttributeName(String name) {
        this.mergeAttributeName = name;
    }

    public String[] getAttributeNames() {
        return this.attributeNames;
    }

    public void setAttributeNames(String ... names) {
        this.attributeNames = names;
    }

    @Override
    public LdapEntry apply(LdapEntry entry) {
        this.handleEntry(entry);
        return entry;
    }

    @Override
    protected void handleAttributes(LdapEntry entry) {
        boolean newAttribute = false;
        LdapAttribute mergedAttribute = entry.getAttribute(this.mergeAttributeName);
        if (mergedAttribute == null) {
            mergedAttribute = new LdapAttribute(this.mergeAttributeName);
            newAttribute = true;
        }
        for (String s2 : this.attributeNames) {
            LdapAttribute la = entry.getAttribute(s2);
            if (la == null) continue;
            if (la.isBinary()) {
                mergedAttribute.addBinaryValues(la.getBinaryValues());
                continue;
            }
            mergedAttribute.addStringValues(la.getStringValues());
        }
        if (mergedAttribute.size() > 0 && newAttribute) {
            entry.addAttributes(mergedAttribute);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof MergeAttributeEntryHandler) {
            MergeAttributeEntryHandler v = (MergeAttributeEntryHandler)o;
            return LdapUtils.areEqual(this.attributeNames, v.attributeNames) && LdapUtils.areEqual(this.mergeAttributeName, v.mergeAttributeName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(827, this.attributeNames, this.mergeAttributeName);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "mergeAttributeName=" + this.mergeAttributeName + ", " + "attributeNames=" + Arrays.toString(this.attributeNames) + "]";
    }
}

