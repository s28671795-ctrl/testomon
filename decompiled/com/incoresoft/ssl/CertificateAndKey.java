/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.ssl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;

public record CertificateAndKey(PrivateKey privateKey, X509Certificate certificate, X509Certificate[] chainedCertificates) {
    public String privateKeyToString() {
        return CertificateAndKey.writeToString(this.privateKey);
    }

    public String certificateToString() {
        ArrayList<X509Certificate> certificates = new ArrayList<X509Certificate>();
        certificates.add(this.certificate);
        certificates.addAll(Arrays.asList(this.chainedCertificates));
        return CertificateAndKey.writeToString(certificates.toArray());
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static String writeToString(Object ... objects) {
        try (StringWriter writer = new StringWriter();){
            String string;
            try (JcaPEMWriter pemWriter = new JcaPEMWriter(writer);){
                for (Object obj : objects) {
                    if (obj == null) continue;
                    pemWriter.writeObject(obj);
                }
                pemWriter.flush();
                string = writer.toString();
            }
            return string;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CertificateAndKey read(File privateKeyFile, File certificateFile) {
        return CertificateAndKey.read(privateKeyFile.toPath(), certificateFile.toPath());
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static CertificateAndKey read(Path privateKeyPath, Path certificatePath) {
        try (InputStream privateKeyStream = Files.newInputStream(privateKeyPath, new OpenOption[0]);){
            CertificateAndKey certificateAndKey;
            block14: {
                InputStream certificateStream = Files.newInputStream(certificatePath, new OpenOption[0]);
                try {
                    certificateAndKey = CertificateAndKey.read(privateKeyStream, certificateStream);
                    if (certificateStream == null) break block14;
                }
                catch (Throwable throwable) {
                    if (certificateStream != null) {
                        try {
                            certificateStream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                certificateStream.close();
            }
            return certificateAndKey;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CertificateAndKey read(InputStream privateKeyStream, InputStream certificateStream) {
        PrivateKey privateKey;
        X509Certificate certificate;
        try (PEMParser certificateParser = new PEMParser(new InputStreamReader(certificateStream));){
            JcaX509CertificateConverter x509Converter = new JcaX509CertificateConverter();
            certificate = x509Converter.getCertificate((X509CertificateHolder)certificateParser.readObject());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        try (PEMParser privateKeyParser = new PEMParser(new InputStreamReader(privateKeyStream));){
            KeyFactory factory2 = KeyFactory.getInstance("RSA");
            PemObject pemObject = privateKeyParser.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
            privateKey = factory2.generatePrivate(privateKeySpec);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new CertificateAndKey(privateKey, certificate, new X509Certificate[0]);
    }
}

