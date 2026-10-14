/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.ConsistentHash$;
import java.io.Serializable;
import java.util.Arrays;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.StringOps$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SortedMap;
import scala.math.package$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005e\u0001B\u000e\u001d\u0001\u0005B\u0001\"\u000b\u0001\u0003\u0002\u0003\u0006IA\u000b\u0005\t\u0001\u0002\u0011)\u0019!C\u0001\u0003\"A!\t\u0001B\u0001B\u0003%!\u0007\u0003\u0005D\u0001\t\r\t\u0015a\u0003E\u0011\u0015Q\u0005\u0001\"\u0003L\u0011)\u0011\u0006\u0001%A\u0001\u0004\u0003\u0006Ia\u0015\u0005\b5\u0002\u0011\r\u0011\"\u0003\\\u0011\u0019a\u0006\u0001)A\u0005-\"9Q\f\u0001b\u0001\n\u0013q\u0006BB0\u0001A\u0003%\u0011\fC\u0003a\u0001\u0011\u0005\u0011\rC\u0003e\u0001\u0011\u0005Q\rC\u0003h\u0001\u0011\u0005\u0001\u000eC\u0003k\u0001\u0011\u00051\u000eC\u0003n\u0001\u0011%a\u000eC\u0003r\u0001\u0011\u0005!\u000fC\u0003r\u0001\u0011\u0005\u0011\u0010C\u0004\u0002\u000e\u0001!\t!a\u0004\b\u000f\u0005]A\u0004#\u0001\u0002\u001a\u001911\u0004\bE\u0001\u00037AaA\u0013\u000b\u0005\u0002\u0005u\u0001bBA\u0010)\u0011\u0005\u0011\u0011\u0005\u0005\b\u0003\u0013\"B\u0011AA&\u0011\u001d\t9\u0007\u0006C\u0005\u0003SBq!a\u001d\u0015\t\u0013\t)\bC\u0004\u0002tQ!I!a\u001f\u0003\u001d\r{gn]5ti\u0016tG\u000fS1tQ*\u0011QDH\u0001\be>,H/\u001b8h\u0015\u0005y\u0012\u0001B1lW\u0006\u001c\u0001!\u0006\u0002#oM\u0011\u0001a\t\t\u0003I\u001dj\u0011!\n\u0006\u0002M\u0005)1oY1mC&\u0011\u0001&\n\u0002\u0007\u0003:L(+\u001a4\u0002\u000b9|G-Z:\u0011\t-\u0002$'N\u0007\u0002Y)\u0011QFL\u0001\nS6lW\u000f^1cY\u0016T!aL\u0013\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u00022Y\tI1k\u001c:uK\u0012l\u0015\r\u001d\t\u0003IMJ!\u0001N\u0013\u0003\u0007%sG\u000f\u0005\u00027o1\u0001A!\u0002\u001d\u0001\u0005\u0004I$!\u0001+\u0012\u0005ij\u0004C\u0001\u0013<\u0013\taTEA\u0004O_RD\u0017N\\4\u0011\u0005\u0011r\u0014BA &\u0005\r\te._\u0001\u0013m&\u0014H/^1m\u001d>$Wm\u001d$bGR|'/F\u00013\u0003M1\u0018N\u001d;vC2tu\u000eZ3t\r\u0006\u001cGo\u001c:!\u0003))g/\u001b3f]\u000e,G%\r\t\u0004\u000b\"+T\"\u0001$\u000b\u0005\u001d+\u0013a\u0002:fM2,7\r^\u0005\u0003\u0013\u001a\u0013\u0001b\u00117bgN$\u0016mZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00071\u0003\u0016\u000b\u0006\u0002N\u001fB\u0019a\nA\u001b\u000e\u0003qAQaQ\u0003A\u0004\u0011CQ!K\u0003A\u0002)BQ\u0001Q\u0003A\u0002I\n1\u0001\u001f\u00133!\u0011!CKV-\n\u0005U+#A\u0002+va2,'\u0007E\u0002%/JJ!\u0001W\u0013\u0003\u000b\u0005\u0013(/Y=\u0011\u0007\u0011:V'\u0001\u0007o_\u0012,\u0007*Y:i%&tw-F\u0001W\u00035qw\u000eZ3ICND'+\u001b8hA\u0005Aan\u001c3f%&tw-F\u0001Z\u0003%qw\u000eZ3SS:<\u0007%A\u0006%G>dwN\u001c\u0013qYV\u001cHCA'c\u0011\u0015\u00197\u00021\u00016\u0003\u0011qw\u000eZ3\u0002\u0007\u0005$G\r\u0006\u0002NM\")1\r\u0004a\u0001k\u0005aAeY8m_:$S.\u001b8vgR\u0011Q*\u001b\u0005\u0006G6\u0001\r!N\u0001\u0007e\u0016lwN^3\u0015\u00055c\u0007\"B2\u000f\u0001\u0004)\u0014aA5eqR\u0011!g\u001c\u0005\u0006a>\u0001\rAM\u0001\u0002S\u00069an\u001c3f\r>\u0014HCA\u001bt\u0011\u0015!\b\u00031\u0001v\u0003\rYW-\u001f\t\u0004I]3\bC\u0001\u0013x\u0013\tAXE\u0001\u0003CsR,GCA\u001b{\u0011\u0015!\u0018\u00031\u0001|!\ra\u0018q\u0001\b\u0004{\u0006\r\u0001C\u0001@&\u001b\u0005y(bAA\u0001A\u00051AH]8pizJ1!!\u0002&\u0003\u0019\u0001&/\u001a3fM&!\u0011\u0011BA\u0006\u0005\u0019\u0019FO]5oO*\u0019\u0011QA\u0013\u0002\u000f%\u001cX)\u001c9usV\u0011\u0011\u0011\u0003\t\u0004I\u0005M\u0011bAA\u000bK\t9!i\\8mK\u0006t\u0017AD\"p]NL7\u000f^3oi\"\u000b7\u000f\u001b\t\u0003\u001dR\u0019\"\u0001F\u0012\u0015\u0005\u0005e\u0011!B1qa2LX\u0003BA\u0012\u0003W!b!!\n\u00024\u0005\u001dC\u0003BA\u0014\u0003[\u0001BA\u0014\u0001\u0002*A\u0019a'a\u000b\u0005\u000ba2\"\u0019A\u001d\t\u0013\u0005=b#!AA\u0004\u0005E\u0012AC3wS\u0012,gnY3%eA!Q\tSA\u0015\u0011\u0019Ic\u00031\u0001\u00026A1\u0011qGA!\u0003SqA!!\u000f\u0002>9\u0019a0a\u000f\n\u0003\u0019J1!a\u0010&\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u0011\u0002F\tA\u0011\n^3sC\ndWMC\u0002\u0002@\u0015BQ\u0001\u0011\fA\u0002I\naa\u0019:fCR,W\u0003BA'\u0003'\"b!a\u0014\u0002V\u0005\u0015\u0004\u0003\u0002(\u0001\u0003#\u00022ANA*\t\u0015AtC1\u0001:\u0011\u0019Is\u00031\u0001\u0002XA1\u0011\u0011LA2\u0003#j!!a\u0017\u000b\t\u0005u\u0013qL\u0001\u0005Y\u0006twM\u0003\u0002\u0002b\u0005!!.\u0019<b\u0013\u0011\t\u0019%a\u0017\t\u000b\u0001;\u0002\u0019\u0001\u001a\u0002'\r|gnY1uK:\fG/\u001a(pI\u0016D\u0015m\u001d5\u0015\u000bI\nY'a\u001c\t\r\u00055\u0004\u00041\u00013\u0003!qw\u000eZ3ICND\u0007BBA91\u0001\u0007!'A\u0003w]>$W-A\u0004iCNDgi\u001c:\u0015\u0007I\n9\b\u0003\u0004\u0002ze\u0001\r!^\u0001\u0006Ef$Xm\u001d\u000b\u0004e\u0005u\u0004BBA@5\u0001\u000710\u0001\u0004tiJLgn\u001a")
public class ConsistentHash<T> {
    private final SortedMap<Object, T> nodes;
    private final int virtualNodesFactor;
    private final ClassTag<T> evidence$1;
    private final /* synthetic */ Tuple2 x$2;
    private final int[] nodeHashRing;
    private final Object nodeRing;

    public static <T> ConsistentHash<T> create(java.lang.Iterable<T> nodes, int virtualNodesFactor) {
        return ConsistentHash$.MODULE$.create(nodes, virtualNodesFactor);
    }

    public static <T> ConsistentHash<T> apply(Iterable<T> nodes, int virtualNodesFactor, ClassTag<T> evidence$2) {
        return ConsistentHash$.MODULE$.apply(nodes, virtualNodesFactor, evidence$2);
    }

    public int virtualNodesFactor() {
        return this.virtualNodesFactor;
    }

    private int[] nodeHashRing() {
        return this.nodeHashRing;
    }

    private Object nodeRing() {
        return this.nodeRing;
    }

    public ConsistentHash<T> $colon$plus(T node) {
        int nodeHash = ConsistentHash$.MODULE$.akka$routing$ConsistentHash$$hashFor(node.toString());
        return new ConsistentHash<T>((SortedMap)this.nodes.$plus$plus(RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), this.virtualNodesFactor()).map((Function1<Object, Tuple2> & Serializable)r -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(ConsistentHash$.MODULE$.akka$routing$ConsistentHash$$concatenateNodeHash(nodeHash, BoxesRunTime.unboxToInt(r)))), node))), this.virtualNodesFactor(), this.evidence$1);
    }

    public ConsistentHash<T> add(T node) {
        return this.$colon$plus(node);
    }

    public ConsistentHash<T> $colon$minus(T node) {
        int nodeHash = ConsistentHash$.MODULE$.akka$routing$ConsistentHash$$hashFor(node.toString());
        return new ConsistentHash<T>((SortedMap)this.nodes.$minus$minus((IterableOnce<Object>)RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), this.virtualNodesFactor()).map(r -> ConsistentHash$.MODULE$.akka$routing$ConsistentHash$$concatenateNodeHash(nodeHash, r))), this.virtualNodesFactor(), this.evidence$1);
    }

    public ConsistentHash<T> remove(T node) {
        return this.$colon$minus(node);
    }

    private int idx(int i) {
        if (i >= 0) {
            return i;
        }
        int j = package$.MODULE$.abs(i + 1);
        if (j >= this.nodeHashRing().length) {
            return 0;
        }
        return j;
    }

    public T nodeFor(byte[] key) {
        if (this.isEmpty()) {
            throw new IllegalStateException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("Can't get node for [%s] from an empty node ring"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{key})));
        }
        return (T)ScalaRunTime$.MODULE$.array_apply(this.nodeRing(), this.idx(Arrays.binarySearch(this.nodeHashRing(), ConsistentHash$.MODULE$.akka$routing$ConsistentHash$$hashFor(key))));
    }

    public T nodeFor(String key) {
        if (this.isEmpty()) {
            throw new IllegalStateException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("Can't get node for [%s] from an empty node ring"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{key})));
        }
        return (T)ScalaRunTime$.MODULE$.array_apply(this.nodeRing(), this.idx(Arrays.binarySearch(this.nodeHashRing(), ConsistentHash$.MODULE$.akka$routing$ConsistentHash$$hashFor(key))));
    }

    public boolean isEmpty() {
        return this.nodes.isEmpty();
    }

    public ConsistentHash(SortedMap<Object, T> nodes, int virtualNodesFactor, ClassTag<T> evidence$1) {
        int[] nArray;
        Object nodeRing;
        block10: {
            Tuple2<Object, Object> tuple2;
            block9: {
                Seq seq;
                Seq nr;
                block8: {
                    Tuple2 tuple22;
                    block7: {
                        this.nodes = nodes;
                        this.virtualNodesFactor = virtualNodesFactor;
                        this.evidence$1 = evidence$1;
                        if (virtualNodesFactor < 1) {
                            throw new IllegalArgumentException("virtualNodesFactor must be >= 1");
                        }
                        tuple22 = nodes.toSeq().unzip(Predef$.MODULE$.$conforms());
                        if (tuple22 == null) break block7;
                        Seq nhr = (Seq)tuple22._1();
                        nr = (Seq)tuple22._2();
                        if (nhr == null) break block7;
                        seq = nhr;
                        if (nr != null) break block8;
                    }
                    throw new MatchError(tuple22);
                }
                Seq seq2 = nr;
                Tuple2<Seq, Seq> tuple23 = new Tuple2<Seq, Seq>(seq, seq2);
                Seq nhr = tuple23._1();
                Seq nr2 = tuple23._2();
                tuple2 = new Tuple2<Object, Object>(nhr.toArray(ClassTag$.MODULE$.Int()), nr2.toArray(evidence$1));
                if (tuple2 == null) break block9;
                int[] nodeHashRing = (int[])tuple2._1();
                nodeRing = tuple2._2();
                if (nodeHashRing == null) break block9;
                nArray = nodeHashRing;
                if (nodeRing != null) break block10;
            }
            throw new MatchError(tuple2);
        }
        Object object = nodeRing;
        this.x$2 = new Tuple2<int[], Object>(nArray, object);
        this.nodeHashRing = (int[])this.x$2._1();
        this.nodeRing = this.x$2._2();
    }
}

