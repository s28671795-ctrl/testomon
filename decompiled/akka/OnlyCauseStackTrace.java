/*
 * Decompiled with CFR 0.152.
 */
package akka;

import scala.Array$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00192\u0001b\u0001\u0003\u0011\u0002\u0007\u0005q!\t\u0005\u0006\u001d\u0001!\ta\u0004\u0005\u0006'\u0001!\t\u0005\u0006\u0002\u0014\u001f:d\u0017pQ1vg\u0016\u001cF/Y2l)J\f7-\u001a\u0006\u0002\u000b\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\u0005\u0011\u0005%aQ\"\u0001\u0006\u000b\u0003-\tQa]2bY\u0006L!!\u0004\u0006\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t\u0001\u0003\u0005\u0002\n#%\u0011!C\u0003\u0002\u0005+:LG/\u0001\tgS2d\u0017J\\*uC\u000e\\GK]1dKR\tQ\u0003\u0005\u0002\u0017=9\u0011q\u0003\b\b\u00031mi\u0011!\u0007\u0006\u00035\u0019\ta\u0001\u0010:p_Rt\u0014\"A\u0006\n\u0005uQ\u0011a\u00029bG.\fw-Z\u0005\u0003?\u0001\u0012\u0011\u0002\u00165s_^\f'\r\\3\u000b\u0005uQ!c\u0001\u0012%+\u0019!1\u0005\u0001\u0001\"\u00051a$/\u001a4j]\u0016lWM\u001c;?!\t)\u0003!D\u0001\u0005\u0001")
public interface OnlyCauseStackTrace {
    public static /* synthetic */ Throwable fillInStackTrace$(OnlyCauseStackTrace $this) {
        return $this.fillInStackTrace();
    }

    default public Throwable fillInStackTrace() {
        Throwable throwable = ((Throwable)((Object)this)).getCause();
        ((Throwable)((Object)this)).setStackTrace(throwable == null ? (StackTraceElement[])Array$.MODULE$.empty(ClassTag$.MODULE$.apply(StackTraceElement.class)) : throwable.getStackTrace());
        return (Throwable)((Object)this);
    }

    public static void $init$(OnlyCauseStackTrace $this) {
    }
}

