/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.InvalidSequenceNumberException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class InvalidSequenceNumberException$
extends AbstractFunction3<Object, Object, String, InvalidSequenceNumberException>
implements Serializable {
    public static final InvalidSequenceNumberException$ MODULE$ = new InvalidSequenceNumberException$();

    @Override
    public final String toString() {
        return "InvalidSequenceNumberException";
    }

    @Override
    public InvalidSequenceNumberException apply(long expectedSeqNr, long gotSeqNr, String msg) {
        return new InvalidSequenceNumberException(expectedSeqNr, gotSeqNr, msg);
    }

    public Option<Tuple3<Object, Object, String>> unapply(InvalidSequenceNumberException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Object, Object, String>>(new Tuple3<Long, Long, String>(BoxesRunTime.boxToLong(x$0.expectedSeqNr()), BoxesRunTime.boxToLong(x$0.gotSeqNr()), x$0.msg()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(InvalidSequenceNumberException$.class);
    }

    private InvalidSequenceNumberException$() {
    }
}

