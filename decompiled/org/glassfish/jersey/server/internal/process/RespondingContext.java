/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.process;

import java.util.function.Function;
import org.glassfish.jersey.process.internal.ChainableStage;
import org.glassfish.jersey.process.internal.Stage;
import org.glassfish.jersey.server.ContainerResponse;

public interface RespondingContext {
    public void push(Function<ContainerResponse, ContainerResponse> var1);

    public void push(ChainableStage<ContainerResponse> var1);

    public Stage<ContainerResponse> createRespondingRoot();
}

