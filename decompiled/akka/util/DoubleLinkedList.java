/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015e!\u0002\u0010 \u0005\u0005\u001a\u0003\u0002C\u0016\u0001\u0005\u0003\u0005\u000b\u0011B\u0017\t\u0011}\u0002!\u0011!Q\u0001\n5B\u0001\u0002\u0011\u0001\u0003\u0002\u0003\u0006I!\u0011\u0005\t\u000f\u0002\u0011\t\u0011)A\u0005\u0003\")\u0001\n\u0001C\u0001\u0013\"1q\n\u0001Q!\nmBa\u0001\u0015\u0001!B\u0013Y\u0004\"B)\u0001\t\u0003\u0011\u0006\"\u0002,\u0001\t\u00039\u0006\"\u0002-\u0001\t\u00039\u0006\"B-\u0001\t\u0003Q\u0006\"B/\u0001\t\u0003q\u0006\"\u00021\u0001\t\u0003\t\u0007\"B2\u0001\t\u0003!\u0007\"\u00024\u0001\t\u00039\u0007\"B5\u0001\t\u0003Q\u0007\"B:\u0001\t\u0003!\b\"B<\u0001\t\u0003A\b\"\u0002?\u0001\t\u0003i\bbBA\u0002\u0001\u0011\u0005\u0011Q\u0001\u0005\b\u0003#\u0001A\u0011AA\n\u0011\u001d\ty\u0002\u0001C\u0001\u0003CAq!a\u000f\u0001\t\u0003\t\t\u0003C\u0004\u0002>\u0001!I!a\u0010\t\u000f\u0005\u001d\u0003\u0001\"\u0003\u0002J!9\u0011\u0011\u000b\u0001\u0005\n\u0005M\u0003bBA.\u0001\u0011%\u0011Q\f\u0005\b\u0003C\u0002A\u0011BA2\u0011\u001d\ty\u0007\u0001C\u0005\u0003c\u0012\u0001\u0003R8vE2,G*\u001b8lK\u0012d\u0015n\u001d;\u000b\u0005\u0001\n\u0013\u0001B;uS2T\u0011AI\u0001\u0005C.\\\u0017-\u0006\u0002%eM\u0011\u0001!\n\t\u0003M%j\u0011a\n\u0006\u0002Q\u0005)1oY1mC&\u0011!f\n\u0002\u0007\u0003:L(+\u001a4\u0002\u0017\u001d,G\u000f\u0015:fm&|Wo]\u0002\u0001!\u00111c\u0006M\u001e\n\u0005=:#!\u0003$v]\u000e$\u0018n\u001c82!\t\t$\u0007\u0004\u0001\u0005\u000bM\u0002!\u0019\u0001\u001b\u0003\t9{G-Z\t\u0003ka\u0002\"A\n\u001c\n\u0005]:#a\u0002(pi\"Lgn\u001a\t\u0003MeJ!AO\u0014\u0003\u0007\u0005s\u0017\u0010E\u0002={Aj\u0011aH\u0005\u0003}}\u0011\u0011b\u00149uS>tg+\u00197\u0002\u000f\u001d,GOT3yi\u0006Y1/\u001a;Qe\u00164\u0018n\\;t!\u00151#\tM\u001eE\u0013\t\u0019uEA\u0005Gk:\u001cG/[8oeA\u0011a%R\u0005\u0003\r\u001e\u0012A!\u00168ji\u000691/\u001a;OKb$\u0018A\u0002\u001fj]&$h\bF\u0003K\u00172ke\nE\u0002=\u0001ABQaK\u0003A\u00025BQaP\u0003A\u00025BQ\u0001Q\u0003A\u0002\u0005CQaR\u0003A\u0002\u0005\u000bQAZ5sgR\fA\u0001\\1ti\u00069\u0011n]#naRLX#A*\u0011\u0005\u0019\"\u0016BA+(\u0005\u001d\u0011un\u001c7fC:\f\u0001bZ3u\r&\u00148\u000f^\u000b\u0002w\u00059q-\u001a;MCN$\u0018a\u00029sKB,g\u000e\u001a\u000b\u0003amCQ\u0001X\u0006A\u0002A\nAA\\8eK\u00061\u0011\r\u001d9f]\u0012$\"\u0001M0\t\u000bqc\u0001\u0019\u0001\u0019\u0002\rI,Wn\u001c<f)\t!%\rC\u0003]\u001b\u0001\u0007\u0001'A\u0006n_Z,Gk\u001c$s_:$HC\u0001\u0019f\u0011\u0015af\u00021\u00011\u0003)iwN^3U_\n\u000b7m\u001b\u000b\u0003a!DQ\u0001X\bA\u0002A\nQcZ3u\r&\u00148\u000f^(s\u000b2\u001cX\r\u0015:fa\u0016tG\rF\u00021W:DQ\u0001\u001c\tA\u00025\fQa\u00195fG.\u0004BA\n\u00181'\"1q\u000e\u0005CA\u0002A\fqA\\3x\u001d>$W\rE\u0002'cBJ!A]\u0014\u0003\u0011q\u0012\u0017P\\1nKz\n1cZ3u\u0019\u0006\u001cHo\u0014:FYN,\u0017\t\u001d9f]\u0012$2\u0001M;w\u0011\u0015a\u0017\u00031\u0001n\u0011\u0019y\u0017\u0003\"a\u0001a\u0006\u0019r-\u001a;OKb$xJ]#mg\u0016Len]3siR!\u0001'\u001f>|\u0011\u0015a&\u00031\u00011\u0011\u0015a'\u00031\u0001n\u0011\u0019y'\u0003\"a\u0001a\u00069r-\u001a;Qe\u00164\u0018n\\;t\u001fJ,En]3J]N,'\u000f\u001e\u000b\u0006ay|\u0018\u0011\u0001\u0005\u00069N\u0001\r\u0001\r\u0005\u0006YN\u0001\r!\u001c\u0005\u0007_N!\t\u0019\u00019\u0002)\u0019Lg\u000e\u001a(fqR|%/\u00127tK&s7/\u001a:u)%\u0001\u0014qAA\u0005\u0003\u001b\ty\u0001C\u0003])\u0001\u0007\u0001\u0007\u0003\u0004\u0002\fQ\u0001\r!\\\u0001\tSN\u0014UMZ8sK\")A\u000e\u0006a\u0001[\"1q\u000e\u0006CA\u0002A\f\u0001DZ5oIB\u0013XM^5pkN|%/\u00127tK&s7/\u001a:u)%\u0001\u0014QCA\f\u00037\ti\u0002C\u0003]+\u0001\u0007\u0001\u0007\u0003\u0004\u0002\u001aU\u0001\r!\\\u0001\bSN\fe\r^3s\u0011\u0015aW\u00031\u0001n\u0011\u0019yW\u0003\"a\u0001a\u0006yam\u001c:xCJ$\u0017\n^3sCR|'/\u0006\u0002\u0002$A)\u0011QEA\u001ba9!\u0011qEA\u0019\u001d\u0011\tI#a\f\u000e\u0005\u0005-\"bAA\u0017Y\u00051AH]8pizJ\u0011\u0001K\u0005\u0004\u0003g9\u0013a\u00029bG.\fw-Z\u0005\u0005\u0003o\tID\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0015\r\t\u0019dJ\u0001\u0011E\u0006\u001c7n^1sI&#XM]1u_J\fA\"\u001b8tKJ$()\u001a4pe\u0016$R\u0001MA!\u0003\u0007BQ\u0001\u0018\rA\u0002ABa!!\u0012\u0019\u0001\u0004Y\u0014\u0001\u00028fqR\f1\"\u001b8tKJ$\u0018I\u001a;feR)\u0001'a\u0013\u0002P!1\u0011QJ\rA\u0002m\n\u0001\u0002\u001d:fm&|Wo\u001d\u0005\u00069f\u0001\r\u0001M\u0001\u0005Y&t7\u000eF\u0004E\u0003+\n9&!\u0017\t\r\u00055#\u00041\u0001<\u0011\u0015a&\u00041\u00011\u0011\u0019\t)E\u0007a\u0001w\u00051QO\u001c7j].$2\u0001RA0\u0011\u0015a6\u00041\u00011\u0003)\u0019\b.\u001b4u/\"LG.\u001a\u000b\ba\u0005\u0015\u0014\u0011NA7\u0011\u0019\t9\u0007\ba\u0001a\u0005)1\u000f^1si\"1\u00111\u000e\u000fA\u00025\nQa\u001d5jMRDQ\u0001\u001c\u000fA\u00025\fA\"\u001b;fe\u0006$xN\u001d$s_6$b!a\t\u0002t\u0005U\u0004BBA4;\u0001\u00071\b\u0003\u0004\u0002lu\u0001\r!\f\u0015\u0004\u0001\u0005e\u0004\u0003BA>\u0003\u0003k!!! \u000b\u0007\u0005}\u0014%\u0001\u0006b]:|G/\u0019;j_:LA!a!\u0002~\tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class DoubleLinkedList<Node> {
    private final Function1<Node, OptionVal<Node>> getPrevious;
    private final Function1<Node, OptionVal<Node>> getNext;
    private final Function2<Node, OptionVal<Node>, BoxedUnit> setPrevious;
    private final Function2<Node, OptionVal<Node>, BoxedUnit> setNext;
    private Node first;
    private Node last;

    public boolean isEmpty() {
        return OptionVal$.MODULE$.isEmpty$extension(this.first);
    }

    public Node getFirst() {
        return this.first;
    }

    public Node getLast() {
        return this.last;
    }

    public Node prepend(Node node) {
        return this.insertBefore(node, this.first);
    }

    public Node append(Node node) {
        return this.insertAfter(this.last, node);
    }

    public void remove(Node node) {
        this.unlink(node);
        this.setNext.apply(node, new OptionVal(OptionVal$.MODULE$.none()));
        this.setPrevious.apply(node, new OptionVal(OptionVal$.MODULE$.none()));
    }

    public Node moveToFront(Node node) {
        if (OptionVal$.MODULE$.contains$extension(this.first, node)) {
            return node;
        }
        this.unlink(node);
        return this.prepend(node);
    }

    public Node moveToBack(Node node) {
        if (OptionVal$.MODULE$.contains$extension(this.last, node)) {
            return node;
        }
        this.unlink(node);
        return this.append(node);
    }

    public Node getFirstOrElsePrepend(Function1<Node, Object> check2, Function0<Node> newNode) {
        Node first;
        Node Node2 = this.first;
        Node Node3 = OptionVal$Some$.MODULE$.unapply(Node2);
        if (!OptionVal$.MODULE$.isEmpty$extension(Node3) && BoxesRunTime.unboxToBoolean(check2.apply(first = OptionVal$.MODULE$.get$extension(Node3)))) {
            return first;
        }
        return this.prepend(newNode.apply());
    }

    public Node getLastOrElseAppend(Function1<Node, Object> check2, Function0<Node> newNode) {
        Node last;
        Node Node2 = this.last;
        Node Node3 = OptionVal$Some$.MODULE$.unapply(Node2);
        if (!OptionVal$.MODULE$.isEmpty$extension(Node3) && BoxesRunTime.unboxToBoolean(check2.apply(last = OptionVal$.MODULE$.get$extension(Node3)))) {
            return last;
        }
        return this.append(newNode.apply());
    }

    public Node getNextOrElseInsert(Node node, Function1<Node, Object> check2, Function0<Node> newNode) {
        Node next;
        Node Node2 = this.getNext.apply(node).x();
        Node Node3 = OptionVal$Some$.MODULE$.unapply(Node2);
        if (!OptionVal$.MODULE$.isEmpty$extension(Node3) && BoxesRunTime.unboxToBoolean(check2.apply(next = OptionVal$.MODULE$.get$extension(Node3)))) {
            return next;
        }
        return this.insertAfter(OptionVal$Some$.MODULE$.apply(node), newNode.apply());
    }

    public Node getPreviousOrElseInsert(Node node, Function1<Node, Object> check2, Function0<Node> newNode) {
        Node previous;
        Node Node2 = this.getPrevious.apply(node).x();
        Node Node3 = OptionVal$Some$.MODULE$.unapply(Node2);
        if (!OptionVal$.MODULE$.isEmpty$extension(Node3) && BoxesRunTime.unboxToBoolean(check2.apply(previous = OptionVal$.MODULE$.get$extension(Node3)))) {
            return previous;
        }
        return this.insertBefore(newNode.apply(), OptionVal$Some$.MODULE$.apply(node));
    }

    public Node findNextOrElseInsert(Node node, Function1<Node, Object> isBefore, Function1<Node, Object> check2, Function0<Node> newNode) {
        return this.getNextOrElseInsert(this.shiftWhile(node, this.getNext, isBefore), check2, newNode);
    }

    public Node findPreviousOrElseInsert(Node node, Function1<Node, Object> isAfter, Function1<Node, Object> check2, Function0<Node> newNode) {
        return this.getPreviousOrElseInsert(this.shiftWhile(node, this.getPrevious, isAfter), check2, newNode);
    }

    public Iterator<Node> forwardIterator() {
        return this.iteratorFrom(this.first, this.getNext);
    }

    public Iterator<Node> backwardIterator() {
        return this.iteratorFrom(this.last, this.getPrevious);
    }

    private Node insertBefore(Node node, Node next) {
        Object previous = OptionVal$.MODULE$.isDefined$extension(next) ? this.getPrevious.apply(OptionVal$.MODULE$.get$extension(next)).x() : OptionVal$.MODULE$.none();
        this.link(previous, node, next);
        return node;
    }

    private Node insertAfter(Node previous, Node node) {
        Object next = OptionVal$.MODULE$.isDefined$extension(previous) ? this.getNext.apply(OptionVal$.MODULE$.get$extension(previous)).x() : OptionVal$.MODULE$.none();
        this.link(previous, node, next);
        return node;
    }

    private void link(Node previous, Node node, Node next) {
        BoxedUnit boxedUnit;
        this.setPrevious.apply(node, new OptionVal<Node>(previous));
        this.setNext.apply(node, new OptionVal<Node>(next));
        if (OptionVal$.MODULE$.isEmpty$extension(previous)) {
            this.first = OptionVal$Some$.MODULE$.apply(node);
            boxedUnit = BoxedUnit.UNIT;
        } else {
            boxedUnit = this.setNext.apply(OptionVal$.MODULE$.get$extension(previous), new OptionVal<Node>(OptionVal$Some$.MODULE$.apply(node)));
        }
        if (OptionVal$.MODULE$.isEmpty$extension(next)) {
            this.last = OptionVal$Some$.MODULE$.apply(node);
            return;
        }
        this.setPrevious.apply(OptionVal$.MODULE$.get$extension(next), new OptionVal<Node>(OptionVal$Some$.MODULE$.apply(node)));
    }

    private void unlink(Node node) {
        BoxedUnit boxedUnit;
        Node previous = this.getPrevious.apply(node).x();
        Node next = this.getNext.apply(node).x();
        if (OptionVal$.MODULE$.isEmpty$extension(previous)) {
            this.first = next;
            boxedUnit = BoxedUnit.UNIT;
        } else {
            boxedUnit = this.setNext.apply(OptionVal$.MODULE$.get$extension(previous), new OptionVal<Node>(next));
        }
        if (OptionVal$.MODULE$.isEmpty$extension(next)) {
            this.last = previous;
            return;
        }
        this.setPrevious.apply(OptionVal$.MODULE$.get$extension(next), new OptionVal<Node>(previous));
    }

    private Node shiftWhile(Node start2, Function1<Node, OptionVal<Node>> shift, Function1<Node, Object> check2) {
        Node current = start2;
        Node peek = shift.apply(current).x();
        while (OptionVal$.MODULE$.isDefined$extension(peek) && BoxesRunTime.unboxToBoolean(check2.apply(OptionVal$.MODULE$.get$extension(peek)))) {
            current = OptionVal$.MODULE$.get$extension(peek);
            peek = shift.apply(current).x();
        }
        return current;
    }

    private Iterator<Node> iteratorFrom(Node start2, Function1<Node, OptionVal<Node>> shift) {
        return new AbstractIterator<Node>(null, start2, shift){
            private Node cursor;
            private final Function1 shift$1;

            public boolean hasNext() {
                return OptionVal$.MODULE$.isDefined$extension(this.cursor);
            }

            public Node next() {
                Node node = this.cursor;
                this.cursor = ((OptionVal)this.shift$1.apply(OptionVal$.MODULE$.get$extension(this.cursor))).x();
                return OptionVal$.MODULE$.get$extension(node);
            }
            {
                this.shift$1 = shift$1;
                this.cursor = start$1;
            }
        };
    }

    public DoubleLinkedList(Function1<Node, OptionVal<Node>> getPrevious, Function1<Node, OptionVal<Node>> getNext, Function2<Node, OptionVal<Node>, BoxedUnit> setPrevious, Function2<Node, OptionVal<Node>, BoxedUnit> setNext) {
        this.getPrevious = getPrevious;
        this.getNext = getNext;
        this.setPrevious = setPrevious;
        this.setNext = setNext;
        this.first = OptionVal$.MODULE$.none();
        this.last = OptionVal$.MODULE$.none();
    }
}

