/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.AuthenticationRequestPropertyInvoker;
import org.ldaptive.props.PropertySource;

public final class AuthenticationRequestPropertySource
extends AbstractPropertySource<AuthenticationRequest> {
    private static final AuthenticationRequestPropertyInvoker INVOKER = new AuthenticationRequestPropertyInvoker(AuthenticationRequest.class);

    public AuthenticationRequestPropertySource(AuthenticationRequest request) {
        this(request, "classpath:/org/ldaptive/ldap.properties");
    }

    public AuthenticationRequestPropertySource(AuthenticationRequest request, String ... paths) {
        this(request, AuthenticationRequestPropertySource.loadProperties(paths));
    }

    public AuthenticationRequestPropertySource(AuthenticationRequest request, Reader ... readers) {
        this(request, AuthenticationRequestPropertySource.loadProperties(readers));
    }

    public AuthenticationRequestPropertySource(AuthenticationRequest request, Properties props) {
        this(request, PropertySource.PropertyDomain.AUTH, props);
    }

    public AuthenticationRequestPropertySource(AuthenticationRequest request, PropertySource.PropertyDomain domain, Properties props) {
        super(request, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

