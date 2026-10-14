/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import org.apache.maven.model.Contributor;

public class Developer
extends Contributor
implements Serializable,
Cloneable {
    private String id;

    @Override
    public Developer clone() {
        try {
            Developer copy = (Developer)super.clone();
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Developer {id=" + this.id + ", " + super.toString() + "}";
    }
}

