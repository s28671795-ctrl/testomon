/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.JavaConverters$;
import scala.collection.Map;
import scala.collection.Seq;
import scala.collection.Set;
import scala.collection.convert.AsJavaConverters;
import scala.collection.mutable.Buffer;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]faB\b\u0011!\u0003\r\ta\u0006\u0005\u00069\u0001!\t!\b\u0005\u0006C\u0001!\u0019A\t\u0005\u0006y\u0001!\u0019!\u0010\u0005\u0006\r\u0002!\u0019a\u0012\u0005\u0006+\u0002!\u0019A\u0016\u0005\u0006?\u0002!\u0019\u0001\u0019\u0005\u0006_\u0002!\u0019\u0001\u001d\u0005\u0006u\u0002!\u0019a\u001f\u0005\b\u0003\u000f\u0001A1AA\u0005\u0011\u001d\ty\u0002\u0001C\u0002\u0003CAq!!\r\u0001\t\u0007\t\u0019\u0004C\u0004\u0002R\u0001!\u0019!a\u0015\t\u000f\u0005%\u0004\u0001b\u0001\u0002l!9\u0011q\u0010\u0001\u0005\u0004\u0005\u0005%a\u0004+p\u0015\u00064\u0018-S7qY&\u001c\u0017\u000e^:\u000b\u0005E\u0011\u0012aB2p]Z,'\u000f\u001e\u0006\u0003'Q\t!bY8mY\u0016\u001cG/[8o\u0015\u0005)\u0012!B:dC2\f7\u0001A\n\u0003\u0001a\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\ta\u0004\u0005\u0002\u001a?%\u0011\u0001\u0005\u0006\u0002\u0005+:LG/\u0001\u000bji\u0016\u0014\u0018\r^8sIU\u0004\u0004G\r\u0019bg*\u000bg/Y\u000b\u0003G9\"\"\u0001J\u001c\u0011\u0007\u0015RC&D\u0001'\u0015\t9\u0003&\u0001\u0003vi&d'\"A\u0015\u0002\t)\fg/Y\u0005\u0003W\u0019\u0012\u0001\"\u0013;fe\u0006$xN\u001d\t\u0003[9b\u0001\u0001B\u00030\u0005\t\u0007\u0001GA\u0001B#\t\tD\u0007\u0005\u0002\u001ae%\u00111\u0007\u0006\u0002\b\u001d>$\b.\u001b8h!\tIR'\u0003\u00027)\t\u0019\u0011I\\=\t\u000ba\u0012\u0001\u0019A\u001d\u0002\u0005%$\bc\u0001\u001e<Y5\t!#\u0003\u0002,%\u00059RM\\;nKJ\fG/[8oIU\u0004\u0004G\r\u0019bg*\u000bg/Y\u000b\u0003}\r#\"a\u0010#\u0011\u0007\u0015\u0002%)\u0003\u0002BM\tYQI\\;nKJ\fG/[8o!\ti3\tB\u00030\u0007\t\u0007\u0001\u0007C\u00039\u0007\u0001\u0007Q\tE\u0002;w\t\u000bA#\u001b;fe\u0006\u0014G.\u001a\u0013vaA\u0012\u0004'Y:KCZ\fWC\u0001%Q)\tI\u0015\u000bE\u0002K\u001b>k\u0011a\u0013\u0006\u0003\u0019\"\nA\u0001\\1oO&\u0011aj\u0013\u0002\t\u0013R,'/\u00192mKB\u0011Q\u0006\u0015\u0003\u0006_\u0011\u0011\r\u0001\r\u0005\u0006%\u0012\u0001\raU\u0001\u0002SB\u0019!\bV(\n\u00059\u0013\u0012AF2pY2,7\r^5p]\u0012*\b\u0007\r\u001a1CNT\u0015M^1\u0016\u0005]cFC\u0001-^!\r)\u0013lW\u0005\u00035\u001a\u0012!bQ8mY\u0016\u001cG/[8o!\tiC\fB\u00030\u000b\t\u0007\u0001\u0007C\u00039\u000b\u0001\u0007a\fE\u0002;)n\u000baCY;gM\u0016\u0014H%\u001e\u00191eA\n5OS1wC2K7\u000f^\u000b\u0003C\u001a$\"AY4\u0011\u0007\u0015\u001aW-\u0003\u0002eM\t!A*[:u!\tic\rB\u00030\r\t\u0007\u0001\u0007C\u0003i\r\u0001\u0007\u0011.A\u0001c!\rQW.Z\u0007\u0002W*\u0011ANE\u0001\b[V$\u0018M\u00197f\u0013\tq7N\u0001\u0004Ck\u001a4WM]\u0001\u001b[V$\u0018M\u00197f'\u0016\fH%\u001e\u00191eA\n5OS1wC2K7\u000f^\u000b\u0003cR$\"A];\u0011\u0007\u0015\u001a7\u000f\u0005\u0002.i\u0012)qf\u0002b\u0001a!)ao\u0002a\u0001o\u0006\u00191/Z9\u0011\u0007)D8/\u0003\u0002zW\n\u00191+Z9\u0002'M,\u0017\u000fJ;1aI\u0002\u0014i\u001d&bm\u0006d\u0015n\u001d;\u0016\u0005q|HcA?\u0002\u0002A\u0019Qe\u0019@\u0011\u00055zH!B\u0018\t\u0005\u0004\u0001\u0004B\u0002<\t\u0001\u0004\t\u0019\u0001\u0005\u0003;\u0003\u000bq\u0018BA=\u0013\u0003eiW\u000f^1cY\u0016\u001cV\r\u001e\u0013vaA\u0012\u0004'Q:KCZ\f7+\u001a;\u0016\t\u0005-\u0011Q\u0003\u000b\u0005\u0003\u001b\t9\u0002E\u0003&\u0003\u001f\t\u0019\"C\u0002\u0002\u0012\u0019\u00121aU3u!\ri\u0013Q\u0003\u0003\u0006_%\u0011\r\u0001\r\u0005\b\u00033I\u0001\u0019AA\u000e\u0003\u0005\u0019\b#\u00026\u0002\u001e\u0005M\u0011bAA\tW\u0006\u00112/\u001a;%kB\u0002$\u0007M!t\u0015\u00064\u0018mU3u+\u0011\t\u0019#!\u000b\u0015\t\u0005\u0015\u00121\u0006\t\u0006K\u0005=\u0011q\u0005\t\u0004[\u0005%B!B\u0018\u000b\u0005\u0004\u0001\u0004bBA\r\u0015\u0001\u0007\u0011Q\u0006\t\u0006u\u0005=\u0012qE\u0005\u0004\u0003#\u0011\u0012!G7vi\u0006\u0014G.Z'ba\u0012*\b\u0007\r\u001a1\u0003NT\u0015M^1NCB,b!!\u000e\u0002@\u0005\u0015C\u0003BA\u001c\u0003\u0013\u0002r!JA\u001d\u0003{\t\u0019%C\u0002\u0002<\u0019\u00121!T1q!\ri\u0013q\b\u0003\u0007\u0003\u0003Z!\u0019\u0001\u0019\u0003\u0003-\u00032!LA#\t\u0019\t9e\u0003b\u0001a\t\ta\u000bC\u0004\u0002L-\u0001\r!!\u0014\u0002\u00035\u0004rA[A(\u0003{\t\u0019%C\u0002\u0002<-\fa\u0003Z5di&|g.\u0019:zIU\u0004\u0004G\r\u0019bg*\u000bg/Y\u000b\u0007\u0003+\ny&a\u0019\u0015\t\u0005]\u0013Q\r\t\bK\u0005e\u0013QLA1\u0013\r\tYF\n\u0002\u000b\t&\u001cG/[8oCJL\bcA\u0017\u0002`\u00111\u0011\u0011\t\u0007C\u0002A\u00022!LA2\t\u0019\t9\u0005\u0004b\u0001a!9\u00111\n\u0007A\u0002\u0005\u001d\u0004c\u00026\u0002P\u0005u\u0013\u0011M\u0001\u0013[\u0006\u0004H%\u001e\u00191eA\n5OS1wC6\u000b\u0007/\u0006\u0004\u0002n\u0005M\u0014q\u000f\u000b\u0005\u0003_\nI\bE\u0004&\u0003s\t\t(!\u001e\u0011\u00075\n\u0019\b\u0002\u0004\u0002B5\u0011\r\u0001\r\t\u0004[\u0005]DABA$\u001b\t\u0007\u0001\u0007C\u0004\u0002L5\u0001\r!a\u001f\u0011\u000fi\ni(!\u001d\u0002v%\u0019\u00111\b\n\u000295\f\u0007\u000fJ;1aI\u0002\u0014i\u001d&bm\u0006\u001cuN\\2veJ,g\u000e^'baV1\u00111QAJ\u0003/#B!!\"\u0002\u001aBA\u0011qQAG\u0003#\u000b)*\u0004\u0002\u0002\n*\u0019\u00111\u0012\u0014\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0002\u0010\u0006%%!D\"p]\u000e,(O]3oi6\u000b\u0007\u000fE\u0002.\u0003'#a!!\u0011\u000f\u0005\u0004\u0001\u0004cA\u0017\u0002\u0018\u00121\u0011q\t\bC\u0002ABq!a\u0013\u000f\u0001\u0004\tY\n\u0005\u0005\u0002\u001e\u0006\u0005\u0016\u0011SAK\u001b\t\tyJC\u0002\u0002\fJIA!a\u000f\u0002 \"Z\u0001!!*\u0002,\u00065\u0016\u0011WAZ!\rI\u0012qU\u0005\u0004\u0003S#\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017EAAX\u00031*6/\u001a\u0011ag\u000e\fG.\u0019\u0018kI.t3i\u001c7mK\u000e$\u0018n\u001c8D_:4XM\u001d;feN\u0004\u0007%\u001b8ti\u0016\fG-A\u0003tS:\u001cW-\t\u0002\u00026\u00061!GL\u00194]A\u0002")
public interface ToJavaImplicits {
    public static /* synthetic */ java.util.Iterator iterator$u0020asJava$(ToJavaImplicits $this, Iterator it) {
        return $this.iterator$u0020asJava(it);
    }

    default public <A> java.util.Iterator<A> iterator$u0020asJava(Iterator<A> it) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, it);
    }

    public static /* synthetic */ Enumeration enumeration$u0020asJava$(ToJavaImplicits $this, Iterator it) {
        return $this.enumeration$u0020asJava(it);
    }

    default public <A> Enumeration<A> enumeration$u0020asJava(Iterator<A> it) {
        return AsJavaConverters.asJavaEnumeration$(JavaConverters$.MODULE$, it);
    }

    public static /* synthetic */ java.lang.Iterable iterable$u0020asJava$(ToJavaImplicits $this, Iterable i) {
        return $this.iterable$u0020asJava(i);
    }

    default public <A> java.lang.Iterable<A> iterable$u0020asJava(Iterable<A> i) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, i);
    }

    public static /* synthetic */ Collection collection$u0020asJava$(ToJavaImplicits $this, Iterable it) {
        return $this.collection$u0020asJava(it);
    }

    default public <A> Collection<A> collection$u0020asJava(Iterable<A> it) {
        return AsJavaConverters.asJavaCollection$(JavaConverters$.MODULE$, it);
    }

    public static /* synthetic */ List buffer$u0020AsJavaList$(ToJavaImplicits $this, Buffer b) {
        return $this.buffer$u0020AsJavaList(b);
    }

    default public <A> List<A> buffer$u0020AsJavaList(Buffer<A> b) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, b);
    }

    public static /* synthetic */ List mutableSeq$u0020AsJavaList$(ToJavaImplicits $this, scala.collection.mutable.Seq seq) {
        return $this.mutableSeq$u0020AsJavaList(seq);
    }

    default public <A> List<A> mutableSeq$u0020AsJavaList(scala.collection.mutable.Seq<A> seq) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, seq);
    }

    public static /* synthetic */ List seq$u0020AsJavaList$(ToJavaImplicits $this, Seq seq) {
        return $this.seq$u0020AsJavaList(seq);
    }

    default public <A> List<A> seq$u0020AsJavaList(Seq<A> seq) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, seq);
    }

    public static /* synthetic */ java.util.Set mutableSet$u0020AsJavaSet$(ToJavaImplicits $this, scala.collection.mutable.Set s2) {
        return $this.mutableSet$u0020AsJavaSet(s2);
    }

    default public <A> java.util.Set<A> mutableSet$u0020AsJavaSet(scala.collection.mutable.Set<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, s2);
    }

    public static /* synthetic */ java.util.Set set$u0020AsJavaSet$(ToJavaImplicits $this, Set s2) {
        return $this.set$u0020AsJavaSet(s2);
    }

    default public <A> java.util.Set<A> set$u0020AsJavaSet(Set<A> s2) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, s2);
    }

    public static /* synthetic */ java.util.Map mutableMap$u0020AsJavaMap$(ToJavaImplicits $this, scala.collection.mutable.Map m4) {
        return $this.mutableMap$u0020AsJavaMap(m4);
    }

    default public <K, V> java.util.Map<K, V> mutableMap$u0020AsJavaMap(scala.collection.mutable.Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, m4);
    }

    public static /* synthetic */ Dictionary dictionary$u0020asJava$(ToJavaImplicits $this, scala.collection.mutable.Map m4) {
        return $this.dictionary$u0020asJava(m4);
    }

    default public <K, V> Dictionary<K, V> dictionary$u0020asJava(scala.collection.mutable.Map<K, V> m4) {
        return AsJavaConverters.asJavaDictionary$(JavaConverters$.MODULE$, m4);
    }

    public static /* synthetic */ java.util.Map map$u0020AsJavaMap$(ToJavaImplicits $this, Map m4) {
        return $this.map$u0020AsJavaMap(m4);
    }

    default public <K, V> java.util.Map<K, V> map$u0020AsJavaMap(Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, m4);
    }

    public static /* synthetic */ ConcurrentMap map$u0020AsJavaConcurrentMap$(ToJavaImplicits $this, scala.collection.concurrent.Map m4) {
        return $this.map$u0020AsJavaConcurrentMap(m4);
    }

    default public <K, V> ConcurrentMap<K, V> map$u0020AsJavaConcurrentMap(scala.collection.concurrent.Map<K, V> m4) {
        return AsJavaConverters.asJava$((AsJavaConverters)JavaConverters$.MODULE$, m4);
    }

    public static void $init$(ToJavaImplicits $this) {
    }
}

