/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.util.Properties;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.ConnectionFactoryManager;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.PooledConnectionFactory;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.DefaultConnectionFactoryPropertySource;
import org.ldaptive.props.PooledConnectionFactoryPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SimplePropertySource;

public abstract class AbstractConnectionFactoryManagerPropertySource<T extends ConnectionFactoryManager>
extends AbstractPropertySource<T> {
    public AbstractConnectionFactoryManagerPropertySource(T resolver2, PropertySource.PropertyDomain domain, Properties props) {
        super(resolver2, domain, props);
    }

    @Override
    public void initialize() {
        ConnectionFactory cf = ((ConnectionFactoryManager)this.object).getConnectionFactory();
        if (cf == null) {
            cf = new DefaultConnectionFactory();
            DefaultConnectionFactoryPropertySource cfPropSource = new DefaultConnectionFactoryPropertySource((DefaultConnectionFactory)cf, this.propertiesDomain, this.properties);
            cfPropSource.initialize();
            ((ConnectionFactoryManager)this.object).setConnectionFactory(cf);
        } else if (cf instanceof DefaultConnectionFactory) {
            DefaultConnectionFactoryPropertySource cfPropSource = new DefaultConnectionFactoryPropertySource((DefaultConnectionFactory)cf, this.propertiesDomain, this.properties);
            cfPropSource.initialize();
        } else if (cf instanceof PooledConnectionFactory) {
            PooledConnectionFactoryPropertySource cfPropSource = new PooledConnectionFactoryPropertySource((PooledConnectionFactory)cf, this.propertiesDomain, this.properties);
            cfPropSource.initialize();
        } else {
            SimplePropertySource<ConnectionFactory> sPropSource = new SimplePropertySource<ConnectionFactory>(cf, this.propertiesDomain, this.properties);
            sPropSource.initialize();
        }
    }
}

