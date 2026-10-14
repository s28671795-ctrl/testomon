/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.ldaptive.LdapUtils;
import org.ldaptive.ssl.AggregateTrustManager;
import org.ldaptive.ssl.SSLContextInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSSLContextInitializer
implements SSLContextInitializer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected TrustManager[] trustManagers;

    @Override
    public TrustManager[] getTrustManagers() throws GeneralSecurityException {
        TrustManager[] tm = this.createTrustManagers();
        TrustManager[] aggregate = null;
        if (tm == null) {
            if (this.trustManagers != null) {
                aggregate = this.aggregateTrustManagers(this.trustManagers);
            }
        } else {
            aggregate = this.aggregateTrustManagers(LdapUtils.concatArrays(tm, new TrustManager[][]{this.trustManagers}));
        }
        return aggregate;
    }

    @Override
    public void setTrustManagers(TrustManager ... managers) {
        this.trustManagers = managers;
    }

    protected abstract TrustManager[] createTrustManagers() throws GeneralSecurityException;

    @Override
    public SSLContext initSSLContext(String protocol) throws GeneralSecurityException {
        Object[] km4 = this.getKeyManagers();
        Object[] tm = this.getTrustManagers();
        this.logger.trace("initialize SSLContext with keyManagers={} and trustManagers={}", (Object)Arrays.toString(km4), (Object)Arrays.toString(tm));
        SSLContext ctx = SSLContext.getInstance(protocol);
        ctx.init((KeyManager[])km4, (TrustManager[])tm, null);
        return ctx;
    }

    protected TrustManager[] aggregateTrustManagers(TrustManager ... managers) {
        X509TrustManager[] x509Managers = null;
        if (managers != null) {
            x509Managers = new X509TrustManager[managers.length];
            for (int i = 0; i < managers.length; ++i) {
                x509Managers[i] = (X509TrustManager)managers[i];
            }
        }
        return new TrustManager[]{new AggregateTrustManager(x509Managers)};
    }
}

