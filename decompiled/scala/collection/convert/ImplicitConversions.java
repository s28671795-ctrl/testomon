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
import scala.collection.convert.ImplicitConversions$;
import scala.collection.mutable.Buffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0019:Qa\u0001\u0003\t\u0002-1Q!\u0004\u0003\t\u00029AQ!G\u0001\u0005\u0002i\t1#S7qY&\u001c\u0017\u000e^\"p]Z,'o]5p]NT!!\u0002\u0004\u0002\u000f\r|gN^3si*\u0011q\u0001C\u0001\u000bG>dG.Z2uS>t'\"A\u0005\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001A\u0011A\"A\u0007\u0002\t\t\u0019\u0012*\u001c9mS\u000eLGoQ8om\u0016\u00148/[8ogN!\u0011aD\n\u0017!\t\u0001\u0012#D\u0001\t\u0013\t\u0011\u0002B\u0001\u0004B]f\u0014VM\u001a\t\u0003\u0019QI!!\u0006\u0003\u0003!Q{7kY1mC&k\u0007\u000f\\5dSR\u001c\bC\u0001\u0007\u0018\u0013\tABAA\bU_*\u000bg/Y%na2L7-\u001b;t\u0003\u0019a\u0014N\\5u}Q\t1\u0002\u000b\u0004\u00029}\u0001#e\t\t\u0003!uI!A\b\u0005\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003\u0005\nA&V:fA\u0001\u001c8-\u00197b])$7NL\"pY2,7\r^5p]\u000e{gN^3si\u0016\u00148\u000f\u0019\u0011j]N$X-\u00193\u0002\u000bMLgnY3\"\u0003\u0011\naA\r\u00182g9\u0002\u0004F\u0002\u0001\u001d?\u0001\u00123\u0005")
public final class ImplicitConversions {
    public static <K, V> ConcurrentMap<K, V> map$u0020AsJavaConcurrentMap(scala.collection.concurrent.Map<K, V> m4) {
        return ImplicitConversions$.MODULE$.map$u0020AsJavaConcurrentMap(m4);
    }

    public static <K, V> java.util.Map<K, V> map$u0020AsJavaMap(Map<K, V> m4) {
        return ImplicitConversions$.MODULE$.map$u0020AsJavaMap(m4);
    }

    public static <K, V> Dictionary<K, V> dictionary$u0020asJava(scala.collection.mutable.Map<K, V> m4) {
        return ImplicitConversions$.MODULE$.dictionary$u0020asJava(m4);
    }

    public static <K, V> java.util.Map<K, V> mutableMap$u0020AsJavaMap(scala.collection.mutable.Map<K, V> m4) {
        return ImplicitConversions$.MODULE$.mutableMap$u0020AsJavaMap(m4);
    }

    public static <A> java.util.Set<A> set$u0020AsJavaSet(Set<A> s2) {
        return ImplicitConversions$.MODULE$.set$u0020AsJavaSet(s2);
    }

    public static <A> java.util.Set<A> mutableSet$u0020AsJavaSet(scala.collection.mutable.Set<A> s2) {
        return ImplicitConversions$.MODULE$.mutableSet$u0020AsJavaSet(s2);
    }

    public static <A> List<A> seq$u0020AsJavaList(Seq<A> seq) {
        return ImplicitConversions$.MODULE$.seq$u0020AsJavaList(seq);
    }

    public static <A> List<A> mutableSeq$u0020AsJavaList(scala.collection.mutable.Seq<A> seq) {
        return ImplicitConversions$.MODULE$.mutableSeq$u0020AsJavaList(seq);
    }

    public static <A> List<A> buffer$u0020AsJavaList(Buffer<A> b) {
        return ImplicitConversions$.MODULE$.buffer$u0020AsJavaList(b);
    }

    public static <A> Collection<A> collection$u0020asJava(Iterable<A> it) {
        return ImplicitConversions$.MODULE$.collection$u0020asJava(it);
    }

    public static <A> java.lang.Iterable<A> iterable$u0020asJava(Iterable<A> i) {
        return ImplicitConversions$.MODULE$.iterable$u0020asJava(i);
    }

    public static <A> Enumeration<A> enumeration$u0020asJava(Iterator<A> it) {
        return ImplicitConversions$.MODULE$.enumeration$u0020asJava(it);
    }

    public static <A> java.util.Iterator<A> iterator$u0020asJava(Iterator<A> it) {
        return ImplicitConversions$.MODULE$.iterator$u0020asJava(it);
    }

    public static scala.collection.mutable.Map<String, String> properties$u0020AsScalaMap(Properties p) {
        return ImplicitConversions$.MODULE$.properties$u0020AsScalaMap(p);
    }

    public static <K, V> scala.collection.mutable.Map<K, V> dictionary$u0020AsScalaMap(Dictionary<K, V> p) {
        return ImplicitConversions$.MODULE$.dictionary$u0020AsScalaMap(p);
    }

    public static <K, V> scala.collection.concurrent.Map<K, V> map$u0020AsScalaConcurrentMap(ConcurrentMap<K, V> m4) {
        return ImplicitConversions$.MODULE$.map$u0020AsScalaConcurrentMap(m4);
    }

    public static <K, V> scala.collection.mutable.Map<K, V> map$u0020AsScala(java.util.Map<K, V> m4) {
        return ImplicitConversions$.MODULE$.map$u0020AsScala(m4);
    }

    public static <A> scala.collection.mutable.Set<A> set$u0020asScala(java.util.Set<A> s2) {
        return ImplicitConversions$.MODULE$.set$u0020asScala(s2);
    }

    public static <A> Buffer<A> list$u0020asScalaBuffer(List<A> l) {
        return ImplicitConversions$.MODULE$.list$u0020asScalaBuffer(l);
    }

    public static <A> Iterable<A> collection$u0020AsScalaIterable(Collection<A> i) {
        return ImplicitConversions$.MODULE$.collection$u0020AsScalaIterable(i);
    }

    public static <A> Iterable<A> iterable$u0020AsScalaIterable(java.lang.Iterable<A> i) {
        return ImplicitConversions$.MODULE$.iterable$u0020AsScalaIterable(i);
    }

    public static <A> Iterator<A> enumeration$u0020AsScalaIterator(Enumeration<A> i) {
        return ImplicitConversions$.MODULE$.enumeration$u0020AsScalaIterator(i);
    }

    public static <A> Iterator<A> iterator$u0020asScala(java.util.Iterator<A> it) {
        return ImplicitConversions$.MODULE$.iterator$u0020asScala(it);
    }
}

