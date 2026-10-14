/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.jcajce.provider;

import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi;

public class Kyber {
    private static final String PREFIX = "org.bouncycastle.pqc.jcajce.provider.kyber.";

    public static class Mappings
    extends AsymmetricAlgorithmProvider {
        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("KeyFactory.KYBER", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi");
            this.addKeyFactoryAlgorithm(configurableProvider, "KYBER512", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi$Kyber512", BCObjectIdentifiers.kyber512, new KyberKeyFactorySpi.Kyber512());
            this.addKeyFactoryAlgorithm(configurableProvider, "KYBER768", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi$Kyber768", BCObjectIdentifiers.kyber768, new KyberKeyFactorySpi.Kyber768());
            this.addKeyFactoryAlgorithm(configurableProvider, "KYBER1024", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyFactorySpi$Kyber1024", BCObjectIdentifiers.kyber1024, new KyberKeyFactorySpi.Kyber1024());
            configurableProvider.addAlgorithm("KeyPairGenerator.KYBER", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyPairGeneratorSpi");
            this.addKeyPairGeneratorAlgorithm(configurableProvider, "KYBER512", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyPairGeneratorSpi$Kyber512", BCObjectIdentifiers.kyber512);
            this.addKeyPairGeneratorAlgorithm(configurableProvider, "KYBER768", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyPairGeneratorSpi$Kyber768", BCObjectIdentifiers.kyber768);
            this.addKeyPairGeneratorAlgorithm(configurableProvider, "KYBER1024", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyPairGeneratorSpi$Kyber1024", BCObjectIdentifiers.kyber1024);
            configurableProvider.addAlgorithm("KeyGenerator.KYBER", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyGeneratorSpi");
            this.addKeyGeneratorAlgorithm(configurableProvider, "KYBER512", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyGeneratorSpi$Kyber512", BCObjectIdentifiers.kyber512);
            this.addKeyGeneratorAlgorithm(configurableProvider, "KYBER768", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyGeneratorSpi$Kyber768", BCObjectIdentifiers.kyber768);
            this.addKeyGeneratorAlgorithm(configurableProvider, "KYBER1024", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberKeyGeneratorSpi$Kyber1024", BCObjectIdentifiers.kyber1024);
            KyberKeyFactorySpi kyberKeyFactorySpi = new KyberKeyFactorySpi();
            configurableProvider.addAlgorithm("Cipher.KYBER", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberCipherSpi$Base");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher." + BCObjectIdentifiers.pqc_kem_kyber, "KYBER");
            this.addCipherAlgorithm(configurableProvider, "KYBER512", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberCipherSpi$Kyber512", BCObjectIdentifiers.kyber512);
            this.addCipherAlgorithm(configurableProvider, "KYBER768", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberCipherSpi$Kyber768", BCObjectIdentifiers.kyber768);
            this.addCipherAlgorithm(configurableProvider, "KYBER1024", "org.bouncycastle.pqc.jcajce.provider.kyber.KyberCipherSpi$Kyber1024", BCObjectIdentifiers.kyber1024);
            this.registerOid(configurableProvider, BCObjectIdentifiers.pqc_kem_kyber, "KYBER", kyberKeyFactorySpi);
        }
    }
}

