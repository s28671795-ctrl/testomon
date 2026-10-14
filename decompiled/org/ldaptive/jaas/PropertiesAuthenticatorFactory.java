/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.util.HashMap;
import java.util.Map;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.jaas.AbstractPropertiesFactory;
import org.ldaptive.jaas.AuthenticatorFactory;
import org.ldaptive.props.AuthenticationRequestPropertySource;
import org.ldaptive.props.AuthenticatorPropertySource;

public class PropertiesAuthenticatorFactory
extends AbstractPropertiesFactory
implements AuthenticatorFactory {
    private static final Map<String, Authenticator> CACHE = new HashMap<String, Authenticator>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Authenticator createAuthenticator(Map<String, ?> jaasOptions) {
        Authenticator a;
        if (jaasOptions.containsKey("cacheId")) {
            String cacheId = (String)jaasOptions.get("cacheId");
            Map<String, Authenticator> map = CACHE;
            synchronized (map) {
                if (!CACHE.containsKey(cacheId)) {
                    a = this.createAuthenticatorInternal(jaasOptions);
                    this.logger.trace("created authenticator: {}", (Object)a);
                    CACHE.put(cacheId, a);
                } else {
                    a = CACHE.get(cacheId);
                    this.logger.trace("retrieved authenticator from CACHE: {}", (Object)a);
                }
            }
        } else {
            a = this.createAuthenticatorInternal(jaasOptions);
            this.logger.trace("created authenticator {} from {}", (Object)a, (Object)jaasOptions);
        }
        return a;
    }

    protected Authenticator createAuthenticatorInternal(Map<String, ?> options) {
        Authenticator a = new Authenticator();
        AuthenticatorPropertySource source2 = new AuthenticatorPropertySource(a, PropertiesAuthenticatorFactory.createProperties(options));
        source2.initialize();
        return a;
    }

    @Override
    public AuthenticationRequest createAuthenticationRequest(Map<String, ?> jaasOptions) {
        AuthenticationRequest ar = new AuthenticationRequest();
        AuthenticationRequestPropertySource source2 = new AuthenticationRequestPropertySource(ar, PropertiesAuthenticatorFactory.createProperties(jaasOptions));
        source2.initialize();
        this.logger.trace("created authentication request {} from {}", (Object)ar, (Object)jaasOptions);
        return ar;
    }

    public static void close() {
        for (Map.Entry<String, Authenticator> e : CACHE.entrySet()) {
            Authenticator a = e.getValue();
            a.close();
        }
    }
}

