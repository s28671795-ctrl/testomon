/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.SerializedSuspendableExecutionContext;
import java.io.Serializable;
import scala.concurrent.ExecutionContext;
import scala.runtime.ModuleSerializationProxy;

public final class SerializedSuspendableExecutionContext$
implements Serializable {
    public static final SerializedSuspendableExecutionContext$ MODULE$ = new SerializedSuspendableExecutionContext$();

    public final int Off() {
        return 0;
    }

    public final int On() {
        return 1;
    }

    public final int Suspended() {
        return 2;
    }

    public SerializedSuspendableExecutionContext apply(int throughput, ExecutionContext context) {
        ExecutionContext executionContext;
        ExecutionContext executionContext2 = context;
        if (executionContext2 instanceof SerializedSuspendableExecutionContext) {
            SerializedSuspendableExecutionContext serializedSuspendableExecutionContext = (SerializedSuspendableExecutionContext)executionContext2;
            executionContext = serializedSuspendableExecutionContext.context();
        } else {
            executionContext = executionContext2;
        }
        return new SerializedSuspendableExecutionContext(throughput, executionContext);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SerializedSuspendableExecutionContext$.class);
    }

    private SerializedSuspendableExecutionContext$() {
    }
}

