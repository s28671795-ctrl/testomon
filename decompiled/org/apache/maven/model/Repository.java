/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import org.apache.maven.model.RepositoryBase;
import org.apache.maven.model.RepositoryPolicy;

public class Repository
extends RepositoryBase
implements Serializable,
Cloneable {
    private RepositoryPolicy releases;
    private RepositoryPolicy snapshots;

    @Override
    public Repository clone() {
        try {
            Repository copy = (Repository)super.clone();
            if (this.releases != null) {
                copy.releases = this.releases.clone();
            }
            if (this.snapshots != null) {
                copy.snapshots = this.snapshots.clone();
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public RepositoryPolicy getReleases() {
        return this.releases;
    }

    public RepositoryPolicy getSnapshots() {
        return this.snapshots;
    }

    public void setReleases(RepositoryPolicy releases) {
        this.releases = releases;
    }

    public void setSnapshots(RepositoryPolicy snapshots2) {
        this.snapshots = snapshots2;
    }
}

