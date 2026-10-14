/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.SearchRequest;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SearchRequestPropertyInvoker;

public final class SearchRequestPropertySource
extends AbstractPropertySource<SearchRequest> {
    private static final SearchRequestPropertyInvoker INVOKER = new SearchRequestPropertyInvoker(SearchRequest.class);

    public SearchRequestPropertySource(SearchRequest request) {
        this(request, "classpath:/org/ldaptive/ldap.properties");
    }

    public SearchRequestPropertySource(SearchRequest request, String ... paths) {
        this(request, SearchRequestPropertySource.loadProperties(paths));
    }

    public SearchRequestPropertySource(SearchRequest request, Reader ... readers) {
        this(request, SearchRequestPropertySource.loadProperties(readers));
    }

    public SearchRequestPropertySource(SearchRequest request, Properties props) {
        this(request, PropertySource.PropertyDomain.LDAP, props);
    }

    public SearchRequestPropertySource(SearchRequest request, PropertySource.PropertyDomain domain, Properties props) {
        super(request, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

