/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.referral;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.LdapURL;
import org.ldaptive.Operation;
import org.ldaptive.Request;
import org.ldaptive.Result;
import org.ldaptive.ResultCode;
import org.ldaptive.referral.ReferralConnectionFactory;
import org.ldaptive.transport.MessageFunctional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFollowReferralHandler<Q extends Request, S extends Result>
extends MessageFunctional.Function<Q, S, S, S> {
    protected static final int DEFAULT_REFERRAL_LIMIT = 10;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final int referralLimit;
    protected final int referralDepth;
    private final ReferralConnectionFactory connectionFactory;

    public AbstractFollowReferralHandler(int limit, int depth, ReferralConnectionFactory factory2) {
        this.referralLimit = limit;
        this.referralDepth = depth;
        this.connectionFactory = factory2;
    }

    public int getReferralLimit() {
        return this.referralLimit;
    }

    public int getReferralDepth() {
        return this.referralDepth;
    }

    public ReferralConnectionFactory getReferralConnectionFactory() {
        return this.connectionFactory;
    }

    protected abstract Q createReferralRequest(LdapURL var1);

    protected abstract Operation<Q, S> createReferralOperation(ConnectionFactory var1);

    protected S followReferral(String[] referralUrls) {
        Result referralResult = null;
        List<String> urls2 = Arrays.asList(referralUrls);
        Collections.shuffle(urls2);
        this.logger.debug("Following referral with URLs: {}", (Object)urls2);
        for (String url2 : urls2) {
            LdapURL ldapUrl = new LdapURL(url2);
            if (ldapUrl.getHostname() == null) continue;
            ConnectionFactory cf = this.connectionFactory.getConnectionFactory(ldapUrl.getHostnameWithSchemeAndPort());
            try {
                Q referralRequest = this.createReferralRequest(ldapUrl);
                Operation<Q, S> op = this.createReferralOperation(cf);
                referralResult = (Result)op.execute(referralRequest);
            }
            catch (LdapException e) {
                this.logger.warn("Could not follow referral to " + url2, e);
            }
            if (referralResult == null || referralResult.getResultCode() != ResultCode.SUCCESS && referralResult.getResultCode() != ResultCode.REFERRAL_LIMIT_EXCEEDED) continue;
            break;
        }
        return (S)referralResult;
    }

    @Override
    public S apply(S result) {
        S r;
        if (result.getReferralURLs() == null || result.getReferralURLs().length == 0) {
            return result;
        }
        S referralResult = result;
        if (this.referralDepth <= this.referralLimit && (r = this.followReferral(result.getReferralURLs())) != null) {
            referralResult = r;
        }
        return referralResult;
    }
}

