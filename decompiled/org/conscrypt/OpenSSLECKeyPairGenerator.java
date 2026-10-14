/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLECGroupContext;
import org.conscrypt.OpenSSLECPrivateKey;
import org.conscrypt.OpenSSLECPublicKey;
import org.conscrypt.OpenSSLKey;

public final class OpenSSLECKeyPairGenerator
extends KeyPairGenerator {
    private static final String ALGORITHM = "EC";
    private static final int DEFAULT_KEY_SIZE = 256;
    private static final Map<Integer, String> SIZE_TO_CURVE_NAME = new HashMap<Integer, String>();
    private OpenSSLECGroupContext group;

    public OpenSSLECKeyPairGenerator() {
        super(ALGORITHM);
    }

    @Override
    public KeyPair generateKeyPair() {
        if (this.group == null) {
            String curveName = SIZE_TO_CURVE_NAME.get(256);
            this.group = OpenSSLECGroupContext.getCurveByName(curveName);
            if (this.group == null) {
                throw new RuntimeException("Curve not recognized: " + curveName);
            }
        }
        OpenSSLKey key = new OpenSSLKey(NativeCrypto.EC_KEY_generate_key(this.group.getNativeRef()));
        return new KeyPair(new OpenSSLECPublicKey(this.group, key), new OpenSSLECPrivateKey(this.group, key));
    }

    @Override
    public void initialize(int keysize, SecureRandom random) {
        String name = SIZE_TO_CURVE_NAME.get(keysize);
        if (name == null) {
            throw new InvalidParameterException("unknown key size " + keysize);
        }
        OpenSSLECGroupContext possibleGroup = OpenSSLECGroupContext.getCurveByName(name);
        if (possibleGroup == null) {
            throw new InvalidParameterException("unknown curve " + name);
        }
        this.group = possibleGroup;
    }

    @Override
    public void initialize(AlgorithmParameterSpec param2, SecureRandom random) throws InvalidAlgorithmParameterException {
        if (param2 instanceof ECParameterSpec) {
            ECParameterSpec ecParam = (ECParameterSpec)param2;
            this.group = OpenSSLECGroupContext.getInstance(ecParam);
        } else if (param2 instanceof ECGenParameterSpec) {
            ECGenParameterSpec ecParam = (ECGenParameterSpec)param2;
            String curveName = ecParam.getName();
            OpenSSLECGroupContext possibleGroup = OpenSSLECGroupContext.getCurveByName(curveName);
            if (possibleGroup == null) {
                throw new InvalidAlgorithmParameterException("unknown curve name: " + curveName);
            }
            this.group = possibleGroup;
        } else {
            throw new InvalidAlgorithmParameterException("parameter must be ECParameterSpec or ECGenParameterSpec");
        }
    }

    public static void assertCurvesAreValid() {
        ArrayList<String> invalidCurves = new ArrayList<String>();
        for (String curveName : SIZE_TO_CURVE_NAME.values()) {
            if (OpenSSLECGroupContext.getCurveByName(curveName) != null) continue;
            invalidCurves.add(curveName);
        }
        if (invalidCurves.size() > 0) {
            throw new AssertionError((Object)("Invalid curve names: " + Arrays.toString(invalidCurves.toArray())));
        }
    }

    static {
        SIZE_TO_CURVE_NAME.put(224, "secp224r1");
        SIZE_TO_CURVE_NAME.put(256, "prime256v1");
        SIZE_TO_CURVE_NAME.put(384, "secp384r1");
        SIZE_TO_CURVE_NAME.put(521, "secp521r1");
    }
}

