/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.HashMap;
import java.util.Map;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLECPointContext;
import org.conscrypt.Platform;

final class OpenSSLECGroupContext {
    private static final Map<String, String> ALIASES = new HashMap<String, String>();
    private final NativeRef.EC_GROUP groupCtx;

    OpenSSLECGroupContext(NativeRef.EC_GROUP groupCtx) {
        this.groupCtx = groupCtx;
    }

    static OpenSSLECGroupContext getCurveByName(String curveName) {
        long ctx;
        if (ALIASES.containsKey(curveName)) {
            curveName = ALIASES.get(curveName);
        }
        if ((ctx = NativeCrypto.EC_GROUP_new_by_curve_name(curveName)) == 0L) {
            return null;
        }
        NativeRef.EC_GROUP groupRef = new NativeRef.EC_GROUP(ctx);
        return new OpenSSLECGroupContext(groupRef);
    }

    public boolean equals(Object o) {
        throw new IllegalArgumentException("OpenSSLECGroupContext.equals is not defined");
    }

    public int hashCode() {
        return super.hashCode();
    }

    NativeRef.EC_GROUP getNativeRef() {
        return this.groupCtx;
    }

    static OpenSSLECGroupContext getInstance(ECParameterSpec params) throws InvalidAlgorithmParameterException {
        long group;
        String curveName = Platform.getCurveName(params);
        if (curveName != null) {
            return OpenSSLECGroupContext.getCurveByName(curveName);
        }
        EllipticCurve curve = params.getCurve();
        ECField field2 = curve.getField();
        if (!(field2 instanceof ECFieldFp)) {
            throw new InvalidParameterException("unhandled field class " + field2.getClass().getName());
        }
        BigInteger p = ((ECFieldFp)field2).getP();
        ECPoint generator = params.getGenerator();
        BigInteger b = curve.getB();
        BigInteger x = generator.getAffineX();
        BigInteger y = generator.getAffineY();
        switch (p.bitLength()) {
            case 224: {
                if (!p.toString(16).equals("ffffffffffffffffffffffffffffffff000000000000000000000001") || !b.toString(16).equals("b4050a850c04b3abf54132565044b0b7d7bfd8ba270b39432355ffb4") || !x.toString(16).equals("b70e0cbd6bb4bf7f321390b94a03c1d356c21122343280d6115c1d21") || !y.toString(16).equals("bd376388b5f723fb4c22dfe6cd4375a05a07476444d5819985007e34")) break;
                curveName = "secp224r1";
                break;
            }
            case 256: {
                if (!p.toString(16).equals("ffffffff00000001000000000000000000000000ffffffffffffffffffffffff") || !b.toString(16).equals("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b") || !x.toString(16).equals("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296") || !y.toString(16).equals("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5")) break;
                curveName = "prime256v1";
                break;
            }
            case 384: {
                if (!p.toString(16).equals("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffff0000000000000000ffffffff") || !b.toString(16).equals("b3312fa7e23ee7e4988e056be3f82d19181d9c6efe8141120314088f5013875ac656398d8a2ed19d2a85c8edd3ec2aef") || !x.toString(16).equals("aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7") || !y.toString(16).equals("3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f")) break;
                curveName = "secp384r1";
                break;
            }
            case 521: {
                if (!p.toString(16).equals("1ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff") || !b.toString(16).equals("51953eb9618e1c9a1f929a21a0b68540eea2da725b99b315f3b8b489918ef109e156193951ec7e937b1652c0bd3bb1bf073573df883d2c34f1ef451fd46b503f00") || !x.toString(16).equals("c6858e06b70404e9cd9e3ecb662395b4429c648139053fb521f828af606b4d3dbaa14b5e77efe75928fe1dc127a2ffa8de3348b3c1856a429bf97e7e31c2e5bd66") || !y.toString(16).equals("11839296a789a3bc0045c8a5fb42c7d1bd998f54449579b446817afbd17273e662c97ee72995ef42640c550b9013fad0761353c7086a272c24088be94769fd16650")) break;
                curveName = "secp521r1";
            }
        }
        if (curveName != null) {
            return OpenSSLECGroupContext.getCurveByName(curveName);
        }
        BigInteger a = curve.getA();
        BigInteger order = params.getOrder();
        int cofactor = params.getCofactor();
        try {
            group = NativeCrypto.EC_GROUP_new_arbitrary(p.toByteArray(), a.toByteArray(), b.toByteArray(), x.toByteArray(), y.toByteArray(), order.toByteArray(), cofactor);
        }
        catch (Throwable exception) {
            throw new InvalidAlgorithmParameterException("EC_GROUP_new_arbitrary failed", exception);
        }
        if (group == 0L) {
            throw new InvalidAlgorithmParameterException("EC_GROUP_new_arbitrary returned NULL");
        }
        NativeRef.EC_GROUP groupRef = new NativeRef.EC_GROUP(group);
        return new OpenSSLECGroupContext(groupRef);
    }

    String getCurveName() {
        return NativeCrypto.EC_GROUP_get_curve_name(this.groupCtx);
    }

    ECParameterSpec getECParameterSpec() {
        String curveName = NativeCrypto.EC_GROUP_get_curve_name(this.groupCtx);
        byte[][] curveParams = NativeCrypto.EC_GROUP_get_curve(this.groupCtx);
        BigInteger p = new BigInteger(curveParams[0]);
        BigInteger a = new BigInteger(curveParams[1]);
        BigInteger b = new BigInteger(curveParams[2]);
        ECFieldFp field2 = new ECFieldFp(p);
        EllipticCurve curve = new EllipticCurve(field2, a, b);
        OpenSSLECPointContext generatorCtx = new OpenSSLECPointContext(this, new NativeRef.EC_POINT(NativeCrypto.EC_GROUP_get_generator(this.groupCtx)));
        ECPoint generator = generatorCtx.getECPoint();
        BigInteger order = new BigInteger(NativeCrypto.EC_GROUP_get_order(this.groupCtx));
        BigInteger cofactor = new BigInteger(NativeCrypto.EC_GROUP_get_cofactor(this.groupCtx));
        ECParameterSpec spec = new ECParameterSpec(curve, generator, order, cofactor.intValue());
        Platform.setCurveName(spec, curveName);
        return spec;
    }

    static {
        ALIASES.put("secp256r1", "prime256v1");
        ALIASES.put("1.3.132.0.33", "secp224r1");
        ALIASES.put("1.3.132.0.34", "secp384r1");
        ALIASES.put("1.3.132.0.35", "secp521r1");
        ALIASES.put("1.2.840.10045.3.1.7", "prime256v1");
    }
}

