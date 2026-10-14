/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.auth.SimpleBindAuthenticationHandler;
import org.ldaptive.props.AbstractConnectionFactoryManagerPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SimpleBindAuthenticationHandlerPropertyInvoker;

public final class SimpleBindAuthenticationHandlerPropertySource
extends AbstractConnectionFactoryManagerPropertySource<SimpleBindAuthenticationHandler> {
    private static final SimpleBindAuthenticationHandlerPropertyInvoker INVOKER = new SimpleBindAuthenticationHandlerPropertyInvoker(SimpleBindAuthenticationHandler.class);

    public SimpleBindAuthenticationHandlerPropertySource(SimpleBindAuthenticationHandler handler) {
        this(handler, "classpath:/org/ldaptive/ldap.properties");
    }

    public SimpleBindAuthenticationHandlerPropertySource(SimpleBindAuthenticationHandler handler, String ... paths) {
        this(handler, SimpleBindAuthenticationHandlerPropertySource.loadProperties(paths));
    }

    public SimpleBindAuthenticationHandlerPropertySource(SimpleBindAuthenticationHandler handler, Reader ... readers) {
        this(handler, SimpleBindAuthenticationHandlerPropertySource.loadProperties(readers));
    }

    public SimpleBindAuthenticationHandlerPropertySource(SimpleBindAuthenticationHandler handler, Properties props) {
        this(handler, PropertySource.PropertyDomain.AUTH, props);
    }

    public SimpleBindAuthenticationHandlerPropertySource(SimpleBindAuthenticationHandler handler, PropertySource.PropertyDomain domain, Properties props) {
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

