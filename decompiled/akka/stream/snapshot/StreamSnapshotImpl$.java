/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.actor.ActorPath;
import akka.stream.snapshot.RunningInterpreter;
import akka.stream.snapshot.StreamSnapshotImpl;
import akka.stream.snapshot.UninitializedInterpreter;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

public final class StreamSnapshotImpl$
extends AbstractFunction3<ActorPath, Seq<RunningInterpreter>, Seq<UninitializedInterpreter>, StreamSnapshotImpl>
implements Serializable {
    public static final StreamSnapshotImpl$ MODULE$ = new StreamSnapshotImpl$();

    @Override
    public final String toString() {
        return "StreamSnapshotImpl";
    }

    @Override
    public StreamSnapshotImpl apply(ActorPath self, Seq<RunningInterpreter> activeInterpreters, Seq<UninitializedInterpreter> newShells) {
        return new StreamSnapshotImpl(self, activeInterpreters, newShells);
    }

    public Option<Tuple3<ActorPath, Seq<RunningInterpreter>, Seq<UninitializedInterpreter>>> unapply(StreamSnapshotImpl x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<ActorPath, Seq<RunningInterpreter>, Seq<UninitializedInterpreter>>>(new Tuple3<ActorPath, Seq<RunningInterpreter>, Seq<UninitializedInterpreter>>(x$0.self(), x$0.activeInterpreters(), x$0.newShells()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(StreamSnapshotImpl$.class);
    }

    private StreamSnapshotImpl$() {
    }
}

