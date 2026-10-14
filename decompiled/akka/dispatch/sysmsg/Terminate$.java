/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.Terminate;
import java.io.Serializable;
import scala.runtime.AbstractFunction0;
import scala.runtime.ModuleSerializationProxy;

public final class Terminate$
extends AbstractFunction0<Terminate>
implements Serializable {
    public static final Terminate$ MODULE$ = new Terminate$();

    @Override
    public final String toString() {
        return "Terminate";
    }

    @Override
    public Terminate apply() {
        return new Terminate();
    }

    public boolean unapply(Terminate x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Terminate$.class);
    }

    private Terminate$() {
    }
}

