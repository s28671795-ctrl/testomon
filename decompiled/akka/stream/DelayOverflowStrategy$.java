/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.event.Logging$;
import akka.stream.DelayOverflowStrategy;
import akka.stream.OverflowStrategies;
import akka.stream.OverflowStrategies$EmitEarly$;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class DelayOverflowStrategy$
implements Serializable {
    public static final DelayOverflowStrategy$ MODULE$ = new DelayOverflowStrategy$();

    public DelayOverflowStrategy emitEarly() {
        return OverflowStrategies$EmitEarly$.MODULE$;
    }

    public DelayOverflowStrategy dropHead() {
        return new OverflowStrategies.DropHead(Logging$.MODULE$.DebugLevel());
    }

    public DelayOverflowStrategy dropTail() {
        return new OverflowStrategies.DropTail(Logging$.MODULE$.DebugLevel());
    }

    public DelayOverflowStrategy dropBuffer() {
        return new OverflowStrategies.DropBuffer(Logging$.MODULE$.DebugLevel());
    }

    public DelayOverflowStrategy dropNew() {
        return new OverflowStrategies.DropNew(Logging$.MODULE$.DebugLevel());
    }

    public DelayOverflowStrategy backpressure() {
        return new OverflowStrategies.Backpressure(Logging$.MODULE$.DebugLevel());
    }

    public DelayOverflowStrategy fail() {
        return new OverflowStrategies.Fail(Logging$.MODULE$.ErrorLevel());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DelayOverflowStrategy$.class);
    }

    private DelayOverflowStrategy$() {
    }
}

