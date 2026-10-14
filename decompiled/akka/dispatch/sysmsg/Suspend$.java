/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.Suspend;
import java.io.Serializable;
import scala.runtime.AbstractFunction0;
import scala.runtime.ModuleSerializationProxy;

public final class Suspend$
extends AbstractFunction0<Suspend>
implements Serializable {
    public static final Suspend$ MODULE$ = new Suspend$();

    @Override
    public final String toString() {
        return "Suspend";
    }

    @Override
    public Suspend apply() {
        return new Suspend();
    }

    public boolean unapply(Suspend x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Suspend$.class);
    }

    private Suspend$() {
    }
}

