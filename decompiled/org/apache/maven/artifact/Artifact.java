/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.artifact;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.metadata.ArtifactMetadata;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.OverConstrainedVersionException;
import org.apache.maven.artifact.versioning.VersionRange;

public interface Artifact
extends Comparable<Artifact> {
    public static final String RELEASE_VERSION = "RELEASE";
    public static final String LATEST_VERSION = "LATEST";
    public static final String SNAPSHOT_VERSION = "SNAPSHOT";
    public static final Pattern VERSION_FILE_PATTERN = Pattern.compile("^(.*)-([0-9]{8}\\.[0-9]{6})-([0-9]+)$");
    public static final String SCOPE_COMPILE = "compile";
    public static final String SCOPE_COMPILE_PLUS_RUNTIME = "compile+runtime";
    public static final String SCOPE_TEST = "test";
    public static final String SCOPE_RUNTIME = "runtime";
    public static final String SCOPE_RUNTIME_PLUS_SYSTEM = "runtime+system";
    public static final String SCOPE_PROVIDED = "provided";
    public static final String SCOPE_SYSTEM = "system";
    public static final String SCOPE_IMPORT = "import";

    public String getGroupId();

    public String getArtifactId();

    public String getVersion();

    public void setVersion(String var1);

    public String getScope();

    public String getType();

    public String getClassifier();

    public boolean hasClassifier();

    public File getFile();

    public void setFile(File var1);

    public String getBaseVersion();

    public void setBaseVersion(String var1);

    public String getId();

    public String getDependencyConflictId();

    public void addMetadata(ArtifactMetadata var1);

    public Collection<ArtifactMetadata> getMetadataList();

    public void setRepository(ArtifactRepository var1);

    public ArtifactRepository getRepository();

    public void updateVersion(String var1, ArtifactRepository var2);

    public String getDownloadUrl();

    public void setDownloadUrl(String var1);

    public ArtifactFilter getDependencyFilter();

    public void setDependencyFilter(ArtifactFilter var1);

    public ArtifactHandler getArtifactHandler();

    public List<String> getDependencyTrail();

    public void setDependencyTrail(List<String> var1);

    public void setScope(String var1);

    public VersionRange getVersionRange();

    public void setVersionRange(VersionRange var1);

    public void selectVersion(String var1);

    public void setGroupId(String var1);

    public void setArtifactId(String var1);

    public boolean isSnapshot();

    public void setResolved(boolean var1);

    public boolean isResolved();

    public void setResolvedVersion(String var1);

    public void setArtifactHandler(ArtifactHandler var1);

    public boolean isRelease();

    public void setRelease(boolean var1);

    public List<ArtifactVersion> getAvailableVersions();

    public void setAvailableVersions(List<ArtifactVersion> var1);

    public boolean isOptional();

    public void setOptional(boolean var1);

    public ArtifactVersion getSelectedVersion() throws OverConstrainedVersionException;

    public boolean isSelectedVersionKnown() throws OverConstrainedVersionException;
}

