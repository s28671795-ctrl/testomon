/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class NoStackTrace$
implements Serializable {
    public static final NoStackTrace$ MODULE$ = new NoStackTrace$();
    private static boolean _noSuppression = false;

    static {
        _noSuppression = System.getProperty("scala.control.noTraceSuppression", "").equalsIgnoreCase("true");
    }

    public final boolean noSuppression() {
        return _noSuppression;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(NoStackTrace$.class);
    }

    private NoStackTrace$() {
    }
}

