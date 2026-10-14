/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.annotation.DoNotInherit;
import akka.stream.javadsl.PartitionHub$;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.ToLongBiFunction;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005es!\u0002\u0007\u000e\u0011\u0003!b!\u0002\f\u000e\u0011\u00039\u0002\"\u0002\u0010\u0002\t\u0003y\u0002\"\u0002\u0011\u0002\t\u0003\t\u0003\"\u0002\u0011\u0002\t\u0003y\bbBA\f\u0003\u0011\u0005\u0011\u0011\u0004\u0005\b\u0003/\tA\u0011AA\"\r\u001da\u0016\u0001%A\u0012\u0002uCQAX\u0004\u0007\u0002}CQaZ\u0004\u0007\u0002!DQA\\\u0004\u0007\u0002=DQA]\u0004\u0007\u0002M\fA\u0002U1si&$\u0018n\u001c8Ik\nT!AD\b\u0002\u000f)\fg/\u00193tY*\u0011\u0001#E\u0001\u0007gR\u0014X-Y7\u000b\u0003I\tA!Y6lC\u000e\u0001\u0001CA\u000b\u0002\u001b\u0005i!\u0001\u0004)beRLG/[8o\u0011V\u00147CA\u0001\u0019!\tIB$D\u0001\u001b\u0015\u0005Y\u0012!B:dC2\f\u0017BA\u000f\u001b\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012\u0001F\u0001\u000b_\u001a\u001cF/\u0019;fMVdWC\u0001\u0012))\u0015\u0019\u0003\bT>~!\u0011)BEJ\u0019\n\u0005\u0015j!\u0001B*j].\u0004\"a\n\u0015\r\u0001\u0011)\u0011f\u0001b\u0001U\t\tA+\u0005\u0002,]A\u0011\u0011\u0004L\u0005\u0003[i\u0011qAT8uQ&tw\r\u0005\u0002\u001a_%\u0011\u0001G\u0007\u0002\u0004\u0003:L\b\u0003B\u000b3MQJ!aM\u0007\u0003\rM{WO]2f!\t)d'D\u0001\u0012\u0013\t9\u0014CA\u0004O_R,6/\u001a3\t\u000be\u001a\u0001\u0019\u0001\u001e\u0002\u000b\rd\u0017M\u001f>\u0011\u0007m\u0012eE\u0004\u0002=\u0001B\u0011QHG\u0007\u0002})\u0011qhE\u0001\u0007yI|w\u000e\u001e \n\u0005\u0005S\u0012A\u0002)sK\u0012,g-\u0003\u0002D\t\n)1\t\\1tg*\u0011\u0011I\u0007\u0015\u0003q\u0019\u0003\"a\u0012&\u000e\u0003!S!!S\t\u0002\tU$\u0018\u000e\\\u0005\u0003\u0017\"\u0013a!\u001e8vg\u0016$\u0007\"B'\u0004\u0001\u0004q\u0015a\u00039beRLG/[8oKJ\u00042aT+X\u001b\u0005\u0001&BA)S\u0003!1WO\\2uS>t'BA%T\u0015\u0005!\u0016\u0001\u00026bm\u0006L!A\u0016)\u0003\u0011M+\b\u000f\u001d7jKJ\u0004Ba\u0014-[M%\u0011\u0011\f\u0015\u0002\u0011)>duN\\4CS\u001a+hn\u0019;j_:\u0004\"aW\u0004\u000e\u0003\u0005\u0011AbQ8ogVlWM]%oM>\u001c\"a\u0002\r\u0002\u001d\u001d,GoQ8ogVlWM]%egV\t\u0001\rE\u0002bE\u0012l\u0011AU\u0005\u0003GJ\u0013A\u0001T5tiB\u0011\u0011$Z\u0005\u0003Mj\u0011A\u0001T8oO\u0006y1m\u001c8tk6,'/\u00133Cs&#\u0007\u0010\u0006\u0002eS\")!.\u0003a\u0001W\u0006\u0019\u0011\u000e\u001a=\u0011\u0005ea\u0017BA7\u001b\u0005\rIe\u000e^\u0001\ncV,W/Z*ju\u0016$\"a\u001b9\t\u000bET\u0001\u0019\u00013\u0002\u0015\r|gn];nKJLE-\u0001\u0003tSj,W#A6)\u0005\u001d)\bC\u0001<z\u001b\u00059(B\u0001=\u0012\u0003)\tgN\\8uCRLwN\\\u0005\u0003u^\u0014A\u0002R8O_RLe\u000e[3sSRDQ\u0001`\u0002A\u0002-\fqc\u001d;beR\fe\r^3s\u001dJ|emQ8ogVlWM]:\t\u000by\u001c\u0001\u0019A6\u0002\u0015\t,hMZ3s'&TX-\u0006\u0003\u0002\u0002\u0005\u001dA\u0003CA\u0002\u0003\u0017\ty!!\u0006\u0011\rU!\u0013QAA\u0005!\r9\u0013q\u0001\u0003\u0006S\u0011\u0011\rA\u000b\t\u0006+I\n)\u0001\u000e\u0005\u0007s\u0011\u0001\r!!\u0004\u0011\tm\u0012\u0015Q\u0001\u0005\u0007\u001b\u0012\u0001\r!!\u0005\u0011\t=+\u00161\u0003\t\u0006\u001fbS\u0016Q\u0001\u0005\u0006y\u0012\u0001\ra[\u0001\u0003_\u001a,B!a\u0007\u0002\"QQ\u0011QDA\u0013\u0003W\ty$!\u0011\u0011\rU!\u0013qDA\u0012!\r9\u0013\u0011\u0005\u0003\u0006S\u0015\u0011\rA\u000b\t\u0006+I\ny\u0002\u000e\u0005\u0007s\u0015\u0001\r!a\n\u0011\tm\u0012\u0015q\u0004\u0015\u0004\u0003K1\u0005BB'\u0006\u0001\u0004\ti\u0003E\u0005P\u0003_\t\u0019$a\b\u00024%\u0019\u0011\u0011\u0007)\u0003\u0015\tKg)\u001e8di&|g\u000e\u0005\u0003\u00026\u0005mRBAA\u001c\u0015\r\tIdU\u0001\u0005Y\u0006tw-\u0003\u0003\u0002>\u0005]\"aB%oi\u0016<WM\u001d\u0005\u0006y\u0016\u0001\ra\u001b\u0005\u0006}\u0016\u0001\ra[\u000b\u0005\u0003\u000b\nY\u0005\u0006\u0005\u0002H\u0005=\u00131KA,!\u0019)B%!\u0013\u0002NA\u0019q%a\u0013\u0005\u000b%2!\u0019\u0001\u0016\u0011\u000bU\u0011\u0014\u0011\n\u001b\t\re2\u0001\u0019AA)!\u0011Y$)!\u0013\t\r53\u0001\u0019AA+!%y\u0015qFA\u001a\u0003\u0013\n\u0019\u0004C\u0003}\r\u0001\u00071\u000e")
public final class PartitionHub {
    public static <T> Sink<T, Source<T, NotUsed>> of(Class<T> clazz, BiFunction<Integer, T, Integer> partitioner, int startAfterNrOfConsumers) {
        return PartitionHub$.MODULE$.of(clazz, partitioner, startAfterNrOfConsumers);
    }

    public static <T> Sink<T, Source<T, NotUsed>> of(Class<T> clazz, BiFunction<Integer, T, Integer> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        return PartitionHub$.MODULE$.of(clazz, partitioner, startAfterNrOfConsumers, bufferSize);
    }

    public static <T> Sink<T, Source<T, NotUsed>> ofStateful(Class<T> clazz, Supplier<ToLongBiFunction<ConsumerInfo, T>> partitioner, int startAfterNrOfConsumers) {
        return PartitionHub$.MODULE$.ofStateful(clazz, partitioner, startAfterNrOfConsumers);
    }

    public static <T> Sink<T, Source<T, NotUsed>> ofStateful(Class<T> clazz, Supplier<ToLongBiFunction<ConsumerInfo, T>> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        return PartitionHub$.MODULE$.ofStateful(clazz, partitioner, startAfterNrOfConsumers, bufferSize);
    }

    @DoNotInherit
    public static interface ConsumerInfo {
        public List<Object> getConsumerIds();

        public long consumerIdByIdx(int var1);

        public int queueSize(long var1);

        public int size();
    }
}

