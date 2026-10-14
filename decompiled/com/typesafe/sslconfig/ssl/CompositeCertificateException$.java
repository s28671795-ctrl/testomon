/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.CompositeCertificateException;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;

public final class CompositeCertificateException$
implements Serializable {
    public static final CompositeCertificateException$ MODULE$ = new CompositeCertificateException$();

    public void unwrap(Throwable e, Function1<Throwable, BoxedUnit> block) {
        for (Throwable cause = e; cause != null; cause = cause.getCause()) {
            BoxedUnit boxedUnit;
            Throwable throwable = cause;
            if (throwable instanceof CompositeCertificateException) {
                CompositeCertificateException compositeCertificateException = (CompositeCertificateException)throwable;
                ArrayOps$.MODULE$.foreach$extension(Predef$.MODULE$.refArrayOps(compositeCertificateException.getSourceExceptions()), (Function1<Throwable, Object> & Serializable)sourceException -> {
                    block.apply(sourceException);
                    return BoxedUnit.UNIT;
                });
                boxedUnit = BoxedUnit.UNIT;
                continue;
            }
            boxedUnit = block.apply(throwable);
        }
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CompositeCertificateException$.class);
    }

    private CompositeCertificateException$() {
    }
}

