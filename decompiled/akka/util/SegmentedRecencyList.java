/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.Clock;
import akka.util.DoubleLinkedList;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import akka.util.SegmentedRecencyList$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.IndexedSeq;
import scala.collection.mutable.IndexedSeq$;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Numeric$IntIsIntegral$;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\teuAB%K\u0011\u0003aeJ\u0002\u0004Q\u0015\"\u0005A*\u0015\u0005\u00061\u0006!\tA\u0017\u0005\u00067\u0006!\t\u0001X\u0004\b\u0005{\n\u0001\u0012\u0001B@\r\u001d\u0011\t)\u0001E\u0001\u0005\u0007Ca\u0001W\u0003\u0005\u0002\t\u0015\u0005BB.\u0006\t\u0003\u00119I\u0002\u0004\u0002J\u00051\u00111\n\u0005\u000b\u0003\u001fB!Q1A\u0005\u0002\u0005E\u0003BCA,\u0011\t\u0005\t\u0015!\u0003\u0002T!1\u0001\f\u0003C\u0001\u00033B\u0011\"!\u0019\t\u0001\u0004%\t!a\t\t\u0013\u0005\r\u0004\u00021A\u0005\u0002\u0005\u0015\u0004bBA5\u0011\u0001\u0006KA\u001b\u0005\n\u0003WB\u0001\u0019!C\u0001\u0003[B\u0011\"!\u001e\t\u0001\u0004%\t!a\u001e\t\u0011\u0005m\u0004\u0002)Q\u0005\u0003_B\u0011\"! \t\u0001\u0004%\t!a \t\u0013\u0005\r\u0005\u00021A\u0005\u0002\u0005\u0015\u0005\u0002CAE\u0011\u0001\u0006K!!!\t\u0013\u0005-\u0005\u00021A\u0005\u0002\u0005}\u0004\"CAG\u0011\u0001\u0007I\u0011AAH\u0011!\t\u0019\n\u0003Q!\n\u0005\u0005\u0005\"CAK\u0011\u0001\u0007I\u0011AA@\u0011%\t9\n\u0003a\u0001\n\u0003\tI\n\u0003\u0005\u0002\u001e\"\u0001\u000b\u0015BAA\u0011%\ty\n\u0003a\u0001\n\u0003\ty\bC\u0005\u0002\"\"\u0001\r\u0011\"\u0001\u0002$\"A\u0011q\u0015\u0005!B\u0013\t\tIB\u0003Q\u0015\nau\f\u0003\u0005b=\t\u0005\t\u0015!\u0003c\u0011!igD!A!\u0002\u0013q\u0007\"\u0002-\u001f\t\u0003!\b\"CA\u0004=\u0001\u0007I\u0011BA\u0005\u0011%\t\tB\ba\u0001\n\u0013\t\u0019\u0002\u0003\u0005\u0002 y\u0001\u000b\u0015BA\u0006\u0011%\t\tC\ba\u0001\n\u0013\t\u0019\u0003C\u0005\u0002&y\u0001\r\u0011\"\u0003\u0002(!9\u00111\u0006\u0010!B\u0013Q\u0007\"CA\u0017=\t\u0007I\u0011BA\u0012\u0011\u001d\tyC\bQ\u0001\n)D\u0011\"!\r\u001f\u0005\u0004%I!a\t\t\u000f\u0005Mb\u0004)A\u0005U\"I\u0011Q\u0007\u0010C\u0002\u0013%\u00111\u0005\u0005\b\u0003oq\u0002\u0015!\u0003k\u0011%\tID\bb\u0001\n\u0013\tY\u0004\u0003\u0005\u0002*z\u0001\u000b\u0011BA\u001f\u0011%\tYK\bb\u0001\n\u0013\ti\u000b\u0003\u0005\u0002:z\u0001\u000b\u0011BAX\u0011%\tYL\bb\u0001\n\u0013\ti\f\u0003\u0005\u0002@z\u0001\u000b\u0011BA \u0011%\t\tM\bb\u0001\n\u0013\t\u0019\r\u0003\u0005\u0002Lz\u0001\u000b\u0011BAc\u0011\u001d\tiM\bC\u0001\u0003GAq!a4\u001f\t\u0003\t\t\u000eC\u0004\u0002Vz!\t!a6\t\u000f\u0005mg\u0004\"\u0001\u0002^\"9\u0011\u0011\u001d\u0010\u0005\u0002\u0005\r\bbBAw=\u0011\u0005\u0011q\u001e\u0005\b\u0003gtB\u0011AA{\u0011\u001d\u0011\tB\bC\u0001\u0005'AqAa\u0006\u001f\t\u0003\u0011\u0019\u0002C\u0004\u0003\u001ay!\tAa\u0007\t\u000f\t=b\u0004\"\u0001\u00032!9!q\u0007\u0010\u0005\n\te\u0002b\u0002B\u001e=\u0011%!Q\b\u0005\b\u0005\u0003rB\u0011\u0002B\"\u0011\u001d\u0011IE\bC\u0005\u0005\u0017BqAa\u0014\u001f\t\u0013\u0011\t\u0006C\u0004\u0003Vy!IAa\u0016\t\u000f\tuc\u0004\"\u0003\u0003`!9!1\r\u0010\u0005\n\t\u0015\u0014\u0001F*fO6,g\u000e^3e%\u0016\u001cWM\\2z\u0019&\u001cHO\u0003\u0002L\u0019\u0006!Q\u000f^5m\u0015\u0005i\u0015\u0001B1lW\u0006\u0004\"aT\u0001\u000e\u0003)\u0013AcU3h[\u0016tG/\u001a3SK\u000e,gnY=MSN$8CA\u0001S!\t\u0019f+D\u0001U\u0015\u0005)\u0016!B:dC2\f\u0017BA,U\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001O\u0003\u0015)W\u000e\u001d;z+\ri&\u0011\u0010\u000b\u0004=\nm\u0004\u0003B(\u001f\u0005o*\"\u0001\u0019=\u0014\u0005y\u0011\u0016!D5oSRL\u0017\r\u001c'j[&$8\u000fE\u0002dQ*l\u0011\u0001\u001a\u0006\u0003K\u001a\f\u0011\"[7nkR\f'\r\\3\u000b\u0005\u001d$\u0016AC2pY2,7\r^5p]&\u0011\u0011\u000e\u001a\u0002\u0004'\u0016\f\bCA*l\u0013\taGKA\u0002J]R\fQa\u00197pG.\u00042aT8r\u0013\t\u0001(JA\u0005PaRLwN\u001c,bYB\u0011qJ]\u0005\u0003g*\u0013Qa\u00117pG.$R!^A\u0002\u0003\u000b\u00012a\u0014\u0010w!\t9\b\u0010\u0004\u0001\u0005\u000bet\"\u0019\u0001>\u0003\u0003\u0005\u000b\"a\u001f@\u0011\u0005Mc\u0018BA?U\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aU@\n\u0007\u0005\u0005AKA\u0002B]fDQ!Y\u0011A\u0002\tDQ!\\\u0011A\u00029\fa\u0001\\5nSR\u001cXCAA\u0006!\u0011\u0019\u0017Q\u00026\n\u0007\u0005=AM\u0001\u0006J]\u0012,\u00070\u001a3TKF\f!\u0002\\5nSR\u001cx\fJ3r)\u0011\t)\"a\u0007\u0011\u0007M\u000b9\"C\u0002\u0002\u001aQ\u0013A!\u00168ji\"I\u0011QD\u0012\u0002\u0002\u0003\u0007\u00111B\u0001\u0004q\u0012\n\u0014a\u00027j[&$8\u000fI\u0001\u000bi>$\u0018\r\u001c'j[&$X#\u00016\u0002\u001dQ|G/\u00197MS6LGo\u0018\u0013fcR!\u0011QCA\u0015\u0011!\tiBJA\u0001\u0002\u0004Q\u0017a\u0003;pi\u0006dG*[7ji\u0002\na\u0001\\3wK2\u001c\u0018a\u00027fm\u0016d7\u000fI\u0001\u0007Y><Xm\u001d;\u0002\u000f1|w/Z:uA\u00059\u0001.[4iKN$\u0018\u0001\u00035jO\",7\u000f\u001e\u0011\u0002\u0011M,w-\\3oiN,\"!!\u0010\u0011\u000b\r\fi!a\u0010\u0011\u000b=\u000b\t%!\u0012\n\u0007\u0005\r#J\u0001\tE_V\u0014G.\u001a'j].,G\rT5tiB!\u0011q\t\u0005w\u001d\ty\u0005A\u0001\u0003O_\u0012,W\u0003BA'\u0003+\u001a\"\u0001\u0003*\u0002\u000bY\fG.^3\u0016\u0005\u0005M\u0003cA<\u0002V\u0011)\u0011\u0010\u0003b\u0001u\u00061a/\u00197vK\u0002\"B!a\u0017\u0002`A)\u0011Q\f\u0005\u0002T5\t\u0011\u0001C\u0004\u0002P-\u0001\r!a\u0015\u0002\u000b1,g/\u001a7\u0002\u00131,g/\u001a7`I\u0015\fH\u0003BA\u000b\u0003OB\u0001\"!\b\u000e\u0003\u0003\u0005\rA[\u0001\u0007Y\u00164X\r\u001c\u0011\u0002\u0013QLW.Z:uC6\u0004XCAA8!\r\u0019\u0016\u0011O\u0005\u0004\u0003g\"&\u0001\u0002'p]\u001e\fQ\u0002^5nKN$\u0018-\u001c9`I\u0015\fH\u0003BA\u000b\u0003sB\u0011\"!\b\u0011\u0003\u0003\u0005\r!a\u001c\u0002\u0015QLW.Z:uC6\u0004\b%\u0001\u0006mKN\u001c(+Z2f]R,\"!!!\u0011\t={\u00171L\u0001\u000fY\u0016\u001c8OU3dK:$x\fJ3r)\u0011\t)\"a\"\t\u0013\u0005u1#!AA\u0002\u0005\u0005\u0015a\u00037fgN\u0014VmY3oi\u0002\n!\"\\8sKJ+7-\u001a8u\u00039iwN]3SK\u000e,g\u000e^0%KF$B!!\u0006\u0002\u0012\"I\u0011Q\u0004\f\u0002\u0002\u0003\u0007\u0011\u0011Q\u0001\f[>\u0014XMU3dK:$\b%A\tpm\u0016\u0014\u0018\r\u001c7MKN\u001c(+Z2f]R\fQc\u001c<fe\u0006dG\u000eT3tgJ+7-\u001a8u?\u0012*\u0017\u000f\u0006\u0003\u0002\u0016\u0005m\u0005\"CA\u000f3\u0005\u0005\t\u0019AAA\u0003Iyg/\u001a:bY2dUm]:SK\u000e,g\u000e\u001e\u0011\u0002#=4XM]1mY6{'/\u001a*fG\u0016tG/A\u000bpm\u0016\u0014\u0018\r\u001c7N_J,'+Z2f]R|F%Z9\u0015\t\u0005U\u0011Q\u0015\u0005\n\u0003;a\u0012\u0011!a\u0001\u0003\u0003\u000b!c\u001c<fe\u0006dG.T8sKJ+7-\u001a8uA\u0005I1/Z4nK:$8\u000fI\u0001\u0006g&TXm]\u000b\u0003\u0003_\u0003R!!-\u00028*l!!a-\u000b\u0007\u0005Uf-A\u0004nkR\f'\r\\3\n\t\u0005=\u00111W\u0001\u0007g&TXm\u001d\u0011\u0002\u001d=4XM]1mYJ+7-\u001a8dsV\u0011\u0011qH\u0001\u0010_Z,'/\u00197m%\u0016\u001cWM\\2zA\u0005QAn\\8lkBtu\u000eZ3\u0016\u0005\u0005\u0015\u0007cBAY\u0003\u000f4\u0018QI\u0005\u0005\u0003\u0013\f\u0019LA\u0002NCB\f1\u0002\\8pWV\u0004hj\u001c3fA\u0005!1/\u001b>f\u0003\u0019\u0019\u0018N_3PMR\u0019!.a5\t\r\u0005\u0005t\u00071\u0001k\u0003\u0019)\b\u000fZ1uKR\u0019Q/!7\t\r\u0005=\u0003\b1\u0001w\u0003\u0019\u0011X-\\8wKR\u0019Q/a8\t\r\u0005=\u0013\b1\u0001w\u0003!\u0019wN\u001c;bS:\u001cH\u0003BAs\u0003W\u00042aUAt\u0013\r\tI\u000f\u0016\u0002\b\u0005>|G.Z1o\u0011\u0019\tyE\u000fa\u0001m\u0006YA.Z1tiJ+7-\u001a8u+\t\t\t\u0010E\u0002P_Z\f1\u0003\\3bgR$v.T8tiJ+7-\u001a8u\u001f\u001a$B!a>\u0003\u0010A)\u0011\u0011 B\u0005m:!\u00111 B\u0003\u001d\u0011\tiPa\u0001\u000e\u0005\u0005}(b\u0001B\u00013\u00061AH]8pizJ\u0011!V\u0005\u0004\u0005\u000f!\u0016a\u00029bG.\fw-Z\u0005\u0005\u0005\u0017\u0011iA\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0015\r\u00119\u0001\u0016\u0005\u0007\u0003Cb\u0004\u0019\u00016\u00025I,Wn\u001c<f\u0019\u0016\f7\u000f\u001e*fG\u0016tGo\u0014<fe2KW.\u001b;\u0015\u0005\tU\u0001cA2im\u0006\t\"/Z7pm\u0016dU-Y:u%\u0016\u001cWM\u001c;\u0002?I,Wn\u001c<f\u001fZ,'/\u00197m\u0019\u0016\f7\u000f\u001e*fG\u0016tGoT;ug&$W\r\u0006\u0003\u0003\u0016\tu\u0001b\u0002B\u0010\u007f\u0001\u0007!\u0011E\u0001\tIV\u0014\u0018\r^5p]B!!1\u0005B\u0016\u001b\t\u0011)C\u0003\u0003\u0003 \t\u001d\"b\u0001B\u0015)\u0006Q1m\u001c8dkJ\u0014XM\u001c;\n\t\t5\"Q\u0005\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u00031)\b\u000fZ1uK2KW.\u001b;t)\u0011\t)Ba\r\t\r\tU\u0002\t1\u0001c\u0003%qWm\u001e'j[&$8/A\u000bbI*,8\u000f\u001e)s_R,7\r^3e\u0019\u00164X\r\\:\u0015\u0005\u0005U\u0011AB1eUV\u001cH\u000f\u0006\u0003\u0002\u0016\t}\u0002BBA1\u0005\u0002\u0007!.\u0001\u0004j]N,'\u000f\u001e\u000b\u0005\u0003+\u0011)\u0005C\u0004\u0003H\r\u0003\r!!\u0012\u0002\t9|G-Z\u0001\baJ|Wn\u001c;f)\u0011\t)B!\u0014\t\u000f\t\u001dC\t1\u0001\u0002F\u00051A-Z7pi\u0016$B!!\u0006\u0003T!9!qI#A\u0002\u0005\u0015\u0013\u0001C1qa\u0016tG\rV8\u0015\r\u0005U!\u0011\fB.\u0011\u0019\t\tG\u0012a\u0001U\"9!q\t$A\u0002\u0005\u0015\u0013A\u0006:f[>4XM\u0012:p[\u000e+(O]3oi2+g/\u001a7\u0015\t\u0005U!\u0011\r\u0005\b\u0005\u000f:\u0005\u0019AA#\u0003)\u0011X-\\8wK:{G-\u001a\u000b\u0004m\n\u001d\u0004b\u0002B$\u0011\u0002\u0007\u0011Q\t\u0015\u0004=\t-\u0004\u0003\u0002B7\u0005gj!Aa\u001c\u000b\u0007\tED*\u0001\u0006b]:|G/\u0019;j_:LAA!\u001e\u0003p\tY\u0011J\u001c;fe:\fG.\u00119j!\r9(\u0011\u0010\u0003\u0006s\u000e\u0011\rA\u001f\u0005\u0007\u0003\u000f\u0019\u0001\u0019\u00012\u0002%]LG\u000f[(wKJ\fG\u000e\u001c*fG\u0016t7-\u001f\t\u0004\u0003;*!AE<ji\"|e/\u001a:bY2\u0014VmY3oGf\u001c\"!\u0002*\u0015\u0005\t}T\u0003\u0002BE\u0005\u001f#bAa#\u0003\u0012\nM\u0005\u0003B(\u001f\u0005\u001b\u00032a\u001eBH\t\u0015IxA1\u0001{\u0011\u0015iw\u00011\u0001r\u0011\u0019\t9a\u0002a\u0001E\"\u001a\u0011Aa\u001b)\u0007\u0001\u0011Y\u0007")
@InternalApi
public final class SegmentedRecencyList<A> {
    private final Clock clock;
    private scala.collection.immutable.IndexedSeq<Object> limits;
    private int totalLimit;
    private final int levels;
    private final int lowest;
    private final int highest;
    private final scala.collection.immutable.IndexedSeq<DoubleLinkedList<Node<A>>> segments;
    private final IndexedSeq<Object> sizes;
    private final DoubleLinkedList<Node<A>> overallRecency;
    private final Map<A, Node<A>> lookupNode;

    public static <A> SegmentedRecencyList<A> empty(Seq<Object> limits) {
        return SegmentedRecencyList$.MODULE$.empty(limits);
    }

    private scala.collection.immutable.IndexedSeq<Object> limits() {
        return this.limits;
    }

    private void limits_$eq(scala.collection.immutable.IndexedSeq<Object> x$1) {
        this.limits = x$1;
    }

    private int totalLimit() {
        return this.totalLimit;
    }

    private void totalLimit_$eq(int x$1) {
        this.totalLimit = x$1;
    }

    private int levels() {
        return this.levels;
    }

    private int lowest() {
        return this.lowest;
    }

    private int highest() {
        return this.highest;
    }

    private scala.collection.immutable.IndexedSeq<DoubleLinkedList<Node<A>>> segments() {
        return this.segments;
    }

    private IndexedSeq<Object> sizes() {
        return this.sizes;
    }

    private DoubleLinkedList<Node<A>> overallRecency() {
        return this.overallRecency;
    }

    private Map<A, Node<A>> lookupNode() {
        return this.lookupNode;
    }

    public int size() {
        return this.lookupNode().size();
    }

    public int sizeOf(int level) {
        return BoxesRunTime.unboxToInt(this.sizes().apply(level));
    }

    public SegmentedRecencyList<A> update(A value) {
        if (this.lookupNode().contains(value)) {
            this.promote((Node)this.lookupNode().apply(value));
        } else {
            this.insert(new Node<A>(value));
        }
        return this;
    }

    public SegmentedRecencyList<A> remove(A value) {
        BoxedUnit boxedUnit = this.lookupNode().contains(value) ? this.removeNode((Node)this.lookupNode().apply(value)) : BoxedUnit.UNIT;
        return this;
    }

    public boolean contains(A value) {
        return this.lookupNode().contains(value);
    }

    public A leastRecent() {
        Node node = (Node)((DoubleLinkedList)this.segments().apply(this.lowest())).getFirst();
        Node node2 = OptionVal$Some$.MODULE$.unapply(node);
        if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
            Node first = OptionVal$.MODULE$.get$extension(node2);
            return OptionVal$Some$.MODULE$.apply(first.value());
        }
        return OptionVal$.MODULE$.none();
    }

    public Iterator<A> leastToMostRecentOf(int level) {
        return ((DoubleLinkedList)this.segments().apply(level)).forwardIterator().map((Function1<Node, Object> & Serializable)x$5 -> x$5.value());
    }

    public Seq<A> removeLeastRecentOverLimit() {
        if (this.size() > this.totalLimit()) {
            this.adjustProtectedLevels();
            int excess = this.size() - this.totalLimit();
            if (excess == 1) {
                return this.removeLeastRecent();
            }
            return ((DoubleLinkedList)this.segments().apply(this.lowest())).forwardIterator().take(excess).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
        }
        return Nil$.MODULE$;
    }

    public Seq<A> removeLeastRecent() {
        Node node = (Node)((DoubleLinkedList)this.segments().apply(this.lowest())).getFirst();
        Node node2 = OptionVal$Some$.MODULE$.unapply(node);
        if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
            Node first = OptionVal$.MODULE$.get$extension(node2);
            return (Seq)package$.MODULE$.List().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.removeNode(first)}));
        }
        return Nil$.MODULE$;
    }

    public Seq<A> removeOverallLeastRecentOutside(FiniteDuration duration) {
        if (OptionVal$.MODULE$.isEmpty$extension(this.clock)) {
            throw new UnsupportedOperationException("Overall recency is not enabled");
        }
        long min2 = OptionVal$.MODULE$.get$extension(this.clock).earlierTime(duration);
        return this.overallRecency().forwardIterator().takeWhile((Function1<Node, Object> & Serializable)x$6 -> BoxesRunTime.boxToBoolean(SegmentedRecencyList.$anonfun$removeOverallLeastRecentOutside$1(min2, x$6))).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public void updateLimits(Seq<Object> newLimits) {
        this.limits_$eq(newLimits.toIndexedSeq());
        this.totalLimit_$eq(BoxesRunTime.unboxToInt(this.limits().sum(Numeric$IntIsIntegral$.MODULE$)));
    }

    private void adjustProtectedLevels() {
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(this.highest()), this.lowest()).by(-1).foreach$mVc$sp(level -> this.adjust(level));
    }

    private void adjust(int level) {
        int excess = BoxesRunTime.unboxToInt(this.sizes().apply(level)) - BoxesRunTime.unboxToInt(this.limits().apply(level));
        if (excess > 0) {
            ((DoubleLinkedList)this.segments().apply(level)).forwardIterator().take(excess).foreach((Function1<Node, Object> & Serializable)node -> {
                this.demote(node);
                return BoxedUnit.UNIT;
            });
            return;
        }
    }

    private void insert(Node<A> node) {
        this.appendTo(this.lowest(), node);
        this.lookupNode().$plus$eq(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(node.value()), node));
        if (OptionVal$.MODULE$.isDefined$extension(this.clock)) {
            node.timestamp_$eq(OptionVal$.MODULE$.get$extension(this.clock).currentTime());
            this.overallRecency().append(node);
            return;
        }
    }

    private void promote(Node<A> node) {
        Object object;
        if (node.level() == this.highest()) {
            object = ((DoubleLinkedList)this.segments().apply(node.level())).moveToBack(node);
        } else {
            int newLevel = node.level() + 1;
            this.removeFromCurrentLevel(node);
            this.appendTo(newLevel, node);
            this.adjust(newLevel);
            object = BoxedUnit.UNIT;
        }
        if (OptionVal$.MODULE$.isDefined$extension(this.clock)) {
            node.timestamp_$eq(OptionVal$.MODULE$.get$extension(this.clock).currentTime());
            this.overallRecency().moveToBack(node);
            return;
        }
    }

    private void demote(Node<A> node) {
        this.removeFromCurrentLevel(node);
        this.appendTo(node.level() - 1, node);
    }

    private void appendTo(int level, Node<A> node) {
        node.level_$eq(level);
        ((DoubleLinkedList)this.segments().apply(level)).append(node);
        this.sizes().update(level, BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(this.sizes().apply(level)) + 1));
    }

    private void removeFromCurrentLevel(Node<A> node) {
        ((DoubleLinkedList)this.segments().apply(node.level())).remove(node);
        int n = node.level();
        this.sizes().update(n, BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(this.sizes().apply(n)) - 1));
    }

    private A removeNode(Node<A> node) {
        A value = node.value();
        this.removeFromCurrentLevel(node);
        if (OptionVal$.MODULE$.isDefined$extension(this.clock)) {
            this.overallRecency().remove(node);
        }
        this.lookupNode().$minus$eq(value);
        return value;
    }

    public static final /* synthetic */ boolean $anonfun$removeOverallLeastRecentOutside$1(long min$1, Node x$6) {
        return x$6.timestamp() < min$1;
    }

    public SegmentedRecencyList(Seq<Object> initialLimits, Clock clock) {
        this.clock = clock;
        this.limits = initialLimits.toIndexedSeq();
        this.totalLimit = BoxesRunTime.unboxToInt(this.limits().sum(Numeric$IntIsIntegral$.MODULE$));
        this.levels = this.limits().size();
        this.lowest = 0;
        this.highest = this.levels() - 1;
        this.segments = (scala.collection.immutable.IndexedSeq)package$.MODULE$.IndexedSeq().fill(this.levels(), (Function0<DoubleLinkedList> & Serializable)() -> new DoubleLinkedList((Function1<Node, Object> & Serializable)x$1 -> new OptionVal<Node>(x$1.lessRecent()), (Function1<Node, Object> & Serializable)x$2 -> new OptionVal<Node>(x$2.moreRecent()), (Function2<Node, Object, Object> & Serializable)(node, previous) -> {
            node.lessRecent_$eq((Node)((OptionVal)previous).x());
            return BoxedUnit.UNIT;
        }, (Function2<Node, Object, Object> & Serializable)(node, next) -> {
            node.moreRecent_$eq((Node)((OptionVal)next).x());
            return BoxedUnit.UNIT;
        }));
        this.sizes = (IndexedSeq)IndexedSeq$.MODULE$.fill(this.levels(), () -> 0);
        this.overallRecency = new DoubleLinkedList((Function1<Node, Object> & Serializable)x$3 -> new OptionVal<Node>(x$3.overallLessRecent()), (Function1<Node, Object> & Serializable)x$4 -> new OptionVal<Node>(x$4.overallMoreRecent()), (Function2<Node, Object, Object> & Serializable)(node, previous) -> {
            node.overallLessRecent_$eq((Node)((OptionVal)previous).x());
            return BoxedUnit.UNIT;
        }, (Function2<Node, Object, Object> & Serializable)(node, next) -> {
            node.overallMoreRecent_$eq((Node)((OptionVal)next).x());
            return BoxedUnit.UNIT;
        });
        this.lookupNode = (Map)Map$.MODULE$.empty();
    }

    private static final class Node<A> {
        private final A value;
        private int level;
        private long timestamp;
        private Node<A> lessRecent;
        private Node<A> moreRecent;
        private Node<A> overallLessRecent;
        private Node<A> overallMoreRecent;

        public A value() {
            return this.value;
        }

        public int level() {
            return this.level;
        }

        public void level_$eq(int x$1) {
            this.level = x$1;
        }

        public long timestamp() {
            return this.timestamp;
        }

        public void timestamp_$eq(long x$1) {
            this.timestamp = x$1;
        }

        public Node<A> lessRecent() {
            return this.lessRecent;
        }

        public void lessRecent_$eq(Node<A> x$1) {
            this.lessRecent = x$1;
        }

        public Node<A> moreRecent() {
            return this.moreRecent;
        }

        public void moreRecent_$eq(Node<A> x$1) {
            this.moreRecent = x$1;
        }

        public Node<A> overallLessRecent() {
            return this.overallLessRecent;
        }

        public void overallLessRecent_$eq(Node<A> x$1) {
            this.overallLessRecent = x$1;
        }

        public Node<A> overallMoreRecent() {
            return this.overallMoreRecent;
        }

        public void overallMoreRecent_$eq(Node<A> x$1) {
            this.overallMoreRecent = x$1;
        }

        public Node(A value) {
            this.value = value;
            this.level = 0;
            this.timestamp = 0L;
            OptionVal$.MODULE$.None();
            this.lessRecent = null;
            OptionVal$.MODULE$.None();
            this.moreRecent = null;
            OptionVal$.MODULE$.None();
            this.overallLessRecent = null;
            OptionVal$.MODULE$.None();
            this.overallMoreRecent = null;
        }
    }
}

