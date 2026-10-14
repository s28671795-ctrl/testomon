/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.LinearSeqOps;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedLinearSeqOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.AbstractSeq;
import scala.collection.immutable.LinearSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Queue$;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\t\u0015d\u0001\u0002\u0014(!9B\u0001\u0002\u0017\u0001\u0003\u0006\u0004%\t\"\u0017\u0005\t;\u0002\u0011\t\u0011)A\u00055\"Aa\f\u0001BC\u0002\u0013E\u0011\f\u0003\u0005`\u0001\t\u0005\t\u0015!\u0003[\u0011\u0015\u0001\u0007\u0001\"\u0005b\u0011\u0015!\u0007\u0001\"\u0011f\u0011\u0015I\u0007\u0001\"\u0011k\u0011\u0015\u0001\b\u0001\"\u0011r\u0011\u0015)\b\u0001\"\u0011w\u0011\u0015Q\b\u0001\"\u0011|\u0011\u0015a\b\u0001\"\u0011~\u0011\u0015q\b\u0001\"\u0011|\u0011\u0019y\b\u0001\"\u0011\u0002\u0002!9\u0011Q\u0002\u0001\u0005B\u0005=\u0001\u0002CA\n\u0001\u0001&\t&!\u0006\t\u000f\u0005\u001d\u0002\u0001\"\u0011\u0002*!9\u00111\u0006\u0001\u0005B\u00055\u0002bBA \u0001\u0011\u0005\u0013\u0011\t\u0005\b\u0003\u001b\u0002A\u0011IA(\u0011\u001d\t\u0019\u0007\u0001C\u0001\u0003KBq!a\u0019\u0001\t\u000b\t\t\bC\u0004\u0002\"\u0002!\t!a)\t\u000f\u0005E\u0006\u0001\"\u0001\u00024\"9\u00111\u0018\u0001\u0005\u0002\u0005u\u0006BBAc\u0001\u0011\u00051\u0010C\u0004\u0002H\u0002!\t%!3\b\u000f\t\u0005t\u0005#\u0001\u0002t\u001a1ae\nE\u0001\u0003GDa\u0001\u0019\u000f\u0005\u0002\u0005E\bbBA{9\u0011\u0005\u0011q\u001f\u0005\b\u0005\u001baB\u0011\u0001B\b\u0011\u001d\u0011y\u0002\bC\u0001\u0005CAa!\u001b\u000f\u0005B\t-ra\u0002B 9!%!\u0011\t\u0004\b\u0003Cd\u0002\u0012\u0002B.\u0011\u0019\u00017\u0005\"\u0001\u0003`!I!Q\t\u000f\u0002\u0002\u0013%!q\t\u0002\u0006#V,W/\u001a\u0006\u0003Q%\n\u0011\"[7nkR\f'\r\\3\u000b\u0005)Z\u0013AC2pY2,7\r^5p]*\tA&A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005=24\u0003\u0003\u00011\u0001\u000eCEj\u0014*\u0011\u0007E\u0012D'D\u0001(\u0013\t\u0019tEA\u0006BEN$(/Y2u'\u0016\f\bCA\u001b7\u0019\u0001!aa\u000e\u0001\u0005\u0006\u0004A$!A!\u0012\u0005ej\u0004C\u0001\u001e<\u001b\u0005Y\u0013B\u0001\u001f,\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u000f \n\u0005}Z#aA!osB\u0019\u0011'\u0011\u001b\n\u0005\t;#!\u0003'j]\u0016\f'oU3r!\u0015\tD\t\u000e$H\u0013\t)uE\u0001\u0007MS:,\u0017M]*fc>\u00038\u000f\u0005\u00022\u0001A\u0019\u0011\u0007\u0001\u001b\u0011\u000b%SEGR$\u000e\u0003%J!aS\u0015\u00037M#(/[2u\u001fB$\u0018.\\5{K\u0012d\u0015N\\3beN+\u0017o\u00149t!\u0015\tT\n\u000e$H\u0013\tquEA\u000bTiJL7\r^(qi&l\u0017N_3e'\u0016\fx\n]:\u0011\t%\u0003FGR\u0005\u0003#&\u0012q#\u0013;fe\u0006\u0014G.\u001a$bGR|'/\u001f#fM\u0006,H\u000e^:\u0011\u0005M3V\"\u0001+\u000b\u0005UK\u0013aB4f]\u0016\u0014\u0018nY\u0005\u0003/R\u00131\u0003R3gCVdGoU3sS\u0006d\u0017N_1cY\u0016\f!!\u001b8\u0016\u0003i\u00032!M.5\u0013\tavE\u0001\u0003MSN$\u0018aA5oA\u0005\u0019q.\u001e;\u0002\t=,H\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007\u001d\u00137\rC\u0003Y\u000b\u0001\u0007!\fC\u0003_\u000b\u0001\u0007!,A\bji\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z+\u00051\u0007cA%h\r&\u0011\u0001.\u000b\u0002\u000b'\u0016\fh)Y2u_JL\u0018!B1qa2LHC\u0001\u001bl\u0011\u0015aw\u00011\u0001n\u0003\u0005q\u0007C\u0001\u001eo\u0013\ty7FA\u0002J]R\f\u0001\"\u001b;fe\u0006$xN]\u000b\u0002eB\u0019\u0011j\u001d\u001b\n\u0005QL#\u0001C%uKJ\fGo\u001c:\u0002\u000f%\u001cX)\u001c9usV\tq\u000f\u0005\u0002;q&\u0011\u0011p\u000b\u0002\b\u0005>|G.Z1o\u0003\u0011AW-\u00193\u0016\u0003Q\nA\u0001^1jYV\tq)\u0001\u0003mCN$\u0018A\u00024pe\u0006dG\u000eF\u0002x\u0003\u0007Aq!!\u0002\u000e\u0001\u0004\t9!A\u0001q!\u0015Q\u0014\u0011\u0002\u001bx\u0013\r\tYa\u000b\u0002\n\rVt7\r^5p]F\na!\u001a=jgR\u001cHcA<\u0002\u0012!9\u0011Q\u0001\bA\u0002\u0005\u001d\u0011!C2mCN\u001ch*Y7f+\t\t9\u0002\u0005\u0003\u0002\u001a\u0005\rRBAA\u000e\u0015\u0011\ti\"a\b\u0002\t1\fgn\u001a\u0006\u0003\u0003C\tAA[1wC&!\u0011QEA\u000e\u0005\u0019\u0019FO]5oO\u00061A.\u001a8hi\",\u0012!\\\u0001\naJ,\u0007/\u001a8eK\u0012,B!a\f\u00026Q!\u0011\u0011GA\u001e!\u0011\t\u0004!a\r\u0011\u0007U\n)\u0004B\u0004\u00028E\u0011\r!!\u000f\u0003\u0003\t\u000b\"\u0001N\u001f\t\u000f\u0005u\u0012\u00031\u0001\u00024\u0005!Q\r\\3n\u0003!\t\u0007\u000f]3oI\u0016$W\u0003BA\"\u0003\u0013\"B!!\u0012\u0002LA!\u0011\u0007AA$!\r)\u0014\u0011\n\u0003\b\u0003o\u0011\"\u0019AA\u001d\u0011\u001d\tiD\u0005a\u0001\u0003\u000f\n1\"\u00199qK:$W\rZ!mYV!\u0011\u0011KA,)\u0011\t\u0019&!\u0017\u0011\tE\u0002\u0011Q\u000b\t\u0004k\u0005]CaBA\u001c'\t\u0007\u0011\u0011\b\u0005\b\u00037\u001a\u0002\u0019AA/\u0003\u0011!\b.\u0019;\u0011\u000b%\u000by&!\u0016\n\u0007\u0005\u0005\u0014F\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW-A\u0004f]F,X-^3\u0016\t\u0005\u001d\u0014Q\u000e\u000b\u0005\u0003S\ny\u0007\u0005\u00032\u0001\u0005-\u0004cA\u001b\u0002n\u00119\u0011q\u0007\u000bC\u0002\u0005e\u0002bBA\u001f)\u0001\u0007\u00111N\u000b\u0005\u0003g\nI\b\u0006\u0003\u0002v\u0005m\u0004\u0003B\u0019\u0001\u0003o\u00022!NA=\t\u001d\t9$\u0006b\u0001\u0003sAq!! \u0016\u0001\u0004\ty(\u0001\u0003ji\u0016\u0014\b#B%\u0002\u0002\u0006]\u0014bAABS\tA\u0011\n^3sC\ndW\rK\u0002\u0016\u0003\u000f\u00032AOAE\u0013\r\tYi\u000b\u0002\u0007S:d\u0017N\\3)\u0017U\ty)!&\u0002\u0018\u0006m\u0015Q\u0014\t\u0004u\u0005E\u0015bAAJW\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012\u0011\u0011T\u0001J+N,\u0007\u0005Y3ocV,W/Z!mY\u0002\u0004\u0013N\\:uK\u0006$\u0007e\u001c4!A\u0016t\u0017/^3vK\u0002\u0004Co\u001c\u0011f]F,X-^3!C\u0002\u001aw\u000e\u001c7fGRLwN\u001c\u0011pM\u0002*G.Z7f]R\u001c\u0018!B:j]\u000e,\u0017EAAP\u0003\u0019\u0011d&M\u001a/a\u0005QQM\\9vKV,\u0017\t\u001c7\u0016\t\u0005\u0015\u00161\u0016\u000b\u0005\u0003O\u000bi\u000b\u0005\u00032\u0001\u0005%\u0006cA\u001b\u0002,\u00129\u0011q\u0007\fC\u0002\u0005e\u0002bBA?-\u0001\u0007\u0011q\u0016\t\u0006\u0013\u0006\u0005\u0015\u0011V\u0001\bI\u0016\fX/Z;f+\t\t)\fE\u0003;\u0003o#t)C\u0002\u0002:.\u0012a\u0001V;qY\u0016\u0014\u0014!\u00043fcV,W/Z(qi&|g.\u0006\u0002\u0002@B)!(!1\u00026&\u0019\u00111Y\u0016\u0003\r=\u0003H/[8o\u0003\u00151'o\u001c8u\u0003!!xn\u0015;sS:<GCAAf!\u0011\ti-a7\u000f\t\u0005=\u0017q\u001b\t\u0004\u0003#\\SBAAj\u0015\r\t).L\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005e7&\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003K\tiNC\u0002\u0002Z.J#\u0001A\u0012\u0003\u0015\u0015k\u0007\u000f^=Rk\u0016,XmE\u0003\u001d\u0003K\fY\u000fE\u0002;\u0003OL1!!;,\u0005\u0019\te.\u001f*fMB!\u0011*!<G\u0013\r\ty/\u000b\u0002\u001a'R\u0014\u0018n\u0019;PaRLW.\u001b>fIN+\u0017OR1di>\u0014\u0018\u0010\u0006\u0002\u0002tB\u0011\u0011\u0007H\u0001\u000b]\u0016<()^5mI\u0016\u0014X\u0003BA}\u0005\u0013)\"!a?\u0011\u0011\u0005u(1\u0001B\u0004\u0005\u0017i!!a@\u000b\u0007\t\u0005\u0011&A\u0004nkR\f'\r\\3\n\t\t\u0015\u0011q \u0002\b\u0005VLG\u000eZ3s!\r)$\u0011\u0002\u0003\u0006oy\u0011\r\u0001\u000f\t\u0005c\u0001\u00119!\u0001\u0003ge>lW\u0003\u0002B\t\u0005/!BAa\u0005\u0003\u001aA!\u0011\u0007\u0001B\u000b!\r)$q\u0003\u0003\u0006o}\u0011\r\u0001\u000f\u0005\b\u00057y\u0002\u0019\u0001B\u000f\u0003\u0019\u0019x.\u001e:dKB)\u0011*a\u0018\u0003\u0016\u0005)Q-\u001c9usV!!1\u0005B\u0015+\t\u0011)\u0003\u0005\u00032\u0001\t\u001d\u0002cA\u001b\u0003*\u0011)q\u0007\tb\u0001qU!!Q\u0006B\u001a)\u0011\u0011yC!\u000e\u0011\tE\u0002!\u0011\u0007\t\u0004k\tMB!B\u001c\"\u0005\u0004A\u0004b\u0002B\u001cC\u0001\u0007!\u0011H\u0001\u0003qN\u0004RA\u000fB\u001e\u0005cI1A!\u0010,\u0005)a$/\u001a9fCR,GMP\u0001\u000b\u000b6\u0004H/_)vKV,\u0007c\u0001B\"G5\tA$\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003JA!\u0011\u0011\u0004B&\u0013\u0011\u0011i%a\u0007\u0003\r=\u0013'.Z2uQ\u001da\"\u0011\u000bB,\u00053\u00022A\u000fB*\u0013\r\u0011)f\u000b\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012aA\n\u0004G\tu\u0003cA\u0019\u0001sQ\u0011!\u0011I\u0001\u0006#V,W/\u001a\u0015\b7\tE#q\u000bB-\u0001")
public class Queue<A>
extends AbstractSeq<A>
implements LinearSeq<A>,
StrictOptimizedLinearSeqOps<A, Queue, Queue<A>>,
scala.collection.immutable.StrictOptimizedSeqOps<A, Queue, Queue<A>>,
DefaultSerializable {
    private final List<A> in;
    private final List<A> out;

    public static <A> Queue<A> from(IterableOnce<A> source2) {
        return Queue$.MODULE$.from((IterableOnce)source2);
    }

    public static <A> Builder<A, Queue<A>> newBuilder() {
        return Queue$.MODULE$.newBuilder();
    }

    public static SeqOps tabulate(int n, Function1 f) {
        Builder tabulate_b = Queue$.MODULE$.newBuilder();
        tabulate_b.sizeHint(n);
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            Object tabulate_$plus$eq_elem = f.apply(tabulate_i);
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var4_4 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static SeqOps fill(int n, Function0 elem) {
        Builder fill_b = Queue$.MODULE$.newBuilder();
        fill_b.sizeHint(n);
        for (int fill_i = 0; fill_i < n; ++fill_i) {
            Object fill_$plus$eq_elem = elem.apply();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var4_4 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        Queue$ tabulate_this = Queue$.MODULE$;
        Builder tabulate_b = tabulate_this.newBuilder();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var12_12;
            Builder tabulate_b2 = tabulate_this.newBuilder();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var15_15;
                Builder tabulate_b3 = tabulate_this.newBuilder();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var18_18;
                    Builder tabulate_b4 = tabulate_this.newBuilder();
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object var21_21;
                        Builder tabulate_b5 = tabulate_this.newBuilder();
                        tabulate_b5.sizeHint(n5);
                        for (int tabulate_i5 = 0; tabulate_i5 < n5; ++tabulate_i5) {
                            Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4, tabulate_i5);
                            tabulate_b5.addOne(tabulate_$plus$eq_elem);
                            var21_21 = null;
                        }
                        Object var19_19 = null;
                        var21_21 = null;
                        SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b5.result();
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var18_18 = null;
                    }
                    Object var16_16 = null;
                    var18_18 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var15_15 = null;
                }
                Object var13_13 = null;
                var15_15 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var12_12 = null;
            }
            Object var10_10 = null;
            var12_12 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var9_9 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        Queue$ tabulate_this = Queue$.MODULE$;
        Builder tabulate_b = tabulate_this.newBuilder();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var11_11;
            Builder tabulate_b2 = tabulate_this.newBuilder();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var14_14;
                Builder tabulate_b3 = tabulate_this.newBuilder();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var17_17;
                    Builder tabulate_b4 = tabulate_this.newBuilder();
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4);
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        Queue$ tabulate_this = Queue$.MODULE$;
        Builder tabulate_b = tabulate_this.newBuilder();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var10_10;
            Builder tabulate_b2 = tabulate_this.newBuilder();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var13_13;
                Builder tabulate_b3 = tabulate_this.newBuilder();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3);
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        Queue$ tabulate_this = Queue$.MODULE$;
        Builder tabulate_b = tabulate_this.newBuilder();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var9_9;
            Builder tabulate_b2 = tabulate_this.newBuilder();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2);
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var6_6 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        Queue$ fill_this = Queue$.MODULE$;
        Builder fill_b = fill_this.newBuilder();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var12_12;
            Builder fill_b2 = fill_this.newBuilder();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var15_15;
                Builder fill_b3 = fill_this.newBuilder();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var18_18;
                    Builder fill_b4 = fill_this.newBuilder();
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object var21_21;
                        Builder fill_b5 = fill_this.newBuilder();
                        fill_b5.sizeHint(n5);
                        for (int fill_i5 = 0; fill_i5 < n5; ++fill_i5) {
                            Object fill_$plus$eq_elem = elem.apply();
                            fill_b5.addOne(fill_$plus$eq_elem);
                            var21_21 = null;
                        }
                        Object var19_19 = null;
                        var21_21 = null;
                        SeqOps fill_$plus$eq_elem = (SeqOps)fill_b5.result();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var18_18 = null;
                    }
                    Object var16_16 = null;
                    var18_18 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var15_15 = null;
                }
                Object var13_13 = null;
                var15_15 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var12_12 = null;
            }
            Object var10_10 = null;
            var12_12 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var9_9 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        Queue$ fill_this = Queue$.MODULE$;
        Builder fill_b = fill_this.newBuilder();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var11_11;
            Builder fill_b2 = fill_this.newBuilder();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var14_14;
                Builder fill_b3 = fill_this.newBuilder();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var17_17;
                    Builder fill_b4 = fill_this.newBuilder();
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object fill_$plus$eq_elem = elem.apply();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        Queue$ fill_this = Queue$.MODULE$;
        Builder fill_b = fill_this.newBuilder();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var10_10;
            Builder fill_b2 = fill_this.newBuilder();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var13_13;
                Builder fill_b3 = fill_this.newBuilder();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object fill_$plus$eq_elem = elem.apply();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        Queue$ fill_this = Queue$.MODULE$;
        Builder fill_b = fill_this.newBuilder();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var9_9;
            Builder fill_b2 = fill_this.newBuilder();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object fill_$plus$eq_elem = elem.apply();
                fill_b2.addOne(fill_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var6_6 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(Queue$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(Queue$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        Queue$ unfold_this = Queue$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        Queue$ iterate_this = Queue$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public /* synthetic */ Object scala$collection$immutable$StrictOptimizedSeqOps$$super$sorted(Ordering ord) {
        return SeqOps.sorted$(this, ord);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return scala.collection.immutable.StrictOptimizedSeqOps.distinctBy$(this, f);
    }

    @Override
    public Object updated(int index, Object elem) {
        return scala.collection.immutable.StrictOptimizedSeqOps.updated$(this, index, elem);
    }

    @Override
    public Object patch(int from, IterableOnce other, int replaced) {
        return scala.collection.immutable.StrictOptimizedSeqOps.patch$(this, from, other, replaced);
    }

    @Override
    public Object sorted(Ordering ord) {
        return scala.collection.immutable.StrictOptimizedSeqOps.sorted$(this, ord);
    }

    @Override
    public scala.collection.LinearSeq drop(int n) {
        return StrictOptimizedLinearSeqOps.drop$(this, n);
    }

    @Override
    public scala.collection.LinearSeq dropWhile(Function1 p) {
        return StrictOptimizedLinearSeqOps.dropWhile$(this, p);
    }

    @Override
    public Object prependedAll(IterableOnce prefix) {
        return StrictOptimizedSeqOps.prependedAll$(this, prefix);
    }

    @Override
    public Object padTo(int len, Object elem) {
        return StrictOptimizedSeqOps.padTo$(this, len, elem);
    }

    @Override
    public Object diff(Seq that) {
        return StrictOptimizedSeqOps.diff$(this, that);
    }

    @Override
    public Object intersect(Seq that) {
        return StrictOptimizedSeqOps.intersect$(this, that);
    }

    @Override
    public Tuple2<Queue<A>, Queue<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<Queue<A>, Queue<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<Queue<A1>, Queue<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Queue<A1>, Queue<A2>, Queue<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<Queue<A1>, Queue<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public String stringPrefix() {
        return scala.collection.LinearSeq.stringPrefix$(this);
    }

    @Override
    public /* synthetic */ boolean scala$collection$LinearSeqOps$$super$sameElements(IterableOnce that) {
        return SeqOps.sameElements$(this, that);
    }

    @Override
    public Option<A> headOption() {
        return LinearSeqOps.headOption$(this);
    }

    @Override
    public int lengthCompare(int len) {
        return LinearSeqOps.lengthCompare$((LinearSeqOps)this, len);
    }

    @Override
    public int lengthCompare(Iterable<?> that) {
        return LinearSeqOps.lengthCompare$((LinearSeqOps)this, that);
    }

    @Override
    public boolean isDefinedAt(int x) {
        return LinearSeqOps.isDefinedAt$(this, x);
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        LinearSeqOps.foreach$(this, f);
    }

    @Override
    public <A1> boolean contains(A1 elem) {
        return LinearSeqOps.contains$(this, elem);
    }

    @Override
    public Option<A> find(Function1<A, Object> p) {
        return LinearSeqOps.find$(this, p);
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, A, B> op) {
        return (B)LinearSeqOps.foldLeft$(this, z, op);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        return LinearSeqOps.sameElements$(this, that);
    }

    @Override
    public int segmentLength(Function1<A, Object> p, int from) {
        return LinearSeqOps.segmentLength$(this, p, from);
    }

    @Override
    public int indexWhere(Function1<A, Object> p, int from) {
        return LinearSeqOps.indexWhere$(this, p, from);
    }

    @Override
    public int lastIndexWhere(Function1<A, Object> p, int end) {
        return LinearSeqOps.lastIndexWhere$(this, p, end);
    }

    @Override
    public Option<A> findLast(Function1<A, Object> p) {
        return LinearSeqOps.findLast$(this, p);
    }

    @Override
    public Iterator<Queue<A>> tails() {
        return LinearSeqOps.tails$(this);
    }

    public List<A> in() {
        return this.in;
    }

    public List<A> out() {
        return this.out;
    }

    @Override
    public SeqFactory<Queue> iterableFactory() {
        return Queue$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public A apply(int n) {
        void apply_n;
        int index = 0;
        List curr = this.out();
        while (index < n && curr.nonEmpty()) {
            ++index;
            curr = (List)curr.tail();
        }
        if (index == n) {
            if (curr.nonEmpty()) {
                return curr.head();
            }
            if (this.in().nonEmpty()) {
                return this.in().last();
            }
            throw Queue.indexOutOfRange$1(n);
        }
        int indexFromBack = n - index;
        int inLength = this.in().length();
        if (indexFromBack >= inLength) {
            throw Queue.indexOutOfRange$1(n);
        }
        List<A> list = this.in();
        int n2 = inLength - indexFromBack - 1;
        if (list == null) {
            throw null;
        }
        return (A)LinearSeqOps.apply$(list, (int)apply_n);
    }

    @Override
    public Iterator<A> iterator() {
        return this.out().iterator().concat((Function0<List> & Serializable)() -> this.in().reverse());
    }

    @Override
    public boolean isEmpty() {
        return this.in().isEmpty() && this.out().isEmpty();
    }

    @Override
    public A head() {
        if (this.out().nonEmpty()) {
            return this.out().head();
        }
        if (this.in().nonEmpty()) {
            return this.in().last();
        }
        throw new NoSuchElementException("head on empty queue");
    }

    @Override
    public Queue<A> tail() {
        if (this.out().nonEmpty()) {
            return new Queue<A>(this.in(), (List)this.out().tail());
        }
        if (this.in().nonEmpty()) {
            return new Queue<Nothing$>(Nil$.MODULE$, (List)((AbstractIterable)this.in().reverse()).tail());
        }
        throw new NoSuchElementException("tail on empty queue");
    }

    @Override
    public A last() {
        if (this.in().nonEmpty()) {
            return this.in().head();
        }
        if (this.out().nonEmpty()) {
            return this.out().last();
        }
        throw new NoSuchElementException("last on empty queue");
    }

    @Override
    public boolean forall(Function1<A, Object> p) {
        boolean bl;
        block8: {
            List list = this.in();
            if (list == null) {
                throw null;
            }
            List forall_these = list;
            while (!forall_these.isEmpty()) {
                if (!BoxesRunTime.unboxToBoolean(p.apply(forall_these.head()))) {
                    bl = false;
                    break block8;
                }
                forall_these = (List)forall_these.tail();
            }
            bl = true;
        }
        Object var2_2 = null;
        if (bl) {
            boolean bl2;
            block9: {
                List list = this.out();
                if (list == null) {
                    throw null;
                }
                List forall_these = list;
                while (!forall_these.isEmpty()) {
                    if (!BoxesRunTime.unboxToBoolean(p.apply(forall_these.head()))) {
                        bl2 = false;
                        break block9;
                    }
                    forall_these = (List)forall_these.tail();
                }
                bl2 = true;
            }
            Object var3_3 = null;
            if (bl2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean exists(Function1<A, Object> p) {
        block13: {
            block12: {
                boolean bl;
                block11: {
                    boolean bl2;
                    block10: {
                        List list = this.in();
                        if (list == null) {
                            throw null;
                        }
                        List exists_these = list;
                        while (!exists_these.isEmpty()) {
                            if (BoxesRunTime.unboxToBoolean(p.apply(exists_these.head()))) {
                                bl2 = true;
                                break block10;
                            }
                            exists_these = (List)exists_these.tail();
                        }
                        bl2 = false;
                    }
                    Object var2_2 = null;
                    if (bl2) break block12;
                    List list = this.out();
                    if (list == null) {
                        throw null;
                    }
                    List exists_these = list;
                    while (!exists_these.isEmpty()) {
                        if (BoxesRunTime.unboxToBoolean(p.apply(exists_these.head()))) {
                            bl = true;
                            break block11;
                        }
                        exists_these = (List)exists_these.tail();
                    }
                    bl = false;
                }
                Object var3_3 = null;
                if (!bl) break block13;
            }
            return true;
        }
        return false;
    }

    @Override
    public String className() {
        return "Queue";
    }

    @Override
    public int length() {
        return this.in().length() + this.out().length();
    }

    @Override
    public <B> Queue<B> prepended(B elem) {
        List<A> list = this.out();
        if (list == null) {
            throw null;
        }
        List<A> $colon$colon_this = list;
        Object var2_2 = null;
        return new Queue<A>(this.in(), new $colon$colon<B>(elem, $colon$colon_this));
    }

    @Override
    public <B> Queue<B> appended(B elem) {
        return this.enqueue(elem);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> Queue<B> appendedAll(IterableOnce<B> that) {
        List newIn;
        List list;
        if (that instanceof Queue) {
            void $plus$plus_suffix;
            Queue queue = (Queue)that;
            List<A> list2 = queue.in();
            List<A> list3 = queue.out();
            List<A> list4 = this.in().reverse_$colon$colon$colon(list3);
            if (list2 == null) {
                throw null;
            }
            list = list2.appendedAll((IterableOnce)$plus$plus_suffix);
            list4 = null;
        } else if (that instanceof List) {
            List list5 = (List)that;
            list = this.in().reverse_$colon$colon$colon(list5);
        } else {
            List<A> result = this.in();
            Iterator<B> iter = that.iterator();
            while (iter.hasNext()) {
                B b = iter.next();
                if (result == null) {
                    throw null;
                }
                result = new $colon$colon<B>(b, result);
            }
            list = newIn = result;
        }
        if (newIn == this.in()) {
            return this;
        }
        return new Queue(newIn, this.out());
    }

    public <B> Queue<B> enqueue(B elem) {
        List<A> list = this.in();
        if (list == null) {
            throw null;
        }
        List<A> $colon$colon_this = list;
        Object var2_2 = null;
        return new Queue<B>(new $colon$colon<B>(elem, $colon$colon_this), this.out());
    }

    public final <B> Queue<B> enqueue(Iterable<B> iter) {
        return this.appendedAll(iter);
    }

    public <B> Queue<B> enqueueAll(Iterable<B> iter) {
        return this.appendedAll(iter);
    }

    public Tuple2<A, Queue<A>> dequeue() {
        List<A> list = this.out();
        if (((Object)Nil$.MODULE$).equals(list) && !this.in().isEmpty()) {
            Object rev = this.in().reverse();
            return new Tuple2(((AbstractIterable)rev).head(), new Queue<Nothing$>(Nil$.MODULE$, (List)((AbstractIterable)rev).tail()));
        }
        if (list instanceof $colon$colon) {
            $colon$colon $colon$colon = ($colon$colon)list;
            Object x = $colon$colon.head();
            List xs = $colon$colon.next$access$1();
            return new Tuple2(x, new Queue<A>(this.in(), xs));
        }
        throw new NoSuchElementException("dequeue on empty queue");
    }

    public Option<Tuple2<A, Queue<A>>> dequeueOption() {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<A, Queue<A>>>(this.dequeue());
    }

    public A front() {
        return this.head();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public String toString() {
        void mkString_end;
        String string = ")";
        String mkString_sep = ", ";
        String mkString_start = "Queue(";
        return IterableOnceOps.mkString$(this, mkString_start, mkString_sep, (String)mkString_end);
    }

    private static final Nothing$ indexOutOfRange$1(int n$1) {
        throw new IndexOutOfBoundsException(Integer.toString(n$1));
    }

    public Queue(List<A> in, List<A> out) {
        this.in = in;
        this.out = out;
    }
}

