/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import scala.util.control.NoStackTrace;

@InternalApi
public final class ByteStringParser$ {
    public static final ByteStringParser$ MODULE$ = new ByteStringParser$();
    private static final int CompactionThreshold = 16;
    private static final Exception NeedMoreData = new NoStackTrace(){

        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }
        {
            NoStackTrace.$init$(this);
        }
    };

    public int CompactionThreshold() {
        return CompactionThreshold;
    }

    private final boolean Recurse() {
        return true;
    }

    private final boolean DontRecurse() {
        return false;
    }

    public Exception NeedMoreData() {
        return NeedMoreData;
    }

    private ByteStringParser$() {
    }
}

