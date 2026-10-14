/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.annotation.InternalApi;
import akka.util.ByteString;
import akka.util.ByteString$;
import scala.math.Numeric$IntIsIntegral$;
import scala.runtime.ScalaRunTime$;

@InternalApi
public final class GzipDecompressor$ {
    public static final GzipDecompressor$ MODULE$ = new GzipDecompressor$();
    private static final ByteString Header = ByteString$.MODULE$.apply(ScalaRunTime$.MODULE$.wrapIntArray(new int[]{31, 139, 8, 0, 0, 0, 0, 0, 0, 0}), Numeric$IntIsIntegral$.MODULE$);

    public ByteString Header() {
        return Header;
    }

    private GzipDecompressor$() {
    }
}

