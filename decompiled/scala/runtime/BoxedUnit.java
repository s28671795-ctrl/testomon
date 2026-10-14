/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class BoxedUnit
implements Serializable {
    private static final long serialVersionUID = 8405543498931817370L;
    public static final BoxedUnit UNIT = new BoxedUnit();
    public static final Class<Void> TYPE = Void.TYPE;

    private Object readResolve() {
        return UNIT;
    }

    private BoxedUnit() {
    }

    public boolean equals(Object other) {
        return this == other;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "()";
    }
}

