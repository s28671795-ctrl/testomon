/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.SearchResponse;
import org.ldaptive.SearchResultReference;
import org.ldaptive.io.ResourceUtils;
import org.ldaptive.io.SearchResultReader;
import org.ldaptive.io.URLResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LdifReader
implements SearchResultReader {
    private static final int READ_AHEAD_LIMIT = 1024;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Reader ldifReader;

    public LdifReader(Reader reader) {
        this.ldifReader = reader;
    }

    @Override
    public SearchResponse read() throws IOException {
        String line;
        SearchResponse result = new SearchResponse();
        BufferedReader br = new BufferedReader(this.ldifReader);
        br.mark(1024);
        while ((line = br.readLine()) != null) {
            if (!"".equals(line)) {
                br.reset();
                List<String> section = this.readSection(br);
                if (!section.isEmpty()) {
                    if (section.get(0).startsWith("version")) {
                        section.remove(0);
                    }
                    if (!section.isEmpty()) {
                        if (section.get(0).startsWith("dn")) {
                            result.addEntries(this.parseEntry(section));
                        } else if (section.get(0).startsWith("ref")) {
                            result.addReferences(this.parseReference(section));
                        } else {
                            this.logger.debug("Unknown LDIF section {}", (Object)section.get(0));
                        }
                    }
                }
            }
            br.mark(1024);
        }
        return result;
    }

    private List<String> readSection(BufferedReader reader) throws IOException {
        String line;
        ArrayList<String> section = new ArrayList<String>();
        boolean readingComment = false;
        while ((line = reader.readLine()) != null && !"".equals(line)) {
            if (line.startsWith("#")) {
                readingComment = true;
                continue;
            }
            if (line.startsWith(" ")) {
                if (readingComment) continue;
                section.add((String)section.remove(section.size() - 1) + line.substring(1));
                continue;
            }
            readingComment = false;
            section.add(line);
        }
        return section;
    }

    private LdapEntry parseEntry(List<String> section) throws IOException {
        LdapEntry entry = new LdapEntry();
        for (String line : section) {
            if (!line.contains(":")) {
                throw new IllegalArgumentException("Invalid LDAP entry data: " + line);
            }
            LdapAttribute newAttr = this.parseAttribute(line);
            if ("dn".equals(newAttr.getName())) {
                entry.setDn(newAttr.getStringValue());
                continue;
            }
            LdapAttribute existingAttr = entry.getAttribute(newAttr.getName());
            if (existingAttr == null) {
                entry.addAttributes(newAttr);
                continue;
            }
            if (existingAttr.isBinary()) {
                existingAttr.addBinaryValues(new byte[][]{newAttr.getBinaryValue()});
                continue;
            }
            existingAttr.addStringValues(newAttr.getStringValue());
        }
        return entry;
    }

    private LdapAttribute parseAttribute(String line) throws IOException {
        boolean isBase64 = false;
        boolean isUrl = false;
        String[] parts = line.split(":", 2);
        String attrName = parts[0];
        String attrValue = parts[1];
        if (attrValue.startsWith(":")) {
            isBase64 = true;
            attrValue = attrValue.substring(1);
        } else if (attrValue.startsWith("<")) {
            isUrl = true;
            attrValue = attrValue.substring(1);
        }
        while (attrValue.startsWith(" ")) {
            attrValue = attrValue.substring(1);
        }
        LdapAttribute attr = new LdapAttribute(attrName);
        if (isBase64) {
            attr.addBinaryValues(new byte[][]{LdapUtils.base64Decode(attrValue)});
        } else if (isUrl) {
            byte[] b = ResourceUtils.isResource(attrValue) ? ResourceUtils.readResource(attrValue) : ResourceUtils.readResource(attrValue, new URLResourceLoader());
            attr.addBinaryValues(new byte[][]{b});
        } else {
            attr.addStringValues(attrValue);
        }
        return attr;
    }

    private SearchResultReference parseReference(List<String> section) {
        SearchResultReference ref = new SearchResultReference();
        for (String line : section) {
            if (!line.contains(":")) {
                throw new IllegalArgumentException("Invalid LDAP reference data: " + line);
            }
            String[] parts = line.split(":", 2);
            if (!"ref".equals(parts[0])) {
                throw new IllegalArgumentException("Invalid LDAP reference data: " + line);
            }
            if (parts[1].startsWith(" ")) {
                ref.addUris(parts[1].substring(1));
                continue;
            }
            if (parts[1].length() <= 0) continue;
            throw new IllegalArgumentException("Invalid LDAP reference data: " + line);
        }
        return ref;
    }
}

