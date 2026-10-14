/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.referral;

import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.referral.ReferralConnectionFactory;

public class DefaultReferralConnectionFactory
implements ReferralConnectionFactory {
    private final ConnectionConfig connectionConfig;

    public DefaultReferralConnectionFactory() {
        this(new ConnectionConfig());
    }

    public DefaultReferralConnectionFactory(ConnectionConfig config) {
        this.connectionConfig = config;
    }

    @Override
    public ConnectionFactory getConnectionFactory(String url2) {
        ConnectionConfig cc = ConnectionConfig.copy(this.connectionConfig);
        cc.setLdapUrl(url2);
        return new DefaultConnectionFactory(cc);
    }
}

