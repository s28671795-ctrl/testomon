/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.conscrypt.NativeCrypto;
import org.conscrypt.NativeRef;
import org.conscrypt.OpenSSLECGroupContext;

public class ECParameters
extends AlgorithmParametersSpi {
    private OpenSSLECGroupContext curve;

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof ECGenParameterSpec) {
            String newCurveName = ((ECGenParameterSpec)algorithmParameterSpec).getName();
            OpenSSLECGroupContext newCurve = OpenSSLECGroupContext.getCurveByName(newCurveName);
            if (newCurve == null) {
                throw new InvalidParameterSpecException("Unknown EC curve name: " + newCurveName);
            }
            this.curve = newCurve;
        } else if (algorithmParameterSpec instanceof ECParameterSpec) {
            ECParameterSpec ecParamSpec = (ECParameterSpec)algorithmParameterSpec;
            try {
                OpenSSLECGroupContext newCurve = OpenSSLECGroupContext.getInstance(ecParamSpec);
                if (newCurve == null) {
                    throw new InvalidParameterSpecException("Unknown EC curve: " + ecParamSpec);
                }
                this.curve = newCurve;
            }
            catch (InvalidAlgorithmParameterException e) {
                throw new InvalidParameterSpecException(e.getMessage());
            }
        } else {
            throw new InvalidParameterSpecException("Only ECParameterSpec and ECGenParameterSpec are supported");
        }
    }

    @Override
    protected void engineInit(byte[] bytes) throws IOException {
        long ref = NativeCrypto.EC_KEY_parse_curve_name(bytes);
        if (ref == 0L) {
            throw new IOException("Error reading ASN.1 encoding");
        }
        this.curve = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(ref));
    }

    @Override
    protected void engineInit(byte[] bytes, String format) throws IOException {
        if (format != null && !format.equals("ASN.1")) {
            throw new IOException("Unsupported format: " + format);
        }
        this.engineInit(bytes);
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> aClass) throws InvalidParameterSpecException {
        if (aClass == ECParameterSpec.class) {
            return (T)this.curve.getECParameterSpec();
        }
        if (aClass == ECGenParameterSpec.class) {
            return (T)new ECGenParameterSpec(this.curve.getCurveName());
        }
        throw new InvalidParameterSpecException("Unsupported class: " + aClass);
    }

    @Override
    protected byte[] engineGetEncoded() throws IOException {
        return NativeCrypto.EC_KEY_marshal_curve_name(this.curve.getNativeRef());
    }

    @Override
    protected byte[] engineGetEncoded(String format) throws IOException {
        if (format == null || format.equals("ASN.1")) {
            return this.engineGetEncoded();
        }
        throw new IOException("Unsupported format: " + format);
    }

    @Override
    protected String engineToString() {
        return "Conscrypt EC AlgorithmParameters";
    }
}

