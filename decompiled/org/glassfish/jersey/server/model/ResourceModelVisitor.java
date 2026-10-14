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
import org.glassfish.jersey.server.model.RuntimeResource;

public interface ResourceModelVisitor {
    public void visitResource(Resource var1);

    public void visitChildResource(Resource var1);

    public void visitResourceMethod(ResourceMethod var1);

    public void visitInvocable(Invocable var1);

    public void visitMethodHandler(MethodHandler var1);

    public void visitResourceHandlerConstructor(HandlerConstructor var1);

    public void visitResourceModel(ResourceModel var1);

    public void visitRuntimeResource(RuntimeResource var1);
}

