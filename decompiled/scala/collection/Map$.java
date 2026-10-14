/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function0;
import scala.collection.Map;
import scala.collection.MapFactory;
import scala.runtime.ModuleSerializationProxy;

public final class Map$
extends MapFactory.Delegate<Map> {
    public static final Map$ MODULE$ = new Map$();
    private static final long serialVersionUID = 3L;
    private static final Object DefaultSentinel = new Object();
    private static final Function0<Object> scala$collection$Map$$DefaultSentinelFn = (Function0<Object> & Serializable)() -> MODULE$.DefaultSentinel();

    private Object DefaultSentinel() {
        return DefaultSentinel;
    }

    public Function0<Object> scala$collection$Map$$DefaultSentinelFn() {
        return scala$collection$Map$$DefaultSentinelFn;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Map$.class);
    }

    private Map$() {
        super(scala.collection.immutable.Map$.MODULE$);
    }
}

