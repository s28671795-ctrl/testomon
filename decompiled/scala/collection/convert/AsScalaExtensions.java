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
import scala.collection.convert.AsScalaConverters;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Map;
import scala.jdk.javaapi.CollectionConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t%fa\u0002\u001b6!\u0003\r\t\u0001\u0010\u0005\u0006\u0003\u0002!\tA\u0011\u0004\u0005\r\u0002\tq\t\u0003\u0005J\u0005\t\u0005\t\u0015!\u0003K\u0011\u0015i&\u0001\"\u0001_\u0011\u0015\u0011'\u0001\"\u0001d\u0011\u001d9\u0007!!A\u0005\u0004!4Aa\u001c\u0001\u0002a\"A!o\u0002B\u0001B\u0003%1\u000fC\u0003^\u000f\u0011\u0005\u0001\u0010C\u0003c\u000f\u0011\u00051\u0010C\u0004~\u0001\u0005\u0005I1\u0001@\u0007\r\u0005-\u0001!AA\u0007\u0011%IEB!A!\u0002\u0013\t\t\u0002\u0003\u0004^\u0019\u0011\u0005\u0011\u0011\u0005\u0005\u0007E2!\t!a\n\t\u0013\u00055\u0002!!A\u0005\u0004\u0005=bABA\u001f\u0001\u0005\ty\u0004\u0003\u0006\u0002DE\u0011\t\u0011)A\u0005\u0003\u000bBa!X\t\u0005\u0002\u0005=\u0003B\u00022\u0012\t\u0003\t)\u0006C\u0005\u0002Z\u0001\t\t\u0011b\u0001\u0002\\\u00191\u0011\u0011\u000e\u0001\u0002\u0003WB!\"a\u001c\u0017\u0005\u0003\u0005\u000b\u0011BA9\u0011\u0019if\u0003\"\u0001\u0002|!1!M\u0006C\u0001\u0003\u0003C\u0011\"a$\u0001\u0003\u0003%\u0019!!%\u0007\r\u0005}\u0005!AAQ\u0011)\t)k\u0007B\u0001B\u0003%\u0011q\u0015\u0005\u0007;n!\t!!-\t\r\t\\B\u0011AA\\\u0011%\ti\fAA\u0001\n\u0007\tyL\u0002\u0004\u0002N\u0002\t\u0011q\u001a\u0005\u000b\u0003'\u0004#\u0011!Q\u0001\n\u0005U\u0007BB/!\t\u0003\t9\u000f\u0003\u0004cA\u0011\u0005\u0011Q\u001e\u0005\n\u0003g\u0004\u0011\u0011!C\u0002\u0003k4aAa\u0002\u0001\u0003\t%\u0001BCAjK\t\u0005\t\u0015!\u0003\u0003\u000e!1Q,\nC\u0001\u0005CAaAY\u0013\u0005\u0002\t\u001d\u0002\"\u0003B\u0019\u0001\u0005\u0005I1\u0001B\u001a\r\u0019\u0011)\u0005A\u0001\u0003H!Q!1\n\u0016\u0003\u0002\u0003\u0006IA!\u0014\t\ruSC\u0011\u0001B.\u0011\u0019\u0011'\u0006\"\u0001\u0003b!I!Q\r\u0001\u0002\u0002\u0013\r!q\r\u0004\u0007\u0005s\u0002\u0011Aa\u001f\t\u0013%{#\u0011!Q\u0001\n\tu\u0004BB/0\t\u0003\u0011\u0019\t\u0003\u0004c_\u0011\u0005!\u0011\u0012\u0005\n\u0005G\u0003\u0011\u0011!C\u0002\u0005K\u0013\u0011#Q:TG\u0006d\u0017-\u0012=uK:\u001c\u0018n\u001c8t\u0015\t1t'A\u0004d_:4XM\u001d;\u000b\u0005aJ\u0014AC2pY2,7\r^5p]*\t!(A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001i\u0004C\u0001 @\u001b\u0005I\u0014B\u0001!:\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012a\u0011\t\u0003}\u0011K!!R\u001d\u0003\tUs\u0017\u000e\u001e\u0002\u0013\u0013R,'/\u0019;pe\"\u000b7/Q:TG\u0006d\u0017-\u0006\u0002I)N\u0011!!P\u0001\u0002SB\u00191\n\u0015*\u000e\u00031S!!\u0014(\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u001f\u0006!!.\u0019<b\u0013\t\tFJ\u0001\u0005Ji\u0016\u0014\u0018\r^8s!\t\u0019F\u000b\u0004\u0001\u0005\u000bU\u0013!\u0019\u0001,\u0003\u0003\u0005\u000b\"a\u0016.\u0011\u0005yB\u0016BA-:\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"AP.\n\u0005qK$aA!os\u00061A(\u001b8jiz\"\"aX1\u0011\u0007\u0001\u0014!+D\u0001\u0001\u0011\u0015IE\u00011\u0001K\u0003\u001d\t7oU2bY\u0006,\u0012\u0001\u001a\t\u0004K\u001a\u0014V\"A\u001c\n\u0005E;\u0014AE%uKJ\fGo\u001c:ICN\f5oU2bY\u0006,\"!\u001b7\u0015\u0005)l\u0007c\u00011\u0003WB\u00111\u000b\u001c\u0003\u0006+\u001a\u0011\rA\u0016\u0005\u0006\u0013\u001a\u0001\rA\u001c\t\u0004\u0017B['!F#ok6,'/\u0019;j_:D\u0015m]!t'\u000e\fG.Y\u000b\u0003c^\u001c\"aB\u001f\u0002\u0003\u0015\u00042a\u0013;w\u0013\t)HJA\u0006F]VlWM]1uS>t\u0007CA*x\t\u0015)vA1\u0001W)\tI(\u0010E\u0002a\u000fYDQA]\u0005A\u0002M,\u0012\u0001 \t\u0004K\u001a4\u0018!F#ok6,'/\u0019;j_:D\u0015m]!t'\u000e\fG.Y\u000b\u0004\u007f\u0006\u0015A\u0003BA\u0001\u0003\u000f\u0001B\u0001Y\u0004\u0002\u0004A\u00191+!\u0002\u0005\u000bU[!\u0019\u0001,\t\rI\\\u0001\u0019AA\u0005!\u0011YE/a\u0001\u0003%%#XM]1cY\u0016D\u0015m]!t'\u000e\fG.Y\u000b\u0005\u0003\u001f\tyb\u0005\u0002\r{A1\u00111CA\r\u0003;i!!!\u0006\u000b\u0007\u0005]a*\u0001\u0003mC:<\u0017\u0002BA\u000e\u0003+\u0011\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0004'\u0006}A!B+\r\u0005\u00041F\u0003BA\u0012\u0003K\u0001B\u0001\u0019\u0007\u0002\u001e!1\u0011J\u0004a\u0001\u0003#)\"!!\u000b\u0011\u000b\u0015\fY#!\b\n\u0007\u0005mq'\u0001\nJi\u0016\u0014\u0018M\u00197f\u0011\u0006\u001c\u0018i]*dC2\fW\u0003BA\u0019\u0003o!B!a\r\u0002:A!\u0001\rDA\u001b!\r\u0019\u0016q\u0007\u0003\u0006+B\u0011\rA\u0016\u0005\u0007\u0013B\u0001\r!a\u000f\u0011\r\u0005M\u0011\u0011DA\u001b\u0005Q\u0019u\u000e\u001c7fGRLwN\u001c%bg\u0006\u001b8kY1mCV!\u0011\u0011IA''\t\tR(A\u0001d!\u0015Y\u0015qIA&\u0013\r\tI\u0005\u0014\u0002\u000b\u0007>dG.Z2uS>t\u0007cA*\u0002N\u0011)Q+\u0005b\u0001-R!\u0011\u0011KA*!\u0011\u0001\u0017#a\u0013\t\u000f\u0005\r3\u00031\u0001\u0002FU\u0011\u0011q\u000b\t\u0006K\u0006-\u00121J\u0001\u0015\u0007>dG.Z2uS>t\u0007*Y:BgN\u001b\u0017\r\\1\u0016\t\u0005u\u00131\r\u000b\u0005\u0003?\n)\u0007\u0005\u0003a#\u0005\u0005\u0004cA*\u0002d\u0011)Q+\u0006b\u0001-\"9\u00111I\u000bA\u0002\u0005\u001d\u0004#B&\u0002H\u0005\u0005$A\u0004'jgRD\u0015m]!t'\u000e\fG.Y\u000b\u0005\u0003[\nIh\u0005\u0002\u0017{\u0005\tA\u000eE\u0003L\u0003g\n9(C\u0002\u0002v1\u0013A\u0001T5tiB\u00191+!\u001f\u0005\u000bU3\"\u0019\u0001,\u0015\t\u0005u\u0014q\u0010\t\u0005AZ\t9\bC\u0004\u0002pa\u0001\r!!\u001d\u0016\u0005\u0005\r\u0005CBAC\u0003\u0017\u000b9(\u0004\u0002\u0002\b*\u0019\u0011\u0011R\u001c\u0002\u000f5,H/\u00192mK&!\u0011QRAD\u0005\u0019\u0011UO\u001a4fe\u0006qA*[:u\u0011\u0006\u001c\u0018i]*dC2\fW\u0003BAJ\u00033#B!!&\u0002\u001cB!\u0001MFAL!\r\u0019\u0016\u0011\u0014\u0003\u0006+j\u0011\rA\u0016\u0005\b\u0003_R\u0002\u0019AAO!\u0015Y\u00151OAL\u00055\u0019V\r\u001e%bg\u0006\u001b8kY1mCV!\u00111UAX'\tYR(A\u0001t!\u0015Y\u0015\u0011VAW\u0013\r\tY\u000b\u0014\u0002\u0004'\u0016$\bcA*\u00020\u0012)Qk\u0007b\u0001-R!\u00111WA[!\u0011\u00017$!,\t\u000f\u0005\u0015V\u00041\u0001\u0002(V\u0011\u0011\u0011\u0018\t\u0007\u0003\u000b\u000bY,!,\n\t\u0005-\u0016qQ\u0001\u000e'\u0016$\b*Y:BgN\u001b\u0017\r\\1\u0016\t\u0005\u0005\u0017q\u0019\u000b\u0005\u0003\u0007\fI\r\u0005\u0003a7\u0005\u0015\u0007cA*\u0002H\u0012)Qk\bb\u0001-\"9\u0011QU\u0010A\u0002\u0005-\u0007#B&\u0002*\u0006\u0015'!D'ba\"\u000b7/Q:TG\u0006d\u0017-\u0006\u0004\u0002R\u0006u\u00171]\n\u0003Au\n\u0011!\u001c\t\b\u0017\u0006]\u00171\\Aq\u0013\r\tI\u000e\u0014\u0002\u0004\u001b\u0006\u0004\bcA*\u0002^\u00121\u0011q\u001c\u0011C\u0002Y\u0013\u0011a\u0013\t\u0004'\u0006\rHABAsA\t\u0007aKA\u0001W)\u0011\tI/a;\u0011\r\u0001\u0004\u00131\\Aq\u0011\u001d\t\u0019N\ta\u0001\u0003+,\"!a<\u0011\u0011\u0005\u0015\u0015\u0011_An\u0003CLA!!7\u0002\b\u0006iQ*\u00199ICN\f5oU2bY\u0006,b!a>\u0002~\n\u0005A\u0003BA}\u0005\u0007\u0001b\u0001\u0019\u0011\u0002|\u0006}\bcA*\u0002~\u00121\u0011q\u001c\u0013C\u0002Y\u00032a\u0015B\u0001\t\u0019\t)\u000f\nb\u0001-\"9\u00111\u001b\u0013A\u0002\t\u0015\u0001cB&\u0002X\u0006m\u0018q \u0002\u0018\u0007>t7-\u001e:sK:$X*\u00199ICN\f5oU2bY\u0006,bAa\u0003\u0003\u001c\t}1CA\u0013>!!\u0011yA!\u0006\u0003\u001a\tuQB\u0001B\t\u0015\r\u0011\u0019\u0002T\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002\u0002B\f\u0005#\u0011QbQ8oGV\u0014(/\u001a8u\u001b\u0006\u0004\bcA*\u0003\u001c\u00111\u0011q\\\u0013C\u0002Y\u00032a\u0015B\u0010\t\u0019\t)/\nb\u0001-R!!1\u0005B\u0013!\u0019\u0001WE!\u0007\u0003\u001e!9\u00111[\u0014A\u0002\t5QC\u0001B\u0015!!\u0011YCa\f\u0003\u001a\tuQB\u0001B\u0017\u0015\r\u0011\u0019bN\u0005\u0005\u00033\u0014i#A\fD_:\u001cWO\u001d:f]Rl\u0015\r\u001d%bg\u0006\u001b8kY1mCV1!Q\u0007B\u001e\u0005\u007f!BAa\u000e\u0003BA1\u0001-\nB\u001d\u0005{\u00012a\u0015B\u001e\t\u0019\ty.\u000bb\u0001-B\u00191Ka\u0010\u0005\r\u0005\u0015\u0018F1\u0001W\u0011\u001d\t\u0019.\u000ba\u0001\u0005\u0007\u0002\u0002Ba\u0004\u0003\u0016\te\"Q\b\u0002\u0015\t&\u001cG/[8oCJL\b*Y:BgN\u001b\u0017\r\\1\u0016\r\t%#Q\u000bB-'\tQS(A\u0001e!\u001dY%q\nB*\u0005/J1A!\u0015M\u0005)!\u0015n\u0019;j_:\f'/\u001f\t\u0004'\nUCABApU\t\u0007a\u000bE\u0002T\u00053\"a!!:+\u0005\u00041F\u0003\u0002B/\u0005?\u0002b\u0001\u0019\u0016\u0003T\t]\u0003b\u0002B&Y\u0001\u0007!QJ\u000b\u0003\u0005G\u0002\u0002\"!\"\u0002r\nM#qK\u0001\u0015\t&\u001cG/[8oCJL\b*Y:BgN\u001b\u0017\r\\1\u0016\r\t%$q\u000eB:)\u0011\u0011YG!\u001e\u0011\r\u0001T#Q\u000eB9!\r\u0019&q\u000e\u0003\u0007\u0003?t#\u0019\u0001,\u0011\u0007M\u0013\u0019\b\u0002\u0004\u0002f:\u0012\rA\u0016\u0005\b\u0005\u0017r\u0003\u0019\u0001B<!\u001dY%q\nB7\u0005c\u0012A\u0003\u0015:pa\u0016\u0014H/[3t\u0011\u0006\u001c\u0018i]*dC2\f7CA\u0018>!\rY%qP\u0005\u0004\u0005\u0003c%A\u0003)s_B,'\u000f^5fgR!!Q\u0011BD!\t\u0001w\u0006\u0003\u0004Jc\u0001\u0007!QP\u000b\u0003\u0005\u0017\u0003\u0002\"!\"\u0002r\n5%Q\u0012\t\u0005\u0005\u001f\u0013iJ\u0004\u0003\u0003\u0012\ne\u0005c\u0001BJs5\u0011!Q\u0013\u0006\u0004\u0005/[\u0014A\u0002\u001fs_>$h(C\u0002\u0003\u001cf\na\u0001\u0015:fI\u00164\u0017\u0002\u0002BP\u0005C\u0013aa\u0015;sS:<'b\u0001BNs\u0005!\u0002K]8qKJ$\u0018.Z:ICN\f5oU2bY\u0006$BA!\"\u0003(\"1\u0011j\ra\u0001\u0005{\u0002")
public interface AsScalaExtensions {
    public static /* synthetic */ IteratorHasAsScala IteratorHasAsScala$(AsScalaExtensions $this, java.util.Iterator i) {
        return $this.IteratorHasAsScala(i);
    }

    default public <A> IteratorHasAsScala<A> IteratorHasAsScala(java.util.Iterator<A> i) {
        return new IteratorHasAsScala<A>(this, i);
    }

    public static /* synthetic */ EnumerationHasAsScala EnumerationHasAsScala$(AsScalaExtensions $this, Enumeration e) {
        return $this.EnumerationHasAsScala(e);
    }

    default public <A> EnumerationHasAsScala<A> EnumerationHasAsScala(Enumeration<A> e) {
        return new EnumerationHasAsScala<A>(this, e);
    }

    public static /* synthetic */ IterableHasAsScala IterableHasAsScala$(AsScalaExtensions $this, java.lang.Iterable i) {
        return $this.IterableHasAsScala(i);
    }

    default public <A> IterableHasAsScala<A> IterableHasAsScala(java.lang.Iterable<A> i) {
        return new IterableHasAsScala<A>(this, i);
    }

    public static /* synthetic */ CollectionHasAsScala CollectionHasAsScala$(AsScalaExtensions $this, Collection c) {
        return $this.CollectionHasAsScala(c);
    }

    default public <A> CollectionHasAsScala<A> CollectionHasAsScala(Collection<A> c) {
        return new CollectionHasAsScala<A>(this, c);
    }

    public static /* synthetic */ ListHasAsScala ListHasAsScala$(AsScalaExtensions $this, List l) {
        return $this.ListHasAsScala(l);
    }

    default public <A> ListHasAsScala<A> ListHasAsScala(List<A> l) {
        return new ListHasAsScala<A>(this, l);
    }

    public static /* synthetic */ SetHasAsScala SetHasAsScala$(AsScalaExtensions $this, Set s2) {
        return $this.SetHasAsScala(s2);
    }

    default public <A> SetHasAsScala<A> SetHasAsScala(Set<A> s2) {
        return new SetHasAsScala<A>(this, s2);
    }

    public static /* synthetic */ MapHasAsScala MapHasAsScala$(AsScalaExtensions $this, java.util.Map m4) {
        return $this.MapHasAsScala(m4);
    }

    default public <K, V> MapHasAsScala<K, V> MapHasAsScala(java.util.Map<K, V> m4) {
        return new MapHasAsScala<K, V>(this, m4);
    }

    public static /* synthetic */ ConcurrentMapHasAsScala ConcurrentMapHasAsScala$(AsScalaExtensions $this, ConcurrentMap m4) {
        return $this.ConcurrentMapHasAsScala(m4);
    }

    default public <K, V> ConcurrentMapHasAsScala<K, V> ConcurrentMapHasAsScala(ConcurrentMap<K, V> m4) {
        return new ConcurrentMapHasAsScala<K, V>(this, m4);
    }

    public static /* synthetic */ DictionaryHasAsScala DictionaryHasAsScala$(AsScalaExtensions $this, Dictionary d) {
        return $this.DictionaryHasAsScala(d);
    }

    default public <K, V> DictionaryHasAsScala<K, V> DictionaryHasAsScala(Dictionary<K, V> d) {
        return new DictionaryHasAsScala<K, V>(this, d);
    }

    public static /* synthetic */ PropertiesHasAsScala PropertiesHasAsScala$(AsScalaExtensions $this, Properties i) {
        return $this.PropertiesHasAsScala(i);
    }

    default public PropertiesHasAsScala PropertiesHasAsScala(Properties i) {
        return new PropertiesHasAsScala(this, i);
    }

    public static void $init$(AsScalaExtensions $this) {
    }

    public class CollectionHasAsScala<A> {
        private final Collection<A> c;
        public final /* synthetic */ AsScalaExtensions $outer;

        public Iterable<A> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.c);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$CollectionHasAsScala$$$outer() {
            return this.$outer;
        }

        public CollectionHasAsScala(AsScalaExtensions $outer, Collection<A> c) {
            this.c = c;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class ConcurrentMapHasAsScala<K, V> {
        private final ConcurrentMap<K, V> m;
        public final /* synthetic */ AsScalaExtensions $outer;

        public scala.collection.concurrent.Map<K, V> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.m);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$ConcurrentMapHasAsScala$$$outer() {
            return this.$outer;
        }

        public ConcurrentMapHasAsScala(AsScalaExtensions $outer, ConcurrentMap<K, V> m4) {
            this.m = m4;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class DictionaryHasAsScala<K, V> {
        private final Dictionary<K, V> d;
        public final /* synthetic */ AsScalaExtensions $outer;

        public Map<K, V> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.d);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$DictionaryHasAsScala$$$outer() {
            return this.$outer;
        }

        public DictionaryHasAsScala(AsScalaExtensions $outer, Dictionary<K, V> d) {
            this.d = d;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class EnumerationHasAsScala<A> {
        private final Enumeration<A> e;
        public final /* synthetic */ AsScalaExtensions $outer;

        public Iterator<A> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.e);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$EnumerationHasAsScala$$$outer() {
            return this.$outer;
        }

        public EnumerationHasAsScala(AsScalaExtensions $outer, Enumeration<A> e) {
            this.e = e;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class IterableHasAsScala<A> {
        private final java.lang.Iterable<A> i;
        public final /* synthetic */ AsScalaExtensions $outer;

        public Iterable<A> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.i);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$IterableHasAsScala$$$outer() {
            return this.$outer;
        }

        public IterableHasAsScala(AsScalaExtensions $outer, java.lang.Iterable<A> i) {
            this.i = i;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class IteratorHasAsScala<A> {
        private final java.util.Iterator<A> i;
        public final /* synthetic */ AsScalaExtensions $outer;

        public Iterator<A> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.i);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$IteratorHasAsScala$$$outer() {
            return this.$outer;
        }

        public IteratorHasAsScala(AsScalaExtensions $outer, java.util.Iterator<A> i) {
            this.i = i;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class ListHasAsScala<A> {
        private final List<A> l;
        public final /* synthetic */ AsScalaExtensions $outer;

        public Buffer<A> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.l);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$ListHasAsScala$$$outer() {
            return this.$outer;
        }

        public ListHasAsScala(AsScalaExtensions $outer, List<A> l) {
            this.l = l;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class MapHasAsScala<K, V> {
        private final java.util.Map<K, V> m;
        public final /* synthetic */ AsScalaExtensions $outer;

        public Map<K, V> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.m);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$MapHasAsScala$$$outer() {
            return this.$outer;
        }

        public MapHasAsScala(AsScalaExtensions $outer, java.util.Map<K, V> m4) {
            this.m = m4;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class PropertiesHasAsScala {
        private final Properties i;
        public final /* synthetic */ AsScalaExtensions $outer;

        public Map<String, String> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.i);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$PropertiesHasAsScala$$$outer() {
            return this.$outer;
        }

        public PropertiesHasAsScala(AsScalaExtensions $outer, Properties i) {
            this.i = i;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class SetHasAsScala<A> {
        private final Set<A> s;
        public final /* synthetic */ AsScalaExtensions $outer;

        public scala.collection.mutable.Set<A> asScala() {
            return AsScalaConverters.asScala$((AsScalaConverters)CollectionConverters$.MODULE$, this.s);
        }

        public /* synthetic */ AsScalaExtensions scala$collection$convert$AsScalaExtensions$SetHasAsScala$$$outer() {
            return this.$outer;
        }

        public SetHasAsScala(AsScalaExtensions $outer, Set<A> s2) {
            this.s = s2;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }
}

