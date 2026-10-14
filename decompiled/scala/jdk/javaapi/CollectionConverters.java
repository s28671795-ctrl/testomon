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
import scala.collection.mutable.Buffer;
import scala.jdk.javaapi.CollectionConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0001:Qa\u0001\u0003\t\u0002-1Q!\u0004\u0003\t\u00029AQAH\u0001\u0005\u0002}\tAcQ8mY\u0016\u001cG/[8o\u0007>tg/\u001a:uKJ\u001c(BA\u0003\u0007\u0003\u001dQ\u0017M^1ba&T!a\u0002\u0005\u0002\u0007)$7NC\u0001\n\u0003\u0015\u00198-\u00197b\u0007\u0001\u0001\"\u0001D\u0001\u000e\u0003\u0011\u0011AcQ8mY\u0016\u001cG/[8o\u0007>tg/\u001a:uKJ\u001c8\u0003B\u0001\u0010'm\u0001\"\u0001E\t\u000e\u0003!I!A\u0005\u0005\u0003\r\u0005s\u0017PU3g!\t!\u0012$D\u0001\u0016\u0015\t1r#A\u0004d_:4XM\u001d;\u000b\u0005aA\u0011AC2pY2,7\r^5p]&\u0011!$\u0006\u0002\u0011\u0003NT\u0015M^1D_:4XM\u001d;feN\u0004\"\u0001\u0006\u000f\n\u0005u)\"!E!t'\u000e\fG.Y\"p]Z,'\u000f^3sg\u00061A(\u001b8jiz\"\u0012a\u0003")
public final class CollectionConverters {
    public static scala.collection.mutable.Map<String, String> asScala(Properties p) {
        return CollectionConverters$.MODULE$.asScala(p);
    }

    public static <K, V> scala.collection.mutable.Map<K, V> asScala(Dictionary<K, V> d) {
        return CollectionConverters$.MODULE$.asScala(d);
    }

    public static <K, V> scala.collection.concurrent.Map<K, V> asScala(ConcurrentMap<K, V> m4) {
        return CollectionConverters$.MODULE$.asScala(m4);
    }

    public static <K, V> scala.collection.mutable.Map<K, V> asScala(java.util.Map<K, V> m4) {
        return CollectionConverters$.MODULE$.asScala(m4);
    }

    public static <A> scala.collection.mutable.Set<A> asScala(java.util.Set<A> s2) {
        return CollectionConverters$.MODULE$.asScala(s2);
    }

    public static <A> Buffer<A> asScala(List<A> l) {
        return CollectionConverters$.MODULE$.asScala(l);
    }

    public static <A> Iterable<A> asScala(Collection<A> c) {
        return CollectionConverters$.MODULE$.asScala(c);
    }

    public static <A> Iterable<A> asScala(java.lang.Iterable<A> i) {
        return CollectionConverters$.MODULE$.asScala(i);
    }

    public static <A> Iterator<A> asScala(Enumeration<A> e) {
        return CollectionConverters$.MODULE$.asScala(e);
    }

    public static <A> Iterator<A> asScala(java.util.Iterator<A> i) {
        return CollectionConverters$.MODULE$.asScala(i);
    }

    public static <K, V> ConcurrentMap<K, V> asJava(scala.collection.concurrent.Map<K, V> m4) {
        return CollectionConverters$.MODULE$.asJava(m4);
    }

    public static <K, V> java.util.Map<K, V> asJava(Map<K, V> m4) {
        return CollectionConverters$.MODULE$.asJava(m4);
    }

    public static <K, V> Dictionary<K, V> asJavaDictionary(scala.collection.mutable.Map<K, V> m4) {
        return CollectionConverters$.MODULE$.asJavaDictionary(m4);
    }

    public static <K, V> java.util.Map<K, V> asJava(scala.collection.mutable.Map<K, V> m4) {
        return CollectionConverters$.MODULE$.asJava(m4);
    }

    public static <A> java.util.Set<A> asJava(Set<A> s2) {
        return CollectionConverters$.MODULE$.asJava(s2);
    }

    public static <A> java.util.Set<A> asJava(scala.collection.mutable.Set<A> s2) {
        return CollectionConverters$.MODULE$.asJava(s2);
    }

    public static <A> List<A> asJava(Seq<A> s2) {
        return CollectionConverters$.MODULE$.asJava(s2);
    }

    public static <A> List<A> asJava(scala.collection.mutable.Seq<A> s2) {
        return CollectionConverters$.MODULE$.asJava(s2);
    }

    public static <A> List<A> asJava(Buffer<A> b) {
        return CollectionConverters$.MODULE$.asJava(b);
    }

    public static <A> Collection<A> asJavaCollection(Iterable<A> i) {
        return CollectionConverters$.MODULE$.asJavaCollection(i);
    }

    public static <A> java.lang.Iterable<A> asJava(Iterable<A> i) {
        return CollectionConverters$.MODULE$.asJava(i);
    }

    public static <A> Enumeration<A> asJavaEnumeration(Iterator<A> i) {
        return CollectionConverters$.MODULE$.asJavaEnumeration(i);
    }

    public static <A> java.util.Iterator<A> asJava(Iterator<A> i) {
        return CollectionConverters$.MODULE$.asJava(i);
    }
}

