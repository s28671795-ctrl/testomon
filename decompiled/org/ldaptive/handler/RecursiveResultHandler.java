/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.handler.AbstractEntryHandler;
import org.ldaptive.handler.SearchResultHandler;

public class RecursiveResultHandler
extends AbstractEntryHandler<SearchResponse>
implements SearchResultHandler {
    private static final int HASH_CODE_SEED = 829;
    private String searchAttribute;
    private String[] mergeAttributes;
    private String[] retAttrs;

    public RecursiveResultHandler() {
    }

    public RecursiveResultHandler(String searchAttr, String ... mergeAttrs) {
        this.searchAttribute = searchAttr;
        this.mergeAttributes = mergeAttrs;
        this.initializeReturnAttributes();
    }

    public String getSearchAttribute() {
        return this.searchAttribute;
    }

    public void setSearchAttribute(String name) {
        this.searchAttribute = name;
        this.initializeReturnAttributes();
    }

    public String[] getMergeAttributes() {
        return this.mergeAttributes;
    }

    public void setMergeAttributes(String ... mergeAttrs) {
        this.mergeAttributes = mergeAttrs;
        this.initializeReturnAttributes();
    }

    protected void initializeReturnAttributes() {
        if (this.mergeAttributes != null && this.searchAttribute != null) {
            this.retAttrs = new String[this.mergeAttributes.length + 1];
            System.arraycopy(this.mergeAttributes, 0, this.retAttrs, 0, this.mergeAttributes.length);
            this.retAttrs[this.retAttrs.length - 1] = this.searchAttribute;
        }
    }

    @Override
    public SearchResponse apply(SearchResponse response) {
        response.getEntries().forEach(this::handleEntry);
        return response;
    }

    @Override
    public void handleEntry(LdapEntry entry) {
        ArrayList<String> searchedDns = new ArrayList<String>();
        if (entry.getAttribute(this.searchAttribute) != null) {
            searchedDns.add(entry.getDn());
            this.readSearchAttribute(entry, searchedDns);
        } else {
            this.recursiveSearch(entry.getDn(), entry, searchedDns);
        }
    }

    private void readSearchAttribute(LdapEntry entry, List<String> searchedDns) {
        LdapAttribute attr;
        if (entry != null && (attr = entry.getAttribute(this.searchAttribute)) != null && !attr.isBinary()) {
            HashSet<String> values2 = new HashSet<String>(attr.getStringValues());
            for (String s2 : values2) {
                this.recursiveSearch(s2, entry, searchedDns);
            }
        }
    }

    private void recursiveSearch(String dn, LdapEntry entry, List<String> searchedDns) {
        if (!searchedDns.contains(dn)) {
            LdapEntry newEntry = null;
            try {
                SearchResponse result = this.getConnection().operation(SearchRequest.objectScopeSearchRequest(dn, this.retAttrs)).execute();
                if (result.isSuccess()) {
                    newEntry = result.getEntry(dn);
                }
            }
            catch (LdapException e) {
                this.logger.warn("Error retrieving attribute(s): {}", (Object)Arrays.toString(this.retAttrs), (Object)e);
            }
            searchedDns.add(dn);
            if (newEntry != null) {
                this.readSearchAttribute(newEntry, searchedDns);
                for (String s2 : this.mergeAttributes) {
                    LdapAttribute newAttr = newEntry.getAttribute(s2);
                    if (newAttr == null) continue;
                    LdapAttribute oldAttr = entry.getAttribute(s2);
                    if (oldAttr == null) {
                        entry.addAttributes(newAttr);
                        continue;
                    }
                    if (newAttr.isBinary()) {
                        newAttr.getBinaryValues().forEach(xva$0 -> oldAttr.addBinaryValues(new byte[][]{xva$0}));
                        continue;
                    }
                    newAttr.getStringValues().forEach(xva$0 -> oldAttr.addStringValues((String)xva$0));
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof RecursiveResultHandler) {
            RecursiveResultHandler v = (RecursiveResultHandler)o;
            return LdapUtils.areEqual(this.mergeAttributes, v.mergeAttributes) && LdapUtils.areEqual(this.retAttrs, v.retAttrs) && LdapUtils.areEqual(this.searchAttribute, v.searchAttribute);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(829, this.mergeAttributes, this.retAttrs, this.searchAttribute);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "searchAttribute=" + this.searchAttribute + ", " + "mergeAttributes=" + Arrays.toString(this.mergeAttributes) + ", " + "retAttrs=" + Arrays.toString(this.retAttrs) + "]";
    }
}

