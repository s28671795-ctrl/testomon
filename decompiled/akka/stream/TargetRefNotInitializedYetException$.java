/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.TargetRefNotInitializedYetException;
import java.io.Serializable;
import scala.runtime.AbstractFunction0;
import scala.runtime.ModuleSerializationProxy;

public final class TargetRefNotInitializedYetException$
extends AbstractFunction0<TargetRefNotInitializedYetException>
implements Serializable {
    public static final TargetRefNotInitializedYetException$ MODULE$ = new TargetRefNotInitializedYetException$();

    @Override
    public final String toString() {
        return "TargetRefNotInitializedYetException";
    }

    @Override
    public TargetRefNotInitializedYetException apply() {
        return new TargetRefNotInitializedYetException();
    }

    public boolean unapply(TargetRefNotInitializedYetException x$0) {
        return x$0 != null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TargetRefNotInitializedYetException$.class);
    }

    private TargetRefNotInitializedYetException$() {
    }
}

