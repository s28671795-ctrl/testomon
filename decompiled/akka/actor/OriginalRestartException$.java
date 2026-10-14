/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.PostRestartException;
import scala.MatchError;
import scala.Option;
import scala.Some;

public final class OriginalRestartException$ {
    public static final OriginalRestartException$ MODULE$ = new OriginalRestartException$();
    private static final long serialVersionUID = 1L;

    public Option<Throwable> unapply(PostRestartException ex) {
        return this.rec$1(ex);
    }

    private final Option rec$1(PostRestartException ex) {
        Throwable e;
        PostRestartException postRestartException;
        while ((postRestartException = ex) != null && (e = postRestartException.originalCause()) instanceof PostRestartException) {
            PostRestartException postRestartException2;
            ex = postRestartException2 = (PostRestartException)e;
        }
        if (postRestartException != null) {
            Throwable e2 = postRestartException.originalCause();
            return new Some<Throwable>(e2);
        }
        throw new MatchError((Object)postRestartException);
    }

    private OriginalRestartException$() {
    }
}

