/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.util;

import com.google.common.io.BaseEncoding;
import io.grpc.ExperimentalApi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/8024")
public final class CertificateUtils {
    public static X509Certificate[] getX509Certificates(InputStream inputStream2) throws CertificateException {
        CertificateFactory factory2 = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certs = factory2.generateCertificates(inputStream2);
        return certs.toArray(new X509Certificate[0]);
    }

    public static PrivateKey getPrivateKey(InputStream inputStream2) throws UnsupportedEncodingException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream2, "UTF-8"));
        while ((line = reader.readLine()) != null && !"-----BEGIN PRIVATE KEY-----".equals(line)) {
        }
        StringBuilder keyContent = new StringBuilder();
        while ((line = reader.readLine()) != null && !"-----END PRIVATE KEY-----".equals(line)) {
            keyContent.append(line);
        }
        byte[] decodedKeyBytes = BaseEncoding.base64().decode(keyContent.toString());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKeyBytes);
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        }
        catch (InvalidKeySpecException ignore) {
            try {
                return KeyFactory.getInstance("EC").generatePrivate(keySpec);
            }
            catch (InvalidKeySpecException e) {
                throw new InvalidKeySpecException("Neither RSA nor EC worked", e);
            }
        }
    }
}

