/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SslConfigPropertyInvoker;
import org.ldaptive.ssl.SslConfig;

public final class SslConfigPropertySource
extends AbstractPropertySource<SslConfig> {
    private static final SslConfigPropertyInvoker INVOKER = new SslConfigPropertyInvoker(SslConfig.class);

    public SslConfigPropertySource(SslConfig config) {
        this(config, "classpath:/org/ldaptive/ldap.properties");
    }

    public SslConfigPropertySource(SslConfig config, String ... paths) {
        this(config, SslConfigPropertySource.loadProperties(paths));
    }

    public SslConfigPropertySource(SslConfig config, Reader ... readers) {
        this(config, SslConfigPropertySource.loadProperties(readers));
    }

    public SslConfigPropertySource(SslConfig config, Properties props) {
        this(config, PropertySource.PropertyDomain.LDAP, props);
    }

    public SslConfigPropertySource(SslConfig config, PropertySource.PropertyDomain domain, Properties props) {
        super(config, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

