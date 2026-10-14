/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import scala.Function0;
import scala.concurrent.AwaitPermission$;
import scala.concurrent.BlockContext$;

public final class package$ {
    public static final package$ MODULE$ = new package$();

    public final <T> T blocking(Function0<T> body2) throws Exception {
        return BlockContext$.MODULE$.current().blockOn(body2, AwaitPermission$.MODULE$);
    }

    private package$() {
    }
}

