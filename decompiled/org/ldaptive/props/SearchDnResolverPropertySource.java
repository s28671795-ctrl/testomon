/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.auth.SearchDnResolver;
import org.ldaptive.props.AbstractConnectionFactoryManagerPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SearchOperationFactoryPropertyInvoker;

public final class SearchDnResolverPropertySource
extends AbstractConnectionFactoryManagerPropertySource<SearchDnResolver> {
    private static final SearchOperationFactoryPropertyInvoker INVOKER = new SearchOperationFactoryPropertyInvoker(SearchDnResolver.class);

    public SearchDnResolverPropertySource(SearchDnResolver resolver2) {
        this(resolver2, "classpath:/org/ldaptive/ldap.properties");
    }

    public SearchDnResolverPropertySource(SearchDnResolver resolver2, String ... paths) {
        this(resolver2, SearchDnResolverPropertySource.loadProperties(paths));
    }

    public SearchDnResolverPropertySource(SearchDnResolver resolver2, Reader ... readers) {
        this(resolver2, SearchDnResolverPropertySource.loadProperties(readers));
    }

    public SearchDnResolverPropertySource(SearchDnResolver resolver2, Properties props) {
        this(resolver2, PropertySource.PropertyDomain.AUTH, props);
    }

    public SearchDnResolverPropertySource(SearchDnResolver resolver2, PropertySource.PropertyDomain domain, Properties props) {
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

