/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.collection.mutable.StringBuilder;
import scala.runtime.ModuleSerializationProxy;

public final class StringBuilder$
implements Serializable {
    public static final StringBuilder$ MODULE$ = new StringBuilder$();

    public StringBuilder newBuilder() {
        return new StringBuilder();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(StringBuilder$.class);
    }

    private StringBuilder$() {
    }
}

