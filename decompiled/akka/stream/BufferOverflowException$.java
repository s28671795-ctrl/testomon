/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.BufferOverflowException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class BufferOverflowException$
extends AbstractFunction1<String, BufferOverflowException>
implements Serializable {
    public static final BufferOverflowException$ MODULE$ = new BufferOverflowException$();

    @Override
    public final String toString() {
        return "BufferOverflowException";
    }

    @Override
    public BufferOverflowException apply(String msg) {
        return new BufferOverflowException(msg);
    }

    public Option<String> unapply(BufferOverflowException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BufferOverflowException$.class);
    }

    private BufferOverflowException$() {
    }
}

