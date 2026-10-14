/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.resolution;

import org.apache.maven.model.Model;
import org.apache.maven.model.resolution.UnresolvableModelException;

public interface WorkspaceModelResolver {
    public Model resolveRawModel(String var1, String var2, String var3) throws UnresolvableModelException;

    public Model resolveEffectiveModel(String var1, String var2, String var3) throws UnresolvableModelException;
}

