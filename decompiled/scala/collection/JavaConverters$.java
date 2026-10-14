/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;
import scala.Function0;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.JavaConverters;
import scala.collection.Map;
import scala.collection.Seq;
import scala.collection.Set;
import scala.collection.convert.AsJavaConverters;
import scala.collection.convert.AsScalaConverters;
import scala.collection.mutable.Buffer;

public final class JavaConverters$
implements AsJavaConverters,
AsScalaConverters {
    public static final JavaConverters$ MODULE$ = new JavaConverters$();

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

    public <A> java.util.Iterator<A> asJavaIterator(Iterator<A> i) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, i);
    }

    public <A> java.lang.Iterable<A> asJavaIterable(Iterable<A> i) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, i);
    }

    public <A> List<A> bufferAsJavaList(Buffer<A> b) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, b);
    }

    public <A> List<A> mutableSeqAsJavaList(scala.collection.mutable.Seq<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, s2);
    }

    public <A> List<A> seqAsJavaList(Seq<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, s2);
    }

    public <A> java.util.Set<A> mutableSetAsJavaSet(scala.collection.mutable.Set<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, s2);
    }

    public <A> java.util.Set<A> setAsJavaSet(Set<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, s2);
    }

    public <K, V> java.util.Map<K, V> mutableMapAsJavaMap(scala.collection.mutable.Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, m4);
    }

    public <K, V> java.util.Map<K, V> mapAsJavaMap(Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, m4);
    }

    public <K, V> ConcurrentMap<K, V> mapAsJavaConcurrentMap(scala.collection.concurrent.Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)this, m4);
    }

    public <A> Iterator<A> asScalaIterator(java.util.Iterator<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, i);
    }

    public <A> Iterator<A> enumerationAsScalaIterator(Enumeration<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, i);
    }

    public <A> Iterable<A> iterableAsScalaIterable(java.lang.Iterable<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, i);
    }

    public <A> Iterable<A> collectionAsScalaIterable(Collection<A> i) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, i);
    }

    public <A> Buffer<A> asScalaBuffer(List<A> l) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, l);
    }

    public <A> scala.collection.mutable.Set<A> asScalaSet(java.util.Set<A> s2) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, s2);
    }

    public <A, B> scala.collection.mutable.Map<A, B> mapAsScalaMap(java.util.Map<A, B> m4) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, m4);
    }

    public <A, B> scala.collection.concurrent.Map<A, B> mapAsScalaConcurrentMap(ConcurrentMap<A, B> m4) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, m4);
    }

    public <A, B> scala.collection.mutable.Map<A, B> dictionaryAsScalaMap(Dictionary<A, B> p) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, p);
    }

    public scala.collection.mutable.Map<String, String> propertiesAsScalaMap(Properties p) {
        return AsScalaConverters.asScala$((AsScalaConverters)this, p);
    }

    public <A> JavaConverters.AsJava<java.util.Iterator<A>> asJavaIteratorConverter(Iterator<A> i) {
        return new JavaConverters.AsJava<java.util.Iterator<A>>((Function0<java.util.Iterator> & Serializable)() -> MODULE$.asJavaIterator(i));
    }

    public <A> JavaConverters.AsJavaEnumeration<A> asJavaEnumerationConverter(Iterator<A> i) {
        return new JavaConverters.AsJavaEnumeration<A>(i);
    }

    public <A> JavaConverters.AsJava<java.lang.Iterable<A>> asJavaIterableConverter(Iterable<A> i) {
        return new JavaConverters.AsJava<java.lang.Iterable<A>>((Function0<java.lang.Iterable> & Serializable)() -> MODULE$.asJavaIterable(i));
    }

    public <A> JavaConverters.AsJavaCollection<A> asJavaCollectionConverter(Iterable<A> i) {
        return new JavaConverters.AsJavaCollection<A>(i);
    }

    public <A> JavaConverters.AsJava<List<A>> bufferAsJavaListConverter(Buffer<A> b) {
        return new JavaConverters.AsJava<List<A>>((Function0<List> & Serializable)() -> MODULE$.bufferAsJavaList(b));
    }

    public <A> JavaConverters.AsJava<List<A>> mutableSeqAsJavaListConverter(scala.collection.mutable.Seq<A> b) {
        return new JavaConverters.AsJava<List<A>>((Function0<List> & Serializable)() -> MODULE$.mutableSeqAsJavaList(b));
    }

    public <A> JavaConverters.AsJava<List<A>> seqAsJavaListConverter(Seq<A> b) {
        return new JavaConverters.AsJava<List<A>>((Function0<List> & Serializable)() -> MODULE$.seqAsJavaList(b));
    }

    public <A> JavaConverters.AsJava<java.util.Set<A>> mutableSetAsJavaSetConverter(scala.collection.mutable.Set<A> s2) {
        return new JavaConverters.AsJava<java.util.Set<A>>((Function0<java.util.Set> & Serializable)() -> MODULE$.mutableSetAsJavaSet(s2));
    }

    public <A> JavaConverters.AsJava<java.util.Set<A>> setAsJavaSetConverter(Set<A> s2) {
        return new JavaConverters.AsJava<java.util.Set<A>>((Function0<java.util.Set> & Serializable)() -> MODULE$.setAsJavaSet(s2));
    }

    public <K, V> JavaConverters.AsJava<java.util.Map<K, V>> mutableMapAsJavaMapConverter(scala.collection.mutable.Map<K, V> m4) {
        return new JavaConverters.AsJava<java.util.Map<K, V>>((Function0<java.util.Map> & Serializable)() -> MODULE$.mutableMapAsJavaMap(m4));
    }

    public <K, V> JavaConverters.AsJavaDictionary<K, V> asJavaDictionaryConverter(scala.collection.mutable.Map<K, V> m4) {
        return new JavaConverters.AsJavaDictionary<K, V>(m4);
    }

    public <K, V> JavaConverters.AsJava<java.util.Map<K, V>> mapAsJavaMapConverter(Map<K, V> m4) {
        return new JavaConverters.AsJava<java.util.Map<K, V>>((Function0<java.util.Map> & Serializable)() -> MODULE$.mapAsJavaMap(m4));
    }

    public <K, V> JavaConverters.AsJava<ConcurrentMap<K, V>> mapAsJavaConcurrentMapConverter(scala.collection.concurrent.Map<K, V> m4) {
        return new JavaConverters.AsJava<ConcurrentMap<K, V>>((Function0<ConcurrentMap> & Serializable)() -> MODULE$.mapAsJavaConcurrentMap(m4));
    }

    public <A> JavaConverters.AsScala<Iterator<A>> asScalaIteratorConverter(java.util.Iterator<A> i) {
        return new JavaConverters.AsScala<Iterator<A>>((Function0<Iterator> & Serializable)() -> MODULE$.asScalaIterator(i));
    }

    public <A> JavaConverters.AsScala<Iterator<A>> enumerationAsScalaIteratorConverter(Enumeration<A> i) {
        return new JavaConverters.AsScala<Iterator<A>>((Function0<Iterator> & Serializable)() -> MODULE$.enumerationAsScalaIterator(i));
    }

    public <A> JavaConverters.AsScala<Iterable<A>> iterableAsScalaIterableConverter(java.lang.Iterable<A> i) {
        return new JavaConverters.AsScala<Iterable<A>>((Function0<Iterable> & Serializable)() -> MODULE$.iterableAsScalaIterable(i));
    }

    public <A> JavaConverters.AsScala<Iterable<A>> collectionAsScalaIterableConverter(Collection<A> i) {
        return new JavaConverters.AsScala<Iterable<A>>((Function0<Iterable> & Serializable)() -> MODULE$.collectionAsScalaIterable(i));
    }

    public <A> JavaConverters.AsScala<Buffer<A>> asScalaBufferConverter(List<A> l) {
        return new JavaConverters.AsScala<Buffer<A>>((Function0<Buffer> & Serializable)() -> MODULE$.asScalaBuffer(l));
    }

    public <A> JavaConverters.AsScala<scala.collection.mutable.Set<A>> asScalaSetConverter(java.util.Set<A> s2) {
        return new JavaConverters.AsScala<scala.collection.mutable.Set<A>>((Function0<scala.collection.mutable.Set> & Serializable)() -> MODULE$.asScalaSet(s2));
    }

    public <K, V> JavaConverters.AsScala<scala.collection.mutable.Map<K, V>> mapAsScalaMapConverter(java.util.Map<K, V> m4) {
        return new JavaConverters.AsScala<scala.collection.mutable.Map<K, V>>((Function0<scala.collection.mutable.Map> & Serializable)() -> MODULE$.mapAsScalaMap(m4));
    }

    public <K, V> JavaConverters.AsScala<scala.collection.concurrent.Map<K, V>> mapAsScalaConcurrentMapConverter(ConcurrentMap<K, V> m4) {
        return new JavaConverters.AsScala<scala.collection.concurrent.Map<K, V>>((Function0<scala.collection.concurrent.Map> & Serializable)() -> MODULE$.mapAsScalaConcurrentMap(m4));
    }

    public <K, V> JavaConverters.AsScala<scala.collection.mutable.Map<K, V>> dictionaryAsScalaMapConverter(Dictionary<K, V> p) {
        return new JavaConverters.AsScala<scala.collection.mutable.Map<K, V>>((Function0<scala.collection.mutable.Map> & Serializable)() -> MODULE$.dictionaryAsScalaMap(p));
    }

    public JavaConverters.AsScala<scala.collection.mutable.Map<String, String>> propertiesAsScalaMapConverter(Properties p) {
        return new JavaConverters.AsScala<scala.collection.mutable.Map<String, String>>((Function0<scala.collection.mutable.Map> & Serializable)() -> MODULE$.propertiesAsScalaMap(p));
    }

    private JavaConverters$() {
    }
}

