/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import javax.inject.Provider;
import javax.ws.rs.BeanParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.ContextInjectionResolver;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.server.Uri;
import org.glassfish.jersey.server.internal.inject.AsyncResponseValueParamProvider;
import org.glassfish.jersey.server.internal.inject.BeanParamValueParamProvider;
import org.glassfish.jersey.server.internal.inject.CookieParamValueParamProvider;
import org.glassfish.jersey.server.internal.inject.DelegatedInjectionValueParamProvider;
import org.glassfish.jersey.server.internal.inject.EntityParamValueParamProvider;
import org.glassfish.jersey.server.internal.inject.FormParamValueParamProvider;
import org.glassfish.jersey.server.internal.inject.HeaderParamValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MatrixParamValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.internal.inject.PathParamValueParamProvider;
import org.glassfish.jersey.server.internal.inject.QueryParamValueParamProvider;
import org.glassfish.jersey.server.internal.inject.WebTargetValueParamProvider;
import org.glassfish.jersey.server.internal.process.RequestProcessingContextReference;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

public class ValueParamProviderConfigurator
implements BootstrapConfigurator {
    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        Provider<AsyncContext> asyncContextProvider = () -> {
            RequestProcessingContextReference reference = injectionManager.getInstance(RequestProcessingContextReference.class);
            return reference.get().asyncContext();
        };
        LazyValue<ContextInjectionResolver> lazyContextResolver = Values.lazy(() -> injectionManager.getInstance(ContextInjectionResolver.class));
        Supplier<Configuration> configuration = serverBag::getConfiguration;
        Provider<MultivaluedParameterExtractorProvider> paramExtractor = serverBag::getMultivaluedParameterExtractorProvider;
        ArrayList<ValueParamProvider> suppliers = new ArrayList<ValueParamProvider>();
        AsyncResponseValueParamProvider asyncProvider = new AsyncResponseValueParamProvider(asyncContextProvider);
        suppliers.add(asyncProvider);
        CookieParamValueParamProvider cookieProvider = new CookieParamValueParamProvider(paramExtractor);
        suppliers.add(cookieProvider);
        EntityParamValueParamProvider entityProvider = new EntityParamValueParamProvider(paramExtractor);
        suppliers.add(entityProvider);
        FormParamValueParamProvider formProvider = new FormParamValueParamProvider(paramExtractor);
        suppliers.add(formProvider);
        HeaderParamValueParamProvider headerProvider = new HeaderParamValueParamProvider(paramExtractor);
        suppliers.add(headerProvider);
        MatrixParamValueParamProvider matrixProvider = new MatrixParamValueParamProvider(paramExtractor);
        suppliers.add(matrixProvider);
        PathParamValueParamProvider pathProvider = new PathParamValueParamProvider(paramExtractor);
        suppliers.add(pathProvider);
        QueryParamValueParamProvider queryProvider = new QueryParamValueParamProvider(paramExtractor);
        suppliers.add(queryProvider);
        BeanParamValueParamProvider beanProvider = new BeanParamValueParamProvider(paramExtractor, injectionManager);
        suppliers.add(beanProvider);
        WebTargetValueParamProvider webTargetProvider = new WebTargetValueParamProvider(configuration, clientConfigClass -> (Configuration)Injections.getOrCreate(injectionManager, clientConfigClass));
        suppliers.add(webTargetProvider);
        DelegatedInjectionValueParamProvider contextProvider = new DelegatedInjectionValueParamProvider(lazyContextResolver, injectionManager::createForeignDescriptor);
        suppliers.add(contextProvider);
        serverBag.setValueParamProviders(Collections.unmodifiableCollection(suppliers));
        injectionManager.register((Binding)Bindings.service(asyncProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(cookieProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(formProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(headerProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(matrixProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(pathProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(queryProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(webTargetProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(beanProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(entityProvider).to(ValueParamProvider.class));
        injectionManager.register((Binding)Bindings.service(contextProvider).to(ValueParamProvider.class));
        Provider<ContainerRequest> request = () -> {
            RequestProcessingContextReference reference = injectionManager.getInstance(RequestProcessingContextReference.class);
            return reference.get().request();
        };
        this.registerResolver(injectionManager, asyncProvider, Suspended.class, request);
        this.registerResolver(injectionManager, cookieProvider, CookieParam.class, request);
        this.registerResolver(injectionManager, formProvider, FormParam.class, request);
        this.registerResolver(injectionManager, headerProvider, HeaderParam.class, request);
        this.registerResolver(injectionManager, matrixProvider, MatrixParam.class, request);
        this.registerResolver(injectionManager, pathProvider, PathParam.class, request);
        this.registerResolver(injectionManager, queryProvider, QueryParam.class, request);
        this.registerResolver(injectionManager, webTargetProvider, Uri.class, request);
        this.registerResolver(injectionManager, beanProvider, BeanParam.class, request);
    }

    private void registerResolver(InjectionManager im, ValueParamProvider vfp, Class<? extends Annotation> annotation, Provider<ContainerRequest> request) {
        im.register(Bindings.injectionResolver(new ParamInjectionResolver<Annotation>(vfp, annotation, request)));
    }

    @Override
    public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        List addedInstances = injectionManager.getAllInstances((Type)((Object)ValueParamProvider.class));
        if (!addedInstances.isEmpty()) {
            ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
            addedInstances.addAll(serverBag.getValueParamProviders());
            serverBag.setValueParamProviders(Collections.unmodifiableCollection(addedInstances));
        }
    }
}

