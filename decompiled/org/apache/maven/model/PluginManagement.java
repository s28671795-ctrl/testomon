/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import org.apache.maven.model.PluginContainer;

public class PluginManagement
extends PluginContainer
implements Serializable,
Cloneable {
    @Override
    public PluginManagement clone() {
        try {
            PluginManagement copy = (PluginManagement)super.clone();
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }
}

