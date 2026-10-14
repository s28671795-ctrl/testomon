/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ext;

import java.util.Arrays;
import org.ldaptive.LdapEntry;

public class MergeRequest {
    private LdapEntry ldapEntry;
    private boolean deleteEntry;
    private String[] searchAttrs;
    private String[] includeAttrs;
    private String[] excludeAttrs;

    public MergeRequest() {
    }

    public MergeRequest(LdapEntry entry) {
        this.setEntry(entry);
    }

    public MergeRequest(LdapEntry entry, boolean delete) {
        this.setEntry(entry);
        this.setDeleteEntry(delete);
    }

    public LdapEntry getEntry() {
        return this.ldapEntry;
    }

    public void setEntry(LdapEntry entry) {
        this.ldapEntry = entry;
    }

    public boolean getDeleteEntry() {
        return this.deleteEntry;
    }

    public void setDeleteEntry(boolean b) {
        this.deleteEntry = b;
    }

    public String[] getSearchAttributes() {
        return this.searchAttrs;
    }

    public void setSearchAttributes(String ... attrs) {
        this.searchAttrs = attrs;
    }

    public String[] getIncludeAttributes() {
        return this.includeAttrs;
    }

    public void setIncludeAttributes(String ... attrs) {
        this.includeAttrs = attrs;
    }

    public String[] getExcludeAttributes() {
        return this.excludeAttrs;
    }

    public void setExcludeAttributes(String ... attrs) {
        this.excludeAttrs = attrs;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "ldapEntry=" + this.ldapEntry + ", " + "deleteEntry=" + this.deleteEntry + ", " + "searchAttributes=" + Arrays.toString(this.searchAttrs) + ", " + "includeAttributes=" + Arrays.toString(this.includeAttrs) + ", " + "excludeAttributes=" + Arrays.toString(this.includeAttrs) + "]";
    }
}

