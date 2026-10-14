/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.jcajce.provider.asymmetric.compositesignatures;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.CompositePublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.CompositeSignaturesConstants;
import org.bouncycastle.util.Exceptions;

public class SignatureSpi
extends java.security.SignatureSpi {
    private final CompositeSignaturesConstants.CompositeName algorithmIdentifier;
    private final ASN1ObjectIdentifier algorithmIdentifierASN1;
    private final List<Signature> componentSignatures;
    private final Digest digest;
    private byte[] OIDBytes;

    SignatureSpi(CompositeSignaturesConstants.CompositeName compositeName) {
        this.algorithmIdentifier = compositeName;
        this.algorithmIdentifierASN1 = CompositeSignaturesConstants.compositeNameASN1IdentifierMap.get((Object)this.algorithmIdentifier);
        ArrayList<Signature> arrayList = new ArrayList<Signature>();
        try {
            switch (this.algorithmIdentifier) {
                case MLDSA44_Ed25519_SHA512: 
                case MLDSA65_Ed25519_SHA512: {
                    arrayList.add(Signature.getInstance("Dilithium", "BC"));
                    arrayList.add(Signature.getInstance("Ed25519", "BC"));
                    this.digest = DigestFactory.createSHA512();
                    break;
                }
                case MLDSA87_Ed448_SHA512: {
                    arrayList.add(Signature.getInstance("Dilithium", "BC"));
                    arrayList.add(Signature.getInstance("Ed448", "BC"));
                    this.digest = DigestFactory.createSHA512();
                    break;
                }
                case MLDSA44_RSA2048_PSS_SHA256: {
                    arrayList.add(Signature.getInstance("Dilithium", "BC"));
                    arrayList.add(Signature.getInstance("SHA256withRSA/PSS", "BC"));
                    this.digest = DigestFactory.createSHA256();
                    break;
                }
                case MLDSA65_RSA3072_PSS_SHA512: {
                    arrayList.add(Signature.getInstance("Dilithium", "BC"));
                    arrayList.add(Signature.getInstance("SHA512withRSA/PSS", "BC"));
                    this.digest = DigestFactory.createSHA512();
                    break;
                }
                case MLDSA44_RSA2048_PKCS15_SHA256: {
                    arrayList.add(Signature.getInstance("Dilithium", "BC"));
                    arrayList.add(Signature.getInstance("SHA256withRSA", "BC"));
                    this.digest = DigestFactory.createSHA256();
                    break;
                }
                case MLDSA65_RSA3072_PKCS15_SHA512: {
                    arrayList.add(Signature.getInstance("Dilithium", "BC"));
                    arrayList.add(Signature.getInstance("SHA512withRSA", "BC"));
                    this.digest = DigestFactory.createSHA512();
                    break;
                }
                case MLDSA44_ECDSA_P256_SHA256: 
                case MLDSA44_ECDSA_brainpoolP256r1_SHA256: {
                    arrayList.add(Signature.getInstance("Dilithium", "BC"));
                    arrayList.add(Signature.getInstance("SHA256withECDSA", "BC"));
                    this.digest = DigestFactory.createSHA256();
                    break;
                }
                case MLDSA65_ECDSA_P256_SHA512: 
                case MLDSA65_ECDSA_brainpoolP256r1_SHA512: 
                case MLDSA87_ECDSA_P384_SHA512: 
                case MLDSA87_ECDSA_brainpoolP384r1_SHA512: {
                    arrayList.add(Signature.getInstance("Dilithium", "BC"));
                    arrayList.add(Signature.getInstance("SHA512withECDSA", "BC"));
                    this.digest = DigestFactory.createSHA512();
                    break;
                }
                case Falcon512_ECDSA_P256_SHA256: 
                case Falcon512_ECDSA_brainpoolP256r1_SHA256: {
                    arrayList.add(Signature.getInstance("Falcon", "BC"));
                    arrayList.add(Signature.getInstance("SHA256withECDSA", "BC"));
                    this.digest = DigestFactory.createSHA256();
                    break;
                }
                case Falcon512_Ed25519_SHA512: {
                    arrayList.add(Signature.getInstance("Falcon", "BC"));
                    arrayList.add(Signature.getInstance("Ed25519", "BC"));
                    this.digest = DigestFactory.createSHA512();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unknown composite algorithm");
                }
            }
            this.OIDBytes = this.algorithmIdentifierASN1.getEncoded("DER");
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw Exceptions.illegalStateException(generalSecurityException.getMessage(), generalSecurityException);
        }
        catch (IOException iOException) {
            throw Exceptions.illegalStateException(iOException.getMessage(), iOException);
        }
        this.componentSignatures = Collections.unmodifiableList(arrayList);
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        if (!(publicKey instanceof CompositePublicKey)) {
            throw new InvalidKeyException("Public key is not composite.");
        }
        CompositePublicKey compositePublicKey = (CompositePublicKey)publicKey;
        if (!compositePublicKey.getAlgorithmIdentifier().equals(this.algorithmIdentifierASN1)) {
            throw new InvalidKeyException("Provided composite public key cannot be used with the composite signature algorithm.");
        }
        for (int i = 0; i < this.componentSignatures.size(); ++i) {
            this.componentSignatures.get(i).initVerify(compositePublicKey.getPublicKeys().get(i));
        }
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (!(privateKey instanceof CompositePrivateKey)) {
            throw new InvalidKeyException("Private key is not composite.");
        }
        CompositePrivateKey compositePrivateKey = (CompositePrivateKey)privateKey;
        if (!compositePrivateKey.getAlgorithmIdentifier().equals(this.algorithmIdentifierASN1)) {
            throw new InvalidKeyException("Provided composite private key cannot be used with the composite signature algorithm.");
        }
        for (int i = 0; i < this.componentSignatures.size(); ++i) {
            this.componentSignatures.get(i).initSign(compositePrivateKey.getPrivateKeys().get(i));
        }
    }

    @Override
    protected void engineUpdate(byte by) throws SignatureException {
        this.digest.update(by);
    }

    @Override
    protected void engineUpdate(byte[] byArray, int n, int n2) throws SignatureException {
        this.digest.update(byArray, n, n2);
    }

    @Override
    protected byte[] engineSign() throws SignatureException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        try {
            byte[] byArray = new byte[this.digest.getDigestSize()];
            this.digest.doFinal(byArray, 0);
            for (int i = 0; i < this.componentSignatures.size(); ++i) {
                this.componentSignatures.get(i).update(this.OIDBytes);
                this.componentSignatures.get(i).update(byArray);
                byte[] byArray2 = this.componentSignatures.get(i).sign();
                aSN1EncodableVector.add(new DERBitString(byArray2));
            }
            return new DERSequence(aSN1EncodableVector).getEncoded("DER");
        }
        catch (IOException iOException) {
            throw new SignatureException(iOException.getMessage());
        }
    }

    @Override
    protected boolean engineVerify(byte[] byArray) throws SignatureException {
        ASN1Sequence aSN1Sequence = DERSequence.getInstance(byArray);
        if (aSN1Sequence.size() != this.componentSignatures.size()) {
            return false;
        }
        byte[] byArray2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(byArray2, 0);
        boolean bl = false;
        for (int i = 0; i < this.componentSignatures.size(); ++i) {
            this.componentSignatures.get(i).update(this.OIDBytes);
            this.componentSignatures.get(i).update(byArray2);
            if (this.componentSignatures.get(i).verify(ASN1BitString.getInstance(aSN1Sequence.getObjectAt(i)).getOctets())) continue;
            bl = true;
        }
        return !bl;
    }

    @Override
    protected void engineSetParameter(String string, Object object) throws InvalidParameterException {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override
    protected Object engineGetParameter(String string) throws InvalidParameterException {
        throw new UnsupportedOperationException("engineGetParameter unsupported");
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    public static final class Falcon512_ECDSA_P256_SHA256
    extends SignatureSpi {
        public Falcon512_ECDSA_P256_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.Falcon512_ECDSA_P256_SHA256);
        }
    }

    public static final class Falcon512_ECDSA_brainpoolP256r1_SHA256
    extends SignatureSpi {
        public Falcon512_ECDSA_brainpoolP256r1_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.Falcon512_ECDSA_brainpoolP256r1_SHA256);
        }
    }

    public static final class Falcon512_Ed25519_SHA512
    extends SignatureSpi {
        public Falcon512_Ed25519_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.Falcon512_Ed25519_SHA512);
        }
    }

    public static final class MLDSA44_ECDSA_P256_SHA256
    extends SignatureSpi {
        public MLDSA44_ECDSA_P256_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_ECDSA_P256_SHA256);
        }
    }

    public static final class MLDSA44_ECDSA_brainpoolP256r1_SHA256
    extends SignatureSpi {
        public MLDSA44_ECDSA_brainpoolP256r1_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_ECDSA_brainpoolP256r1_SHA256);
        }
    }

    public static final class MLDSA44_Ed25519_SHA512
    extends SignatureSpi {
        public MLDSA44_Ed25519_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_Ed25519_SHA512);
        }
    }

    public static final class MLDSA44_RSA2048_PKCS15_SHA256
    extends SignatureSpi {
        public MLDSA44_RSA2048_PKCS15_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_RSA2048_PKCS15_SHA256);
        }
    }

    public static final class MLDSA44_RSA2048_PSS_SHA256
    extends SignatureSpi {
        public MLDSA44_RSA2048_PSS_SHA256() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA44_RSA2048_PSS_SHA256);
        }
    }

    public static final class MLDSA65_ECDSA_P256_SHA512
    extends SignatureSpi {
        public MLDSA65_ECDSA_P256_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_ECDSA_P256_SHA512);
        }
    }

    public static final class MLDSA65_ECDSA_brainpoolP256r1_SHA512
    extends SignatureSpi {
        public MLDSA65_ECDSA_brainpoolP256r1_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_ECDSA_brainpoolP256r1_SHA512);
        }
    }

    public static final class MLDSA65_Ed25519_SHA512
    extends SignatureSpi {
        public MLDSA65_Ed25519_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_Ed25519_SHA512);
        }
    }

    public static final class MLDSA65_RSA3072_PKCS15_SHA512
    extends SignatureSpi {
        public MLDSA65_RSA3072_PKCS15_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_RSA3072_PKCS15_SHA512);
        }
    }

    public static final class MLDSA65_RSA3072_PSS_SHA512
    extends SignatureSpi {
        public MLDSA65_RSA3072_PSS_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA65_RSA3072_PSS_SHA512);
        }
    }

    public static final class MLDSA87_ECDSA_P384_SHA512
    extends SignatureSpi {
        public MLDSA87_ECDSA_P384_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA87_ECDSA_P384_SHA512);
        }
    }

    public static final class MLDSA87_ECDSA_brainpoolP384r1_SHA512
    extends SignatureSpi {
        public MLDSA87_ECDSA_brainpoolP384r1_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA87_ECDSA_brainpoolP384r1_SHA512);
        }
    }

    public static final class MLDSA87_Ed448_SHA512
    extends SignatureSpi {
        public MLDSA87_Ed448_SHA512() {
            super(CompositeSignaturesConstants.CompositeName.MLDSA87_Ed448_SHA512);
        }
    }
}

