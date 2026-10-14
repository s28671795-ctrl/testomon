/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

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
import scala.collection.convert.AsJavaConverters;
import scala.collection.convert.AsScalaConverters;
import scala.collection.mutable.Buffer;

public final class CollectionConverters$
implements AsJavaConverters,
AsScalaConverters {
    public static final CollectionConverters$ MODULE$ = new CollectionConverters$();

    @Override
    public <A> Iterator<A> asScala(java.util.Iterator<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, i);
    }

    @Override
    public <A> Iterator<A> asScala(Enumeration<A> e) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, e);
    }

    @Override
    public <A> Iterable<A> asScala(java.lang.Iterable<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, i);
    }

    @Override
    public <A> Iterable<A> asScala(Collection<A> c) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, c);
    }

    @Override
    public <A> Buffer<A> asScala(List<A> l) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, l);
    }

    @Override
    public <A> scala.collection.mutable.Set<A> asScala(java.util.Set<A> s2) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, s2);
    }

    @Override
    public <K, V> scala.collection.mutable.Map<K, V> asScala(java.util.Map<K, V> m4) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, m4);
    }

    @Override
    public <K, V> scala.collection.concurrent.Map<K, V> asScala(ConcurrentMap<K, V> m4) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, m4);
    }

    @Override
    public <K, V> scala.collection.mutable.Map<K, V> asScala(Dictionary<K, V> d) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, d);
    }

    @Override
    public scala.collection.mutable.Map<String, String> asScala(Properties p) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, p);
    }

    @Override
    public <A> java.util.Iterator<A> asJava(Iterator<A> i) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, i);
    }

    @Override
    public <A> Enumeration<A> asJavaEnumeration(Iterator<A> i) {
        return AsJavaConverters.asJavaEnumeration$(this, i);
    }

    @Override
    public <A> java.lang.Iterable<A> asJava(Iterable<A> i) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, i);
    }

    @Override
    public <A> Collection<A> asJavaCollection(Iterable<A> i) {
        return AsJavaConverters.asJavaCollection$(this, i);
    }

    @Override
    public <A> List<A> asJava(Buffer<A> b) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, b);
    }

    @Override
    public <A> List<A> asJava(scala.collection.mutable.Seq<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, s2);
    }

    @Override
    public <A> List<A> asJava(Seq<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, s2);
    }

    @Override
    public <A> java.util.Set<A> asJava(scala.collection.mutable.Set<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, s2);
    }

    @Override
    public <A> java.util.Set<A> asJava(Set<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, s2);
    }

    @Override
    public <K, V> java.util.Map<K, V> asJava(scala.collection.mutable.Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, m4);
    }

    @Override
    public <K, V> Dictionary<K, V> asJavaDictionary(scala.collection.mutable.Map<K, V> m4) {
        return AsJavaConverters.asJavaDictionary$(this, m4);
    }

    @Override
    public <K, V> java.util.Map<K, V> asJava(Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, m4);
    }

    @Override
    public <K, V> ConcurrentMap<K, V> asJava(scala.collection.concurrent.Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, m4);
    }

    private CollectionConverters$() {
    }
}

