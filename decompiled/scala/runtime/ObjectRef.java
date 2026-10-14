/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class ObjectRef<T>
implements Serializable {
    private static final long serialVersionUID = -9055728157600312291L;
    public T elem;

    public ObjectRef(T elem) {
        this.elem = elem;
    }

    public String toString() {
        return String.valueOf(this.elem);
    }

    public static <U> ObjectRef<U> create(U e) {
        return new ObjectRef<U>(e);
    }

    public static ObjectRef<Object> zero() {
        return new ObjectRef<Object>(null);
    }
}

