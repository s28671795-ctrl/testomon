/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.ldaptive.AbstractMessage;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.OctetStringType;

public class SearchResultReference
extends AbstractMessage {
    public static final int PROTOCOL_OP = 19;
    private static final int HASH_CODE_SEED = 10313;
    private static final DERPath REFERRAL_URI_PATH = new DERPath("/SEQ/APP(19)/OCTSTR");
    private List<String> references = new ArrayList<String>();

    public SearchResultReference() {
    }

    public SearchResultReference(DERBuffer buffer) {
        DERParser parser = new DERParser();
        parser.registerHandler(AbstractMessage.MessageIDHandler.PATH, new AbstractMessage.MessageIDHandler(this));
        parser.registerHandler(REFERRAL_URI_PATH, new ReferralUriHandler(this));
        parser.registerHandler(AbstractMessage.ControlsHandler.PATH, new AbstractMessage.ControlsHandler(this));
        parser.parse(buffer);
    }

    public String[] getUris() {
        return this.references.toArray(new String[0]);
    }

    public void addUris(String ... uri) {
        for (String s2 : uri) {
            this.references.add(s2);
        }
    }

    public void addUris(Collection<String> uris) {
        this.references.addAll(uris);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SearchResultReference && super.equals(o)) {
            SearchResultReference v = (SearchResultReference)o;
            return LdapUtils.areEqual(this.references, v.references);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10313, this.getMessageID(), this.getControls(), this.references);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "URIs=" + this.references;
    }

    public static SearchResultReference sort(SearchResultReference ref) {
        SearchResultReference sorted2 = new SearchResultReference();
        sorted2.copyValues(ref);
        sorted2.addUris(Stream.of(ref.getUris()).sorted().collect(Collectors.toList()));
        return sorted2;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends AbstractMessage.AbstractBuilder<Builder, SearchResultReference> {
        protected Builder() {
            super(new SearchResultReference());
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder uris(String ... uri) {
            ((SearchResultReference)this.object).addUris(uri);
            return this;
        }
    }

    protected static class ReferralUriHandler
    extends AbstractParseHandler<SearchResultReference> {
        ReferralUriHandler(SearchResultReference response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SearchResultReference)this.getObject()).addUris(OctetStringType.decode(encoded));
        }
    }
}

