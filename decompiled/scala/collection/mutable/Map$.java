/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.MapFactory;
import scala.collection.mutable.HashMap$;
import scala.collection.mutable.Map;
import scala.runtime.ModuleSerializationProxy;

public final class Map$
extends MapFactory.Delegate<Map> {
    public static final Map$ MODULE$ = new Map$();
    private static final long serialVersionUID = 3L;

    private Object writeReplace() {
        return new ModuleSerializationProxy(Map$.class);
    }

    private Map$() {
        super(HashMap$.MODULE$);
    }
}

