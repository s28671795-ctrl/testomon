/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.convert.JavaCollectionWrappers;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Map;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015da\u0002\u0007\u000e!\u0003\r\t\u0001\u0006\u0005\u00063\u0001!\tA\u0007\u0005\u0006=\u0001!\ta\b\u0005\u0006=\u0001!\t!\u000f\u0005\u0006=\u0001!\ta\u0011\u0005\u0006=\u0001!\t\u0001\u0015\u0005\u0006=\u0001!\tA\u0017\u0005\u0006=\u0001!\t!\u001b\u0005\u0006=\u0001!\t\u0001\u001e\u0005\u0007=\u0001!\t!a\u0002\t\ry\u0001A\u0011AA\u0015\u0011\u0019q\u0002\u0001\"\u0001\u0002B\t\t\u0012i]*dC2\f7i\u001c8wKJ$XM]:\u000b\u00059y\u0011aB2p]Z,'\u000f\u001e\u0006\u0003!E\t!bY8mY\u0016\u001cG/[8o\u0015\u0005\u0011\u0012!B:dC2\f7\u0001A\n\u0003\u0001U\u0001\"AF\f\u000e\u0003EI!\u0001G\t\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t1\u0004\u0005\u0002\u00179%\u0011Q$\u0005\u0002\u0005+:LG/A\u0004bgN\u001b\u0017\r\\1\u0016\u0005\u0001:CCA\u00111!\r\u00113%J\u0007\u0002\u001f%\u0011Ae\u0004\u0002\t\u0013R,'/\u0019;peB\u0011ae\n\u0007\u0001\t\u0015A#A1\u0001*\u0005\u0005\t\u0015C\u0001\u0016.!\t12&\u0003\u0002-#\t9aj\u001c;iS:<\u0007C\u0001\f/\u0013\ty\u0013CA\u0002B]fDQ!\r\u0002A\u0002I\n\u0011!\u001b\t\u0004ga*S\"\u0001\u001b\u000b\u0005U2\u0014\u0001B;uS2T\u0011aN\u0001\u0005U\u00064\u0018-\u0003\u0002%iU\u0011!(\u0010\u000b\u0003wy\u00022AI\u0012=!\t1S\bB\u0003)\u0007\t\u0007\u0011\u0006C\u0003@\u0007\u0001\u0007\u0001)A\u0001f!\r\u0019\u0014\tP\u0005\u0003\u0005R\u00121\"\u00128v[\u0016\u0014\u0018\r^5p]V\u0011A)\u0013\u000b\u0003\u000b*\u00032A\t$I\u0013\t9uB\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\t1\u0013\nB\u0003)\t\t\u0007\u0011\u0006C\u00032\t\u0001\u00071\nE\u0002M\u001f\"k\u0011!\u0014\u0006\u0003\u001dZ\nA\u0001\\1oO&\u0011q)T\u000b\u0003#R#\"AU+\u0011\u0007\t25\u000b\u0005\u0002')\u0012)\u0001&\u0002b\u0001S!)a+\u0002a\u0001/\u0006\t1\rE\u000241NK!!\u0017\u001b\u0003\u0015\r{G\u000e\\3di&|g.\u0006\u0002\\GR\u0011A\f\u001a\t\u0004;\u0002\u0014W\"\u00010\u000b\u0005}{\u0011aB7vi\u0006\u0014G.Z\u0005\u0003Cz\u0013aAQ;gM\u0016\u0014\bC\u0001\u0014d\t\u0015AcA1\u0001*\u0011\u0015)g\u00011\u0001g\u0003\u0005a\u0007cA\u001ahE&\u0011\u0001\u000e\u000e\u0002\u0005\u0019&\u001cH/\u0006\u0002k_R\u00111\u000e\u001d\t\u0004;2t\u0017BA7_\u0005\r\u0019V\r\u001e\t\u0003M=$Q\u0001K\u0004C\u0002%BQ!]\u0004A\u0002I\f\u0011a\u001d\t\u0004gMt\u0017BA75+\r)(0 \u000b\u0003m~\u0004B!X<zy&\u0011\u0001P\u0018\u0002\u0004\u001b\u0006\u0004\bC\u0001\u0014{\t\u0015Y\bB1\u0001*\u0005\u0005Y\u0005C\u0001\u0014~\t\u0015q\bB1\u0001*\u0005\u00051\u0006bBA\u0001\u0011\u0001\u0007\u00111A\u0001\u0002[B)1'!\u0002zy&\u0011\u0001\u0010N\u000b\u0007\u0003\u0013\t9\"a\u0007\u0015\t\u0005-\u0011Q\u0004\t\t\u0003\u001b\t\u0019\"!\u0006\u0002\u001a5\u0011\u0011q\u0002\u0006\u0004\u0003#y\u0011AC2p]\u000e,(O]3oi&\u0019\u00010a\u0004\u0011\u0007\u0019\n9\u0002B\u0003|\u0013\t\u0007\u0011\u0006E\u0002'\u00037!QA`\u0005C\u0002%Bq!!\u0001\n\u0001\u0004\ty\u0002\u0005\u0005\u0002\"\u0005\u0015\u0012QCA\r\u001b\t\t\u0019CC\u0002\u0002\u0012QJA!a\n\u0002$\ti1i\u001c8dkJ\u0014XM\u001c;NCB,b!a\u000b\u00022\u0005UB\u0003BA\u0017\u0003o\u0001b!X<\u00020\u0005M\u0002c\u0001\u0014\u00022\u0011)1P\u0003b\u0001SA\u0019a%!\u000e\u0005\u000byT!\u0019A\u0015\t\u000f\u0005e\"\u00021\u0001\u0002<\u0005\tA\rE\u00044\u0003{\ty#a\r\n\u0007\u0005}BG\u0001\u0006ES\u000e$\u0018n\u001c8bef$B!a\u0011\u0002\\A1Ql^A#\u0003\u000b\u0002B!a\u0012\u0002V9!\u0011\u0011JA)!\r\tY%E\u0007\u0003\u0003\u001bR1!a\u0014\u0014\u0003\u0019a$o\\8u}%\u0019\u00111K\t\u0002\rA\u0013X\rZ3g\u0013\u0011\t9&!\u0017\u0003\rM#(/\u001b8h\u0015\r\t\u0019&\u0005\u0005\b\u0003;Z\u0001\u0019AA0\u0003\u0005\u0001\bcA\u001a\u0002b%\u0019\u00111\r\u001b\u0003\u0015A\u0013x\u000e]3si&,7\u000f")
public interface AsScalaConverters {
    public static /* synthetic */ Iterator asScala$(AsScalaConverters $this, java.util.Iterator i) {
        return $this.asScala(i);
    }

    default public <A> Iterator<A> asScala(java.util.Iterator<A> i) {
        if (i == null) {
            return null;
        }
        if (i instanceof JavaCollectionWrappers.IteratorWrapper) {
            return ((JavaCollectionWrappers.IteratorWrapper)i).underlying();
        }
        return new JavaCollectionWrappers.JIteratorWrapper<A>(i);
    }

    public static /* synthetic */ Iterator asScala$(AsScalaConverters $this, Enumeration e) {
        return $this.asScala(e);
    }

    default public <A> Iterator<A> asScala(Enumeration<A> e) {
        if (e == null) {
            return null;
        }
        if (e instanceof JavaCollectionWrappers.IteratorWrapper) {
            return ((JavaCollectionWrappers.IteratorWrapper)e).underlying();
        }
        return new JavaCollectionWrappers.JEnumerationWrapper<A>(e);
    }

    public static /* synthetic */ Iterable asScala$(AsScalaConverters $this, java.lang.Iterable i) {
        return $this.asScala(i);
    }

    default public <A> Iterable<A> asScala(java.lang.Iterable<A> i) {
        if (i == null) {
            return null;
        }
        if (i instanceof JavaCollectionWrappers.IterableWrapper) {
            return ((JavaCollectionWrappers.IterableWrapper)i).underlying();
        }
        return new JavaCollectionWrappers.JIterableWrapper<A>(i);
    }

    public static /* synthetic */ Iterable asScala$(AsScalaConverters $this, Collection c) {
        return $this.asScala(c);
    }

    default public <A> Iterable<A> asScala(Collection<A> c) {
        if (c == null) {
            return null;
        }
        if (c instanceof JavaCollectionWrappers.IterableWrapper) {
            return ((JavaCollectionWrappers.IterableWrapper)c).underlying();
        }
        return new JavaCollectionWrappers.JCollectionWrapper<A>(c);
    }

    public static /* synthetic */ Buffer asScala$(AsScalaConverters $this, List l) {
        return $this.asScala(l);
    }

    default public <A> Buffer<A> asScala(List<A> l) {
        if (l == null) {
            return null;
        }
        if (l instanceof JavaCollectionWrappers.MutableBufferWrapper) {
            return ((JavaCollectionWrappers.MutableBufferWrapper)l).underlying();
        }
        return new JavaCollectionWrappers.JListWrapper<A>(l);
    }

    public static /* synthetic */ scala.collection.mutable.Set asScala$(AsScalaConverters $this, Set s2) {
        return $this.asScala(s2);
    }

    default public <A> scala.collection.mutable.Set<A> asScala(Set<A> s2) {
        if (s2 == null) {
            return null;
        }
        if (s2 instanceof JavaCollectionWrappers.MutableSetWrapper) {
            return ((JavaCollectionWrappers.MutableSetWrapper)s2).underlying();
        }
        return new JavaCollectionWrappers.JSetWrapper<A>(s2);
    }

    public static /* synthetic */ Map asScala$(AsScalaConverters $this, java.util.Map m4) {
        return $this.asScala(m4);
    }

    default public <K, V> Map<K, V> asScala(java.util.Map<K, V> m4) {
        if (m4 == null) {
            return null;
        }
        if (m4 instanceof JavaCollectionWrappers.MutableMapWrapper) {
            return ((JavaCollectionWrappers.MutableMapWrapper)m4).underlying();
        }
        return new JavaCollectionWrappers.JMapWrapper<K, V>(m4);
    }

    public static /* synthetic */ scala.collection.concurrent.Map asScala$(AsScalaConverters $this, ConcurrentMap m4) {
        return $this.asScala(m4);
    }

    default public <K, V> scala.collection.concurrent.Map<K, V> asScala(ConcurrentMap<K, V> m4) {
        if (m4 == null) {
            return null;
        }
        if (m4 instanceof JavaCollectionWrappers.ConcurrentMapWrapper) {
            return ((JavaCollectionWrappers.ConcurrentMapWrapper)m4).underlyingConcurrentMap();
        }
        return new JavaCollectionWrappers.JConcurrentMapWrapper<K, V>(m4);
    }

    public static /* synthetic */ Map asScala$(AsScalaConverters $this, Dictionary d) {
        return $this.asScala(d);
    }

    default public <K, V> Map<K, V> asScala(Dictionary<K, V> d) {
        if (d == null) {
            return null;
        }
        if (d instanceof JavaCollectionWrappers.DictionaryWrapper) {
            return ((JavaCollectionWrappers.DictionaryWrapper)d).underlying();
        }
        return new JavaCollectionWrappers.JDictionaryWrapper<K, V>(d);
    }

    public static /* synthetic */ Map asScala$(AsScalaConverters $this, Properties p) {
        return $this.asScala(p);
    }

    default public Map<String, String> asScala(Properties p) {
        if (p == null) {
            return null;
        }
        return new JavaCollectionWrappers.JPropertiesWrapper(p);
    }

    public static void $init$(AsScalaConverters $this) {
    }
}

