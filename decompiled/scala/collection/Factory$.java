/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.Factory;
import scala.reflect.ClassTag;

public final class Factory$ {
    public static final Factory$ MODULE$ = new Factory$();
    private static final Factory<Object, String> stringFactory = new Factory.StringFactory();

    public Factory<Object, String> stringFactory() {
        return stringFactory;
    }

    public <A> Factory<A, Object> arrayFactory(ClassTag<A> evidence$1) {
        return new Factory.ArrayFactory<A>(evidence$1);
    }

    private Factory$() {
    }
}

