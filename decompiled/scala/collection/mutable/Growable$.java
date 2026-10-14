/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.IterableOnce;
import scala.collection.mutable.Growable;

public final class Growable$ {
    public static final Growable$ MODULE$ = new Growable$();

    public <A> Growable<A> from(Growable<A> empty, IterableOnce<A> it) {
        if (empty == null) {
            throw null;
        }
        return empty.addAll(it);
    }

    private Growable$() {
    }
}

