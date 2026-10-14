/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import org.apache.maven.model.PluginContainer;
import org.apache.maven.model.PluginManagement;

public class PluginConfiguration
extends PluginContainer
implements Serializable,
Cloneable {
    private PluginManagement pluginManagement;

    @Override
    public PluginConfiguration clone() {
        try {
            PluginConfiguration copy = (PluginConfiguration)super.clone();
            if (this.pluginManagement != null) {
                copy.pluginManagement = this.pluginManagement.clone();
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public PluginManagement getPluginManagement() {
        return this.pluginManagement;
    }

    public void setPluginManagement(PluginManagement pluginManagement) {
        this.pluginManagement = pluginManagement;
    }

    @Override
    public String toString() {
        return "PluginConfiguration {" + super.toString() + "}";
    }
}

