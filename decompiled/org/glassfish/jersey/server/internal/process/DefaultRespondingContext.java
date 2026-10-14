/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.process;

import java.util.function.Function;
import org.glassfish.jersey.process.internal.ChainableStage;
import org.glassfish.jersey.process.internal.Stage;
import org.glassfish.jersey.process.internal.Stages;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.internal.process.RespondingContext;

class DefaultRespondingContext
implements RespondingContext {
    private Stage<ContainerResponse> rootStage;

    DefaultRespondingContext() {
    }

    @Override
    public void push(Function<ContainerResponse, ContainerResponse> responseTransformation) {
        this.rootStage = this.rootStage == null ? new Stages.LinkedStage<ContainerResponse>(responseTransformation) : new Stages.LinkedStage<ContainerResponse>(responseTransformation, this.rootStage);
    }

    @Override
    public void push(ChainableStage<ContainerResponse> stage) {
        if (this.rootStage != null) {
            stage.setDefaultNext(this.rootStage);
        }
        this.rootStage = stage;
    }

    @Override
    public Stage<ContainerResponse> createRespondingRoot() {
        return this.rootStage;
    }
}

