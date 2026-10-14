/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert;

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
import scala.collection.convert.ToJavaImplicits;
import scala.collection.convert.ToScalaImplicits;
import scala.collection.mutable.Buffer;

public final class ImplicitConversions$
implements ToScalaImplicits,
ToJavaImplicits {
    public static final ImplicitConversions$ MODULE$ = new ImplicitConversions$();

    @Override
    public <A> java.util.Iterator<A> iterator$u0020asJava(Iterator<A> it) {
        return ToJavaImplicits.iterator$u0020asJava$(this, it);
    }

    @Override
    public <A> Enumeration<A> enumeration$u0020asJava(Iterator<A> it) {
        return ToJavaImplicits.enumeration$u0020asJava$(this, it);
    }

    @Override
    public <A> java.lang.Iterable<A> iterable$u0020asJava(Iterable<A> i) {
        return ToJavaImplicits.iterable$u0020asJava$(this, i);
    }

    @Override
    public <A> Collection<A> collection$u0020asJava(Iterable<A> it) {
        return ToJavaImplicits.collection$u0020asJava$(this, it);
    }

    @Override
    public <A> List<A> buffer$u0020AsJavaList(Buffer<A> b) {
        return ToJavaImplicits.buffer$u0020AsJavaList$(this, b);
    }

    @Override
    public <A> List<A> mutableSeq$u0020AsJavaList(scala.collection.mutable.Seq<A> seq) {
        return ToJavaImplicits.mutableSeq$u0020AsJavaList$(this, seq);
    }

    @Override
    public <A> List<A> seq$u0020AsJavaList(Seq<A> seq) {
        return ToJavaImplicits.seq$u0020AsJavaList$(this, seq);
    }

    @Override
    public <A> java.util.Set<A> mutableSet$u0020AsJavaSet(scala.collection.mutable.Set<A> s2) {
        return ToJavaImplicits.mutableSet$u0020AsJavaSet$(this, s2);
    }

    @Override
    public <A> java.util.Set<A> set$u0020AsJavaSet(Set<A> s2) {
        return ToJavaImplicits.set$u0020AsJavaSet$(this, s2);
    }

    @Override
    public <K, V> java.util.Map<K, V> mutableMap$u0020AsJavaMap(scala.collection.mutable.Map<K, V> m4) {
        return ToJavaImplicits.mutableMap$u0020AsJavaMap$(this, m4);
    }

    @Override
    public <K, V> Dictionary<K, V> dictionary$u0020asJava(scala.collection.mutable.Map<K, V> m4) {
        return ToJavaImplicits.dictionary$u0020asJava$(this, m4);
    }

    @Override
    public <K, V> java.util.Map<K, V> map$u0020AsJavaMap(Map<K, V> m4) {
        return ToJavaImplicits.map$u0020AsJavaMap$(this, m4);
    }

    @Override
    public <K, V> ConcurrentMap<K, V> map$u0020AsJavaConcurrentMap(scala.collection.concurrent.Map<K, V> m4) {
        return ToJavaImplicits.map$u0020AsJavaConcurrentMap$(this, m4);
    }

    @Override
    public <A> Iterator<A> iterator$u0020asScala(java.util.Iterator<A> it) {
        return ToScalaImplicits.iterator$u0020asScala$(this, it);
    }

    @Override
    public <A> Iterator<A> enumeration$u0020AsScalaIterator(Enumeration<A> i) {
        return ToScalaImplicits.enumeration$u0020AsScalaIterator$(this, i);
    }

    @Override
    public <A> Iterable<A> iterable$u0020AsScalaIterable(java.lang.Iterable<A> i) {
        return ToScalaImplicits.iterable$u0020AsScalaIterable$(this, i);
    }

    @Override
    public <A> Iterable<A> collection$u0020AsScalaIterable(Collection<A> i) {
        return ToScalaImplicits.collection$u0020AsScalaIterable$(this, i);
    }

    @Override
    public <A> Buffer<A> list$u0020asScalaBuffer(List<A> l) {
        return ToScalaImplicits.list$u0020asScalaBuffer$(this, l);
    }

    @Override
    public <A> scala.collection.mutable.Set<A> set$u0020asScala(java.util.Set<A> s2) {
        return ToScalaImplicits.set$u0020asScala$(this, s2);
    }

    @Override
    public <K, V> scala.collection.mutable.Map<K, V> map$u0020AsScala(java.util.Map<K, V> m4) {
        return ToScalaImplicits.map$u0020AsScala$(this, m4);
    }

    @Override
    public <K, V> scala.collection.concurrent.Map<K, V> map$u0020AsScalaConcurrentMap(ConcurrentMap<K, V> m4) {
        return ToScalaImplicits.map$u0020AsScalaConcurrentMap$(this, m4);
    }

    @Override
    public <K, V> scala.collection.mutable.Map<K, V> dictionary$u0020AsScalaMap(Dictionary<K, V> p) {
        return ToScalaImplicits.dictionary$u0020AsScalaMap$(this, p);
    }

    @Override
    public scala.collection.mutable.Map<String, String> properties$u0020AsScalaMap(Properties p) {
        return ToScalaImplicits.properties$u0020AsScalaMap$(this, p);
    }

    private ImplicitConversions$() {
    }
}

