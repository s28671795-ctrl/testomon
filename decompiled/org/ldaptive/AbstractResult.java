/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.ArrayList;
import java.util.List;
import org.ldaptive.AbstractMessage;
import org.ldaptive.LdapUtils;
import org.ldaptive.Result;
import org.ldaptive.ResultCode;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;

public abstract class AbstractResult
extends AbstractMessage
implements Result {
    private ResultCode resultCode;
    private String matchedDN;
    private String diagnosticMessage;
    private List<String> referralURLs = new ArrayList<String>();

    @Override
    public ResultCode getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(ResultCode code) {
        this.resultCode = code;
    }

    @Override
    public String getMatchedDN() {
        return this.matchedDN;
    }

    public void setMatchedDN(String dn) {
        this.matchedDN = dn;
    }

    @Override
    public String getDiagnosticMessage() {
        return this.diagnosticMessage;
    }

    public void setDiagnosticMessage(String message) {
        this.diagnosticMessage = message;
    }

    @Override
    public String[] getReferralURLs() {
        return this.referralURLs != null ? this.referralURLs.toArray(new String[0]) : null;
    }

    public void addReferralURLs(String ... urls2) {
        for (String s2 : urls2) {
            this.referralURLs.add(s2);
        }
    }

    @Override
    protected <T extends Result> void copyValues(T result) {
        super.copyValues(result);
        this.setResultCode(result.getResultCode());
        this.setMatchedDN(result.getMatchedDN());
        this.setDiagnosticMessage(result.getDiagnosticMessage());
        this.addReferralURLs(result.getReferralURLs());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbstractResult && super.equals(o)) {
            AbstractResult v = (AbstractResult)o;
            return LdapUtils.areEqual((Object)this.getResultCode(), (Object)v.getResultCode()) && LdapUtils.areEqual(this.getMatchedDN(), v.getMatchedDN()) && LdapUtils.areEqual(this.getDiagnosticMessage(), v.getDiagnosticMessage()) && LdapUtils.areEqual(this.getReferralURLs(), v.getReferralURLs());
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "resultCode=" + (Object)((Object)this.resultCode) + ", " + "matchedDN=" + this.matchedDN + ", " + "diagnosticMessage=" + this.getEncodedDiagnosticMessage() + ", " + "referralURLs=" + this.referralURLs;
    }

    protected static abstract class AbstractBuilder<B, T extends AbstractResult>
    extends AbstractMessage.AbstractBuilder<B, T> {
        protected AbstractBuilder(T t2) {
            super(t2);
        }

        public B resultCode(ResultCode code) {
            ((AbstractResult)this.object).setResultCode(code);
            return this.self();
        }

        public B matchedDN(String dn) {
            ((AbstractResult)this.object).setMatchedDN(dn);
            return this.self();
        }

        public B diagnosticMessage(String message) {
            ((AbstractResult)this.object).setDiagnosticMessage(message);
            return this.self();
        }

        public B referralURLs(String ... url2) {
            ((AbstractResult)this.object).addReferralURLs(url2);
            return this.self();
        }
    }

    protected static class ReferralHandler
    extends AbstractParseHandler<AbstractResult> {
        public ReferralHandler(AbstractResult response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((AbstractResult)this.getObject()).addReferralURLs(OctetStringType.decode(encoded));
        }
    }

    protected static class DiagnosticMessageHandler
    extends AbstractParseHandler<AbstractResult> {
        public DiagnosticMessageHandler(AbstractResult response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((AbstractResult)this.getObject()).setDiagnosticMessage(OctetStringType.decode(encoded));
        }
    }

    protected static class MatchedDNHandler
    extends AbstractParseHandler<AbstractResult> {
        public MatchedDNHandler(AbstractResult response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((AbstractResult)this.getObject()).setMatchedDN(OctetStringType.decode(encoded));
        }
    }

    protected static class ResultCodeHandler
    extends AbstractParseHandler<AbstractResult> {
        public ResultCodeHandler(AbstractResult response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((AbstractResult)this.getObject()).setResultCode(ResultCode.valueOf(IntegerType.decodeUnsignedPrimitive(encoded)));
        }
    }
}

