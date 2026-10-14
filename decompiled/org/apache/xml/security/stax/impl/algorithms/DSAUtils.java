/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.stax.impl.algorithms;

import java.io.IOException;
import org.apache.xml.security.algorithms.implementations.ECDSAUtils;

@Deprecated
public class DSAUtils {
    public static byte[] convertASN1toXMLDSIG(byte[] asn1Bytes) throws IOException {
        return ECDSAUtils.convertASN1toXMLDSIG(asn1Bytes);
    }

    public static byte[] convertXMLDSIGtoASN1(byte[] xmldsigBytes) throws IOException {
        return ECDSAUtils.convertXMLDSIGtoASN1(xmldsigBytes);
    }
}

