/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.auth.CompareAuthenticationHandler;
import org.ldaptive.props.AbstractConnectionFactoryManagerPropertySource;
import org.ldaptive.props.CompareAuthenticationHandlerPropertyInvoker;
import org.ldaptive.props.PropertySource;

public final class CompareAuthenticationHandlerPropertySource
extends AbstractConnectionFactoryManagerPropertySource<CompareAuthenticationHandler> {
    private static final CompareAuthenticationHandlerPropertyInvoker INVOKER = new CompareAuthenticationHandlerPropertyInvoker(CompareAuthenticationHandler.class);

    public CompareAuthenticationHandlerPropertySource(CompareAuthenticationHandler handler) {
        this(handler, "classpath:/org/ldaptive/ldap.properties");
    }

    public CompareAuthenticationHandlerPropertySource(CompareAuthenticationHandler handler, String ... paths) {
        this(handler, CompareAuthenticationHandlerPropertySource.loadProperties(paths));
    }

    public CompareAuthenticationHandlerPropertySource(CompareAuthenticationHandler handler, Reader ... readers) {
        this(handler, CompareAuthenticationHandlerPropertySource.loadProperties(readers));
    }

    public CompareAuthenticationHandlerPropertySource(CompareAuthenticationHandler handler, Properties props) {
        this(handler, PropertySource.PropertyDomain.AUTH, props);
    }

    public CompareAuthenticationHandlerPropertySource(CompareAuthenticationHandler handler, PropertySource.PropertyDomain domain, Properties props) {
        super(handler, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
        super.initialize();
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

