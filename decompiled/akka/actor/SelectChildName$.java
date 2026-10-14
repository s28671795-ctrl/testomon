/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SelectChildName;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class SelectChildName$
extends AbstractFunction1<String, SelectChildName>
implements Serializable {
    public static final SelectChildName$ MODULE$ = new SelectChildName$();

    @Override
    public final String toString() {
        return "SelectChildName";
    }

    @Override
    public SelectChildName apply(String name) {
        return new SelectChildName(name);
    }

    public Option<String> unapply(SelectChildName x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.name());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SelectChildName$.class);
    }

    private SelectChildName$() {
    }
}

