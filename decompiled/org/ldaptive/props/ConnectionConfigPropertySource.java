/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionInitializer;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.BindConnectionInitializerPropertySource;
import org.ldaptive.props.ConnectionConfigPropertyInvoker;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SimplePropertySource;
import org.ldaptive.props.SslConfigPropertySource;
import org.ldaptive.ssl.SslConfig;

public final class ConnectionConfigPropertySource
extends AbstractPropertySource<ConnectionConfig> {
    private static final ConnectionConfigPropertyInvoker INVOKER = new ConnectionConfigPropertyInvoker(ConnectionConfig.class);

    public ConnectionConfigPropertySource(ConnectionConfig cc) {
        this(cc, "classpath:/org/ldaptive/ldap.properties");
    }

    public ConnectionConfigPropertySource(ConnectionConfig cc, String ... paths) {
        this(cc, ConnectionConfigPropertySource.loadProperties(paths));
    }

    public ConnectionConfigPropertySource(ConnectionConfig cc, Reader ... readers) {
        this(cc, ConnectionConfigPropertySource.loadProperties(readers));
    }

    public ConnectionConfigPropertySource(ConnectionConfig cc, Properties props) {
        this(cc, PropertySource.PropertyDomain.LDAP, props);
    }

    public ConnectionConfigPropertySource(ConnectionConfig cc, PropertySource.PropertyDomain domain, Properties props) {
        super(cc, domain, props);
    }

    @Override
    public void initialize() {
        SslConfigPropertySource scSource;
        this.initializeObject(INVOKER);
        SslConfig sc = ((ConnectionConfig)this.object).getSslConfig();
        if (sc == null) {
            sc = new SslConfig();
            scSource = new SslConfigPropertySource(sc, this.propertiesDomain, this.properties);
            scSource.initialize();
            if (!sc.isEmpty()) {
                ((ConnectionConfig)this.object).setSslConfig(sc);
            }
        } else {
            scSource = new SslConfigPropertySource(sc, this.propertiesDomain, this.properties);
            scSource.initialize();
        }
        ConnectionInitializer[] initializers = ((ConnectionConfig)this.object).getConnectionInitializers();
        if (initializers == null) {
            BindConnectionInitializer bci = new BindConnectionInitializer();
            BindConnectionInitializerPropertySource bciSource = new BindConnectionInitializerPropertySource(bci, this.propertiesDomain, this.properties);
            bciSource.initialize();
            if (!bci.isEmpty()) {
                ((ConnectionConfig)this.object).setConnectionInitializers(bci);
            }
        } else {
            for (ConnectionInitializer init2 : initializers) {
                SimplePropertySource<ConnectionInitializer> sPropSource = new SimplePropertySource<ConnectionInitializer>(init2, this.propertiesDomain, this.properties);
                sPropSource.initialize();
            }
        }
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

