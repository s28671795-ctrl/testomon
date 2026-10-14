/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SelectChildPattern;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class SelectChildPattern$
extends AbstractFunction1<String, SelectChildPattern>
implements Serializable {
    public static final SelectChildPattern$ MODULE$ = new SelectChildPattern$();

    @Override
    public final String toString() {
        return "SelectChildPattern";
    }

    @Override
    public SelectChildPattern apply(String patternStr) {
        return new SelectChildPattern(patternStr);
    }

    public Option<String> unapply(SelectChildPattern x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.patternStr());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SelectChildPattern$.class);
    }

    private SelectChildPattern$() {
    }
}

