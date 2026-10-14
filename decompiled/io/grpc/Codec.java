/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Compressor;
import io.grpc.Decompressor;
import io.grpc.ExperimentalApi;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
public interface Codec
extends Compressor,
Decompressor {

    public static final class Identity
    implements Codec {
        public static final Codec NONE = new Identity();

        @Override
        public InputStream decompress(InputStream is) {
            return is;
        }

        @Override
        public String getMessageEncoding() {
            return "identity";
        }

        @Override
        public OutputStream compress(OutputStream os) {
            return os;
        }

        private Identity() {
        }
    }

    public static final class Gzip
    implements Codec {
        @Override
        public String getMessageEncoding() {
            return "gzip";
        }

        @Override
        public OutputStream compress(OutputStream os) throws IOException {
            return new GZIPOutputStream(os);
        }

        @Override
        public InputStream decompress(InputStream is) throws IOException {
            return new GZIPInputStream(is);
        }
    }
}

