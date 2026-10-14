/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.handler.codec.DecoderResult;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpObject;
import io.grpc.netty.shaded.io.netty.util.internal.ObjectUtil;

public class DefaultHttpObject
implements HttpObject {
    private static final int HASH_CODE_PRIME = 31;
    private DecoderResult decoderResult = DecoderResult.SUCCESS;

    protected DefaultHttpObject() {
    }

    @Override
    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    @Override
    @Deprecated
    public DecoderResult getDecoderResult() {
        return this.decoderResult();
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        this.decoderResult = ObjectUtil.checkNotNull(decoderResult, "decoderResult");
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + this.decoderResult.hashCode();
        return result;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttpObject)) {
            return false;
        }
        DefaultHttpObject other = (DefaultHttpObject)o;
        return this.decoderResult().equals(other.decoderResult());
    }
}

