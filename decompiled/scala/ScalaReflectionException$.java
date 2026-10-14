/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.ScalaReflectionException;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ScalaReflectionException$
extends AbstractFunction1<String, ScalaReflectionException>
implements Serializable {
    public static final ScalaReflectionException$ MODULE$ = new ScalaReflectionException$();

    @Override
    public final String toString() {
        return "ScalaReflectionException";
    }

    @Override
    public ScalaReflectionException apply(String msg) {
        return new ScalaReflectionException(msg);
    }

    public Option<String> unapply(ScalaReflectionException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.msg());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ScalaReflectionException$.class);
    }

    private ScalaReflectionException$() {
    }
}

