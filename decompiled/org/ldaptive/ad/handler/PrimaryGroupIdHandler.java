/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.handler;

import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.ad.SecurityIdentifier;
import org.ldaptive.handler.AbstractEntryHandler;
import org.ldaptive.handler.SearchResultHandler;

public class PrimaryGroupIdHandler
extends AbstractEntryHandler<SearchResponse>
implements SearchResultHandler {
    private static final int HASH_CODE_SEED = 1831;
    private String groupFilter = "(&(objectClass=group)(objectSid={0}))";
    private String baseDn;

    public String getGroupFilter() {
        return this.groupFilter;
    }

    public void setGroupFilter(String filter2) {
        this.groupFilter = filter2;
    }

    public String getBaseDn() {
        return this.baseDn;
    }

    public void setBaseDn(String dn) {
        this.baseDn = dn;
    }

    @Override
    public SearchResponse apply(SearchResponse response) {
        response.getEntries().forEach(this::handleEntry);
        return response;
    }

    @Override
    protected void handleAttributes(LdapEntry entry) {
        LdapAttribute objectSid = entry.getAttribute("objectSid");
        LdapAttribute primaryGroupId = entry.getAttribute("primaryGroupID");
        this.logger.debug("Found objectSid {} and primaryGroupID {}", (Object)objectSid, (Object)primaryGroupId);
        if (objectSid != null && primaryGroupId != null) {
            String sid = objectSid.isBinary() ? SecurityIdentifier.toString(objectSid.getBinaryValue()) : objectSid.getStringValue();
            String groupSid = sid.substring(0, sid.lastIndexOf(45) + 1) + primaryGroupId.getStringValue();
            this.logger.debug("Created primary group SID {} from object SID {} and primaryGroupID {}", groupSid, sid, primaryGroupId.getStringValue());
            try {
                SearchRequest sr = (SearchRequest)SearchRequest.builder().dn(this.baseDn != null ? this.baseDn : ((SearchRequest)this.getRequest()).getBaseDn()).returnAttributes(ReturnAttributes.NONE.value()).filter(new FilterTemplate(this.groupFilter, new Object[]{groupSid}).format()).build();
                SearchResponse result = this.getConnection().operation(sr).execute();
                if (!result.isSuccess() || result.entrySize() == 0) {
                    this.logger.debug("Could not find primary group for SID {} with response {}", (Object)groupSid, (Object)result);
                } else {
                    LdapAttribute memberOf = entry.getAttribute("memberOf");
                    if (memberOf == null) {
                        memberOf = new LdapAttribute("memberOf");
                        entry.addAttributes(memberOf);
                    }
                    memberOf.addStringValues(result.getEntry().getDn());
                }
            }
            catch (LdapException e) {
                this.logger.warn("Error retrieving group ID: {}", (Object)groupSid, (Object)e);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof PrimaryGroupIdHandler) {
            PrimaryGroupIdHandler v = (PrimaryGroupIdHandler)o;
            return LdapUtils.areEqual(this.groupFilter, v.groupFilter) && LdapUtils.areEqual(this.baseDn, v.baseDn);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(1831, this.groupFilter, this.baseDn);
    }
}

