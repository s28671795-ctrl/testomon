/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.annotation.InternalApi;
import akka.util.ByteString;
import akka.util.ByteString$;
import akka.util.ByteStringBuilder;
import java.util.zip.Deflater;
import scala.Predef$;

@InternalApi
public final class DeflateCompressor$ {
    public static final DeflateCompressor$ MODULE$ = new DeflateCompressor$();
    private static final int MinBufferSize = 1024;

    public int $lessinit$greater$default$1() {
        return 9;
    }

    public boolean $lessinit$greater$default$2() {
        return false;
    }

    public int MinBufferSize() {
        return MinBufferSize;
    }

    public ByteString drainDeflater(Deflater deflater, byte[] buffer, ByteStringBuilder result) {
        int len;
        while ((len = deflater.deflate(buffer)) > 0) {
            result.$plus$plus$eq(ByteString$.MODULE$.fromArray(buffer, 0, len));
        }
        Predef$.MODULE$.require(deflater.needsInput());
        return result.result();
    }

    public ByteStringBuilder drainDeflater$default$3() {
        return new ByteStringBuilder();
    }

    private DeflateCompressor$() {
    }
}

