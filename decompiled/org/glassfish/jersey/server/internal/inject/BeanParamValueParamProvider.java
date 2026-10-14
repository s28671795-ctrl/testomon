/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.util.function.Function;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.ForeignDescriptor;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.util.collection.Cache;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.inject.AbstractValueParamProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

@Singleton
final class BeanParamValueParamProvider
extends AbstractValueParamProvider {
    private final InjectionManager injectionManager;

    public BeanParamValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep, InjectionManager injectionManager) {
        super(mpep, Parameter.Source.BEAN_PARAM);
        this.injectionManager = injectionManager;
    }

    @Override
    public Function<ContainerRequest, ?> createValueProvider(Parameter parameter) {
        return new BeanParamValueProvider(this.injectionManager, parameter);
    }

    private static final class BeanParamValueProvider
    implements Function<ContainerRequest, Object> {
        private final Parameter parameter;
        private final InjectionManager injectionManager;
        private final Cache<Class<?>, ForeignDescriptor> descriptorCache = new Cache(new Function<Class<?>, ForeignDescriptor>(){

            @Override
            public ForeignDescriptor apply(Class<?> key) {
                return injectionManager.createForeignDescriptor((Binding)Bindings.serviceAsContract(key).in(RequestScoped.class));
            }
        });

        private BeanParamValueProvider(InjectionManager injectionManager, Parameter parameter) {
            this.injectionManager = injectionManager;
            this.parameter = parameter;
        }

        @Override
        public Object apply(ContainerRequest request) {
            Class<?> rawType = this.parameter.getRawType();
            Object fromHk2 = this.injectionManager.getInstance(rawType);
            if (fromHk2 != null) {
                return fromHk2;
            }
            ForeignDescriptor foreignDescriptor = this.descriptorCache.apply(rawType);
            return this.injectionManager.getInstance(foreignDescriptor);
        }
    }
}

