/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.artifact.resolver;

import java.util.List;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.AbstractArtifactResolutionException;

public class ArtifactResolutionException
extends AbstractArtifactResolutionException {
    public ArtifactResolutionException(String message, String groupId, String artifactId, String version, String type, String classifier, List<ArtifactRepository> remoteRepositories, List<String> path, Throwable t2) {
        super(message, groupId, artifactId, version, type, classifier, remoteRepositories, path, t2);
    }

    public ArtifactResolutionException(String message, String groupId, String artifactId, String version, String type, String classifier, Throwable t2) {
        super(message, groupId, artifactId, version, type, classifier, null, null, t2);
    }

    public ArtifactResolutionException(String message, Artifact artifact) {
        super(message, artifact);
    }

    public ArtifactResolutionException(String message, Artifact artifact, List<ArtifactRepository> remoteRepositories) {
        super(message, artifact, remoteRepositories);
    }

    public ArtifactResolutionException(String message, Artifact artifact, Throwable cause) {
        super(message, artifact, null, cause);
    }

    public ArtifactResolutionException(String message, Artifact artifact, List<ArtifactRepository> remoteRepositories, Throwable cause) {
        super(message, artifact, remoteRepositories, cause);
    }
}

