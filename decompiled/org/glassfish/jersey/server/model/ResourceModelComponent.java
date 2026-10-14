/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.List;
import org.glassfish.jersey.server.model.ResourceModelVisitor;

public interface ResourceModelComponent {
    public void accept(ResourceModelVisitor var1);

    public List<? extends ResourceModelComponent> getComponents();
}

