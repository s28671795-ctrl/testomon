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
import akka.util.RecencyList$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.concurrent.duration.FiniteDuration;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005]wA\u0002\u0016,\u0011\u0003isF\u0002\u00042W!\u0005QF\r\u0005\u0006s\u0005!\ta\u000f\u0005\u0006y\u0005!\t!\u0010\u0005\b\u0003\u000b\fA\u0011AAd\r\u0011Y\u0016A\u0002/\t\u0011y+!Q1A\u0005\u0002}C\u0001BY\u0003\u0003\u0002\u0003\u0006I\u0001\u0019\u0005\u0006s\u0015!\ta\u0019\u0005\bO\u0016\u0001\r\u0011\"\u0001i\u0011\u001daW\u00011A\u0005\u00025Daa]\u0003!B\u0013I\u0007b\u0002;\u0006\u0001\u0004%\t\u0001\u001b\u0005\bk\u0016\u0001\r\u0011\"\u0001w\u0011\u0019AX\u0001)Q\u0005S\"9\u00110\u0002a\u0001\n\u0003Q\bb\u0002@\u0006\u0001\u0004%\ta \u0005\b\u0003\u0007)\u0001\u0015)\u0003|\r\u0015\t4FA\u0017A\u0011!\u0011%C!A!\u0002\u0013\u0019\u0005\"B\u001d\u0013\t\u00131\u0005b\u0002+\u0013\u0005\u0004%I!\u0016\u0005\b\u0003\u000b\u0011\u0002\u0015!\u0003W\u0011%\t9A\u0005b\u0001\n\u0013\tI\u0001\u0003\u0005\u0002\u001cI\u0001\u000b\u0011BA\u0006\u0011\u001d\tiB\u0005C\u0001\u0003?Aq!a\n\u0013\t\u0003\tI\u0003C\u0004\u0002.I!\t!a\f\t\u000f\u0005M\"\u0003\"\u0001\u00026!9\u0011q\b\n\u0005\u0002\u0005\u0005\u0003bBA#%\u0011\u0005\u0011\u0011\t\u0005\b\u0003\u000f\u0012B\u0011AA%\u0011\u001d\t\u0019G\u0005C\u0001\u0003\u0013Bq!!\u001a\u0013\t\u0003\t9\u0007C\u0004\u0002fI!\t!!\u001f\t\u000f\u0005\u0015$\u0003\"\u0001\u0002\u0002\"9\u00111\u0011\n\u0005\u0002\u0005\u0015\u0005bBAB%\u0011\u0005\u0011\u0011\u0012\u0005\b\u0003\u0007\u0013B\u0011AAA\u0011\u001d\tyI\u0005C\u0001\u0003#Cq!!*\u0013\t\u0003\t9\u000bC\u0004\u0002,J!I!!,\u0002\u0017I+7-\u001a8ds2K7\u000f\u001e\u0006\u0003Y5\nA!\u001e;jY*\ta&\u0001\u0003bW.\f\u0007C\u0001\u0019\u0002\u001b\u0005Y#a\u0003*fG\u0016t7-\u001f'jgR\u001c\"!A\u001a\u0011\u0005Q:T\"A\u001b\u000b\u0003Y\nQa]2bY\u0006L!\u0001O\u001b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#A\u0018\u0002%\u0015l\u0007\u000f^=XSRDg*\u00198p\u00072|7m[\u000b\u0004}\u0005\rW#A \u0011\tA\u0012\u0012\u0011Y\u000b\u0003\u0003*\u001b\"AE\u001a\u0002\u000b\rdwnY6\u0011\u0005A\"\u0015BA#,\u0005\u0015\u0019En\\2l)\t95\u000bE\u00021%!\u0003\"!\u0013&\r\u0001\u0011)1J\u0005b\u0001\u0019\n\t\u0011)\u0005\u0002N!B\u0011AGT\u0005\u0003\u001fV\u0012qAT8uQ&tw\r\u0005\u00025#&\u0011!+\u000e\u0002\u0004\u0003:L\b\"\u0002\"\u0015\u0001\u0004\u0019\u0015a\u0002:fG\u0016t7-_\u000b\u0002-B\u0019\u0001gV-\n\u0005a[#\u0001\u0005#pk\ndW\rT5oW\u0016$G*[:u!\rQV\u0001\u0013\b\u0003a\u0001\u0011AAT8eKV\u0011Q,Y\n\u0003\u000bM\nQA^1mk\u0016,\u0012\u0001\u0019\t\u0003\u0013\u0006$QaS\u0003C\u00021\u000baA^1mk\u0016\u0004CC\u00013g!\r)W\u0001Y\u0007\u0002\u0003!)a\f\u0003a\u0001A\u0006QA.Z:t%\u0016\u001cWM\u001c;\u0016\u0003%\u00042\u0001\r6e\u0013\tY7FA\u0005PaRLwN\u001c,bY\u0006qA.Z:t%\u0016\u001cWM\u001c;`I\u0015\fHC\u00018r!\t!t.\u0003\u0002qk\t!QK\\5u\u0011\u001d\u0011(\"!AA\u0002%\f1\u0001\u001f\u00132\u0003-aWm]:SK\u000e,g\u000e\u001e\u0011\u0002\u00155|'/\u001a*fG\u0016tG/\u0001\bn_J,'+Z2f]R|F%Z9\u0015\u00059<\bb\u0002:\u000e\u0003\u0003\u0005\r![\u0001\f[>\u0014XMU3dK:$\b%A\u0005uS6,7\u000f^1naV\t1\u0010\u0005\u00025y&\u0011Q0\u000e\u0002\u0005\u0019>tw-A\u0007uS6,7\u000f^1na~#S-\u001d\u000b\u0004]\u0006\u0005\u0001b\u0002:\u0011\u0003\u0003\u0005\ra_\u0001\u000bi&lWm\u001d;b[B\u0004\u0013\u0001\u0003:fG\u0016t7-\u001f\u0011\u0002\u00151|wn[;q\u001d>$W-\u0006\u0002\u0002\fA1\u0011QBA\f\u0011fk!!a\u0004\u000b\t\u0005E\u00111C\u0001\b[V$\u0018M\u00197f\u0015\r\t)\"N\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA\r\u0003\u001f\u00111!T1q\u0003-awn\\6va:{G-\u001a\u0011\u0002\tML'0Z\u000b\u0003\u0003C\u00012\u0001NA\u0012\u0013\r\t)#\u000e\u0002\u0004\u0013:$\u0018AB;qI\u0006$X\rF\u0002H\u0003WAQA\u0018\u000eA\u0002!\u000baA]3n_Z,GcA$\u00022!)al\u0007a\u0001\u0011\u0006A1m\u001c8uC&t7\u000f\u0006\u0003\u00028\u0005u\u0002c\u0001\u001b\u0002:%\u0019\u00111H\u001b\u0003\u000f\t{w\u000e\\3b]\")a\f\ba\u0001\u0011\u0006YA.Z1tiJ+7-\u001a8u+\t\t\u0019\u0005E\u00021U\"\u000b!\"\\8tiJ+7-\u001a8u\u0003EaW-Y:u)>lun\u001d;SK\u000e,g\u000e^\u000b\u0003\u0003\u0017\u0002R!!\u0014\u0002^!sA!a\u0014\u0002Z9!\u0011\u0011KA,\u001b\t\t\u0019FC\u0002\u0002Vi\na\u0001\u0010:p_Rt\u0014\"\u0001\u001c\n\u0007\u0005mS'A\u0004qC\u000e\\\u0017mZ3\n\t\u0005}\u0013\u0011\r\u0002\t\u0013R,'/\u0019;pe*\u0019\u00111L\u001b\u0002#5|7\u000f\u001e+p\u0019\u0016\f7\u000f\u001e*fG\u0016tG/A\tsK6|g/\u001a'fCN$(+Z2f]R$B!!\u001b\u0002vA)\u00111NA9\u00116\u0011\u0011Q\u000e\u0006\u0005\u0003_\n\u0019\"A\u0005j[6,H/\u00192mK&!\u00111OA7\u0005\r\u0019V-\u001d\u0005\b\u0003o\n\u0003\u0019AA\u0011\u0003\u0005qGCBA5\u0003w\ni\bC\u0004\u0002x\t\u0002\r!!\t\t\u000f\u0005}$\u00051\u0001\u0002\"\u0005!1o[5q)\t\tI'\u0001\tsK6|g/Z'pgR\u0014VmY3oiR!\u0011\u0011NAD\u0011\u001d\t9\b\na\u0001\u0003C!b!!\u001b\u0002\f\u00065\u0005bBA<K\u0001\u0007\u0011\u0011\u0005\u0005\b\u0003\u007f*\u0003\u0019AA\u0011\u0003a\u0011X-\\8wK2+\u0017m\u001d;SK\u000e,g\u000e^(viNLG-\u001a\u000b\u0005\u0003S\n\u0019\nC\u0004\u0002\u0016\u001e\u0002\r!a&\u0002\u0011\u0011,(/\u0019;j_:\u0004B!!'\u0002\"6\u0011\u00111\u0014\u0006\u0005\u0003+\u000biJC\u0002\u0002 V\n!bY8oGV\u0014(/\u001a8u\u0013\u0011\t\u0019+a'\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u00061\"/Z7pm\u0016lun\u001d;SK\u000e,g\u000e^,ji\"Lg\u000e\u0006\u0003\u0002j\u0005%\u0006bBAKQ\u0001\u0007\u0011qS\u0001\u000be\u0016lwN^3O_\u0012,Gc\u0001%\u00020\"1\u0011\u0011W\u0015A\u0002e\u000bAA\\8eK\"\u001a!#!.\u0011\t\u0005]\u0016QX\u0007\u0003\u0003sS1!a/.\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u007f\u000bILA\u0006J]R,'O\\1m\u0003BL\u0007cA%\u0002D\u0012)1j\u0001b\u0001\u0019\u0006)\u0011\r\u001d9msV!\u0011\u0011ZAh)\u0011\tY-!5\u0011\tA\u0012\u0012Q\u001a\t\u0004\u0013\u0006=G!B&\u0005\u0005\u0004a\u0005\"\u0002\"\u0005\u0001\u0004\u0019\u0005fA\u0001\u00026\"\u001a\u0001!!.")
@InternalApi
public final class RecencyList<A> {
    private final Clock clock;
    private final DoubleLinkedList<Node<A>> recency;
    private final Map<A, Node<A>> lookupNode;

    public static <A> RecencyList<A> apply(Clock clock) {
        return RecencyList$.MODULE$.apply(clock);
    }

    public static <A> RecencyList<A> emptyWithNanoClock() {
        return RecencyList$.MODULE$.emptyWithNanoClock();
    }

    private DoubleLinkedList<Node<A>> recency() {
        return this.recency;
    }

    private Map<A, Node<A>> lookupNode() {
        return this.lookupNode;
    }

    public int size() {
        return this.lookupNode().size();
    }

    public RecencyList<A> update(A value) {
        Object object;
        if (this.lookupNode().contains(value)) {
            Node node = (Node)this.lookupNode().apply(value);
            node.timestamp_$eq(this.clock.currentTime());
            object = this.recency().moveToBack(node);
        } else {
            Node<A> node = new Node<A>(value);
            node.timestamp_$eq(this.clock.currentTime());
            this.recency().append(node);
            object = this.lookupNode().$plus$eq(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(value), node));
        }
        return this;
    }

    public RecencyList<A> remove(A value) {
        BoxedUnit boxedUnit = this.lookupNode().contains(value) ? this.removeNode((Node)this.lookupNode().apply(value)) : BoxedUnit.UNIT;
        return this;
    }

    public boolean contains(A value) {
        return this.lookupNode().contains(value);
    }

    public A leastRecent() {
        Node<A> node = this.recency().getFirst();
        Node<A> node2 = OptionVal$Some$.MODULE$.unapply(node);
        if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
            Node<A> first = OptionVal$.MODULE$.get$extension(node2);
            return OptionVal$Some$.MODULE$.apply(first.value());
        }
        return OptionVal$.MODULE$.none();
    }

    public A mostRecent() {
        Node<A> node = this.recency().getLast();
        Node<A> node2 = OptionVal$Some$.MODULE$.unapply(node);
        if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
            Node<A> last = OptionVal$.MODULE$.get$extension(node2);
            return OptionVal$Some$.MODULE$.apply(last.value());
        }
        return OptionVal$.MODULE$.none();
    }

    public Iterator<A> leastToMostRecent() {
        return this.recency().forwardIterator().map((Function1<Node, Object> & Serializable)x$3 -> x$3.value());
    }

    public Iterator<A> mostToLeastRecent() {
        return this.recency().backwardIterator().map((Function1<Node, Object> & Serializable)x$4 -> x$4.value());
    }

    public Seq<A> removeLeastRecent(int n) {
        if (n == 1) {
            return this.removeLeastRecent();
        }
        return this.recency().forwardIterator().take(n).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public Seq<A> removeLeastRecent(int n, int skip) {
        return this.recency().forwardIterator().slice(skip, skip + n).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public Seq<A> removeLeastRecent() {
        Node<A> node = this.recency().getFirst();
        Node<A> node2 = OptionVal$Some$.MODULE$.unapply(node);
        if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
            Node<A> first = OptionVal$.MODULE$.get$extension(node2);
            return (Seq)package$.MODULE$.List().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.removeNode(first)}));
        }
        return Nil$.MODULE$;
    }

    public Seq<A> removeMostRecent(int n) {
        if (n == 1) {
            return this.removeMostRecent();
        }
        return this.recency().backwardIterator().take(n).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public Seq<A> removeMostRecent(int n, int skip) {
        return this.recency().backwardIterator().slice(skip, skip + n).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public Seq<A> removeMostRecent() {
        Node<A> node = this.recency().getLast();
        Node<A> node2 = OptionVal$Some$.MODULE$.unapply(node);
        if (!OptionVal$.MODULE$.isEmpty$extension(node2)) {
            Node<A> last = OptionVal$.MODULE$.get$extension(node2);
            return (Seq)package$.MODULE$.List().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.removeNode(last)}));
        }
        return Nil$.MODULE$;
    }

    public Seq<A> removeLeastRecentOutside(FiniteDuration duration) {
        long min2 = this.clock.earlierTime(duration);
        return this.recency().forwardIterator().takeWhile((Function1<Node, Object> & Serializable)x$5 -> BoxesRunTime.boxToBoolean(RecencyList.$anonfun$removeLeastRecentOutside$1(min2, x$5))).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    public Seq<A> removeMostRecentWithin(FiniteDuration duration) {
        long max = this.clock.earlierTime(duration);
        return this.recency().backwardIterator().takeWhile((Function1<Node, Object> & Serializable)x$6 -> BoxesRunTime.boxToBoolean(RecencyList.$anonfun$removeMostRecentWithin$1(max, x$6))).map((Function1<Node, Object> & Serializable)node -> this.removeNode((Node)node)).toList();
    }

    private A removeNode(Node<A> node) {
        A value = node.value();
        this.recency().remove(node);
        this.lookupNode().$minus$eq(value);
        return value;
    }

    public static final /* synthetic */ boolean $anonfun$removeLeastRecentOutside$1(long min$1, Node x$5) {
        return x$5.timestamp() < min$1;
    }

    public static final /* synthetic */ boolean $anonfun$removeMostRecentWithin$1(long max$1, Node x$6) {
        return x$6.timestamp() > max$1;
    }

    public RecencyList(Clock clock) {
        this.clock = clock;
        this.recency = new DoubleLinkedList((Function1<Node, Object> & Serializable)x$1 -> new OptionVal<Node>(x$1.lessRecent()), (Function1<Node, Object> & Serializable)x$2 -> new OptionVal<Node>(x$2.moreRecent()), (Function2<Node, Object, Object> & Serializable)(node, previous) -> {
            node.lessRecent_$eq((Node)((OptionVal)previous).x());
            return BoxedUnit.UNIT;
        }, (Function2<Node, Object, Object> & Serializable)(node, next) -> {
            node.moreRecent_$eq((Node)((OptionVal)next).x());
            return BoxedUnit.UNIT;
        });
        this.lookupNode = (Map)Map$.MODULE$.empty();
    }

    private static final class Node<A> {
        private final A value;
        private Node<A> lessRecent;
        private Node<A> moreRecent;
        private long timestamp;

        public A value() {
            return this.value;
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

        public long timestamp() {
            return this.timestamp;
        }

        public void timestamp_$eq(long x$1) {
            this.timestamp = x$1;
        }

        public Node(A value) {
            this.value = value;
            OptionVal$.MODULE$.None();
            this.lessRecent = null;
            OptionVal$.MODULE$.None();
            this.moreRecent = null;
            this.timestamp = 0L;
        }
    }
}

