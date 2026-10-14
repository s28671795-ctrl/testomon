/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostnameResolver {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SSLSession sslSession;

    public HostnameResolver(SSLSession session) {
        this.sslSession = session;
    }

    public String resolve() {
        SNIServerName sniName;
        String hostname = null;
        if (this.sslSession instanceof ExtendedSSLSession && (sniName = (SNIServerName)((ExtendedSSLSession)this.sslSession).getRequestedServerNames().stream().filter(n -> 0 == n.getType()).findFirst().orElse(null)) != null) {
            if (sniName instanceof SNIHostName) {
                hostname = ((SNIHostName)sniName).getAsciiName();
            } else {
                try {
                    hostname = new SNIHostName(sniName.getEncoded()).getAsciiName();
                }
                catch (IllegalArgumentException e) {
                    this.logger.warn("Illegal server name " + sniName, e);
                }
            }
        }
        if (hostname == null && this.sslSession != null) {
            hostname = this.sslSession.getPeerHost();
        }
        return hostname;
    }
}

