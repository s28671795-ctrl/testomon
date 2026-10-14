/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.jaas.SearchRoleResolver;
import org.ldaptive.props.AbstractConnectionFactoryManagerPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SearchOperationFactoryPropertyInvoker;

public final class SearchRoleResolverPropertySource
extends AbstractConnectionFactoryManagerPropertySource<SearchRoleResolver> {
    private static final SearchOperationFactoryPropertyInvoker INVOKER = new SearchOperationFactoryPropertyInvoker(SearchRoleResolver.class);

    public SearchRoleResolverPropertySource(SearchRoleResolver resolver2) {
        this(resolver2, "classpath:/org/ldaptive/ldap.properties");
    }

    public SearchRoleResolverPropertySource(SearchRoleResolver resolver2, String ... paths) {
        this(resolver2, SearchRoleResolverPropertySource.loadProperties(paths));
    }

    public SearchRoleResolverPropertySource(SearchRoleResolver resolver2, Reader ... readers) {
        this(resolver2, SearchRoleResolverPropertySource.loadProperties(readers));
    }

    public SearchRoleResolverPropertySource(SearchRoleResolver resolver2, Properties props) {
        this(resolver2, PropertySource.PropertyDomain.AUTH, props);
    }

    public SearchRoleResolverPropertySource(SearchRoleResolver resolver2, PropertySource.PropertyDomain domain, Properties props) {
        super(resolver2, domain, props);
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

