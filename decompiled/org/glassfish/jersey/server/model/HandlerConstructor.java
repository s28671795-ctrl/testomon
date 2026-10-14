/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.lang.reflect.Constructor;
import java.util.List;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Parameterized;
import org.glassfish.jersey.server.model.ResourceModelComponent;
import org.glassfish.jersey.server.model.ResourceModelVisitor;

public final class HandlerConstructor
implements Parameterized,
ResourceModelComponent {
    private final Constructor<?> constructor;
    private final List<Parameter> parameters;

    HandlerConstructor(Constructor<?> constructor, List<Parameter> parameters) {
        this.constructor = constructor;
        this.parameters = parameters;
    }

    public Constructor<?> getConstructor() {
        return this.constructor;
    }

    @Override
    public List<Parameter> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean requiresEntity() {
        for (Parameter p : this.getParameters()) {
            if (Parameter.Source.ENTITY != p.getSource()) continue;
            return true;
        }
        return false;
    }

    @Override
    public void accept(ResourceModelVisitor visitor) {
        visitor.visitResourceHandlerConstructor(this);
    }

    public List<ResourceModelComponent> getComponents() {
        return null;
    }
}

