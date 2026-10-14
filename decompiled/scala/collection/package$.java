/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.Iterable$;
import scala.collection.IterableOnce$;
import scala.collection.Map$;
import scala.collection.Seq$;
import scala.collection.Set$;

public final class package$ {
    public static final package$ MODULE$ = new package$();
    private static final Iterable$ Traversable = Iterable$.MODULE$;
    private static final IterableOnce$ TraversableOnce = IterableOnce$.MODULE$;
    private static final IterableOnce$ GenTraversableOnce = IterableOnce$.MODULE$;
    private static final Iterable$ GenTraversable = Iterable$.MODULE$;
    private static final Iterable$ GenIterable = Iterable$.MODULE$;
    private static final Seq$ GenSeq = Seq$.MODULE$;
    private static final Set$ GenSet = Set$.MODULE$;
    private static final Map$ GenMap = Map$.MODULE$;

    public Iterable$ Traversable() {
        return Traversable;
    }

    public IterableOnce$ TraversableOnce() {
        return TraversableOnce;
    }

    public IterableOnce$ GenTraversableOnce() {
        return GenTraversableOnce;
    }

    public Iterable$ GenTraversable() {
        return GenTraversable;
    }

    public Iterable$ GenIterable() {
        return GenIterable;
    }

    public Seq$ GenSeq() {
        return GenSeq;
    }

    public Set$ GenSet() {
        return GenSet;
    }

    public Map$ GenMap() {
        return GenMap;
    }

    private package$() {
    }
}

