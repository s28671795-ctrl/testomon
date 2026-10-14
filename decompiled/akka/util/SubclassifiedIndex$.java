/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import scala.Predef$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;

public final class SubclassifiedIndex$ {
    public static final SubclassifiedIndex$ MODULE$ = new SubclassifiedIndex$();
    private static final Map<Object, Set<Object>> internalEmptyMergeMap = ((Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$)).withDefaultValue(Predef$.MODULE$.Set().apply((Seq)Nil$.MODULE$));

    public <K, V> Map<K, Set<V>> emptyMergeMap() {
        return internalEmptyMergeMap;
    }

    private SubclassifiedIndex$() {
    }
}

