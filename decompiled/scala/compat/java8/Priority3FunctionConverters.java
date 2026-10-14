/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2qa\u0001\u0003\u0011\u0002\u0007\u00051\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0011\raCA\u000eQe&|'/\u001b;zg\u0019+hn\u0019;j_:\u001cuN\u001c<feR,'o\u001d\u0006\u0003\u000b\u0019\tQA[1wCbR!a\u0002\u0005\u0002\r\r|W\u000e]1u\u0015\u0005I\u0011!B:dC2\f7\u0001A\n\u0003\u00011\u0001\"!\u0004\b\u000e\u0003!I!a\u0004\u0005\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t!\u0003\u0005\u0002\u000e'%\u0011A\u0003\u0003\u0002\u0005+:LG/\u0001\ff]JL7\r[!t\u0015\u00064\u0018MQ5Gk:\u001cG/[8o+\u00119\u0002EK\u0017\u0015\u0005ay\u0003#B\r\u001d=%bS\"\u0001\u000e\u000b\u0005m!\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\n\u0005uQ\"!\u0007*jG\"4UO\\2uS>t''Q:CS\u001a+hn\u0019;j_:\u0004\"a\b\u0011\r\u0001\u0011)\u0011E\u0001b\u0001E\t\tA+\u0005\u0002$MA\u0011Q\u0002J\u0005\u0003K!\u0011qAT8uQ&tw\r\u0005\u0002\u000eO%\u0011\u0001\u0006\u0003\u0002\u0004\u0003:L\bCA\u0010+\t\u0015Y#A1\u0001#\u0005\u0005)\u0006CA\u0010.\t\u0015q#A1\u0001#\u0005\u0005\u0011\u0006\"\u0002\u0019\u0003\u0001\u0004\t\u0014AA:g!\u0015i!GH\u0015-\u0013\t\u0019\u0004BA\u0005Gk:\u001cG/[8oe!\u0012!!\u000e\t\u0003\u001bYJ!a\u000e\u0005\u0003\r%tG.\u001b8f\u0001")
public interface Priority3FunctionConverters {
    public static /* synthetic */ Function2 enrichAsJavaBiFunction$(Priority3FunctionConverters $this, Function2 sf) {
        return $this.enrichAsJavaBiFunction(sf);
    }

    default public <T, U, R> Function2<T, U, R> enrichAsJavaBiFunction(Function2<T, U, R> sf) {
        return sf;
    }

    public static void $init$(Priority3FunctionConverters $this) {
    }
}

