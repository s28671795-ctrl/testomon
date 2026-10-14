/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.ldaptive.AbstractMessage;
import org.ldaptive.AbstractResult;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapUtils;
import org.ldaptive.SearchResultReference;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;

public class SearchResponse
extends AbstractResult {
    public static final int PROTOCOL_OP = 5;
    private static final int HASH_CODE_SEED = 10301;
    private static final DERPath RESULT_CODE_PATH = new DERPath("/SEQ/APP(5)/ENUM[0]");
    private static final DERPath MATCHED_DN_PATH = new DERPath("/SEQ/APP(5)/OCTSTR[1]");
    private static final DERPath DIAGNOSTIC_MESSAGE_PATH = new DERPath("/SEQ/APP(5)/OCTSTR[2]");
    private static final DERPath REFERRAL_PATH = new DERPath("/SEQ/APP(5)/CTX(3)/OCTSTR[0]");
    private final Map<String, LdapEntry> resultEntries = new LinkedHashMap<String, LdapEntry>();
    private final Set<SearchResultReference> resultReferences = new LinkedHashSet<SearchResultReference>();

    public SearchResponse() {
    }

    public SearchResponse(DERBuffer buffer) {
        DERParser parser = new DERParser();
        parser.registerHandler(AbstractMessage.MessageIDHandler.PATH, new AbstractMessage.MessageIDHandler(this));
        parser.registerHandler(RESULT_CODE_PATH, new AbstractResult.ResultCodeHandler(this));
        parser.registerHandler(MATCHED_DN_PATH, new AbstractResult.MatchedDNHandler(this));
        parser.registerHandler(DIAGNOSTIC_MESSAGE_PATH, new AbstractResult.DiagnosticMessageHandler(this));
        parser.registerHandler(REFERRAL_PATH, new AbstractResult.ReferralHandler(this));
        parser.registerHandler(AbstractMessage.ControlsHandler.PATH, new AbstractMessage.ControlsHandler(this));
        parser.parse(buffer);
    }

    public void initialize(SearchResponse result) {
        this.copyValues(result);
    }

    public Collection<LdapEntry> getEntries() {
        return this.resultEntries.values();
    }

    public LdapEntry getEntry() {
        if (this.resultEntries.isEmpty()) {
            return null;
        }
        return this.resultEntries.values().iterator().next();
    }

    public LdapEntry getEntry(String dn) {
        return this.resultEntries.get(dn.toLowerCase());
    }

    public Set<String> getEntryDns() {
        return this.resultEntries.keySet();
    }

    public void addEntries(LdapEntry ... entry) {
        Stream.of(entry).forEach(e -> this.resultEntries.put(e.getDn().toLowerCase(), (LdapEntry)e));
    }

    public void addEntries(Collection<LdapEntry> entries) {
        entries.forEach(e -> this.resultEntries.put(e.getDn().toLowerCase(), (LdapEntry)e));
    }

    public int entrySize() {
        return this.resultEntries.size();
    }

    public Collection<SearchResultReference> getReferences() {
        return this.resultReferences;
    }

    public SearchResultReference getReference() {
        if (this.resultReferences.isEmpty()) {
            return null;
        }
        return this.resultReferences.iterator().next();
    }

    public void addReferences(SearchResultReference ... reference) {
        Collections.addAll(this.resultReferences, reference);
    }

    public void addReferences(Collection<SearchResultReference> references) {
        references.forEach(this.resultReferences::add);
    }

    public int referenceSize() {
        return this.resultReferences.size();
    }

    public SearchResponse subResult(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > this.resultEntries.size() || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Illegal index value");
        }
        SearchResponse result = new SearchResponse();
        if (this.resultEntries.isEmpty() || fromIndex == toIndex) {
            return result;
        }
        int i = 0;
        for (Map.Entry<String, LdapEntry> e : this.resultEntries.entrySet()) {
            if (i >= fromIndex && i < toIndex) {
                result.addEntries(e.getValue());
            }
            ++i;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SearchResponse && super.equals(o)) {
            SearchResponse v = (SearchResponse)o;
            return LdapUtils.areEqual(this.resultEntries, v.resultEntries) && LdapUtils.areEqual(this.resultReferences, v.resultReferences);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10301, new Object[]{this.getMessageID(), this.getControls(), this.getResultCode(), this.getMatchedDN(), this.getDiagnosticMessage(), this.getReferralURLs(), this.resultEntries, this.resultReferences});
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "entries=" + (this.resultEntries != null ? this.resultEntries.values() : null) + ", " + "references=" + this.resultReferences;
    }

    public static SearchResponse sort(SearchResponse sr) {
        SearchResponse sorted2 = new SearchResponse();
        sorted2.copyValues(sr);
        sorted2.addEntries(sr.getEntries().stream().map(LdapEntry::sort).sorted(Comparator.comparing(LdapEntry::getDn, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toCollection(LinkedHashSet::new)));
        sorted2.addReferences(sr.getReferences().stream().map(SearchResultReference::sort).sorted(Comparator.comparing(SearchResultReference::hashCode)).collect(Collectors.toCollection(LinkedHashSet::new)));
        return sorted2;
    }

    public static SearchResponse merge(SearchResponse result) {
        LdapEntry mergedEntry = null;
        if (result != null) {
            for (LdapEntry entry : result.getEntries()) {
                if (mergedEntry == null) {
                    mergedEntry = entry;
                    continue;
                }
                for (LdapAttribute la : entry.getAttributes()) {
                    LdapAttribute oldAttr = mergedEntry.getAttribute(la.getName());
                    if (oldAttr == null) {
                        mergedEntry.addAttributes(la);
                        continue;
                    }
                    if (oldAttr.isBinary()) {
                        oldAttr.addBinaryValues(la.getBinaryValues());
                        continue;
                    }
                    oldAttr.addStringValues(la.getStringValues());
                }
            }
        }
        return mergedEntry != null ? (SearchResponse)SearchResponse.builder().entry((LdapEntry)LdapEntry.builder().dn(mergedEntry.getDn()).attributes(mergedEntry.getAttributes()).build()).build() : new SearchResponse();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractResult.AbstractBuilder<Builder, SearchResponse> {
        protected Builder() {
            super(new SearchResponse());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder entry(LdapEntry ... e) {
            ((SearchResponse)this.object).addEntries(e);
            return this;
        }

        public Builder reference(SearchResultReference ... r) {
            ((SearchResponse)this.object).addReferences(r);
            return this;
        }
    }
}

