/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.Dropped;
import akka.japi.function.Procedure2;
import akka.util.MessageBuffer;
import akka.util.MessageBuffer$;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import scala.Function2;
import scala.MatchError;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005]a\u0001B\t\u0013\u0005]AQa\b\u0001\u0005\u0002\u0001BqA\f\u0001C\u0002\u0013%q\u0006\u0003\u0004;\u0001\u0001\u0006I\u0001\r\u0005\u0006w\u0001!\t\u0001\u0010\u0005\u0006\u0001\u0002!\t\u0001\u0010\u0005\u0006\u0003\u0002!\tA\u0011\u0005\u0006\r\u0002!\tA\u0011\u0005\u0006\u000f\u0002!I\u0001\u0013\u0005\u0006\u0017\u0002!\t\u0001\u0014\u0005\u0006#\u0002!\tA\u0015\u0005\u0006=\u0002!\ta\u0018\u0005\u0006C\u0002!\tA\u0019\u0005\u0006g\u0002!\t\u0001\u001e\u0005\u0006m\u0002!\ta\u001e\u0005\u0006s\u0002!\tA\u001f\u0005\b\u0003\u0003\u0001A\u0011AA\u0002\u0005AiUm]:bO\u0016\u0014UO\u001a4fe6\u000b\u0007O\u0003\u0002\u0014)\u0005!Q\u000f^5m\u0015\u0005)\u0012\u0001B1lW\u0006\u001c\u0001!\u0006\u0002\u0019KM\u0011\u0001!\u0007\t\u00035ui\u0011a\u0007\u0006\u00029\u0005)1oY1mC&\u0011ad\u0007\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\t\u0003c\u0001\u0012\u0001G5\t!\u0003\u0005\u0002%K1\u0001A!\u0002\u0014\u0001\u0005\u00049#!A%\u0012\u0005!Z\u0003C\u0001\u000e*\u0013\tQ3DA\u0004O_RD\u0017N\\4\u0011\u0005ia\u0013BA\u0017\u001c\u0005\r\te._\u0001\nEV4g-\u001a:NCB,\u0012\u0001\r\t\u0005cU\u001as'D\u00013\u0015\t\u00192GC\u00015\u0003\u0011Q\u0017M^1\n\u0005Y\u0012$a\u0002%bg\"l\u0015\r\u001d\t\u0003EaJ!!\u000f\n\u0003\u001b5+7o]1hK\n+hMZ3s\u0003)\u0011WO\u001a4fe6\u000b\u0007\u000fI\u0001\bSN,U\u000e\u001d;z+\u0005i\u0004C\u0001\u000e?\u0013\ty4DA\u0004C_>dW-\u00198\u0002\u00119|g.R7qif\fAa]5{KV\t1\t\u0005\u0002\u001b\t&\u0011Qi\u0007\u0002\u0004\u0013:$\u0018!\u0003;pi\u0006d7+\u001b>f\u000399W\r^(s\u0003\u0012$')\u001e4gKJ$\"aN%\t\u000b)C\u0001\u0019A\u0012\u0002\u0005%$\u0017aA1eIR\u0011Q\n\u0015\t\u000359K!aT\u000e\u0003\tUs\u0017\u000e\u001e\u0005\u0006\u0015&\u0001\raI\u0001\u0007CB\u0004XM\u001c3\u0015\t5\u001bFK\u0016\u0005\u0006\u0015*\u0001\ra\t\u0005\u0006+*\u0001\raK\u0001\b[\u0016\u001c8/Y4f\u0011\u00159&\u00021\u0001Y\u0003\r\u0011XM\u001a\t\u00033rk\u0011A\u0017\u0006\u00037R\tQ!Y2u_JL!!\u0018.\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\faA]3n_Z,GCA'a\u0011\u0015Q5\u00021\u0001$\u0003\u0011!'o\u001c9\u0015\t\r\u001bG-\u001d\u0005\u0006\u00152\u0001\ra\t\u0005\u0006K2\u0001\rAZ\u0001\u0007e\u0016\f7o\u001c8\u0011\u0005\u001dtgB\u00015m!\tI7$D\u0001k\u0015\tYg#\u0001\u0004=e>|GOP\u0005\u0003[n\ta\u0001\u0015:fI\u00164\u0017BA8q\u0005\u0019\u0019FO]5oO*\u0011Qn\u0007\u0005\u0006e2\u0001\r\u0001W\u0001\fI\u0016\fG\rT3ui\u0016\u00148/\u0001\u0005d_:$\u0018-\u001b8t)\tiT\u000fC\u0003K\u001b\u0001\u00071%\u0001\u0006hKR|%/R7qif$\"a\u000e=\t\u000b)s\u0001\u0019A\u0012\u0002\u000f\u0019|'/Z1dQR\u0011Qj\u001f\u0005\u0006y>\u0001\r!`\u0001\u0002MB)!D`\u00128\u001b&\u0011qp\u0007\u0002\n\rVt7\r^5p]J\nqAZ8s\u000b\u0006\u001c\u0007\u000eF\u0002N\u0003\u000bAa\u0001 \tA\u0002\u0005\u001d\u0001CBA\u0005\u0003'\u0019s'\u0004\u0002\u0002\f)!\u0011QBA\b\u0003!1WO\\2uS>t'bAA\t)\u0005!!.\u00199j\u0013\u0011\t)\"a\u0003\u0003\u0015A\u0013xnY3ekJ,'\u0007")
public final class MessageBufferMap<I> {
    private final HashMap<I, MessageBuffer> bufferMap = new HashMap();

    private HashMap<I, MessageBuffer> bufferMap() {
        return this.bufferMap;
    }

    public boolean isEmpty() {
        return this.bufferMap().isEmpty();
    }

    public boolean nonEmpty() {
        return !this.isEmpty();
    }

    public int size() {
        return this.bufferMap().size();
    }

    public int totalSize() {
        int s2 = 0;
        Iterator<MessageBuffer> values2 = this.bufferMap().values().iterator();
        while (values2.hasNext()) {
            s2 += values2.next().size();
        }
        return s2;
    }

    private MessageBuffer getOrAddBuffer(I id) {
        MessageBuffer buffer = this.bufferMap().get(id);
        if (buffer == null) {
            MessageBuffer newBuffer = MessageBuffer$.MODULE$.empty();
            this.bufferMap().put(id, newBuffer);
            return newBuffer;
        }
        return buffer;
    }

    public void add(I id) {
        this.getOrAddBuffer(id);
    }

    public void append(I id, Object message, ActorRef ref) {
        MessageBuffer buffer = this.getOrAddBuffer(id);
        buffer.append(message, ref);
    }

    public void remove(I id) {
        this.bufferMap().remove(id);
    }

    public int drop(I id, String reason, ActorRef deadLetters) {
        MessageBuffer entries = this.bufferMap().get(id);
        if (entries.nonEmpty()) {
            entries.foreach((Function2<Object, ActorRef, BoxedUnit>)(Function2<Object, ActorRef, Object> & Serializable)(msg, ref) -> {
                MessageBufferMap.$anonfun$drop$1(reason, deadLetters, msg, ref);
                return BoxedUnit.UNIT;
            });
        }
        this.remove(id);
        return entries.size();
    }

    public boolean contains(I id) {
        return this.bufferMap().containsKey(id);
    }

    public MessageBuffer getOrEmpty(I id) {
        MessageBuffer buffer = this.bufferMap().get(id);
        if (buffer != null) {
            return buffer;
        }
        return MessageBuffer$.MODULE$.empty();
    }

    public void foreach(Function2<I, MessageBuffer, BoxedUnit> f) {
        for (Map.Entry<I, MessageBuffer> entry : this.bufferMap().entrySet()) {
            f.apply(entry.getKey(), entry.getValue());
        }
    }

    public void forEach(Procedure2<I, MessageBuffer> f) {
        this.foreach((Function2<Object, MessageBuffer, Object> & Serializable)(x0$1, x1$1) -> {
            MessageBufferMap.$anonfun$forEach$2(f, x0$1, x1$1);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$drop$1(String reason$1, ActorRef deadLetters$1, Object msg, ActorRef ref) {
        Dropped x$1 = new Dropped(msg, reason$1, ref, ActorRef$.MODULE$.noSender());
        ActorRef x$2 = deadLetters$1.$bang$default$2(x$1);
        deadLetters$1.$bang(x$1, x$2);
    }

    public static final /* synthetic */ void $anonfun$forEach$2(Procedure2 f$2, Object x0$1, MessageBuffer x1$1) {
        Tuple2<Object, MessageBuffer> tuple2 = new Tuple2<Object, MessageBuffer>(x0$1, x1$1);
        if (tuple2 != null) {
            Object id = tuple2._1();
            MessageBuffer buffer = tuple2._2();
            f$2.apply(id, buffer);
            return;
        }
        throw new MatchError(tuple2);
    }
}

