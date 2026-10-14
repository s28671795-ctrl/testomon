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

class ReturnValueConstraintLocation
implements ConstraintLocation {
    private final Callable callable;
    private final ConstraintLocation.ConstraintLocationKind kind;

    ReturnValueConstraintLocation(Callable callable) {
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
        return this.callable.getTypeForValidatorResolution();
    }

    @Override
    public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
        path.addReturnValueNode();
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
        return "ReturnValueConstraintLocation [callable=" + this.callable + "]";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.callable.hashCode();
        return result;
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
        ReturnValueConstraintLocation other = (ReturnValueConstraintLocation)obj;
        return this.callable.equals(other.callable);
    }
}

