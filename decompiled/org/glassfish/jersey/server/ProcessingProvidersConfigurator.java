/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import javax.ws.rs.NameBinding;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.model.ContractProvider;
import org.glassfish.jersey.model.internal.ComponentBag;
import org.glassfish.jersey.model.internal.RankedProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerBootstrapBag;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.ProcessingProviders;

class ProcessingProvidersConfigurator
implements BootstrapConfigurator {
    private static final Logger LOGGER = Logger.getLogger(ProcessingProvidersConfigurator.class.getName());

    ProcessingProvidersConfigurator() {
    }

    @Override
    public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
    }

    @Override
    public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
        ServerBootstrapBag serverBag = (ServerBootstrapBag)bootstrapBag;
        ComponentBag componentBag = serverBag.getRuntimeConfig().getComponentBag();
        Collection<Class<? extends Annotation>> applicationNameBindings = ReflectionHelper.getAnnotationTypes(ResourceConfig.unwrapApplication(serverBag.getRuntimeConfig()).getClass(), NameBinding.class);
        MultivaluedHashMap<RankedProvider<ContainerResponseFilter>, Class<? extends Annotation>> nameBoundRespFiltersInverse = new MultivaluedHashMap<RankedProvider<ContainerResponseFilter>, Class<? extends Annotation>>();
        MultivaluedHashMap<RankedProvider<ContainerRequestFilter>, Class<? extends Annotation>> nameBoundReqFiltersInverse = new MultivaluedHashMap<RankedProvider<ContainerRequestFilter>, Class<? extends Annotation>>();
        MultivaluedHashMap<RankedProvider<ReaderInterceptor>, Class<? extends Annotation>> nameBoundReaderInterceptorsInverse = new MultivaluedHashMap<RankedProvider<ReaderInterceptor>, Class<? extends Annotation>>();
        MultivaluedHashMap<RankedProvider<WriterInterceptor>, Class<? extends Annotation>> nameBoundWriterInterceptorsInverse = new MultivaluedHashMap<RankedProvider<WriterInterceptor>, Class<? extends Annotation>>();
        Iterable<RankedProvider<ContainerResponseFilter>> responseFilters = Providers.getAllRankedProviders(injectionManager, ContainerResponseFilter.class);
        MultivaluedMap<Class<? extends Annotation>, RankedProvider<ContainerResponseFilter>> nameBoundResponseFilters = ProcessingProvidersConfigurator.filterNameBound(responseFilters, null, componentBag, applicationNameBindings, nameBoundRespFiltersInverse);
        Iterable<RankedProvider<ContainerRequestFilter>> requestFilters = Providers.getAllRankedProviders(injectionManager, ContainerRequestFilter.class);
        ArrayList<RankedProvider<ContainerRequestFilter>> preMatchFilters = new ArrayList<RankedProvider<ContainerRequestFilter>>();
        MultivaluedMap<Class<? extends Annotation>, RankedProvider<ContainerRequestFilter>> nameBoundReqFilters = ProcessingProvidersConfigurator.filterNameBound(requestFilters, preMatchFilters, componentBag, applicationNameBindings, nameBoundReqFiltersInverse);
        Iterable<RankedProvider<ReaderInterceptor>> readerInterceptors = Providers.getAllRankedProviders(injectionManager, ReaderInterceptor.class);
        MultivaluedMap<Class<? extends Annotation>, RankedProvider<ReaderInterceptor>> nameBoundReaderInterceptors = ProcessingProvidersConfigurator.filterNameBound(readerInterceptors, null, componentBag, applicationNameBindings, nameBoundReaderInterceptorsInverse);
        Iterable<RankedProvider<WriterInterceptor>> writerInterceptors = Providers.getAllRankedProviders(injectionManager, WriterInterceptor.class);
        MultivaluedMap<Class<? extends Annotation>, RankedProvider<WriterInterceptor>> nameBoundWriterInterceptors = ProcessingProvidersConfigurator.filterNameBound(writerInterceptors, null, componentBag, applicationNameBindings, nameBoundWriterInterceptorsInverse);
        Iterable<DynamicFeature> dynamicFeatures = Providers.getAllProviders(injectionManager, DynamicFeature.class);
        ProcessingProviders processingProviders = new ProcessingProviders(nameBoundReqFilters, nameBoundReqFiltersInverse, nameBoundResponseFilters, nameBoundRespFiltersInverse, nameBoundReaderInterceptors, nameBoundReaderInterceptorsInverse, nameBoundWriterInterceptors, nameBoundWriterInterceptorsInverse, requestFilters, preMatchFilters, responseFilters, readerInterceptors, writerInterceptors, dynamicFeatures);
        serverBag.setProcessingProviders(processingProviders);
    }

    private static <T> MultivaluedMap<Class<? extends Annotation>, RankedProvider<T>> filterNameBound(Iterable<RankedProvider<T>> all, Collection<RankedProvider<ContainerRequestFilter>> preMatchingFilters, ComponentBag componentBag, Collection<Class<? extends Annotation>> applicationNameBindings, MultivaluedMap<RankedProvider<T>, Class<? extends Annotation>> inverseNameBoundMap) {
        MultivaluedHashMap<Class<Annotation>, RankedProvider<T>> result = new MultivaluedHashMap<Class<Annotation>, RankedProvider<T>>();
        Iterator<RankedProvider<T>> it = all.iterator();
        while (it.hasNext()) {
            boolean nameBound;
            boolean preMatching;
            ContractProvider model;
            RankedProvider<T> provider = it.next();
            Class<?> providerClass = provider.getProvider().getClass();
            Set<Type> contractTypes = provider.getContractTypes();
            if (contractTypes != null && !contractTypes.contains(providerClass)) {
                providerClass = ReflectionHelper.theMostSpecificTypeOf(contractTypes);
            }
            if ((model = componentBag.getModel(providerClass)) == null) {
                model = ComponentBag.modelFor(providerClass);
            }
            boolean bl = preMatching = providerClass.getAnnotation(PreMatching.class) != null;
            if (preMatching && preMatchingFilters != null) {
                it.remove();
                preMatchingFilters.add(new RankedProvider<ContainerRequestFilter>((ContainerRequestFilter)provider.getProvider(), model.getPriority(ContainerRequestFilter.class)));
            }
            if ((nameBound = model.isNameBound()) && !applicationNameBindings.isEmpty() && applicationNameBindings.containsAll(model.getNameBindings())) {
                nameBound = false;
            }
            if (!nameBound) continue;
            if (!preMatching) {
                it.remove();
                for (Class binding : model.getNameBindings()) {
                    result.add(binding, provider);
                    inverseNameBoundMap.add(provider, binding);
                }
                continue;
            }
            LOGGER.warning(LocalizationMessages.PREMATCHING_ALSO_NAME_BOUND(providerClass));
        }
        return result;
    }
}

