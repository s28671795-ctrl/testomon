/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.SearchConnectionValidator;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SimplePropertyInvoker;

public final class SearchConnectionValidatorPropertySource
extends AbstractPropertySource<SearchConnectionValidator> {
    private static final SimplePropertyInvoker INVOKER = new SimplePropertyInvoker(SearchConnectionValidator.class);

    public SearchConnectionValidatorPropertySource(SearchConnectionValidator cv) {
        this(cv, "classpath:/org/ldaptive/ldap.properties");
    }

    public SearchConnectionValidatorPropertySource(SearchConnectionValidator cv, String ... paths) {
        this(cv, SearchConnectionValidatorPropertySource.loadProperties(paths));
    }

    public SearchConnectionValidatorPropertySource(SearchConnectionValidator cv, Reader ... readers) {
        this(cv, SearchConnectionValidatorPropertySource.loadProperties(readers));
    }

    public SearchConnectionValidatorPropertySource(SearchConnectionValidator cv, Properties props) {
        this(cv, PropertySource.PropertyDomain.POOL, props);
    }

    public SearchConnectionValidatorPropertySource(SearchConnectionValidator cv, PropertySource.PropertyDomain domain, Properties props) {
        super(cv, domain, props);
    }

    @Override
    public void initialize() {
        this.initializeObject(INVOKER);
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

