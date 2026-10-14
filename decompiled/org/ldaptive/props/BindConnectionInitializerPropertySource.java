/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.BindConnectionInitializer;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.BindConnectionInitializerPropertyInvoker;
import org.ldaptive.props.PropertySource;

public final class BindConnectionInitializerPropertySource
extends AbstractPropertySource<BindConnectionInitializer> {
    private static final BindConnectionInitializerPropertyInvoker INVOKER = new BindConnectionInitializerPropertyInvoker(BindConnectionInitializer.class);

    public BindConnectionInitializerPropertySource(BindConnectionInitializer initializer) {
        this(initializer, "classpath:/org/ldaptive/ldap.properties");
    }

    public BindConnectionInitializerPropertySource(BindConnectionInitializer initializer, String ... paths) {
        this(initializer, BindConnectionInitializerPropertySource.loadProperties(paths));
    }

    public BindConnectionInitializerPropertySource(BindConnectionInitializer initializer, Reader ... readers) {
        this(initializer, BindConnectionInitializerPropertySource.loadProperties(readers));
    }

    public BindConnectionInitializerPropertySource(BindConnectionInitializer initializer, Properties props) {
        this(initializer, PropertySource.PropertyDomain.LDAP, props);
    }

    public BindConnectionInitializerPropertySource(BindConnectionInitializer initializer, PropertySource.PropertyDomain domain, Properties props) {
        super(initializer, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

