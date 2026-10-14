/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.security.KeyStore;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.spi.ClientBuilderListener;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.util.collection.UnsafeValue;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.model.internal.RankedComparator;
import org.glassfish.jersey.model.internal.RankedProvider;

public class JerseyClientBuilder
extends ClientBuilder {
    private final ClientConfig config = new ClientConfig();
    private HostnameVerifier hostnameVerifier;
    private SslConfigurator sslConfigurator;
    private SSLContext sslContext;
    private static final List<ClientBuilderListener> CLIENT_BUILDER_LISTENERS;

    public static JerseyClient createClient() {
        return new JerseyClientBuilder().build();
    }

    public static JerseyClient createClient(Configuration configuration) {
        return new JerseyClientBuilder().withConfig(configuration).build();
    }

    public JerseyClientBuilder() {
        JerseyClientBuilder.init(this);
    }

    private static void init(ClientBuilder builder) {
        for (ClientBuilderListener listener : CLIENT_BUILDER_LISTENERS) {
            listener.onNewBuilder(builder);
        }
    }

    @Override
    public JerseyClientBuilder sslContext(SSLContext sslContext) {
        if (sslContext == null) {
            throw new NullPointerException(LocalizationMessages.NULL_SSL_CONTEXT());
        }
        this.sslContext = sslContext;
        this.sslConfigurator = null;
        return this;
    }

    @Override
    public JerseyClientBuilder keyStore(KeyStore keyStore, char[] password) {
        if (keyStore == null) {
            throw new NullPointerException(LocalizationMessages.NULL_KEYSTORE());
        }
        if (password == null) {
            throw new NullPointerException(LocalizationMessages.NULL_KEYSTORE_PASWORD());
        }
        if (this.sslConfigurator == null) {
            this.sslConfigurator = SslConfigurator.newInstance();
        }
        this.sslConfigurator.keyStore(keyStore);
        this.sslConfigurator.keyPassword(password);
        this.sslContext = null;
        return this;
    }

    @Override
    public JerseyClientBuilder trustStore(KeyStore trustStore) {
        if (trustStore == null) {
            throw new NullPointerException(LocalizationMessages.NULL_TRUSTSTORE());
        }
        if (this.sslConfigurator == null) {
            this.sslConfigurator = SslConfigurator.newInstance();
        }
        this.sslConfigurator.trustStore(trustStore);
        this.sslContext = null;
        return this;
    }

    @Override
    public JerseyClientBuilder hostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    @Override
    public ClientBuilder executorService(ExecutorService executorService2) {
        this.config.executorService(executorService2);
        return this;
    }

    @Override
    public ClientBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.config.scheduledExecutorService(scheduledExecutorService);
        return this;
    }

    @Override
    public ClientBuilder connectTimeout(long timeout2, TimeUnit unit) {
        if (timeout2 < 0L) {
            throw new IllegalArgumentException("Negative timeout.");
        }
        this.property("jersey.config.client.connectTimeout", Math.toIntExact(unit.toMillis(timeout2)));
        return this;
    }

    @Override
    public ClientBuilder readTimeout(long timeout2, TimeUnit unit) {
        if (timeout2 < 0L) {
            throw new IllegalArgumentException("Negative timeout.");
        }
        this.property("jersey.config.client.readTimeout", Math.toIntExact(unit.toMillis(timeout2)));
        return this;
    }

    @Override
    public JerseyClient build() {
        if (this.sslContext != null) {
            return new JerseyClient((Configuration)this.config, this.sslContext, this.hostnameVerifier, null);
        }
        if (this.sslConfigurator != null) {
            final SslConfigurator sslConfiguratorCopy = this.sslConfigurator.copy();
            return new JerseyClient((Configuration)this.config, Values.lazy(new UnsafeValue<SSLContext, IllegalStateException>(){

                @Override
                public SSLContext get() {
                    return sslConfiguratorCopy.createSSLContext();
                }
            }), this.hostnameVerifier);
        }
        return new JerseyClient((Configuration)this.config, (UnsafeValue<SSLContext, IllegalStateException>)null, this.hostnameVerifier);
    }

    @Override
    public ClientConfig getConfiguration() {
        return this.config;
    }

    @Override
    public JerseyClientBuilder property(String name, Object value) {
        this.config.property(name, value);
        return this;
    }

    @Override
    public JerseyClientBuilder register(Class<?> componentClass) {
        this.config.register((Class)componentClass);
        return this;
    }

    @Override
    public JerseyClientBuilder register(Class<?> componentClass, int priority) {
        this.config.register((Class)componentClass, priority);
        return this;
    }

    @Override
    public JerseyClientBuilder register(Class<?> componentClass, Class<?> ... contracts) {
        this.config.register((Class)componentClass, (Class[])contracts);
        return this;
    }

    @Override
    public JerseyClientBuilder register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        this.config.register((Class)componentClass, (Map)contracts);
        return this;
    }

    @Override
    public JerseyClientBuilder register(Object component) {
        this.config.register(component);
        return this;
    }

    @Override
    public JerseyClientBuilder register(Object component, int priority) {
        this.config.register(component, priority);
        return this;
    }

    @Override
    public JerseyClientBuilder register(Object component, Class<?> ... contracts) {
        this.config.register(component, (Class[])contracts);
        return this;
    }

    @Override
    public JerseyClientBuilder register(Object component, Map<Class<?>, Integer> contracts) {
        this.config.register(component, (Map)contracts);
        return this;
    }

    @Override
    public JerseyClientBuilder withConfig(Configuration config) {
        this.config.loadFrom(config);
        return this;
    }

    static {
        LinkedList<RankedProvider<ClientBuilderListener>> listeners = new LinkedList<RankedProvider<ClientBuilderListener>>();
        for (ClientBuilderListener listener : ServiceFinder.find(ClientBuilderListener.class)) {
            listeners.add(new RankedProvider<ClientBuilderListener>(listener));
        }
        listeners.sort(new RankedComparator(RankedComparator.Order.ASCENDING));
        LinkedList sortedList = new LinkedList();
        for (RankedProvider rankedProvider : listeners) {
            sortedList.add(rankedProvider.getProvider());
        }
        CLIENT_BUILDER_LISTENERS = Collections.unmodifiableList(sortedList);
    }
}

