/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.concurrent.INodeBase;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.TrieMap;
import scala.collection.immutable.Seq;
import scala.collection.mutable.GrowableBuilder;
import scala.runtime.ModuleSerializationProxy;

public final class TrieMap$
implements MapFactory<TrieMap> {
    public static final TrieMap$ MODULE$ = new TrieMap$();
    private static final long serialVersionUID = 3L;
    private static final transient AtomicReferenceFieldUpdater<INodeBase<?, ?>, MainNode<?, ?>> inodeupdater = AtomicReferenceFieldUpdater.newUpdater(INodeBase.class, MainNode.class, "mainnode");

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, TrieMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    @Override
    public <K, V> TrieMap<K, V> empty() {
        return new TrieMap();
    }

    @Override
    public <K, V> TrieMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return (TrieMap)new TrieMap().addAll(it);
    }

    @Override
    public <K, V> GrowableBuilder<Tuple2<K, V>, TrieMap<K, V>> newBuilder() {
        return new GrowableBuilder<Tuple2<K, V>, TrieMap<K, V>>(new TrieMap());
    }

    public AtomicReferenceFieldUpdater<INodeBase<?, ?>, MainNode<?, ?>> inodeupdater() {
        return inodeupdater;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TrieMap$.class);
    }

    private TrieMap$() {
    }
}

