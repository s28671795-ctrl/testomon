/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.process.MappableException;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Parameterized;
import org.glassfish.jersey.server.spi.internal.ParamValueFactoryWithSource;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

public final class ParameterValueHelper {
    public static Object[] getParameterValues(List<ParamValueFactoryWithSource<?>> valueProviders, ContainerRequest request) {
        Object[] params = new Object[valueProviders.size()];
        try {
            int entityProviderIndex = -1;
            int index = 0;
            for (ParamValueFactoryWithSource<?> paramValProvider : valueProviders) {
                if (paramValProvider.getSource().equals((Object)Parameter.Source.ENTITY)) {
                    entityProviderIndex = index++;
                    continue;
                }
                params[index++] = paramValProvider.apply(request);
            }
            if (entityProviderIndex != -1) {
                params[entityProviderIndex] = valueProviders.get(entityProviderIndex).apply(request);
            }
            return params;
        }
        catch (WebApplicationException e) {
            throw e;
        }
        catch (MessageBodyProviderNotFoundException e) {
            throw new NotSupportedException(e);
        }
        catch (ProcessingException e) {
            throw e;
        }
        catch (RuntimeException e) {
            if (e.getCause() instanceof WebApplicationException) {
                throw (WebApplicationException)e.getCause();
            }
            throw new MappableException("Exception obtaining parameters", e);
        }
    }

    public static List<ParamValueFactoryWithSource<?>> createValueProviders(Collection<ValueParamProvider> valueSuppliers, Parameterized parameterized) {
        Function<ContainerRequest, ?> valueFunction;
        if (null == parameterized.getParameters() || 0 == parameterized.getParameters().size()) {
            return Collections.emptyList();
        }
        List<ValueParamProvider> valueParamProviders = valueSuppliers.stream().sorted((o1, o2) -> o2.getPriority().getWeight() - o1.getPriority().getWeight()).collect(Collectors.toList());
        boolean entityParamFound = false;
        ArrayList providers = new ArrayList(parameterized.getParameters().size());
        for (Parameter parameter : parameterized.getParameters()) {
            Parameter.Source parameterSource = parameter.getSource();
            entityParamFound = entityParamFound || Parameter.Source.ENTITY == parameterSource;
            valueFunction = ParameterValueHelper.getParamValueProvider(valueParamProviders, parameter);
            if (valueFunction != null) {
                providers.add(ParameterValueHelper.wrapParamValueProvider(valueFunction, parameterSource));
                continue;
            }
            providers.add(null);
        }
        if (!entityParamFound && Collections.frequency(providers, null) == 1) {
            Parameter parameter;
            int entityParamIndex = providers.lastIndexOf(null);
            parameter = parameterized.getParameters().get(entityParamIndex);
            if (Parameter.Source.UNKNOWN == parameter.getSource() && !parameter.isQualified()) {
                Parameter overriddenParameter = Parameter.overrideSource(parameter, Parameter.Source.ENTITY);
                valueFunction = ParameterValueHelper.getParamValueProvider(valueParamProviders, overriddenParameter);
                if (valueFunction != null) {
                    providers.set(entityParamIndex, ParameterValueHelper.wrapParamValueProvider(valueFunction, overriddenParameter.getSource()));
                } else {
                    providers.set(entityParamIndex, null);
                }
            }
        }
        return providers;
    }

    private static <T> ParamValueFactoryWithSource<T> wrapParamValueProvider(Function<ContainerRequest, T> factory2, Parameter.Source paramSource) {
        return new ParamValueFactoryWithSource<T>(factory2, paramSource);
    }

    private static Function<ContainerRequest, ?> getParamValueProvider(Collection<ValueParamProvider> valueProviders, Parameter parameter) {
        Function<ContainerRequest, ?> valueProvider = null;
        Iterator<ValueParamProvider> vfpIterator = valueProviders.iterator();
        while (valueProvider == null && vfpIterator.hasNext()) {
            valueProvider = vfpIterator.next().getValueProvider(parameter);
        }
        return valueProvider;
    }

    private ParameterValueHelper() {
    }
}

