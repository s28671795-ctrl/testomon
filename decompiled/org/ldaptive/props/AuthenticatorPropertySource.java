/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.ldaptive.ConnectionFactoryManager;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.auth.AuthenticationHandler;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.auth.CompareAuthenticationHandler;
import org.ldaptive.auth.DnResolver;
import org.ldaptive.auth.EntryResolver;
import org.ldaptive.auth.SearchDnResolver;
import org.ldaptive.auth.SearchEntryResolver;
import org.ldaptive.auth.SimpleBindAuthenticationHandler;
import org.ldaptive.props.AbstractConnectionFactoryManagerPropertySource;
import org.ldaptive.props.AbstractPropertySource;
import org.ldaptive.props.AuthenticatorPropertyInvoker;
import org.ldaptive.props.CompareAuthenticationHandlerPropertySource;
import org.ldaptive.props.DefaultConnectionFactoryPropertySource;
import org.ldaptive.props.PropertySource;
import org.ldaptive.props.SearchDnResolverPropertySource;
import org.ldaptive.props.SearchEntryResolverPropertySource;
import org.ldaptive.props.SimpleBindAuthenticationHandlerPropertySource;
import org.ldaptive.props.SimplePropertySource;

public final class AuthenticatorPropertySource
extends AbstractPropertySource<Authenticator> {
    private static final AuthenticatorPropertyInvoker INVOKER = new AuthenticatorPropertyInvoker(Authenticator.class);

    public AuthenticatorPropertySource(Authenticator a) {
        this(a, "classpath:/org/ldaptive/ldap.properties");
    }

    public AuthenticatorPropertySource(Authenticator a, String ... paths) {
        this(a, AuthenticatorPropertySource.loadProperties(paths));
    }

    public AuthenticatorPropertySource(Authenticator a, Reader ... readers) {
        this(a, AuthenticatorPropertySource.loadProperties(readers));
    }

    public AuthenticatorPropertySource(Authenticator a, Properties props) {
        this(a, PropertySource.PropertyDomain.AUTH, props);
    }

    public AuthenticatorPropertySource(Authenticator a, PropertySource.PropertyDomain domain, Properties props) {
        super(a, domain, props);
    }

    @Override
    public void initialize() {
        AbstractConnectionFactoryManagerPropertySource ahPropSource;
        AuthenticationHandler authHandler;
        SearchDnResolverPropertySource dnPropSource;
        this.initializeObject(INVOKER);
        DnResolver dnResolver = ((Authenticator)this.object).getDnResolver();
        if (dnResolver == null) {
            dnResolver = new SearchDnResolver();
            dnPropSource = new SearchDnResolverPropertySource((SearchDnResolver)dnResolver, this.propertiesDomain, this.properties);
            dnPropSource.initialize();
            ((Authenticator)this.object).setDnResolver(dnResolver);
        } else if (dnResolver instanceof SearchDnResolver) {
            dnPropSource = new SearchDnResolverPropertySource((SearchDnResolver)dnResolver, this.propertiesDomain, this.properties);
            dnPropSource.initialize();
        } else {
            ConnectionFactoryManager resolverManager;
            SimplePropertySource<DnResolver> sPropSource = new SimplePropertySource<DnResolver>(dnResolver, this.propertiesDomain, this.properties);
            sPropSource.initialize();
            if (dnResolver instanceof ConnectionFactoryManager && (resolverManager = (ConnectionFactoryManager)((Object)dnResolver)).getConnectionFactory() == null) {
                this.initConnectionFactoryManager(resolverManager);
            }
        }
        EntryResolver entryResolver = ((Authenticator)this.object).getEntryResolver();
        if (entryResolver != null) {
            if (entryResolver instanceof SearchEntryResolver) {
                SearchEntryResolverPropertySource entryPropSource = new SearchEntryResolverPropertySource((SearchEntryResolver)entryResolver, this.propertiesDomain, this.properties);
                entryPropSource.initialize();
            } else {
                ConnectionFactoryManager resolverManager;
                SimplePropertySource<EntryResolver> sPropSource = new SimplePropertySource<EntryResolver>(entryResolver, this.propertiesDomain, this.properties);
                sPropSource.initialize();
                if (entryResolver instanceof ConnectionFactoryManager && (resolverManager = (ConnectionFactoryManager)((Object)entryResolver)).getConnectionFactory() == null) {
                    this.initConnectionFactoryManager(resolverManager);
                }
            }
        }
        if ((authHandler = ((Authenticator)this.object).getAuthenticationHandler()) == null) {
            authHandler = new SimpleBindAuthenticationHandler();
            ahPropSource = new SimpleBindAuthenticationHandlerPropertySource((SimpleBindAuthenticationHandler)authHandler, this.propertiesDomain, this.properties);
            ((SimpleBindAuthenticationHandlerPropertySource)ahPropSource).initialize();
            ((Authenticator)this.object).setAuthenticationHandler(authHandler);
        } else if (authHandler instanceof SimpleBindAuthenticationHandler) {
            ahPropSource = new SimpleBindAuthenticationHandlerPropertySource((SimpleBindAuthenticationHandler)authHandler, this.propertiesDomain, this.properties);
            ((SimpleBindAuthenticationHandlerPropertySource)ahPropSource).initialize();
        } else if (authHandler instanceof CompareAuthenticationHandler) {
            ahPropSource = new CompareAuthenticationHandlerPropertySource((CompareAuthenticationHandler)authHandler, this.propertiesDomain, this.properties);
            ((CompareAuthenticationHandlerPropertySource)ahPropSource).initialize();
        } else {
            ConnectionFactoryManager handlerManager;
            SimplePropertySource<AuthenticationHandler> sPropSource = new SimplePropertySource<AuthenticationHandler>(authHandler, this.propertiesDomain, this.properties);
            sPropSource.initialize();
            if (authHandler instanceof ConnectionFactoryManager && (handlerManager = (ConnectionFactoryManager)((Object)authHandler)).getConnectionFactory() == null) {
                this.initConnectionFactoryManager(handlerManager);
            }
        }
    }

    private void initConnectionFactoryManager(ConnectionFactoryManager cfm) {
        DefaultConnectionFactory cf = new DefaultConnectionFactory();
        DefaultConnectionFactoryPropertySource cfPropSource = new DefaultConnectionFactoryPropertySource(cf, this.propertiesDomain, this.properties);
        cfPropSource.initialize();
        cfm.setConnectionFactory(cf);
    }

    public static Set<String> getProperties() {
        return INVOKER.getProperties();
    }
}

