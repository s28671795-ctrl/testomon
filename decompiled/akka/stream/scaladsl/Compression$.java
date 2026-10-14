/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.impl.io.compression.CompressionUtils$;
import akka.stream.impl.io.compression.Compressor;
import akka.stream.impl.io.compression.DeflateCompressor;
import akka.stream.impl.io.compression.DeflateDecompressor;
import akka.stream.impl.io.compression.GzipCompressor;
import akka.stream.impl.io.compression.GzipDecompressor;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.util.ByteString;
import java.io.Serializable;
import scala.Function0;

public final class Compression$ {
    public static final Compression$ MODULE$ = new Compression$();

    public final int MaxBytesPerChunkDefault() {
        return 65536;
    }

    public Flow<ByteString, ByteString, NotUsed> gzip() {
        return this.gzip(9);
    }

    public Flow<ByteString, ByteString, NotUsed> gzip(int level) {
        return CompressionUtils$.MODULE$.compressorFlow((Function0<Compressor>)(Function0<GzipCompressor> & Serializable)() -> new GzipCompressor(level));
    }

    public Flow<ByteString, ByteString, NotUsed> gunzip(int maxBytesPerChunk) {
        return ((Flow)Flow$.MODULE$.apply().via((Graph)new GzipDecompressor(maxBytesPerChunk))).named("gunzip");
    }

    public int gunzip$default$1() {
        return 65536;
    }

    public Flow<ByteString, ByteString, NotUsed> deflate() {
        return this.deflate(9, false);
    }

    public Flow<ByteString, ByteString, NotUsed> deflate(int level, boolean nowrap) {
        return CompressionUtils$.MODULE$.compressorFlow((Function0<Compressor>)(Function0<DeflateCompressor> & Serializable)() -> new DeflateCompressor(level, nowrap));
    }

    public Flow<ByteString, ByteString, NotUsed> inflate(int maxBytesPerChunk) {
        return this.inflate(maxBytesPerChunk, false);
    }

    public Flow<ByteString, ByteString, NotUsed> inflate(int maxBytesPerChunk, boolean nowrap) {
        return ((Flow)Flow$.MODULE$.apply().via((Graph)new DeflateDecompressor(maxBytesPerChunk, nowrap))).named("inflate");
    }

    public int inflate$default$1() {
        return 65536;
    }

    private Compression$() {
    }
}

