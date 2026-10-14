/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.Seq;
import scala.collection.Set;
import scala.collection.convert.AsJavaExtensions;
import scala.collection.convert.AsScalaExtensions;
import scala.collection.mutable.Buffer;

public final class CollectionConverters$
implements AsJavaExtensions,
AsScalaExtensions {
    public static final CollectionConverters$ MODULE$ = new CollectionConverters$();

    @Override
    public <A> AsScalaExtensions.IteratorHasAsScala<A> IteratorHasAsScala(java.util.Iterator<A> i) {
        return AsScalaExtensions.IteratorHasAsScala$(this, i);
    }

    @Override
    public <A> AsScalaExtensions.EnumerationHasAsScala<A> EnumerationHasAsScala(Enumeration<A> e) {
        return AsScalaExtensions.EnumerationHasAsScala$(this, e);
    }

    @Override
    public <A> AsScalaExtensions.IterableHasAsScala<A> IterableHasAsScala(java.lang.Iterable<A> i) {
        return AsScalaExtensions.IterableHasAsScala$(this, i);
    }

    @Override
    public <A> AsScalaExtensions.CollectionHasAsScala<A> CollectionHasAsScala(Collection<A> c) {
        return AsScalaExtensions.CollectionHasAsScala$(this, c);
    }

    @Override
    public <A> AsScalaExtensions.ListHasAsScala<A> ListHasAsScala(List<A> l) {
        return AsScalaExtensions.ListHasAsScala$(this, l);
    }

    @Override
    public <A> AsScalaExtensions.SetHasAsScala<A> SetHasAsScala(java.util.Set<A> s2) {
        return AsScalaExtensions.SetHasAsScala$(this, s2);
    }

    @Override
    public <K, V> AsScalaExtensions.MapHasAsScala<K, V> MapHasAsScala(java.util.Map<K, V> m4) {
        return AsScalaExtensions.MapHasAsScala$(this, m4);
    }

    @Override
    public <K, V> AsScalaExtensions.ConcurrentMapHasAsScala<K, V> ConcurrentMapHasAsScala(ConcurrentMap<K, V> m4) {
        return AsScalaExtensions.ConcurrentMapHasAsScala$(this, m4);
    }

    @Override
    public <K, V> AsScalaExtensions.DictionaryHasAsScala<K, V> DictionaryHasAsScala(Dictionary<K, V> d) {
        return AsScalaExtensions.DictionaryHasAsScala$(this, d);
    }

    @Override
    public AsScalaExtensions.PropertiesHasAsScala PropertiesHasAsScala(Properties i) {
        return AsScalaExtensions.PropertiesHasAsScala$(this, i);
    }

    @Override
    public <A> AsJavaExtensions.IteratorHasAsJava<A> IteratorHasAsJava(Iterator<A> i) {
        return AsJavaExtensions.IteratorHasAsJava$(this, i);
    }

    @Override
    public <A> AsJavaExtensions.IterableHasAsJava<A> IterableHasAsJava(Iterable<A> i) {
        return AsJavaExtensions.IterableHasAsJava$(this, i);
    }

    @Override
    public <A> AsJavaExtensions.BufferHasAsJava<A> BufferHasAsJava(Buffer<A> b) {
        return AsJavaExtensions.BufferHasAsJava$(this, b);
    }

    @Override
    public <A> AsJavaExtensions.MutableSeqHasAsJava<A> MutableSeqHasAsJava(scala.collection.mutable.Seq<A> s2) {
        return AsJavaExtensions.MutableSeqHasAsJava$(this, s2);
    }

    @Override
    public <A> AsJavaExtensions.SeqHasAsJava<A> SeqHasAsJava(Seq<A> s2) {
        return AsJavaExtensions.SeqHasAsJava$(this, s2);
    }

    @Override
    public <A> AsJavaExtensions.MutableSetHasAsJava<A> MutableSetHasAsJava(scala.collection.mutable.Set<A> s2) {
        return AsJavaExtensions.MutableSetHasAsJava$(this, s2);
    }

    @Override
    public <A> AsJavaExtensions.SetHasAsJava<A> SetHasAsJava(Set<A> s2) {
        return AsJavaExtensions.SetHasAsJava$(this, s2);
    }

    @Override
    public <K, V> AsJavaExtensions.MutableMapHasAsJava<K, V> MutableMapHasAsJava(scala.collection.mutable.Map<K, V> m4) {
        return AsJavaExtensions.MutableMapHasAsJava$(this, m4);
    }

    @Override
    public <K, V> AsJavaExtensions.MapHasAsJava<K, V> MapHasAsJava(Map<K, V> m4) {
        return AsJavaExtensions.MapHasAsJava$(this, m4);
    }

    @Override
    public <K, V> AsJavaExtensions.ConcurrentMapHasAsJava<K, V> ConcurrentMapHasAsJava(scala.collection.concurrent.Map<K, V> m4) {
        return AsJavaExtensions.ConcurrentMapHasAsJava$(this, m4);
    }

    private CollectionConverters$() {
    }
}

