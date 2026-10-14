/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import akka.annotation.InternalApi;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

@InternalApi
final class OnSpinWait {
    private static final MethodHandle handle;

    OnSpinWait() {
    }

    public static final void spinWait() throws Throwable {
        handle.invoke();
    }

    static {
        MethodHandle methodHandle;
        MethodHandle methodHandle2 = MethodHandles.constant(Object.class, null).asType(MethodType.methodType(Void.TYPE));
        try {
            methodHandle = MethodHandles.lookup().findStatic(Thread.class, "onSpinWait", MethodType.methodType(Void.TYPE));
        }
        catch (NoSuchMethodException noSuchMethodException) {
            methodHandle = methodHandle2;
        }
        catch (IllegalAccessException illegalAccessException) {
            methodHandle = methodHandle2;
        }
        handle = methodHandle;
    }
}

