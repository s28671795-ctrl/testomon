/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.stream.snapshot.ConnectionSnapshot;
import akka.stream.snapshot.ConnectionSnapshotImpl;
import akka.stream.snapshot.LogicSnapshot;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.AbstractFunction4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class ConnectionSnapshotImpl$
extends AbstractFunction4<Object, LogicSnapshot, LogicSnapshot, ConnectionSnapshot.ConnectionState, ConnectionSnapshotImpl>
implements Serializable {
    public static final ConnectionSnapshotImpl$ MODULE$ = new ConnectionSnapshotImpl$();

    @Override
    public final String toString() {
        return "ConnectionSnapshotImpl";
    }

    @Override
    public ConnectionSnapshotImpl apply(int id, LogicSnapshot in, LogicSnapshot out, ConnectionSnapshot.ConnectionState state) {
        return new ConnectionSnapshotImpl(id, in, out, state);
    }

    public Option<Tuple4<Object, LogicSnapshot, LogicSnapshot, ConnectionSnapshot.ConnectionState>> unapply(ConnectionSnapshotImpl x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Object, LogicSnapshot, LogicSnapshot, ConnectionSnapshot.ConnectionState>>(new Tuple4<Integer, LogicSnapshot, LogicSnapshot, ConnectionSnapshot.ConnectionState>(BoxesRunTime.boxToInteger(x$0.id()), x$0.in(), x$0.out(), x$0.state()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ConnectionSnapshotImpl$.class);
    }

    private ConnectionSnapshotImpl$() {
    }
}

