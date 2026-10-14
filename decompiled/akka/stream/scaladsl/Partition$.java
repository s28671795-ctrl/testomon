/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Partition;
import scala.Function1;

public final class Partition$ {
    public static final Partition$ MODULE$ = new Partition$();

    public <T> Partition<T> apply(int outputPorts, Function1<T, Object> partitioner) {
        return new Partition<T>(outputPorts, partitioner, false);
    }

    private Partition$() {
    }
}

