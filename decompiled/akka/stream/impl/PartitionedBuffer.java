/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005Ea!B\f\u0019\u0005aq\u0002\u0002\u0003\u0014\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0015\t\u000b-\u0002A\u0011\u0001\u0017\t\u000fy\u0002!\u0019!C\u0005\u007f!1a\t\u0001Q\u0001\n\u0001Cqa\u0012\u0001C\u0002\u0013%\u0001\n\u0003\u0004S\u0001\u0001\u0006I!\u0013\u0005\u0006'\u0002!\t\u0001\u0016\u0005\u0006+\u0002!\t\u0001\u0016\u0005\u0006-\u0002!\ta\u0016\u0005\u00067\u0002!\ta\u0016\u0005\u00069\u0002!\ta\u0016\u0005\u0006;\u0002!\tA\u0018\u0005\u0006C\u0002!\tA\u0019\u0005\u0006S\u0002!\tA\u001b\u0005\u0006]\u0002!\ta\u001c\u0005\u0006a\u0002!\t!\u001d\u0005\u0006g\u0002!\ta\u001c\u0005\u0006i\u0002!\t!\u001e\u0005\u0006u\u0002!\ta\u001f\u0005\u0006{\u0002!\tA \u0005\u0006\u007f\u0002!\tA \u0005\u0007\u0003\u0003\u0001A\u0011\u0001@\u0003#A\u000b'\u000f^5uS>tW\r\u001a\"vM\u001a,'O\u0003\u0002\u001a5\u0005!\u0011.\u001c9m\u0015\tYB$\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002;\u0005!\u0011m[6b+\ry\u0012gO\n\u0003\u0001\u0001\u0002\"!\t\u0013\u000e\u0003\tR\u0011aI\u0001\u0006g\u000e\fG.Y\u0005\u0003K\t\u0012a!\u00118z%\u00164\u0017\u0001B:ju\u0016\u001c\u0001\u0001\u0005\u0002\"S%\u0011!F\t\u0002\u0004\u0013:$\u0018A\u0002\u001fj]&$h\b\u0006\u0002.{A!a\u0006A\u0018;\u001b\u0005A\u0002C\u0001\u00192\u0019\u0001!QA\r\u0001C\u0002M\u0012\u0011aS\t\u0003i]\u0002\"!I\u001b\n\u0005Y\u0012#a\u0002(pi\"Lgn\u001a\t\u0003CaJ!!\u000f\u0012\u0003\u0007\u0005s\u0017\u0010\u0005\u00021w\u0011)A\b\u0001b\u0001g\t\ta\u000bC\u0003'\u0005\u0001\u0007\u0001&\u0001\u0007mS:,\u0017M\u001d\"vM\u001a,'/F\u0001A!\rq\u0013iQ\u0005\u0003\u0005b\u0011aAQ;gM\u0016\u0014\b\u0003B\u0011E_iJ!!\u0012\u0012\u0003\rQ+\b\u000f\\33\u00035a\u0017N\\3be\n+hMZ3sA\u0005\u0001\u0002/\u0019:uSRLwN\u001c\"vM\u001a,'o]\u000b\u0002\u0013B!!jT\u0018R\u001b\u0005Y%B\u0001'N\u0003\u001diW\u000f^1cY\u0016T!A\u0014\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002Q\u0017\n\u0019Q*\u00199\u0011\u00079\n%(A\tqCJ$\u0018\u000e^5p]\n+hMZ3sg\u0002\n\u0001bY1qC\u000eLG/_\u000b\u0002Q\u0005!Qo]3e\u0003\u0019I7OR;mYV\t\u0001\f\u0005\u0002\"3&\u0011!L\t\u0002\b\u0005>|G.Z1o\u0003\u001dI7/R7qif\f\u0001B\\8o\u000b6\u0004H/_\u0001\u0012G>tG/Y5ogB\u000b'\u000f^5uS>tGC\u0001-`\u0011\u0015\u0001G\u00021\u00010\u0003\rYW-_\u0001\bK:\fX/Z;f)\r\u0019gm\u001a\t\u0003C\u0011L!!\u001a\u0012\u0003\tUs\u0017\u000e\u001e\u0005\u0006A6\u0001\ra\f\u0005\u0006Q6\u0001\rAO\u0001\u0005K2,W.\u0001\u0007bI\u0012\u0004\u0016M\u001d;ji&|g\u000eF\u0002dW2DQ\u0001\u0019\bA\u0002=BQ!\u001c\bA\u0002E\u000baAY;gM\u0016\u0014\u0018a\u00023fcV,W/\u001a\u000b\u0002u\u0005)BM]8q\u001f:d\u0017\u0010U1si&$\u0018n\u001c8IK\u0006$GC\u0001-s\u0011\u0015\u0001\u0007\u00031\u00010\u0003\u0011\u0001X-Z6\u0002\u001bA,Wm\u001b)beRLG/[8o)\t1\u0018\u0010E\u0002\"ojJ!\u0001\u001f\u0012\u0003\r=\u0003H/[8o\u0011\u0015\u0001'\u00031\u00010\u00035\u0001\u0018M\u001d;ji&|g\u000eS3bIR\u0011a\u000f \u0005\u0006AN\u0001\raL\u0001\u0006G2,\u0017M\u001d\u000b\u0002G\u0006AAM]8q\u0011\u0016\fG-\u0001\u0005ee>\u0004H+Y5mQ\r\u0001\u0011Q\u0001\t\u0005\u0003\u000f\ti!\u0004\u0002\u0002\n)\u0019\u00111\u0002\u000f\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\u0010\u0005%!aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public final class PartitionedBuffer<K, V> {
    private final Buffer<Tuple2<K, V>> linearBuffer;
    private final Map<K, Buffer<V>> partitionBuffers;

    private Buffer<Tuple2<K, V>> linearBuffer() {
        return this.linearBuffer;
    }

    private Map<K, Buffer<V>> partitionBuffers() {
        return this.partitionBuffers;
    }

    public int capacity() {
        return this.linearBuffer().capacity();
    }

    public int used() {
        return this.linearBuffer().used();
    }

    public boolean isFull() {
        return this.linearBuffer().isFull();
    }

    public boolean isEmpty() {
        return this.linearBuffer().isEmpty();
    }

    public boolean nonEmpty() {
        return this.linearBuffer().nonEmpty();
    }

    public boolean containsPartition(K key) {
        return this.partitionBuffers().contains(key);
    }

    public void enqueue(K key, V elem) {
        this.linearBuffer().enqueue(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(key), elem));
        Option option = this.partitionBuffers().get(key);
        if (option instanceof Some) {
            Some some = (Some)option;
            Buffer pbuf = (Buffer)some.value();
            pbuf.enqueue(elem);
            return;
        }
        if (None$.MODULE$.equals(option)) {
            throw new IllegalStateException(new StringBuilder(102).append("Enqueuing to key ").append(key).append(" that does not have a buffer, make sure to addPartition for each key before enqueuing").toString());
        }
        throw new MatchError(option);
    }

    public void addPartition(K key, Buffer<V> buffer) {
        this.partitionBuffers().$plus$eq(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(key), buffer));
    }

    public V dequeue() {
        Tuple2<K, V> tuple2 = this.linearBuffer().dequeue();
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        K key = tuple2._1();
        V value = tuple2._2();
        Tuple2<K, V> tuple22 = new Tuple2<K, V>(key, value);
        K key2 = tuple22._1();
        V value2 = tuple22._2();
        Option option = this.partitionBuffers().get(key2);
        if (option instanceof Some) {
            Some some = (Some)option;
            Buffer pbuf = (Buffer)some.value();
            if (BoxesRunTime.equals(pbuf.peek(), value2)) {
                pbuf.dropHead();
                if (pbuf.isEmpty()) {
                    this.partitionBuffers().remove(key2);
                }
            }
        } else if (None$.MODULE$.equals(option)) {
        } else {
            throw new MatchError(option);
        }
        return value2;
    }

    public boolean dropOnlyPartitionHead(K key) {
        Option option = this.partitionBuffers().get(key);
        if (option instanceof Some) {
            Some some = (Some)option;
            Buffer pbuf = (Buffer)some.value();
            pbuf.dropHead();
            Serializable serializable = pbuf.isEmpty() ? this.partitionBuffers().remove(key) : BoxedUnit.UNIT;
            return true;
        }
        if (None$.MODULE$.equals(option)) {
            return false;
        }
        throw new MatchError(option);
    }

    public V peek() {
        return this.linearBuffer().peek()._2();
    }

    public Option<V> peekPartition(K key) {
        Option pbuf = this.partitionBuffers().get(key);
        return pbuf.map((Function1<Buffer, Object> & Serializable)x$2 -> x$2.peek());
    }

    public Option<V> partitionHead(K key) {
        Option pbuf = this.partitionBuffers().get(key);
        return pbuf.map((Function1<Buffer, Object> & Serializable)x$3 -> x$3.peek());
    }

    public void clear() {
        this.linearBuffer().clear();
        this.partitionBuffers().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
            PartitionedBuffer.$anonfun$clear$1(x0$1);
            return BoxedUnit.UNIT;
        });
        this.partitionBuffers().clear();
    }

    public void dropHead() {
        if (this.nonEmpty()) {
            Tuple2<K, V> tuple2 = this.linearBuffer().dequeue();
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            K key = tuple2._1();
            V head = tuple2._2();
            Tuple2<K, V> tuple22 = new Tuple2<K, V>(key, head);
            Object key2 = tuple22._1();
            Object head2 = tuple22._2();
            this.partitionBuffers().get(key2).foreach((Function1<Buffer, Object> & Serializable)pbuf -> {
                if (BoxesRunTime.equals(pbuf.peek(), head2)) {
                    pbuf.dropHead();
                    if (pbuf.isEmpty()) {
                        return this.partitionBuffers().remove(key2);
                    }
                    return BoxedUnit.UNIT;
                }
                return BoxedUnit.UNIT;
            });
            return;
        }
    }

    public void dropTail() {
        throw new UnsupportedOperationException("cannot drop tail of a partitioned buffer");
    }

    public static final /* synthetic */ void $anonfun$clear$1(Tuple2 x0$1) {
        Tuple2 tuple2 = x0$1;
        if (tuple2 != null) {
            Buffer buf = (Buffer)tuple2._2();
            buf.clear();
            return;
        }
        throw new MatchError(tuple2);
    }

    public PartitionedBuffer(int size) {
        this.linearBuffer = Buffer$.MODULE$.apply(size, size);
        this.partitionBuffers = (Map)Map$.MODULE$.empty();
    }
}

