/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.ConnectionConfigPropertySource;
import org.ldaptive.props.DefaultConnectionFactoryPropertyInvoker;
import org.ldaptive.props.PropertySource;

public final class DefaultConnectionFactoryPropertySource
extends AbstractPropertySource<DefaultConnectionFactory> {
    private static final DefaultConnectionFactoryPropertyInvoker INVOKER = new DefaultConnectionFactoryPropertyInvoker(DefaultConnectionFactory.class);

    public DefaultConnectionFactoryPropertySource(DefaultConnectionFactory cf) {
        this(cf, "classpath:/org/ldaptive/ldap.properties");
    }

    public DefaultConnectionFactoryPropertySource(DefaultConnectionFactory cf, String ... paths) {
        this(cf, DefaultConnectionFactoryPropertySource.loadProperties(paths));
    }

    public DefaultConnectionFactoryPropertySource(DefaultConnectionFactory cf, Reader ... readers) {
        this(cf, DefaultConnectionFactoryPropertySource.loadProperties(readers));
    }

    public DefaultConnectionFactoryPropertySource(DefaultConnectionFactory cf, Properties props) {
        this(cf, PropertySource.PropertyDomain.LDAP, props);
    }

    public DefaultConnectionFactoryPropertySource(DefaultConnectionFactory cf, PropertySource.PropertyDomain domain, Properties props) {
        super(cf, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
        ConnectionConfig cc = new ConnectionConfig();
        ConnectionConfigPropertySource ccPropSource = new ConnectionConfigPropertySource(cc, this.propertiesDomain, this.properties);
        ccPropSource.initialize();
        ((DefaultConnectionFactory)this.object).setConnectionConfig(cc);
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

