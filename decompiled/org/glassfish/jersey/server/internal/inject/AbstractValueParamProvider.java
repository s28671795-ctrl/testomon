/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import javax.inject.Provider;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractor;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

public abstract class AbstractValueParamProvider
implements ValueParamProvider {
    private final Provider<MultivaluedParameterExtractorProvider> mpep;
    private final Set<Parameter.Source> compatibleSources;

    protected AbstractValueParamProvider(Provider<MultivaluedParameterExtractorProvider> mpep, Parameter.Source ... compatibleSources) {
        this.mpep = mpep;
        this.compatibleSources = new HashSet<Parameter.Source>(Arrays.asList(compatibleSources));
    }

    protected final MultivaluedParameterExtractor<?> get(Parameter parameter) {
        return this.mpep.get().get(parameter);
    }

    protected abstract Function<ContainerRequest, ?> createValueProvider(Parameter var1);

    @Override
    public final Function<ContainerRequest, ?> getValueProvider(Parameter parameter) {
        if (!this.compatibleSources.contains((Object)parameter.getSource())) {
            return null;
        }
        return this.createValueProvider(parameter);
    }

    @Override
    public ValueParamProvider.PriorityType getPriority() {
        return ValueParamProvider.Priority.NORMAL;
    }
}

