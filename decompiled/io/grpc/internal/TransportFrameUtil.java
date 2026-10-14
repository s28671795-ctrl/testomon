/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import io.grpc.InternalMetadata;
import io.grpc.Metadata;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;

public final class TransportFrameUtil {
    private static final Logger logger = Logger.getLogger(TransportFrameUtil.class.getName());
    private static final byte[] binaryHeaderSuffixBytes = "-bin".getBytes(Charsets.US_ASCII);

    public static byte[][] toHttp2Headers(Metadata headers) {
        byte[][] serializedHeaders = InternalMetadata.serialize(headers);
        if (serializedHeaders == null) {
            return new byte[0][];
        }
        int k = 0;
        for (int i = 0; i < serializedHeaders.length; i += 2) {
            byte[] key = serializedHeaders[i];
            byte[] value = serializedHeaders[i + 1];
            if (TransportFrameUtil.endsWith(key, binaryHeaderSuffixBytes)) {
                serializedHeaders[k] = key;
                serializedHeaders[k + 1] = InternalMetadata.BASE64_ENCODING_OMIT_PADDING.encode(value).getBytes(Charsets.US_ASCII);
                k += 2;
                continue;
            }
            if (TransportFrameUtil.isSpecCompliantAscii(value)) {
                serializedHeaders[k] = key;
                serializedHeaders[k + 1] = value;
                k += 2;
                continue;
            }
            String keyString = new String(key, Charsets.US_ASCII);
            logger.warning("Metadata key=" + keyString + ", value=" + Arrays.toString(value) + " contains invalid ASCII characters");
        }
        if (k == serializedHeaders.length) {
            return serializedHeaders;
        }
        return (byte[][])Arrays.copyOfRange(serializedHeaders, 0, k);
    }

    @CheckReturnValue
    public static byte[][] toRawSerializedHeaders(byte[][] http2Headers) {
        for (int i = 0; i < http2Headers.length; i += 2) {
            byte[] key = http2Headers[i];
            byte[] value = http2Headers[i + 1];
            if (!TransportFrameUtil.endsWith(key, binaryHeaderSuffixBytes)) continue;
            for (int idx = 0; idx < value.length; ++idx) {
                if (value[idx] != 44) continue;
                return TransportFrameUtil.serializeHeadersWithCommasInBin(http2Headers, i);
            }
            byte[] decodedVal = BaseEncoding.base64().decode(new String(value, Charsets.US_ASCII));
            http2Headers[i + 1] = decodedVal;
        }
        return http2Headers;
    }

    private static byte[][] serializeHeadersWithCommasInBin(byte[][] http2Headers, int resumeFrom) {
        int i;
        ArrayList<byte[]> headerList = new ArrayList<byte[]>(http2Headers.length + 10);
        for (i = 0; i < resumeFrom; ++i) {
            headerList.add(http2Headers[i]);
        }
        for (i = resumeFrom; i < http2Headers.length; i += 2) {
            byte[] key = http2Headers[i];
            byte[] value = http2Headers[i + 1];
            if (!TransportFrameUtil.endsWith(key, binaryHeaderSuffixBytes)) {
                headerList.add(key);
                headerList.add(value);
                continue;
            }
            int prevIdx = 0;
            for (int idx = 0; idx <= value.length; ++idx) {
                if (idx != value.length && value[idx] != 44) continue;
                byte[] decodedVal = BaseEncoding.base64().decode(new String(value, prevIdx, idx - prevIdx, Charsets.US_ASCII));
                prevIdx = idx + 1;
                headerList.add(key);
                headerList.add(decodedVal);
            }
        }
        return (byte[][])headerList.toArray((T[])new byte[0][]);
    }

    private static boolean endsWith(byte[] subject, byte[] suffix) {
        int start2 = subject.length - suffix.length;
        if (start2 < 0) {
            return false;
        }
        for (int i = start2; i < subject.length; ++i) {
            if (subject[i] == suffix[i - start2]) continue;
            return false;
        }
        return true;
    }

    private static boolean isSpecCompliantAscii(byte[] subject) {
        for (byte b : subject) {
            if (b >= 32 && b <= 126) continue;
            return false;
        }
        return true;
    }

    private TransportFrameUtil() {
    }
}

