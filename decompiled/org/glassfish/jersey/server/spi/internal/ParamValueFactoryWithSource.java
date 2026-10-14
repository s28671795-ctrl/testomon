/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi.internal;

import java.util.function.Function;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.ContainerRequest;

public final class ParamValueFactoryWithSource<T>
implements Function<ContainerRequest, T> {
    private final Function<ContainerRequest, T> parameterFunction;
    private final Parameter.Source parameterSource;

    public ParamValueFactoryWithSource(Function<ContainerRequest, T> paramFunction, Parameter.Source parameterSource) {
        this.parameterFunction = paramFunction;
        this.parameterSource = parameterSource;
    }

    @Override
    public T apply(ContainerRequest request) {
        return this.parameterFunction.apply(request);
    }

    public Parameter.Source getSource() {
        return this.parameterSource;
    }
}

