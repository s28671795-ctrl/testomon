/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Serializable;
import scala.Console$;
import scala.Function0;
import scala.Function1;
import scala.PartialFunction;
import scala.collection.immutable.Seq;
import scala.runtime.ScalaRunTime$;
import scala.sys.SystemProperties;

public final class processInternal$ {
    public static final processInternal$ MODULE$ = new processInternal$();
    private static final boolean processDebug = new SystemProperties().contains("scala.process.debug");

    static {
        MODULE$.dbg(ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{"Initializing process package."}));
    }

    public final boolean processDebug() {
        return processDebug;
    }

    public <T> PartialFunction<Throwable, T> onError(Function1<Throwable, T> handler) {
        return new Serializable(handler){
            private static final long serialVersionUID = 0L;
            private final Function1 handler$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                return (B1)this.handler$1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                return true;
            }
            {
                this.handler$1 = handler$1;
            }
        };
    }

    public <T> PartialFunction<Throwable, T> onIOInterrupt(Function0<T> handler) {
        return new Serializable(handler){
            private static final long serialVersionUID = 0L;
            private final Function0 handler$2;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                if (x1 instanceof InterruptedIOException) {
                    return (B1)this.handler$2.apply();
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                return x1 instanceof InterruptedIOException;
            }
            {
                this.handler$2 = handler$2;
            }
        };
    }

    public <T> PartialFunction<Throwable, T> onInterrupt(Function0<T> handler) {
        return new Serializable(handler){
            private static final long serialVersionUID = 0L;
            private final Function0 handler$3;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                if (x1 instanceof InterruptedException) {
                    return (B1)this.handler$3.apply();
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                return x1 instanceof InterruptedException;
            }
            {
                this.handler$3 = handler$3;
            }
        };
    }

    public <T> PartialFunction<Throwable, T> ioFailure(Function1<IOException, T> handler) {
        return new Serializable(handler){
            private static final long serialVersionUID = 0L;
            private final Function1 handler$4;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                if (x1 instanceof IOException) {
                    IOException iOException = (IOException)x1;
                    return (B1)this.handler$4.apply(iOException);
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                return x1 instanceof IOException;
            }
            {
                this.handler$4 = handler$4;
            }
        };
    }

    /*
     * WARNING - void declaration
     */
    public void dbg(Seq<Object> msgs) {
        if (this.processDebug()) {
            void mkString_sep;
            String string = " ";
            if (msgs == null) {
                throw null;
            }
            string = null;
            Console$.MODULE$.println(new StringBuilder(10).append("[process] ").append(msgs.mkString("", (String)mkString_sep, "")).toString());
            return;
        }
    }

    private processInternal$() {
    }
}

