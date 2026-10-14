/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler.gzip;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.GZIPContentDecoder;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.util.component.Destroyable;
import org.eclipse.jetty.util.compression.InflaterPool;

public class GzipHttpInputInterceptor
implements HttpInput.Interceptor,
Destroyable {
    private final Decoder _decoder;
    private ByteBuffer _chunk;

    public GzipHttpInputInterceptor(InflaterPool inflaterPool, ByteBufferPool pool, int bufferSize) {
        this(inflaterPool, pool, bufferSize, false);
    }

    public GzipHttpInputInterceptor(InflaterPool inflaterPool, ByteBufferPool pool, int bufferSize, boolean useDirectBuffers) {
        this._decoder = new Decoder(inflaterPool, pool, bufferSize, useDirectBuffers);
    }

    @Override
    public HttpInput.Content readFrom(HttpInput.Content content) {
        if (content.isSpecial()) {
            return content;
        }
        this._decoder.decodeChunks(content.getByteBuffer());
        final ByteBuffer chunk = this._chunk;
        if (chunk == null) {
            return null;
        }
        return new HttpInput.Content(chunk){

            @Override
            public void succeeded() {
                GzipHttpInputInterceptor.this._decoder.release(chunk);
            }

            @Override
            public void failed(Throwable x) {
                GzipHttpInputInterceptor.this._decoder.release(chunk);
            }
        };
    }

    @Override
    public void destroy() {
        this._decoder.destroy();
    }

    private class Decoder
    extends GZIPContentDecoder {
        private Decoder(InflaterPool inflaterPool, ByteBufferPool bufferPool, int bufferSize, boolean useDirectBuffers) {
            super(inflaterPool, bufferPool, bufferSize, useDirectBuffers);
        }

        @Override
        protected boolean decodedChunk(ByteBuffer chunk) {
            GzipHttpInputInterceptor.this._chunk = chunk;
            return true;
        }

        @Override
        public void decodeChunks(ByteBuffer compressed) {
            GzipHttpInputInterceptor.this._chunk = null;
            super.decodeChunks(compressed);
        }
    }
}

