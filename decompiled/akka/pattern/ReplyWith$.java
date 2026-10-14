/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.pattern.ReplyWith;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ReplyWith$
extends AbstractFunction1<Object, ReplyWith>
implements Serializable {
    public static final ReplyWith$ MODULE$ = new ReplyWith$();

    @Override
    public final String toString() {
        return "ReplyWith";
    }

    @Override
    public ReplyWith apply(Object msg) {
        return new ReplyWith(msg);
    }

    public Option<Object> unapply(ReplyWith x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ReplyWith$.class);
    }

    private ReplyWith$() {
    }
}

