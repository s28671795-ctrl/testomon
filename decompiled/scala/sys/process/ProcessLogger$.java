/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.File;
import scala.Function0;
import scala.Function1;
import scala.runtime.BoxedUnit;
import scala.sys.process.FileProcessLogger;
import scala.sys.process.ProcessLogger;

public final class ProcessLogger$ {
    public static final ProcessLogger$ MODULE$ = new ProcessLogger$();

    public FileProcessLogger apply(File file) {
        return new FileProcessLogger(file);
    }

    public ProcessLogger apply(Function1<String, BoxedUnit> fn) {
        return new ProcessLogger(fn, fn){
            private final Function1 fout$1;
            private final Function1 ferr$1;

            public void out(Function0<String> s2) {
                this.fout$1.apply(s2.apply());
            }

            public void err(Function0<String> s2) {
                this.ferr$1.apply(s2.apply());
            }

            public <T> T buffer(Function0<T> f) {
                return f.apply();
            }
            {
                this.fout$1 = fout$1;
                this.ferr$1 = ferr$1;
            }
        };
    }

    public ProcessLogger apply(Function1<String, BoxedUnit> fout, Function1<String, BoxedUnit> ferr) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    private ProcessLogger$() {
    }
}

