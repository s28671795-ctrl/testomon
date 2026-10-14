/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.List;
import org.glassfish.jersey.server.model.Parameter;

public interface Parameterized {
    public List<Parameter> getParameters();

    public boolean requiresEntity();
}

