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

public class CaseChangeEntryHandler
extends AbstractEntryHandler<LdapEntry>
implements LdapEntryHandler {
    private static final int HASH_CODE_SEED = 821;
    private CaseChange dnCaseChange = CaseChange.NONE;
    private CaseChange attributeNameCaseChange = CaseChange.NONE;
    private CaseChange attributeValueCaseChange = CaseChange.NONE;
    private String[] attributeNames;

    public CaseChange getDnCaseChange() {
        return this.dnCaseChange;
    }

    public void setDnCaseChange(CaseChange cc) {
        this.dnCaseChange = cc;
    }

    public CaseChange getAttributeNameCaseChange() {
        return this.attributeNameCaseChange;
    }

    public void setAttributeNameCaseChange(CaseChange cc) {
        this.attributeNameCaseChange = cc;
    }

    public CaseChange getAttributeValueCaseChange() {
        return this.attributeValueCaseChange;
    }

    public void setAttributeValueCaseChange(CaseChange cc) {
        this.attributeValueCaseChange = cc;
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
    protected String handleDn(LdapEntry entry) {
        return CaseChange.perform(this.dnCaseChange, entry.getDn());
    }

    @Override
    protected void handleAttributes(LdapEntry entry) {
        if (this.attributeNames == null) {
            super.handleAttributes(entry);
        } else {
            for (String s2 : this.attributeNames) {
                LdapAttribute la = entry.getAttribute(s2);
                if (la == null) continue;
                this.handleAttribute(la);
            }
        }
    }

    @Override
    protected String handleAttributeName(String name) {
        return CaseChange.perform(this.attributeNameCaseChange, name);
    }

    @Override
    protected String handleAttributeValue(String value) {
        return CaseChange.perform(this.attributeValueCaseChange, value);
    }

    @Override
    protected byte[] handleAttributeValue(byte[] value) {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CaseChangeEntryHandler) {
            CaseChangeEntryHandler v = (CaseChangeEntryHandler)o;
            return LdapUtils.areEqual((Object)this.dnCaseChange, (Object)v.dnCaseChange) && LdapUtils.areEqual((Object)this.attributeNameCaseChange, (Object)v.attributeNameCaseChange) && LdapUtils.areEqual((Object)this.attributeValueCaseChange, (Object)v.attributeValueCaseChange) && LdapUtils.areEqual(this.attributeNames, v.attributeNames);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(821, new Object[]{this.dnCaseChange, this.attributeNameCaseChange, this.attributeValueCaseChange, this.attributeNames});
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "dnCaseChange=" + (Object)((Object)this.dnCaseChange) + ", " + "attributeNameCaseChange=" + (Object)((Object)this.attributeNameCaseChange) + ", " + "attributeValueCaseChange=" + (Object)((Object)this.attributeValueCaseChange) + ", " + "attributeNames=" + Arrays.toString(this.attributeNames) + "]";
    }

    public static enum CaseChange {
        NONE,
        LOWER,
        UPPER;


        public static String perform(CaseChange cc, String string) {
            String s2 = null;
            if (LOWER == cc) {
                s2 = string.toLowerCase();
            } else if (UPPER == cc) {
                s2 = string.toUpperCase();
            } else if (NONE == cc) {
                s2 = string;
            }
            return s2;
        }
    }
}

