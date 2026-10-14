/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.japi.Pair;
import akka.japi.Pair$;
import akka.japi.function.Procedure2;
import akka.util.MessageBuffer$;
import java.io.Serializable;
import scala.Function2;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.AbstractSeq;
import scala.collection.immutable.Vector;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005Ud\u0001\u0002\u0013&\u0005)B\u0001\"\r\u0001\u0003\u0002\u0004%IA\r\u0005\tg\u0002\u0011\t\u0019!C\u0005i\"Aa\u000f\u0001B\u0001B\u0003&1\u0007\u0003\u0005x\u0001\t\u0005\r\u0011\"\u00033\u0011!A\bA!a\u0001\n\u0013I\b\u0002C>\u0001\u0005\u0003\u0005\u000b\u0015B\u001a\t\u000be\u0002A\u0011\u0002?\t\u0011}\u0004\u0001\u0019!C\u0005\u0003\u0003A\u0011\"!\u0003\u0001\u0001\u0004%I!a\u0003\t\u0011\u0005=\u0001\u0001)Q\u0005\u0003\u0007Aq!!\u0005\u0001\t\u0003\t\u0019\u0002C\u0004\u0002\u001c\u0001!\t!a\u0005\t\u000f\u0005u\u0001\u0001\"\u0001\u0002\u0002!9\u0011q\u0004\u0001\u0005\u0002\u0005\u0005\u0002bBA\u0014\u0001\u0011\u0005\u0011\u0011\u0006\u0005\b\u0003W\u0001A\u0011AA\u0017\u0011\u001d\t)\u0004\u0001C\u0001\u0003oAq!!\u0012\u0001\t\u0003\t9\u0005C\u0004\u0002L\u0001!\t!!\u0014\t\u0011\u0005u\u0003\u0001\"\u0001(\u0003?:QAN\u0013\t\u0002]2Q\u0001J\u0013\t\u0002aBQ!\u000f\f\u0005\u0002i2Aa\u000f\f\u0007y!AQ\b\u0007BA\u0002\u0013\u0005a\b\u0003\u0005B1\t\u0005\r\u0011\"\u0001C\u0011!A\u0005D!A!B\u0013y\u0004\u0002C%\u0019\u0005\u000b\u0007I\u0011\u0001&\t\u00119C\"\u0011!Q\u0001\n-C\u0001b\u0014\r\u0003\u0006\u0004%\t\u0001\u0015\u0005\t/b\u0011\t\u0011)A\u0005#\")\u0011\b\u0007C\u00011\")A\f\u0007C\u0001;\")1\r\u0007C!I\")\u0001O\u0006C\u0001c\niQ*Z:tC\u001e,')\u001e4gKJT!AJ\u0014\u0002\tU$\u0018\u000e\u001c\u0006\u0002Q\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u0016\u0011\u00051zS\"A\u0017\u000b\u00039\nQa]2bY\u0006L!\u0001M\u0017\u0003\r\u0005s\u0017PU3g\u0003\u0015y\u0006.Z1e+\u0005\u0019\u0004C\u0001\u001b\u0019\u001d\t)T#D\u0001&\u00035iUm]:bO\u0016\u0014UO\u001a4feB\u0011QGF\n\u0003--\na\u0001P5oSRtD#A\u001c\u0003\t9{G-Z\n\u00031-\nAA\\3yiV\tq\b\u0005\u0002A15\ta#\u0001\u0005oKb$x\fJ3r)\t\u0019e\t\u0005\u0002-\t&\u0011Q)\f\u0002\u0005+:LG\u000fC\u0004H5\u0005\u0005\t\u0019A \u0002\u0007a$\u0013'A\u0003oKb$\b%A\u0004nKN\u001c\u0018mZ3\u0016\u0003-\u0003\"\u0001\f'\n\u00055k#aA!os\u0006AQ.Z:tC\u001e,\u0007%A\u0002sK\u001a,\u0012!\u0015\t\u0003%Vk\u0011a\u0015\u0006\u0003)\u001e\nQ!Y2u_JL!AV*\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\fAA]3gAQ!q(\u0017.\\\u0011\u0015i\u0004\u00051\u0001@\u0011\u0015I\u0005\u00051\u0001L\u0011\u0015y\u0005\u00051\u0001R\u0003\u0015\t\u0007\u000f\u001d7z)\t\u0019e\fC\u0003`C\u0001\u0007\u0001-A\u0001g!\u0015a\u0013mS)D\u0013\t\u0011WFA\u0005Gk:\u001cG/[8oe\u0005AAo\\*ue&tw\rF\u0001f!\t1WN\u0004\u0002hWB\u0011\u0001.L\u0007\u0002S*\u0011!.K\u0001\u0007yI|w\u000e\u001e \n\u00051l\u0013A\u0002)sK\u0012,g-\u0003\u0002o_\n11\u000b\u001e:j]\u001eT!\u0001\\\u0017\u0002\u000b\u0015l\u0007\u000f^=\u0016\u0003I\u0004\"!\u000e\u0001\u0002\u0013}CW-\u00193`I\u0015\fHCA\"v\u0011\u001d9%!!AA\u0002M\naa\u00185fC\u0012\u0004\u0013!B0uC&d\u0017!C0uC&dw\fJ3r)\t\u0019%\u0010C\u0004H\u000b\u0005\u0005\t\u0019A\u001a\u0002\r}#\u0018-\u001b7!)\r\u0011XP \u0005\u0006c\u001d\u0001\ra\r\u0005\u0006o\u001e\u0001\raM\u0001\u0006?NL'0Z\u000b\u0003\u0003\u0007\u00012\u0001LA\u0003\u0013\r\t9!\f\u0002\u0004\u0013:$\u0018!C0tSj,w\fJ3r)\r\u0019\u0015Q\u0002\u0005\t\u000f&\t\t\u00111\u0001\u0002\u0004\u00051ql]5{K\u0002\nq![:F[B$\u00180\u0006\u0002\u0002\u0016A\u0019A&a\u0006\n\u0007\u0005eQFA\u0004C_>dW-\u00198\u0002\u00119|g.R7qif\fAa]5{K\u00061\u0011\r\u001d9f]\u0012$RA]A\u0012\u0003KAQ!\u0013\bA\u0002-CQa\u0014\bA\u0002E\u000b\u0001\u0002\u001a:pa\"+\u0017\r\u001a\u000b\u0002\u0007\u0006!\u0001.Z1e)\t\ty\u0003E\u0003-\u0003cY\u0015+C\u0002\u000245\u0012a\u0001V;qY\u0016\u0014\u0014aB4fi\"+\u0017\r\u001a\u000b\u0003\u0003s\u0001b!a\u000f\u0002B-\u000bVBAA\u001f\u0015\r\tydJ\u0001\u0005U\u0006\u0004\u0018.\u0003\u0003\u0002D\u0005u\"\u0001\u0002)bSJ\fqAZ8sK\u0006\u001c\u0007\u000eF\u0002D\u0003\u0013BQa\u0018\nA\u0002\u0001\fqAZ8s\u000b\u0006\u001c\u0007\u000eF\u0002D\u0003\u001fBaaX\nA\u0002\u0005E\u0003CBA*\u00033Z\u0015+\u0004\u0002\u0002V)!\u0011qKA\u001f\u0003!1WO\\2uS>t\u0017\u0002BA.\u0003+\u0012!\u0002\u0015:pG\u0016$WO]33\u0003%1\u0017\u000e\u001c;fe:{G\u000fF\u0002D\u0003CBq!a\u0019\u0015\u0001\u0004\t)'A\u0001q!\u0019a\u0013mS)\u0002\u0016!\u001aA#!\u001b\u0011\t\u0005-\u0014\u0011O\u0007\u0003\u0003[R1!a\u001c(\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003g\niGA\u0006J]R,'O\\1m\u0003BL\u0007")
public final class MessageBuffer {
    private Node _head;
    private Node _tail;
    private int _size;

    public static MessageBuffer empty() {
        return MessageBuffer$.MODULE$.empty();
    }

    private Node _head() {
        return this._head;
    }

    private void _head_$eq(Node x$1) {
        this._head = x$1;
    }

    private Node _tail() {
        return this._tail;
    }

    private void _tail_$eq(Node x$1) {
        this._tail = x$1;
    }

    private int _size() {
        return this._size;
    }

    private void _size_$eq(int x$1) {
        this._size = x$1;
    }

    public boolean isEmpty() {
        return this._head() == null;
    }

    public boolean nonEmpty() {
        return !this.isEmpty();
    }

    public int size() {
        return this._size();
    }

    public MessageBuffer append(Object message, ActorRef ref) {
        Node node = new Node(null, message, ref);
        if (this.isEmpty()) {
            this._head_$eq(node);
            this._tail_$eq(node);
        } else {
            this._tail().next_$eq(node);
            this._tail_$eq(node);
        }
        this._size_$eq(this._size() + 1);
        return this;
    }

    public void dropHead() {
        if (this.nonEmpty()) {
            this._head_$eq(this._head().next());
            this._size_$eq(this._size() - 1);
            if (this.isEmpty()) {
                this._tail_$eq(null);
                return;
            }
            return;
        }
    }

    public Tuple2<Object, ActorRef> head() {
        if (this.nonEmpty()) {
            return new Tuple2<Object, ActorRef>(this._head().message(), this._head().ref());
        }
        return new Tuple2<Object, Object>(null, null);
    }

    public Pair<Object, ActorRef> getHead() {
        if (this.nonEmpty()) {
            return Pair$.MODULE$.create(this._head().message(), this._head().ref());
        }
        return Pair$.MODULE$.create(null, null);
    }

    public void foreach(Function2<Object, ActorRef, BoxedUnit> f) {
        for (Node node = this._head(); node != null; node = node.next()) {
            node.apply(f);
        }
    }

    public void forEach(Procedure2<Object, ActorRef> f) {
        this.foreach((Function2<Object, ActorRef, BoxedUnit>)(Function2<Object, ActorRef, Object> & Serializable)(x0$1, x1$1) -> {
            MessageBuffer.$anonfun$forEach$1(f, x0$1, x1$1);
            return BoxedUnit.UNIT;
        });
    }

    @InternalApi
    public void filterNot(Function2<Object, ActorRef, Object> p) {
        Object result = package$.MODULE$.Vector().empty();
        for (Node node = this._head(); node != null; node = node.next()) {
            if (BoxesRunTime.unboxToBoolean(p.apply(node.message(), node.ref()))) continue;
            result = (Vector)((AbstractSeq)result).$colon$plus(node);
        }
        this._size_$eq(((AbstractSeq)result).size());
        if (this._size() == 0) {
            this._head_$eq(null);
            this._tail_$eq(null);
            return;
        }
        if (this._size() == 1) {
            this._head_$eq((Node)((Vector)result).head());
            this._tail_$eq((Node)((Vector)result).head());
            this._tail().next_$eq(null);
            return;
        }
        this._head_$eq((Node)((Vector)result).head());
        this._tail_$eq((Node)((Vector)result).last());
        this._tail().next_$eq(null);
        for (int i = 0; i < ((AbstractSeq)result).size(); ++i) {
            Node node = (Node)result.apply(i);
            if (node == this._tail()) continue;
            node.next_$eq((Node)result.apply(i + 1));
        }
    }

    public static final /* synthetic */ void $anonfun$forEach$1(Procedure2 f$1, Object x0$1, ActorRef x1$1) {
        Tuple2<Object, ActorRef> tuple2 = new Tuple2<Object, ActorRef>(x0$1, x1$1);
        if (tuple2 != null) {
            Object message = tuple2._1();
            ActorRef ref = tuple2._2();
            f$1.apply(message, ref);
            return;
        }
        throw new MatchError(tuple2);
    }

    public MessageBuffer(Node _head, Node _tail) {
        this._head = _head;
        this._tail = _tail;
        this._size = this._head() == null ? 0 : 1;
    }

    private static final class Node {
        private Node next;
        private final Object message;
        private final ActorRef ref;

        public Node next() {
            return this.next;
        }

        public void next_$eq(Node x$1) {
            this.next = x$1;
        }

        public Object message() {
            return this.message;
        }

        public ActorRef ref() {
            return this.ref;
        }

        public void apply(Function2<Object, ActorRef, BoxedUnit> f) {
            f.apply(this.message(), this.ref());
        }

        public String toString() {
            return new StringBuilder(7).append("Node(").append(this.message()).append(",").append(this.ref()).append(")").toString();
        }

        public Node(Node next, Object message, ActorRef ref) {
            this.next = next;
            this.message = message;
            this.ref = ref;
        }
    }
}

