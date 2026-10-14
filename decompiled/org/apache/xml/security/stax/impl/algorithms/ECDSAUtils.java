/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.stax.impl.algorithms;

import java.io.IOException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import org.apache.xml.security.algorithms.implementations.ECDSAUtils;

@Deprecated
public final class ECDSAUtils {
    private ECDSAUtils() {
    }

    public static byte[] convertASN1toXMLDSIG(byte[] asn1Bytes) throws IOException {
        return org.apache.xml.security.algorithms.implementations.ECDSAUtils.convertASN1toXMLDSIG(asn1Bytes);
    }

    public static byte[] convertXMLDSIGtoASN1(byte[] xmldsigBytes) throws IOException {
        return org.apache.xml.security.algorithms.implementations.ECDSAUtils.convertXMLDSIGtoASN1(xmldsigBytes);
    }

    public static String getOIDFromPublicKey(ECPublicKey ecPublicKey) {
        return org.apache.xml.security.algorithms.implementations.ECDSAUtils.getOIDFromPublicKey(ecPublicKey);
    }

    public static ECCurveDefinition getECCurveDefinition(String oid) {
        ECDSAUtils.ECCurveDefinition curveDef = org.apache.xml.security.algorithms.implementations.ECDSAUtils.getECCurveDefinition(oid);
        if (curveDef != null) {
            return new ECCurveDefinition(curveDef.getName(), curveDef.getOid(), curveDef.getField(), curveDef.getA(), curveDef.getB(), curveDef.getX(), curveDef.getY(), curveDef.getN(), curveDef.getH());
        }
        return null;
    }

    public static byte[] encodePoint(ECPoint ecPoint, EllipticCurve ellipticCurve) {
        return org.apache.xml.security.algorithms.implementations.ECDSAUtils.encodePoint(ecPoint, ellipticCurve);
    }

    public static ECPoint decodePoint(byte[] encodedBytes, EllipticCurve ellipticCurve) {
        return org.apache.xml.security.algorithms.implementations.ECDSAUtils.decodePoint(encodedBytes, ellipticCurve);
    }

    public static byte[] stripLeadingZeros(byte[] bytes) {
        return org.apache.xml.security.algorithms.implementations.ECDSAUtils.stripLeadingZeros(bytes);
    }

    public static class ECCurveDefinition
    extends ECDSAUtils.ECCurveDefinition {
        public ECCurveDefinition(String name, String oid, String field2, String a, String b, String x, String y, String n, int h2) {
            super(name, oid, field2, a, b, x, y, n, h2);
        }
    }
}

