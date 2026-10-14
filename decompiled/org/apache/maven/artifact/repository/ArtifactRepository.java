/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.artifact.repository;

import java.util.List;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.repository.Authentication;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.repository.Proxy;

public interface ArtifactRepository {
    public String pathOf(Artifact var1);

    public String pathOfRemoteRepositoryMetadata(ArtifactMetadata var1);

    public String pathOfLocalRepositoryMetadata(ArtifactMetadata var1, ArtifactRepository var2);

    public String getUrl();

    public void setUrl(String var1);

    public String getBasedir();

    public String getProtocol();

    public String getId();

    public void setId(String var1);

    public ArtifactRepositoryPolicy getSnapshots();

    public void setSnapshotUpdatePolicy(ArtifactRepositoryPolicy var1);

    public ArtifactRepositoryPolicy getReleases();

    public void setReleaseUpdatePolicy(ArtifactRepositoryPolicy var1);

    public ArtifactRepositoryLayout getLayout();

    public void setLayout(ArtifactRepositoryLayout var1);

    public String getKey();

    @Deprecated
    public boolean isUniqueVersion();

    @Deprecated
    public boolean isBlacklisted();

    @Deprecated
    public void setBlacklisted(boolean var1);

    public Artifact find(Artifact var1);

    public List<String> findVersions(Artifact var1);

    public boolean isProjectAware();

    public void setAuthentication(Authentication var1);

    public Authentication getAuthentication();

    public void setProxy(Proxy var1);

    public Proxy getProxy();

    public List<ArtifactRepository> getMirroredRepositories();

    public void setMirroredRepositories(List<ArtifactRepository> var1);
}

