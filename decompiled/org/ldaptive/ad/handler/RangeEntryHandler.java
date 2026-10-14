/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.handler.AbstractEntryHandler;
import org.ldaptive.handler.SearchResultHandler;

public class RangeEntryHandler
extends AbstractEntryHandler<SearchResponse>
implements SearchResultHandler {
    private static final int HASH_CODE_SEED = 839;
    private static final String END_OF_RANGE = "*";
    private static final String RANGE_FORMAT = "%1$s;Range=%2$s-%3$s";
    private static final String RANGE_PATTERN_STRING = "^(.*?);Range=([\\d\\*]+)-([\\d\\*]+)";
    private static final Pattern RANGE_PATTERN = Pattern.compile("^(.*?);Range=([\\d\\*]+)-([\\d\\*]+)", 2);

    @Override
    public SearchResponse apply(SearchResponse response) {
        response.getEntries().forEach(this::handleEntry);
        return response;
    }

    @Override
    protected void handleAttributes(LdapEntry entry) {
        HashMap<LdapAttribute, Matcher> matchingAttrs = new HashMap<LdapAttribute, Matcher>();
        for (LdapAttribute ldapAttribute : entry.getAttributes()) {
            Matcher matcher = RANGE_PATTERN.matcher(ldapAttribute.getName());
            if (!matcher.find()) continue;
            matchingAttrs.put(ldapAttribute, matcher);
        }
        for (Map.Entry entry2 : matchingAttrs.entrySet()) {
            LdapAttribute la = (LdapAttribute)entry2.getKey();
            Matcher matcher = (Matcher)entry2.getValue();
            String msg = String.format("attribute '%s' entry '%s'", la.getName(), entry.getDn());
            String attrTypeName = matcher.group(1);
            this.logger.debug("Found Range option {}", (Object)msg);
            if (attrTypeName == null || attrTypeName.isEmpty()) {
                this.logger.error("Unable to determine the attribute type name for {}", (Object)msg);
                throw new IllegalArgumentException("Unable to determine the attribute type name for " + msg);
            }
            LdapAttribute newAttr = entry.getAttribute(attrTypeName);
            if (newAttr == null) {
                newAttr = new LdapAttribute();
                newAttr.setBinary(la.isBinary());
                newAttr.setName(attrTypeName);
                entry.addAttributes(newAttr);
            }
            if (la.isBinary()) {
                newAttr.addBinaryValues(la.getBinaryValues());
            } else {
                newAttr.addStringValues(la.getStringValues());
            }
            entry.removeAttributes(la);
            if (la.getName().endsWith(END_OF_RANGE)) continue;
            int start2 = Integer.parseInt(matcher.group(2));
            int end = Integer.parseInt(matcher.group(3));
            int diff = end - start2;
            String nextAttrID = String.format(RANGE_FORMAT, attrTypeName, end + 1, end + diff + 1);
            try {
                this.logger.debug("Searching for '{}' to increment {}", (Object)nextAttrID, (Object)msg);
                SearchRequest sr = SearchRequest.objectScopeSearchRequest(entry.getDn(), new String[]{nextAttrID});
                SearchResponse result = this.getConnection().operation(sr).execute();
                if (!result.isSuccess() || result.entrySize() == 0) {
                    this.logger.debug("Could not find attribute ID: {} with response {}", (Object)nextAttrID, (Object)result);
                } else {
                    entry.addAttributes(result.getEntry().getAttributes());
                }
            }
            catch (LdapException e) {
                this.logger.warn("Error retrieving attribute ID: {}", (Object)nextAttrID, (Object)e);
            }
            this.handleAttributes(entry);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof RangeEntryHandler;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(839, null);
    }
}

