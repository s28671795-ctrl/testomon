/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.stream.Attributes;
import akka.stream.snapshot.LogicSnapshotImpl;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class LogicSnapshotImpl$
extends AbstractFunction3<Object, String, Attributes, LogicSnapshotImpl>
implements Serializable {
    public static final LogicSnapshotImpl$ MODULE$ = new LogicSnapshotImpl$();

    @Override
    public final String toString() {
        return "LogicSnapshotImpl";
    }

    @Override
    public LogicSnapshotImpl apply(int index, String label, Attributes attributes) {
        return new LogicSnapshotImpl(index, label, attributes);
    }

    public Option<Tuple3<Object, String, Attributes>> unapply(LogicSnapshotImpl x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Object, String, Attributes>>(new Tuple3<Integer, String, Attributes>(BoxesRunTime.boxToInteger(x$0.index()), x$0.label(), x$0.attributes()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LogicSnapshotImpl$.class);
    }

    private LogicSnapshotImpl$() {
    }
}

