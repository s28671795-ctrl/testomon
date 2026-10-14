/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.asn;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.cryptacular.EncodingException;
import org.cryptacular.asn.AbstractPrivateKeyDecoder;
import org.cryptacular.pbe.OpenSSLAlgorithm;
import org.cryptacular.pbe.OpenSSLEncryptionScheme;
import org.cryptacular.util.ByteUtil;
import org.cryptacular.util.CodecUtil;
import org.cryptacular.util.PemUtil;

public class OpenSSLPrivateKeyDecoder
extends AbstractPrivateKeyDecoder<AsymmetricKeyParameter> {
    @Override
    protected byte[] decryptKey(byte[] encrypted, char[] password) {
        String pem = new String(encrypted, ByteUtil.ASCII_CHARSET);
        int start2 = pem.indexOf("DEK-Info:");
        int eol = pem.indexOf(10, start2);
        String[] dekInfo = pem.substring(start2 + 10, eol).split(",");
        String alg = dekInfo[0];
        byte[] iv = CodecUtil.hex(dekInfo[1]);
        byte[] bytes = PemUtil.decode(encrypted);
        return new OpenSSLEncryptionScheme(OpenSSLAlgorithm.fromAlgorithmId(alg), iv, password).decrypt(bytes);
    }

    @Override
    protected AsymmetricKeyParameter decodeASN1(byte[] encoded) {
        AsymmetricKeyParameter key;
        ASN1Primitive o;
        ASN1InputStream stream = new ASN1InputStream(encoded);
        try {
            o = stream.readObject();
        }
        catch (Exception e) {
            throw new EncodingException("Invalid encoded key", e);
        }
        if (o instanceof ASN1ObjectIdentifier) {
            try {
                key = this.parseECPrivateKey(ASN1Sequence.getInstance(stream.readObject()));
            }
            catch (Exception e) {
                throw new EncodingException("Invalid encoded key", e);
            }
        } else {
            ASN1Sequence sequence2 = ASN1Sequence.getInstance(o);
            if (sequence2.size() == 9) {
                key = new RSAPrivateCrtKeyParameters(ASN1Integer.getInstance(sequence2.getObjectAt(1)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(2)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(3)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(4)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(5)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(6)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(7)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(8)).getValue());
            } else if (sequence2.size() == 6) {
                key = new DSAPrivateKeyParameters(ASN1Integer.getInstance(sequence2.getObjectAt(5)).getValue(), new DSAParameters(ASN1Integer.getInstance(sequence2.getObjectAt(1)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(2)).getValue(), ASN1Integer.getInstance(sequence2.getObjectAt(3)).getValue()));
            } else if (sequence2.size() == 4) {
                key = this.parseECPrivateKey(sequence2);
            } else {
                throw new EncodingException("Invalid OpenSSL traditional private key format.");
            }
        }
        return key;
    }

    private ECPrivateKeyParameters parseECPrivateKey(ASN1Sequence seq) {
        ASN1TaggedObject asn1Params = ASN1TaggedObject.getInstance(seq.getObjectAt(2));
        X9ECParameters params = asn1Params.getObject() instanceof ASN1ObjectIdentifier ? ECUtil.getNamedCurveByOid(ASN1ObjectIdentifier.getInstance(asn1Params.getObject())) : X9ECParameters.getInstance(asn1Params.getObject());
        return new ECPrivateKeyParameters(new BigInteger(1, ASN1OctetString.getInstance(seq.getObjectAt(1)).getOctets()), new ECDomainParameters(params.getCurve(), params.getG(), params.getN(), params.getH(), params.getSeed()));
    }
}

