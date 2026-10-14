/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.ConnectionValidator;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.SearchConnectionValidator;
import org.ldaptive.pool.BlockingConnectionPool;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.BlockingConnectionPoolPropertyInvoker;
import org.ldaptive.props.DefaultConnectionFactoryPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SearchConnectionValidatorPropertySource;
import org.ldaptive.props.SimplePropertySource;

public final class BlockingConnectionPoolPropertySource
extends AbstractPropertySource<BlockingConnectionPool> {
    private static final BlockingConnectionPoolPropertyInvoker INVOKER = new BlockingConnectionPoolPropertyInvoker(BlockingConnectionPool.class);

    public BlockingConnectionPoolPropertySource(BlockingConnectionPool cp) {
        this(cp, "classpath:/org/ldaptive/ldap.properties");
    }

    public BlockingConnectionPoolPropertySource(BlockingConnectionPool cp, String ... paths) {
        this(cp, BlockingConnectionPoolPropertySource.loadProperties(paths));
    }

    public BlockingConnectionPoolPropertySource(BlockingConnectionPool cp, Reader ... readers) {
        this(cp, BlockingConnectionPoolPropertySource.loadProperties(readers));
    }

    public BlockingConnectionPoolPropertySource(BlockingConnectionPool cp, Properties props) {
        this(cp, PropertySource.PropertyDomain.POOL, props);
    }

    public BlockingConnectionPoolPropertySource(BlockingConnectionPool cp, PropertySource.PropertyDomain domain, Properties props) {
        super(cp, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
        DefaultConnectionFactory cf = ((BlockingConnectionPool)this.object).getDefaultConnectionFactory();
        if (cf == null) {
            cf = new DefaultConnectionFactory();
            ((BlockingConnectionPool)this.object).setDefaultConnectionFactory(cf);
        }
        DefaultConnectionFactoryPropertySource cfPropSource = new DefaultConnectionFactoryPropertySource(cf, this.propertiesDomain, this.properties);
        cfPropSource.initialize();
        ConnectionValidator cv = ((BlockingConnectionPool)this.object).getValidator();
        if (cv == null) {
            cv = new SearchConnectionValidator();
            SearchConnectionValidatorPropertySource cvPropSource = new SearchConnectionValidatorPropertySource((SearchConnectionValidator)cv, this.propertiesDomain, this.properties);
            cvPropSource.initialize();
            ((BlockingConnectionPool)this.object).setValidator(cv);
        } else if (cv instanceof SearchConnectionValidator) {
            SearchConnectionValidatorPropertySource cvPropSource = new SearchConnectionValidatorPropertySource((SearchConnectionValidator)cv, this.propertiesDomain, this.properties);
            cvPropSource.initialize();
        } else {
            SimplePropertySource<ConnectionValidator> sPropSource = new SimplePropertySource<ConnectionValidator>(cv, this.propertiesDomain, this.properties);
            sPropSource.initialize();
        }
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

