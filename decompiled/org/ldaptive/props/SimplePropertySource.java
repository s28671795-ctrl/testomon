/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SimplePropertyInvoker;

public final class SimplePropertySource<T>
extends AbstractPropertySource<T> {
    private final SimplePropertyInvoker invoker;

    public SimplePropertySource(T t2) {
        this(t2, "classpath:/org/ldaptive/ldap.properties");
    }

    public SimplePropertySource(T t2, String ... paths) {
        this(t2, SimplePropertySource.loadProperties(paths));
    }

    public SimplePropertySource(T t2, Reader ... readers) {
        this(t2, SimplePropertySource.loadProperties(readers));
    }

    public SimplePropertySource(T t2, Properties props) {
        this(t2, PropertySource.PropertyDomain.LDAP, props);
    }

    public SimplePropertySource(T t2, PropertySource.PropertyDomain domain, Properties props) {
        super(t2, domain, props);
        this.invoker = new SimplePropertyInvoker(t2.getClass());
    }

    @Override
    public void initialize() {
        this.initializeObject(this.invoker);
    }
}

