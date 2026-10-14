/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.jcajce.provider.asymmetric.compositesignatures;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.CompositePublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.CompositeSignaturesConstants;
import org.bouncycastle.pqc.jcajce.spec.DilithiumParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.FalconParameterSpec;

public class KeyPairGeneratorSpi
extends java.security.KeyPairGeneratorSpi {
    private final CompositeSignaturesConstants.CompositeName algorithmIdentifier;
    private final ASN1ObjectIdentifier algorithmIdentifierASN1;
    private List<KeyPairGenerator> generators;
    private SecureRandom secureRandom;
    private boolean parametersInitialized = false;

    KeyPairGeneratorSpi(CompositeSignaturesConstants.CompositeName compositeName) {
        this.algorithmIdentifier = compositeName;
        this.algorithmIdentifierASN1 = CompositeSignaturesConstants.compositeNameASN1IdentifierMap.get((Object)this.algorithmIdentifier);
    }

    private void initializeParameters() {
        if (this.secureRandom == null) {
            this.secureRandom = new SecureRandom();
        }
        ArrayList<KeyPairGenerator> arrayList = new ArrayList<KeyPairGenerator>();
        try {
            switch (this.algorithmIdentifier) {
                case MLDSA44_Ed25519_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("Ed25519", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium2, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(256, this.secureRandom);
                    break;
                }
                case MLDSA65_Ed25519_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("Ed25519", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium3, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(256, this.secureRandom);
                    break;
                }
                case MLDSA87_Ed448_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("Ed448", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium5, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(448, this.secureRandom);
                    break;
                }
                case MLDSA44_RSA2048_PSS_SHA256: 
                case MLDSA44_RSA2048_PKCS15_SHA256: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("RSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium2, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(2048, this.secureRandom);
                    break;
                }
                case MLDSA65_RSA3072_PSS_SHA512: 
                case MLDSA65_RSA3072_PKCS15_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("RSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium3, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(3072, this.secureRandom);
                    break;
                }
                case MLDSA44_ECDSA_P256_SHA256: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("ECDSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium2, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(new ECGenParameterSpec("P-256"), this.secureRandom);
                    break;
                }
                case MLDSA44_ECDSA_brainpoolP256r1_SHA256: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("ECDSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium2, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(new ECGenParameterSpec("brainpoolP256r1"), this.secureRandom);
                    break;
                }
                case MLDSA65_ECDSA_P256_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("ECDSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium3, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(new ECGenParameterSpec("P-256"), this.secureRandom);
                    break;
                }
                case MLDSA65_ECDSA_brainpoolP256r1_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("ECDSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium3, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(new ECGenParameterSpec("brainpoolP256r1"), this.secureRandom);
                    break;
                }
                case MLDSA87_ECDSA_P384_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("ECDSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium5, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(new ECGenParameterSpec("P-384"), this.secureRandom);
                    break;
                }
                case MLDSA87_ECDSA_brainpoolP384r1_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Dilithium", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("ECDSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(DilithiumParameterSpec.dilithium5, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(new ECGenParameterSpec("brainpoolP384r1"), this.secureRandom);
                    break;
                }
                case Falcon512_ECDSA_P256_SHA256: {
                    arrayList.add(KeyPairGenerator.getInstance("Falcon", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("ECDSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(FalconParameterSpec.falcon_512, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(new ECGenParameterSpec("P-256"), this.secureRandom);
                    break;
                }
                case Falcon512_ECDSA_brainpoolP256r1_SHA256: {
                    arrayList.add(KeyPairGenerator.getInstance("Falcon", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("ECDSA", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(FalconParameterSpec.falcon_512, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(new ECGenParameterSpec("brainpoolP256r1"), this.secureRandom);
                    break;
                }
                case Falcon512_Ed25519_SHA512: {
                    arrayList.add(KeyPairGenerator.getInstance("Falcon", "BC"));
                    arrayList.add(KeyPairGenerator.getInstance("Ed25519", "BC"));
                    ((KeyPairGenerator)arrayList.get(0)).initialize(FalconParameterSpec.falcon_512, this.secureRandom);
                    ((KeyPairGenerator)arrayList.get(1)).initialize(256, this.secureRandom);
                    break;
                }
                default: {
                    throw new IllegalStateException("Generators not correctly initialized. Unsupported composite algorithm.");
                }
            }
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new RuntimeException(generalSecurityException);
        }
        this.generators = Collections.unmodifiableList(arrayList);
        this.parametersInitialized = true;
    }

    @Override
    public void initialize(int n, SecureRandom secureRandom) {
        throw new IllegalArgumentException("use AlgorithmParameterSpec");
    }

    @Override
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            throw new IllegalArgumentException("Use initialize only for custom SecureRandom. AlgorithmParameterSpec must be null because it is determined by algorithm name.");
        }
        this.secureRandom = secureRandom;
        this.initializeParameters();
    }

    @Override
    public KeyPair generateKeyPair() {
        if (!this.parametersInitialized) {
            this.initializeParameters();
        }
        return this.getCompositeKeyPair();
    }

    private KeyPair getCompositeKeyPair() {
        Serializable serializable;
        PublicKey[] publicKeyArray = new PublicKey[this.generators.size()];
        PrivateKey[] privateKeyArray = new PrivateKey[this.generators.size()];
        for (int i = 0; i < this.generators.size(); ++i) {
            serializable = this.generators.get(i).generateKeyPair();
            publicKeyArray[i] = ((KeyPair)serializable).getPublic();
            privateKeyArray[i] = ((KeyPair)serializable).getPrivate();
        }
        CompositePublicKey compositePublicKey = new CompositePublicKey(this.algorithmIdentifierASN1, publicKeyArray);
        serializable = new CompositePrivateKey(this.algorithmIdentifierASN1, privateKeyArray);
        return new KeyPair(compositePublicKey, (PrivateKey)serializable);
    }

    public static final class Falcon512_ECDSA_P256_SHA256
    extends KeyPairGeneratorSpi {
        public Falcon512_ECDSA_P256_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.Falcon512_ECDSA_P256_SHA256);
        }
    }

    public static final class Falcon512_ECDSA_brainpoolP256r1_SHA256
    extends KeyPairGeneratorSpi {
        public Falcon512_ECDSA_brainpoolP256r1_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.Falcon512_ECDSA_brainpoolP256r1_SHA256);
        }
    }

    public static final class Falcon512_Ed25519_SHA512
    extends KeyPairGeneratorSpi {
        public Falcon512_Ed25519_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.Falcon512_Ed25519_SHA512);
        }
    }

    public static final class MLDSA44_ECDSA_P256_SHA256
    extends KeyPairGeneratorSpi {
        public MLDSA44_ECDSA_P256_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_ECDSA_P256_SHA256);
        }
    }

    public static final class MLDSA44_ECDSA_brainpoolP256r1_SHA256
    extends KeyPairGeneratorSpi {
        public MLDSA44_ECDSA_brainpoolP256r1_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_ECDSA_brainpoolP256r1_SHA256);
        }
    }

    public static final class MLDSA44_Ed25519_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA44_Ed25519_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_Ed25519_SHA512);
        }
    }

    public static final class MLDSA44_RSA2048_PKCS15_SHA256
    extends KeyPairGeneratorSpi {
        public MLDSA44_RSA2048_PKCS15_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_RSA2048_PKCS15_SHA256);
        }
    }

    public static final class MLDSA44_RSA2048_PSS_SHA256
    extends KeyPairGeneratorSpi {
        public MLDSA44_RSA2048_PSS_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_RSA2048_PSS_SHA256);
        }
    }

    public static final class MLDSA65_ECDSA_P256_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA65_ECDSA_P256_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_ECDSA_P256_SHA512);
        }
    }

    public static final class MLDSA65_ECDSA_brainpoolP256r1_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA65_ECDSA_brainpoolP256r1_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_ECDSA_brainpoolP256r1_SHA512);
        }
    }

    public static final class MLDSA65_Ed25519_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA65_Ed25519_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_Ed25519_SHA512);
        }
    }

    public static final class MLDSA65_RSA3072_PKCS15_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA65_RSA3072_PKCS15_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_RSA3072_PKCS15_SHA512);
        }
    }

    public static final class MLDSA65_RSA3072_PSS_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA65_RSA3072_PSS_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_RSA3072_PSS_SHA512);
        }
    }

    public static final class MLDSA87_ECDSA_P384_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA87_ECDSA_P384_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA87_ECDSA_P384_SHA512);
        }
    }

    public static final class MLDSA87_ECDSA_brainpoolP384r1_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA87_ECDSA_brainpoolP384r1_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA87_ECDSA_brainpoolP384r1_SHA512);
        }
    }

    public static final class MLDSA87_Ed448_SHA512
    extends KeyPairGeneratorSpi {
        public MLDSA87_Ed448_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA87_Ed448_SHA512);
        }
    }
}

