/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import java.io.Serializable;
import scala.Function1;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.util.hashing.Hashing;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class Hashing$
implements Serializable {
    public static final Hashing$ MODULE$ = new Hashing$();

    public <T> Hashing.Default<T> default() {
        return new Hashing.Default();
    }

    public <T> Hashing<T> fromFunction(Function1<T, Object> f) {
        return new Hashing<T>(f){
            private final Function1 f$1;

            public int hash(T x) {
                return BoxesRunTime.unboxToInt(this.f$1.apply(x));
            }
            {
                this.f$1 = f$1;
            }
        };
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Hashing$.class);
    }

    private Hashing$() {
    }
}

