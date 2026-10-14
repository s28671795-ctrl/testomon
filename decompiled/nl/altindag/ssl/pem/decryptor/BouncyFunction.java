/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.decryptor;

import java.io.IOException;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCSException;

@FunctionalInterface
public interface BouncyFunction<T, R> {
    public R apply(T var1) throws OperatorCreationException, PKCSException, IOException;

    default public <V> BouncyFunction<T, V> andThen(BouncyFunction<? super R, ? extends V> after) {
        return t2 -> after.apply((R)this.apply(t2));
    }
}

