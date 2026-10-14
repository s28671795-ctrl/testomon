/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.asn;

import org.cryptacular.EncodingException;
import org.cryptacular.asn.ASN1Decoder;
import org.cryptacular.util.PemUtil;

public abstract class AbstractPrivateKeyDecoder<T>
implements ASN1Decoder<T> {
    @Override
    public T decode(byte[] encoded, Object ... args2) throws EncodingException {
        try {
            byte[] asn1Bytes = args2 != null && args2.length > 0 && args2[0] instanceof char[] ? this.decryptKey(encoded, (char[])args2[0]) : this.tryConvertPem(encoded);
            return this.decodeASN1(asn1Bytes);
        }
        catch (EncodingException e) {
            throw e;
        }
        catch (RuntimeException e) {
            throw new EncodingException("Key encoding error", e);
        }
    }

    protected byte[] tryConvertPem(byte[] input) {
        if (PemUtil.isPem(input)) {
            return PemUtil.decode(input);
        }
        return input;
    }

    protected abstract byte[] decryptKey(byte[] var1, char[] var2);

    protected abstract T decodeASN1(byte[] var1);
}

