/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.util.HashMap;
import java.util.Map;
import org.ldaptive.ConnectionFactoryManager;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.SearchRequest;
import org.ldaptive.jaas.AbstractPropertiesFactory;
import org.ldaptive.jaas.RoleResolver;
import org.ldaptive.jaas.RoleResolverFactory;
import org.ldaptive.jaas.SearchRoleResolver;
import org.ldaptive.props.DefaultConnectionFactoryPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SearchRequestPropertySource;
import org.ldaptive.props.SearchRoleResolverPropertySource;

public class PropertiesRoleResolverFactory
extends AbstractPropertiesFactory
implements RoleResolverFactory {
    private static final Map<String, RoleResolver> CACHE = new HashMap<String, RoleResolver>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public RoleResolver createRoleResolver(Map<String, ?> jaasOptions) {
        RoleResolver rr;
        if (jaasOptions.containsKey("cacheId")) {
            String cacheId = (String)jaasOptions.get("cacheId");
            Map<String, RoleResolver> map = CACHE;
            synchronized (map) {
                if (!CACHE.containsKey(cacheId)) {
                    rr = this.createRoleResolverInternal(jaasOptions);
                    this.logger.trace("created role resolver: {}", (Object)rr);
                    CACHE.put(cacheId, rr);
                } else {
                    rr = CACHE.get(cacheId);
                    this.logger.trace("retrieved role resolver from CACHE: {}", (Object)rr);
                }
            }
        } else {
            rr = this.createRoleResolverInternal(jaasOptions);
            this.logger.trace("created role resolver {} from {}", (Object)rr, (Object)jaasOptions);
        }
        return rr;
    }

    protected RoleResolver createRoleResolverInternal(Map<String, ?> options) {
        RoleResolver rr;
        if (options.containsKey("roleResolver")) {
            ConnectionFactoryManager cfm;
            try {
                String className = (String)options.get("roleResolver");
                rr = (RoleResolver)Class.forName(className).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
            if (rr instanceof ConnectionFactoryManager && (cfm = (ConnectionFactoryManager)((Object)rr)).getConnectionFactory() == null) {
                DefaultConnectionFactory cf = new DefaultConnectionFactory();
                DefaultConnectionFactoryPropertySource cfPropSource = new DefaultConnectionFactoryPropertySource(cf, PropertySource.PropertyDomain.AUTH, PropertiesRoleResolverFactory.createProperties(options));
                cfPropSource.initialize();
                cfm.setConnectionFactory(cf);
            }
        } else {
            rr = new SearchRoleResolver();
            SearchRoleResolverPropertySource source2 = new SearchRoleResolverPropertySource((SearchRoleResolver)rr, PropertiesRoleResolverFactory.createProperties(options));
            source2.initialize();
        }
        return rr;
    }

    @Override
    public SearchRequest createSearchRequest(Map<String, ?> jaasOptions) {
        SearchRequest sr = new SearchRequest();
        SearchRequestPropertySource source2 = new SearchRequestPropertySource(sr, PropertySource.PropertyDomain.AUTH, PropertiesRoleResolverFactory.createProperties(jaasOptions));
        source2.initialize();
        this.logger.trace("created search request {} from {}", (Object)sr, (Object)jaasOptions);
        return sr;
    }

    public static void close() {
        CACHE.values().stream().filter(rr -> rr instanceof ConnectionFactoryManager).forEach(rr -> {
            ConnectionFactoryManager cfm = (ConnectionFactoryManager)((Object)rr);
            cfm.getConnectionFactory().close();
        });
    }
}

