/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import org.apache.maven.model.Repository;

public class DeploymentRepository
extends Repository
implements Serializable,
Cloneable {
    private boolean uniqueVersion = true;

    @Override
    public DeploymentRepository clone() {
        try {
            DeploymentRepository copy = (DeploymentRepository)super.clone();
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public boolean isUniqueVersion() {
        return this.uniqueVersion;
    }

    public void setUniqueVersion(boolean uniqueVersion) {
        this.uniqueVersion = uniqueVersion;
    }
}

