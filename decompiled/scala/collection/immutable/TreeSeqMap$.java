/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.immutable.HashMap$;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.TreeSeqMap;
import scala.collection.immutable.TreeSeqMap$OrderBy$Insertion$;
import scala.collection.immutable.TreeSeqMap$OrderBy$Modification$;
import scala.collection.immutable.TreeSeqMap$Ordering$Zero$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class TreeSeqMap$
implements MapFactory<TreeSeqMap> {
    public static final TreeSeqMap$ MODULE$ = new TreeSeqMap$();
    private static final TreeSeqMap<Nothing$, Nothing$> EmptyByInsertion = new TreeSeqMap(TreeSeqMap$Ordering$Zero$.MODULE$, HashMap$.MODULE$.empty(), 0, TreeSeqMap$OrderBy$Insertion$.MODULE$);
    private static final TreeSeqMap<Nothing$, Nothing$> EmptyByModification = new TreeSeqMap(TreeSeqMap$Ordering$Zero$.MODULE$, HashMap$.MODULE$.empty(), 0, TreeSeqMap$OrderBy$Modification$.MODULE$);
    private static final TreeSeqMap<Nothing$, Nothing$> Empty = MODULE$.EmptyByInsertion();
    private static final Map$ Mapping = Map$.MODULE$;

    @Override
    public Object apply(Seq elems) {
        return MapFactory.apply$(this, elems);
    }

    @Override
    public <K, V> Factory<Tuple2<K, V>, TreeSeqMap<K, V>> mapFactory() {
        return MapFactory.mapFactory$(this);
    }

    private TreeSeqMap<Nothing$, Nothing$> EmptyByInsertion() {
        return EmptyByInsertion;
    }

    private TreeSeqMap<Nothing$, Nothing$> EmptyByModification() {
        return EmptyByModification;
    }

    public TreeSeqMap<Nothing$, Nothing$> Empty() {
        return Empty;
    }

    @Override
    public <K, V> TreeSeqMap<K, V> empty() {
        return this.empty(TreeSeqMap$OrderBy$Insertion$.MODULE$);
    }

    public <K, V> TreeSeqMap<K, V> empty(TreeSeqMap.OrderBy orderBy) {
        TreeSeqMap.OrderBy orderBy2 = orderBy;
        TreeSeqMap$OrderBy$Modification$ treeSeqMap$OrderBy$Modification$ = TreeSeqMap$OrderBy$Modification$.MODULE$;
        if (orderBy2 != null && orderBy2.equals(treeSeqMap$OrderBy$Modification$)) {
            return this.EmptyByModification();
        }
        return this.EmptyByInsertion();
    }

    @Override
    public <K, V> TreeSeqMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        if (it instanceof TreeSeqMap) {
            return (TreeSeqMap)it;
        }
        TreeSeqMap$OrderBy$Insertion$ newBuilder_newBuilder_orderedBy = TreeSeqMap$OrderBy$Insertion$.MODULE$;
        Object var2_2 = null;
        return (TreeSeqMap)((Builder)Growable.addAll$(new TreeSeqMap.Builder(newBuilder_newBuilder_orderedBy), it)).result();
    }

    public int scala$collection$immutable$TreeSeqMap$$increment(int ord) {
        if (ord == Integer.MAX_VALUE) {
            return Integer.MIN_VALUE;
        }
        return ord + 1;
    }

    @Override
    public <K, V> Builder<Tuple2<K, V>, TreeSeqMap<K, V>> newBuilder() {
        TreeSeqMap$OrderBy$Insertion$ newBuilder_orderedBy = TreeSeqMap$OrderBy$Insertion$.MODULE$;
        return new TreeSeqMap.Builder(newBuilder_orderedBy);
    }

    public <K, V> Builder<Tuple2<K, V>, TreeSeqMap<K, V>> newBuilder(TreeSeqMap.OrderBy orderedBy) {
        return new TreeSeqMap.Builder(orderedBy);
    }

    private Map$ Mapping() {
        return Mapping;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TreeSeqMap$.class);
    }

    private TreeSeqMap$() {
    }
}

