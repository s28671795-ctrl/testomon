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
import scala.jdk.CollectionConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y9Qa\u0001\u0003\t\u0002%1Qa\u0003\u0003\t\u00021AQ\u0001H\u0001\u0005\u0002u\tAcQ8mY\u0016\u001cG/[8o\u0007>tg/\u001a:uKJ\u001c(BA\u0003\u0007\u0003\rQGm\u001b\u0006\u0002\u000f\u0005)1oY1mC\u000e\u0001\u0001C\u0001\u0006\u0002\u001b\u0005!!\u0001F\"pY2,7\r^5p]\u000e{gN^3si\u0016\u00148o\u0005\u0003\u0002\u001bEI\u0002C\u0001\b\u0010\u001b\u00051\u0011B\u0001\t\u0007\u0005\u0019\te.\u001f*fMB\u0011!cF\u0007\u0002')\u0011A#F\u0001\bG>tg/\u001a:u\u0015\t1b!\u0001\u0006d_2dWm\u0019;j_:L!\u0001G\n\u0003!\u0005\u001b(*\u0019<b\u000bb$XM\\:j_:\u001c\bC\u0001\n\u001b\u0013\tY2CA\tBgN\u001b\u0017\r\\1FqR,gn]5p]N\fa\u0001P5oSRtD#A\u0005")
public final class CollectionConverters {
    public static AsScalaExtensions.PropertiesHasAsScala PropertiesHasAsScala(Properties i) {
        return CollectionConverters$.MODULE$.PropertiesHasAsScala(i);
    }

    public static <K, V> AsScalaExtensions.DictionaryHasAsScala<K, V> DictionaryHasAsScala(Dictionary<K, V> d) {
        return CollectionConverters$.MODULE$.DictionaryHasAsScala(d);
    }

    public static <K, V> AsScalaExtensions.ConcurrentMapHasAsScala<K, V> ConcurrentMapHasAsScala(ConcurrentMap<K, V> m4) {
        return CollectionConverters$.MODULE$.ConcurrentMapHasAsScala(m4);
    }

    public static <K, V> AsScalaExtensions.MapHasAsScala<K, V> MapHasAsScala(java.util.Map<K, V> m4) {
        return CollectionConverters$.MODULE$.MapHasAsScala(m4);
    }

    public static <A> AsScalaExtensions.SetHasAsScala<A> SetHasAsScala(java.util.Set<A> s2) {
        return CollectionConverters$.MODULE$.SetHasAsScala(s2);
    }

    public static <A> AsScalaExtensions.ListHasAsScala<A> ListHasAsScala(List<A> l) {
        return CollectionConverters$.MODULE$.ListHasAsScala(l);
    }

    public static <A> AsScalaExtensions.CollectionHasAsScala<A> CollectionHasAsScala(Collection<A> c) {
        return CollectionConverters$.MODULE$.CollectionHasAsScala(c);
    }

    public static <A> AsScalaExtensions.IterableHasAsScala<A> IterableHasAsScala(java.lang.Iterable<A> i) {
        return CollectionConverters$.MODULE$.IterableHasAsScala(i);
    }

    public static <A> AsScalaExtensions.EnumerationHasAsScala<A> EnumerationHasAsScala(Enumeration<A> e) {
        return CollectionConverters$.MODULE$.EnumerationHasAsScala(e);
    }

    public static <A> AsScalaExtensions.IteratorHasAsScala<A> IteratorHasAsScala(java.util.Iterator<A> i) {
        return CollectionConverters$.MODULE$.IteratorHasAsScala(i);
    }

    public static <K, V> AsJavaExtensions.ConcurrentMapHasAsJava<K, V> ConcurrentMapHasAsJava(scala.collection.concurrent.Map<K, V> m4) {
        return CollectionConverters$.MODULE$.ConcurrentMapHasAsJava(m4);
    }

    public static <K, V> AsJavaExtensions.MapHasAsJava<K, V> MapHasAsJava(Map<K, V> m4) {
        return CollectionConverters$.MODULE$.MapHasAsJava(m4);
    }

    public static <K, V> AsJavaExtensions.MutableMapHasAsJava<K, V> MutableMapHasAsJava(scala.collection.mutable.Map<K, V> m4) {
        return CollectionConverters$.MODULE$.MutableMapHasAsJava(m4);
    }

    public static <A> AsJavaExtensions.SetHasAsJava<A> SetHasAsJava(Set<A> s2) {
        return CollectionConverters$.MODULE$.SetHasAsJava(s2);
    }

    public static <A> AsJavaExtensions.MutableSetHasAsJava<A> MutableSetHasAsJava(scala.collection.mutable.Set<A> s2) {
        return CollectionConverters$.MODULE$.MutableSetHasAsJava(s2);
    }

    public static <A> AsJavaExtensions.SeqHasAsJava<A> SeqHasAsJava(Seq<A> s2) {
        return CollectionConverters$.MODULE$.SeqHasAsJava(s2);
    }

    public static <A> AsJavaExtensions.MutableSeqHasAsJava<A> MutableSeqHasAsJava(scala.collection.mutable.Seq<A> s2) {
        return CollectionConverters$.MODULE$.MutableSeqHasAsJava(s2);
    }

    public static <A> AsJavaExtensions.BufferHasAsJava<A> BufferHasAsJava(Buffer<A> b) {
        return CollectionConverters$.MODULE$.BufferHasAsJava(b);
    }

    public static <A> AsJavaExtensions.IterableHasAsJava<A> IterableHasAsJava(Iterable<A> i) {
        return CollectionConverters$.MODULE$.IterableHasAsJava(i);
    }

    public static <A> AsJavaExtensions.IteratorHasAsJava<A> IteratorHasAsJava(Iterator<A> i) {
        return CollectionConverters$.MODULE$.IteratorHasAsJava(i);
    }
}

