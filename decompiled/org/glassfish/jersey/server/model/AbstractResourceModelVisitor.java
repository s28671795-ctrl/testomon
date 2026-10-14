/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import org.glassfish.jersey.server.model.HandlerConstructor;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.MethodHandler;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.model.ResourceModelVisitor;
import org.glassfish.jersey.server.model.RuntimeResource;

public abstract class AbstractResourceModelVisitor
implements ResourceModelVisitor {
    @Override
    public void visitResource(Resource resource) {
    }

    @Override
    public void visitChildResource(Resource resource) {
    }

    @Override
    public void visitResourceMethod(ResourceMethod method2) {
    }

    @Override
    public void visitInvocable(Invocable invocable) {
    }

    @Override
    public void visitMethodHandler(MethodHandler methodHandler) {
    }

    @Override
    public void visitResourceHandlerConstructor(HandlerConstructor constructor) {
    }

    @Override
    public void visitResourceModel(ResourceModel resourceModel) {
    }

    @Override
    public void visitRuntimeResource(RuntimeResource runtimeResource) {
    }
}

