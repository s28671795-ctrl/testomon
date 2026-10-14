/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

class CrossParameterConstraintLocation
implements ConstraintLocation {
    private final Callable callable;
    private final ConstraintLocation.ConstraintLocationKind kind;

    CrossParameterConstraintLocation(Callable callable) {
        this.callable = callable;
        this.kind = ConstraintLocation.ConstraintLocationKind.of(callable.getConstrainedElementKind());
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.callable.getDeclaringClass();
    }

    @Override
    public Constrainable getConstrainable() {
        return this.callable;
    }

    @Override
    public Type getTypeForValidatorResolution() {
        return Object[].class;
    }

    @Override
    public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
        path.addCrossParameterNode();
    }

    @Override
    public Object getValue(Object parent) {
        return parent;
    }

    @Override
    public ConstraintLocation.ConstraintLocationKind getKind() {
        return this.kind;
    }

    public String toString() {
        return "CrossParameterConstraintLocation [callable=" + this.callable + "]";
    }

    public int hashCode() {
        return this.callable.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CrossParameterConstraintLocation other = (CrossParameterConstraintLocation)obj;
        return this.callable.equals(other.callable);
    }
}

