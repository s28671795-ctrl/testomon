/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.artifact.resolver.filter;

import org.apache.maven.artifact.Artifact;

public interface ArtifactFilter {
    public boolean include(Artifact var1);
}

