/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.PooledConnectionFactory;
import org.ldaptive.pool.BlockingConnectionPool;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.BlockingConnectionPoolPropertySource;
import org.ldaptive.props.ConnectionConfigPropertySource;
import org.ldaptive.props.PooledConnectionFactoryPropertyInvoker;
import org.ldaptive.props.PropertySource;

public final class PooledConnectionFactoryPropertySource
extends AbstractPropertySource<PooledConnectionFactory> {
    private static final PooledConnectionFactoryPropertyInvoker INVOKER = new PooledConnectionFactoryPropertyInvoker(PooledConnectionFactory.class);

    public PooledConnectionFactoryPropertySource(PooledConnectionFactory cf) {
        this(cf, "classpath:/org/ldaptive/ldap.properties");
    }

    public PooledConnectionFactoryPropertySource(PooledConnectionFactory cf, String ... paths) {
        this(cf, PooledConnectionFactoryPropertySource.loadProperties(paths));
    }

    public PooledConnectionFactoryPropertySource(PooledConnectionFactory cf, Reader ... readers) {
        this(cf, PooledConnectionFactoryPropertySource.loadProperties(readers));
    }

    public PooledConnectionFactoryPropertySource(PooledConnectionFactory cf, Properties props) {
        this(cf, PropertySource.PropertyDomain.POOL, props);
    }

    public PooledConnectionFactoryPropertySource(PooledConnectionFactory cf, PropertySource.PropertyDomain domain, Properties props) {
        super(cf, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
        ConnectionConfig cc = ((PooledConnectionFactory)this.object).getConnectionConfig();
        if (cc == null) {
            cc = new ConnectionConfig();
            ConnectionConfigPropertySource ccPropSource = new ConnectionConfigPropertySource(cc, this.propertiesDomain, this.properties);
            ccPropSource.initialize();
            ((PooledConnectionFactory)this.object).setConnectionConfig(cc);
        }
        BlockingConnectionPoolPropertySource cpPropSource = new BlockingConnectionPoolPropertySource((BlockingConnectionPool)this.object, this.propertiesDomain, this.properties);
        cpPropSource.initialize();
        ((PooledConnectionFactory)this.object).initialize();
    }

    public static Set<String> getProperties() {
        return Collections.emptySet();
    }
}

