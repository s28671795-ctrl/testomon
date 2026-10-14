/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.resolution;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Repository;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.resolution.InvalidRepositoryException;
import org.apache.maven.model.resolution.UnresolvableModelException;

public interface ModelResolver {
    public ModelSource resolveModel(String var1, String var2, String var3) throws UnresolvableModelException;

    public ModelSource resolveModel(Parent var1) throws UnresolvableModelException;

    public ModelSource resolveModel(Dependency var1) throws UnresolvableModelException;

    public void addRepository(Repository var1) throws InvalidRepositoryException;

    public void addRepository(Repository var1, boolean var2) throws InvalidRepositoryException;

    public ModelResolver newCopy();
}

