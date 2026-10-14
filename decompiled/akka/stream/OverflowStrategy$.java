/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.event.Logging$;
import akka.stream.OverflowStrategies;
import akka.stream.OverflowStrategy;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class OverflowStrategy$
implements Serializable {
    public static final OverflowStrategy$ MODULE$ = new OverflowStrategy$();

    public OverflowStrategy dropHead() {
        return new OverflowStrategies.DropHead(Logging$.MODULE$.DebugLevel());
    }

    public OverflowStrategy dropTail() {
        return new OverflowStrategies.DropTail(Logging$.MODULE$.DebugLevel());
    }

    public OverflowStrategy dropBuffer() {
        return new OverflowStrategies.DropBuffer(Logging$.MODULE$.DebugLevel());
    }

    @Deprecated
    public OverflowStrategy dropNew() {
        return new OverflowStrategies.DropNew(Logging$.MODULE$.DebugLevel());
    }

    public OverflowStrategy backpressure() {
        return new OverflowStrategies.Backpressure(Logging$.MODULE$.DebugLevel());
    }

    public OverflowStrategy fail() {
        return new OverflowStrategies.Fail(Logging$.MODULE$.ErrorLevel());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(OverflowStrategy$.class);
    }

    private OverflowStrategy$() {
    }
}

