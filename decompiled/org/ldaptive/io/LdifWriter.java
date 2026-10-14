/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.IOException;
import java.io.Writer;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.SearchResponse;
import org.ldaptive.SearchResultReference;
import org.ldaptive.io.SearchResultWriter;

public class LdifWriter
implements SearchResultWriter {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final Writer ldifWriter;

    public LdifWriter(Writer writer) {
        this.ldifWriter = writer;
    }

    @Override
    public void write(SearchResponse result) throws IOException {
        this.ldifWriter.write(this.createLdif(result));
        this.ldifWriter.flush();
    }

    protected String createLdif(SearchResponse result) {
        StringBuilder ldif = new StringBuilder();
        if (result != null) {
            for (LdapEntry le : result.getEntries()) {
                ldif.append(this.createLdifEntry(le));
            }
            for (SearchResultReference sr : result.getReferences()) {
                ldif.append(this.createSearchReference(sr));
            }
        }
        return ldif.toString();
    }

    protected String createLdifEntry(LdapEntry entry) {
        if (entry == null) {
            return "";
        }
        StringBuilder entryLdif = new StringBuilder();
        String dn = entry.getDn();
        if (dn != null) {
            if (LdapUtils.shouldBase64Encode(dn)) {
                entryLdif.append("dn:: ").append(LdapUtils.base64Encode(dn)).append(LINE_SEPARATOR);
            } else {
                entryLdif.append("dn: ").append(dn).append(LINE_SEPARATOR);
            }
        }
        for (LdapAttribute attr : entry.getAttributes()) {
            String attrName = attr.getName();
            for (String attrValue : attr.getStringValues()) {
                if (attr.isBinary()) {
                    entryLdif.append(attrName).append(":: ").append(attrValue).append(LINE_SEPARATOR);
                    continue;
                }
                if (LdapUtils.shouldBase64Encode(attrValue)) {
                    entryLdif.append(attrName).append(":: ").append(LdapUtils.base64Encode(attrValue)).append(LINE_SEPARATOR);
                    continue;
                }
                entryLdif.append(attrName).append(": ").append(attrValue).append(LINE_SEPARATOR);
            }
        }
        if (entryLdif.length() > 0) {
            entryLdif.append(LINE_SEPARATOR);
        }
        return entryLdif.toString();
    }

    protected String createSearchReference(SearchResultReference ref) {
        if (ref == null) {
            return "";
        }
        StringBuilder refLdif = new StringBuilder();
        for (String url2 : ref.getUris()) {
            if (LdapUtils.shouldBase64Encode(url2)) {
                refLdif.append("ref:: ").append(LdapUtils.base64Encode(url2)).append(LINE_SEPARATOR);
                continue;
            }
            refLdif.append("ref: ").append(url2).append(LINE_SEPARATOR);
        }
        if (refLdif.length() > 0) {
            refLdif.append(LINE_SEPARATOR);
        }
        return refLdif.toString();
    }
}

