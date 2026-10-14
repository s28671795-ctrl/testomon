/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanOutShape;
import akka.stream.javadsl.Broadcast$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005};QAB\u0004\t\u000291Q\u0001E\u0004\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002mAQAG\u0001\u0005\u0002uBQAG\u0001\u0005\u0002\u0011\u000b\u0011B\u0011:pC\u0012\u001c\u0017m\u001d;\u000b\u0005!I\u0011a\u00026bm\u0006$7\u000f\u001c\u0006\u0003\u0015-\taa\u001d;sK\u0006l'\"\u0001\u0007\u0002\t\u0005\\7.Y\u0002\u0001!\ty\u0011!D\u0001\b\u0005%\u0011%o\\1eG\u0006\u001cHo\u0005\u0002\u0002%A\u00111CF\u0007\u0002))\tQ#A\u0003tG\u0006d\u0017-\u0003\u0002\u0018)\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\b\u0002\r\r\u0014X-\u0019;f+\tab\u0005F\u0002\u001ega\u0002BAH\u0010\"_5\t\u0011\"\u0003\u0002!\u0013\t)qI]1qQB!aD\t\u0013%\u0013\t\u0019\u0013B\u0001\nV]&4wN]7GC:|U\u000f^*iCB,\u0007CA\u0013'\u0019\u0001!QaJ\u0002C\u0002!\u0012\u0011\u0001V\t\u0003S1\u0002\"a\u0005\u0016\n\u0005-\"\"a\u0002(pi\"Lgn\u001a\t\u0003'5J!A\f\u000b\u0003\u0007\u0005s\u0017\u0010\u0005\u00021c5\t1\"\u0003\u00023\u0017\t9aj\u001c;Vg\u0016$\u0007\"\u0002\u001b\u0004\u0001\u0004)\u0014aC8viB,HoQ8v]R\u0004\"a\u0005\u001c\n\u0005]\"\"aA%oi\")\u0011h\u0001a\u0001u\u0005YQ-Y4fe\u000e\u000bgnY3m!\t\u00192(\u0003\u0002=)\t9!i\\8mK\u0006tWC\u0001 C)\ty4\t\u0005\u0003\u001f?\u0001{\u0003\u0003\u0002\u0010#\u0003\u0006\u0003\"!\n\"\u0005\u000b\u001d\"!\u0019\u0001\u0015\t\u000bQ\"\u0001\u0019A\u001b\u0016\u0005\u0015KEc\u0001$K=B!adH$0!\u0011q\"\u0005\u0013%\u0011\u0005\u0015JE!B\u0014\u0006\u0005\u0004A\u0003\"B&\u0006\u0001\u0004a\u0015!B2mCjT\bcA'U\u0011:\u0011aJ\u0015\t\u0003\u001fRi\u0011\u0001\u0015\u0006\u0003#6\ta\u0001\u0010:p_Rt\u0014BA*\u0015\u0003\u0019\u0001&/\u001a3fM&\u0011QK\u0016\u0002\u0006\u00072\f7o\u001d\u0006\u0003'RA#A\u0013-\u0011\u0005ecV\"\u0001.\u000b\u0005m[\u0011\u0001B;uS2L!!\u0018.\u0003\rUtWo]3e\u0011\u0015!T\u00011\u00016\u0001")
public final class Broadcast {
    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount) {
        return Broadcast$.MODULE$.create(clazz, outputCount);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount) {
        return Broadcast$.MODULE$.create(outputCount);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, boolean eagerCancel) {
        return Broadcast$.MODULE$.create(outputCount, eagerCancel);
    }
}

