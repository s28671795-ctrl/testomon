/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import scala.reflect.ScalaSignature;
import scala.runtime.AbstractFunction1;

@ScalaSignature(bytes="\u0006\u0005\u00193Q\u0001B\u0003\u0002\u0002)AQa\t\u0001\u0005\u0002\u0011BQa\n\u0001\u0005\u0002!BQa\u000b\u0001\u0005\u00021\u0012a!T1qa\u0016\u0014(B\u0001\u0004\b\u0003!!\u0017n\u001d9bi\u000eD'\"\u0001\u0005\u0002\t\u0005\\7.Y\u0002\u0001+\rYa#I\n\u0003\u00011\u0001B!\u0004\n\u0015A5\taB\u0003\u0002\u0010!\u00059!/\u001e8uS6,'\"A\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005Mq!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA\u0011QC\u0006\u0007\u0001\t\u00199\u0002\u0001#b\u00011\t\tA+\u0005\u0002\u001a;A\u0011!dG\u0007\u0002!%\u0011A\u0004\u0005\u0002\b\u001d>$\b.\u001b8h!\tQb$\u0003\u0002 !\t\u0019\u0011I\\=\u0011\u0005U\tCA\u0002\u0012\u0001\t\u000b\u0007\u0001DA\u0001S\u0003\u0019a\u0014N\\5u}Q\tQ\u0005\u0005\u0003'\u0001Q\u0001S\"A\u0003\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0005\u0001J\u0003\"\u0002\u0016\u0003\u0001\u0004!\u0012!\u00039be\u0006lW\r^3s\u00031\u0019\u0007.Z2lK\u0012\f\u0005\u000f\u001d7z)\t\u0001S\u0006C\u0003+\u0007\u0001\u0007A\u0003\u000b\u0002._A\u0011\u0001gM\u0007\u0002c)\u0011!gB\u0001\u0005kRLG.\u0003\u00025c\t1QO\\;tK\u0012D3a\u0001\u001cF!\rQr'O\u0005\u0003qA\u0011a\u0001\u001e5s_^\u001c\bC\u0001\u001eC\u001d\tY\u0004I\u0004\u0002=\u007f5\tQH\u0003\u0002?\u0013\u00051AH]8pizJ\u0011!E\u0005\u0003\u0003B\tq\u0001]1dW\u0006<W-\u0003\u0002D\t\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003\u0003B\u0019\u0013!\u000f")
public abstract class Mapper<T, R>
extends AbstractFunction1<T, R> {
    @Override
    public R apply(T parameter) {
        return this.checkedApply(parameter);
    }

    public R checkedApply(T parameter) throws Throwable {
        throw new UnsupportedOperationException("Mapper.checkedApply has not been implemented");
    }
}

