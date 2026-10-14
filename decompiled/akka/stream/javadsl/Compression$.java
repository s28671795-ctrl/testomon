/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import akka.util.ByteString;

public final class Compression$ {
    public static final Compression$ MODULE$ = new Compression$();

    public Flow<ByteString, ByteString, NotUsed> gunzip(int maxBytesPerChunk) {
        return akka.stream.scaladsl.Compression$.MODULE$.gunzip(maxBytesPerChunk).asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> inflate(int maxBytesPerChunk) {
        return this.inflate(maxBytesPerChunk, false);
    }

    public Flow<ByteString, ByteString, NotUsed> inflate(int maxBytesPerChunk, boolean nowrap) {
        return akka.stream.scaladsl.Compression$.MODULE$.inflate(maxBytesPerChunk, nowrap).asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> gzip() {
        return akka.stream.scaladsl.Compression$.MODULE$.gzip().asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> gzip(int level) {
        return akka.stream.scaladsl.Compression$.MODULE$.gzip(level).asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> deflate() {
        return akka.stream.scaladsl.Compression$.MODULE$.deflate().asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> deflate(int level, boolean nowrap) {
        return akka.stream.scaladsl.Compression$.MODULE$.deflate(level, nowrap).asJava();
    }

    private Compression$() {
    }
}

