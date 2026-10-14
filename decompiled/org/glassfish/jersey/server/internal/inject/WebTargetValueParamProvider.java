/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.internal.Errors;
import org.glassfish.jersey.internal.util.Producer;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ClientBinding;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

final class WebTargetValueParamProvider
extends AbstractValueParamProvider {
    private final Function<Class<? extends Configuration>, Configuration> clientConfigProvider;
    private final Supplier<Configuration> serverConfig;
    private final ConcurrentMap<BindingModel, Value<ManagedClient>> managedClients;

    public WebTargetValueParamProvider(final Supplier<Configuration> serverConfig, Function<Class<? extends Configuration>, Configuration> clientConfigProvider) {
        super(null, Parameter.Source.URI);
        this.clientConfigProvider = clientConfigProvider;
        this.serverConfig = serverConfig;
        this.managedClients = new ConcurrentHashMap<BindingModel, Value<ManagedClient>>();
        this.managedClients.put(BindingModel.EMPTY, Values.lazy(new Value<ManagedClient>(){

            @Override
            public ManagedClient get() {
                Client client;
                if (serverConfig.get() == null) {
                    client = ClientBuilder.newClient();
                } else {
                    ClientConfig clientConfig = new ClientConfig();
                    WebTargetValueParamProvider.this.copyProviders((Configuration)serverConfig.get(), clientConfig);
                    client = ClientBuilder.newClient(clientConfig);
                }
                return new ManagedClient(client, "");
            }
        }));
    }

    private void copyProviders(Configuration source2, Configurable<?> target) {
        Configuration targetConfig = target.getConfiguration();
        for (Class<?> c : source2.getClasses()) {
            if (targetConfig.isRegistered(c)) continue;
            target.register(c, source2.getContracts(c));
        }
        for (Object o : source2.getInstances()) {
            Class<?> c = o.getClass();
            if (targetConfig.isRegistered(o)) continue;
            target.register(o, source2.getContracts(c));
        }
    }

    @Override
    protected Function<ContainerRequest, ?> createValueProvider(final Parameter parameter) {
        return (Function)Errors.processWithException(new Producer<Function<ContainerRequest, ?>>(){

            @Override
            public Function<ContainerRequest, ?> call() {
                String targetUriTemplate = parameter.getSourceName();
                if (targetUriTemplate == null || targetUriTemplate.length() == 0) {
                    Errors.warning(this, LocalizationMessages.INJECTED_WEBTARGET_URI_INVALID(targetUriTemplate));
                    return null;
                }
                Class<?> rawParameterType = parameter.getRawType();
                if (rawParameterType == WebTarget.class) {
                    final BindingModel binding = BindingModel.create(Arrays.asList(parameter.getAnnotations()));
                    Value<ManagedClient> client = (LazyValue<ManagedClient>)WebTargetValueParamProvider.this.managedClients.get(binding);
                    if (client == null) {
                        client = Values.lazy(new Value<ManagedClient>(){

                            @Override
                            public ManagedClient get() {
                                String prefix = binding.getAnnotation().annotationType().getName() + ".";
                                String baseUriProperty = prefix + "baseUri";
                                Object bu = ((Configuration)WebTargetValueParamProvider.this.serverConfig.get()).getProperty(baseUriProperty);
                                String customBaseUri = bu != null ? bu.toString() : binding.baseUri();
                                String configClassProperty = prefix + "configClass";
                                ClientConfig cfg = WebTargetValueParamProvider.this.resolveConfig(configClassProperty, binding);
                                String inheritProvidersProperty = prefix + "inheritServerProviders";
                                if (PropertiesHelper.isProperty(((Configuration)WebTargetValueParamProvider.this.serverConfig.get()).getProperty(inheritProvidersProperty)) || binding.inheritProviders()) {
                                    WebTargetValueParamProvider.this.copyProviders((Configuration)WebTargetValueParamProvider.this.serverConfig.get(), cfg);
                                }
                                String propertyPrefix = prefix + "property.";
                                Collection clientProperties = ((Configuration)WebTargetValueParamProvider.this.serverConfig.get()).getPropertyNames().stream().filter(property -> property.startsWith(propertyPrefix)).collect(Collectors.toSet());
                                for (String property2 : clientProperties) {
                                    cfg.property(property2.substring(propertyPrefix.length()), ((Configuration)WebTargetValueParamProvider.this.serverConfig.get()).getProperty(property2));
                                }
                                return new ManagedClient(ClientBuilder.newClient(cfg), customBaseUri);
                            }
                        });
                        Value previous = WebTargetValueParamProvider.this.managedClients.putIfAbsent(binding, client);
                        if (previous != null) {
                            client = previous;
                        }
                    }
                    return new WebTargetValueSupplier(targetUriTemplate, client);
                }
                Errors.warning(this, LocalizationMessages.UNSUPPORTED_URI_INJECTION_TYPE(rawParameterType));
                return null;
            }
        });
    }

    private ClientConfig resolveConfig(String configClassProperty, BindingModel binding) {
        Configuration cfg;
        Class<? extends Configuration> configClass = binding.getConfigClass();
        Object _cc = this.serverConfig.get().getProperty(configClassProperty);
        if (_cc != null) {
            Class cc = _cc instanceof String ? AccessController.doPrivileged(ReflectionHelper.classForNamePA((String)_cc)) : (_cc instanceof Class ? (Class)_cc : null);
            if (cc != null && Configuration.class.isAssignableFrom(cc)) {
                configClass = cc.asSubclass(Configuration.class);
            } else {
                Errors.warning(this, LocalizationMessages.ILLEGAL_CLIENT_CONFIG_CLASS_PROPERTY_VALUE(configClassProperty, _cc, configClass.getName()));
            }
        }
        return (cfg = this.clientConfigProvider.apply(configClass)) instanceof ClientConfig ? (ClientConfig)cfg : new ClientConfig().loadFrom(cfg);
    }

    private static final class WebTargetValueSupplier
    implements Function<ContainerRequest, WebTarget> {
        private final String uri;
        private final Value<ManagedClient> client;

        WebTargetValueSupplier(String uri, Value<ManagedClient> client) {
            this.uri = uri;
            this.client = client;
        }

        @Override
        public WebTarget apply(ContainerRequest containerRequest) {
            ExtendedUriInfo uriInfo = containerRequest.getUriInfo();
            Map<String, Object> pathParamValues = uriInfo.getPathParameters().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, stringObjectEntry -> {
                List input = (List)stringObjectEntry.getValue();
                return input.isEmpty() ? null : input.get(0);
            }));
            UriBuilder uriBuilder = new JerseyUriBuilder().uri(this.uri).resolveTemplates((Map)pathParamValues);
            ManagedClient managedClient = this.client.get();
            if (!((JerseyUriBuilder)uriBuilder).isAbsolute()) {
                String customBaseUri = managedClient.customBaseUri;
                String rootUri = customBaseUri.isEmpty() ? uriInfo.getBaseUri().toString() : customBaseUri;
                uriBuilder = new JerseyUriBuilder().uri(rootUri).path(((JerseyUriBuilder)uriBuilder).toTemplate());
            }
            return managedClient.instance.target(uriBuilder);
        }
    }

    private static class BindingModel {
        public static final BindingModel EMPTY = new BindingModel(null);
        private final Annotation annotation;
        private final Class<? extends Configuration> configClass;
        private final boolean inheritProviders;
        private final String baseUri;

        public static BindingModel create(Annotation binding) {
            if (binding == null || binding.annotationType().getAnnotation(ClientBinding.class) == null) {
                return EMPTY;
            }
            return new BindingModel(binding);
        }

        public static BindingModel create(Collection<Annotation> bindingCandidates) {
            Collection filtered2 = bindingCandidates.stream().filter(input -> input != null && input.annotationType().getAnnotation(ClientBinding.class) != null).collect(Collectors.toList());
            if (filtered2.isEmpty()) {
                return EMPTY;
            }
            if (filtered2.size() > 1) {
                throw new ProcessingException("Too many client binding annotations.");
            }
            return new BindingModel((Annotation)filtered2.iterator().next());
        }

        private BindingModel(Annotation annotation) {
            if (annotation == null) {
                this.annotation = null;
                this.configClass = ClientConfig.class;
                this.inheritProviders = true;
                this.baseUri = "";
            } else {
                this.annotation = annotation;
                ClientBinding cba = annotation.annotationType().getAnnotation(ClientBinding.class);
                this.configClass = cba.configClass();
                this.inheritProviders = cba.inheritServerProviders();
                this.baseUri = cba.baseUri();
            }
        }

        public Annotation getAnnotation() {
            return this.annotation;
        }

        public Class<? extends Configuration> getConfigClass() {
            return this.configClass;
        }

        public boolean inheritProviders() {
            return this.inheritProviders;
        }

        public String baseUri() {
            return this.baseUri;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            BindingModel that = (BindingModel)o;
            return this.annotation != null ? this.annotation.equals(that.annotation) : that.annotation == null;
        }

        public int hashCode() {
            return this.annotation != null ? this.annotation.hashCode() : 0;
        }

        public String toString() {
            return "BindingModel{binding=" + this.annotation + ", configClass=" + this.configClass + ", inheritProviders=" + this.inheritProviders + ", baseUri=" + this.baseUri + '}';
        }
    }

    private static class ManagedClient {
        private final Client instance;
        private final String customBaseUri;

        private ManagedClient(Client instance, String customBaseUri) {
            this.instance = instance;
            this.customBaseUri = customBaseUri;
        }
    }
}

