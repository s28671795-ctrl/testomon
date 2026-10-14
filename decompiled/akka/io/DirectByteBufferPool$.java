/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import scala.Function1;
import scala.Option;
import scala.runtime.BoxedUnit;
import scala.util.control.NonFatal$;

public final class DirectByteBufferPool$ {
    public static final DirectByteBufferPool$ MODULE$ = new DirectByteBufferPool$();
    private static final Function1<ByteBuffer, BoxedUnit> CleanDirectBuffer = DirectByteBufferPool$.liftedTree1$1();

    private Function1<ByteBuffer, BoxedUnit> CleanDirectBuffer() {
        return CleanDirectBuffer;
    }

    public void tryCleanDirectByteBuffer(ByteBuffer byteBuffer) {
        this.CleanDirectBuffer().apply(byteBuffer);
    }

    public static final /* synthetic */ void $anonfun$CleanDirectBuffer$1(Method cleanerMethod$1, Method cleanMethod$1, ByteBuffer bb) {
        try {
            if (bb.isDirect()) {
                Object cleaner = cleanerMethod$1.invoke((Object)bb, new Object[0]);
                cleanMethod$1.invoke(cleaner, new Object[0]);
            }
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
            }
            throw throwable;
        }
    }

    public static final /* synthetic */ void $anonfun$CleanDirectBuffer$2(ByteBuffer x$1) {
    }

    private static final /* synthetic */ Function1 liftedTree1$1() {
        Function1<ByteBuffer, Object> & Serializable intersect;
        try {
            Method cleanerMethod = Class.forName("java.nio.DirectByteBuffer").getMethod("cleaner", new Class[0]);
            cleanerMethod.setAccessible(true);
            Method cleanMethod = Class.forName("sun.misc.Cleaner").getMethod("clean", new Class[0]);
            cleanMethod.setAccessible(true);
            intersect = (Function1<ByteBuffer, Object> & Serializable)bb -> {
                DirectByteBufferPool$.$anonfun$CleanDirectBuffer$1(cleanerMethod, cleanMethod, bb);
                return BoxedUnit.UNIT;
            };
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                intersect = (Function1<ByteBuffer, Object> & Serializable)x$1 -> {
                    DirectByteBufferPool$.$anonfun$CleanDirectBuffer$2(x$1);
                    return BoxedUnit.UNIT;
                };
            }
            throw throwable;
        }
        return intersect;
    }

    private DirectByteBufferPool$() {
    }
}

