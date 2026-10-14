/*
 * Decompiled with CFR 0.152.
 */
package scala.sys;

import scala.sys.Prop;

public final class Prop$ {
    public static final Prop$ MODULE$ = new Prop$();

    public <T> Prop<T> apply(String key, Prop.Creator<T> evidence$1) {
        return evidence$1.apply(key);
    }

    private Prop$() {
    }
}

