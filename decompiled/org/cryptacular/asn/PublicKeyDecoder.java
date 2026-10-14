/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.asn;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.cryptacular.EncodingException;
import org.cryptacular.asn.ASN1Decoder;
import org.cryptacular.util.PemUtil;

public class PublicKeyDecoder
implements ASN1Decoder<AsymmetricKeyParameter> {
    @Override
    public AsymmetricKeyParameter decode(byte[] encoded, Object ... args2) {
        try {
            if (PemUtil.isPem(encoded)) {
                return PublicKeyFactory.createKey(PemUtil.decode(encoded));
            }
            return PublicKeyFactory.createKey(new ASN1InputStream(encoded).readObject().getEncoded());
        }
        catch (IOException e) {
            throw new EncodingException("ASN.1 decoding error", e);
        }
    }
}

