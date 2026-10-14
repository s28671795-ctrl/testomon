/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.jcajce.provider.asymmetric.compositesignatures;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.internal.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.CompositePublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.compositesignatures.CompositeSignaturesConstants;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import org.bouncycastle.util.Exceptions;

public class KeyFactorySpi
extends BaseKeyFactorySpi {
    private static final AlgorithmIdentifier dilithium2Identifier = new AlgorithmIdentifier(BCObjectIdentifiers.dilithium2);
    private static final AlgorithmIdentifier dilithium3Identifier = new AlgorithmIdentifier(BCObjectIdentifiers.dilithium3);
    private static final AlgorithmIdentifier dilithium5Identifier = new AlgorithmIdentifier(BCObjectIdentifiers.dilithium5);
    private static final AlgorithmIdentifier falcon512Identifier = new AlgorithmIdentifier(BCObjectIdentifiers.falcon_512);
    private static final AlgorithmIdentifier ed25519Identifier = new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed25519);
    private static final AlgorithmIdentifier ecdsaP256Identifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, new X962Parameters(SECObjectIdentifiers.secp256r1));
    private static final AlgorithmIdentifier ecdsaBrainpoolP256r1Identifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, new X962Parameters(TeleTrusTObjectIdentifiers.brainpoolP256r1));
    private static final AlgorithmIdentifier rsaIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption);
    private static final AlgorithmIdentifier ed448Identifier = new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed448);
    private static final AlgorithmIdentifier ecdsaP384Identifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, new X962Parameters(SECObjectIdentifiers.secp384r1));
    private static final AlgorithmIdentifier ecdsaBrainpoolP384r1Identifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, new X962Parameters(TeleTrusTObjectIdentifiers.brainpoolP384r1));

    @Override
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        try {
            if (key instanceof PrivateKey) {
                return this.generatePrivate(PrivateKeyInfo.getInstance(key.getEncoded()));
            }
            if (key instanceof PublicKey) {
                return this.generatePublic(SubjectPublicKeyInfo.getInstance(key.getEncoded()));
            }
        }
        catch (IOException iOException) {
            throw new InvalidKeyException("Key could not be parsed: " + iOException.getMessage());
        }
        throw new InvalidKeyException("Key not recognized");
    }

    @Override
    public PrivateKey generatePrivate(PrivateKeyInfo privateKeyInfo) throws IOException {
        ASN1Sequence aSN1Sequence = DERSequence.getInstance(privateKeyInfo.parsePrivateKey());
        ASN1ObjectIdentifier aSN1ObjectIdentifier = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        try {
            List<KeyFactory> list = this.getKeyFactoriesFromIdentifier(aSN1ObjectIdentifier);
            PrivateKey[] privateKeyArray = new PrivateKey[aSN1Sequence.size()];
            for (int i = 0; i < aSN1Sequence.size(); ++i) {
                PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(PrivateKeyInfo.getInstance(aSN1Sequence.getObjectAt(i)).getEncoded());
                privateKeyArray[i] = list.get(i).generatePrivate(pKCS8EncodedKeySpec);
            }
            return new CompositePrivateKey(aSN1ObjectIdentifier, privateKeyArray);
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw Exceptions.ioException(generalSecurityException.getMessage(), generalSecurityException);
        }
    }

    @Override
    public PublicKey generatePublic(SubjectPublicKeyInfo subjectPublicKeyInfo) throws IOException {
        ASN1Sequence aSN1Sequence = DERSequence.getInstance(subjectPublicKeyInfo.getPublicKeyData().getBytes());
        ASN1ObjectIdentifier aSN1ObjectIdentifier = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
        try {
            List<KeyFactory> list = this.getKeyFactoriesFromIdentifier(aSN1ObjectIdentifier);
            ASN1BitString[] aSN1BitStringArray = new ASN1BitString[aSN1Sequence.size()];
            for (int i = 0; i < aSN1Sequence.size(); ++i) {
                aSN1BitStringArray[i] = aSN1Sequence.getObjectAt(i) instanceof DEROctetString ? new DERBitString(((DEROctetString)aSN1Sequence.getObjectAt(i)).getOctets()) : (DERBitString)aSN1Sequence.getObjectAt(i);
            }
            X509EncodedKeySpec[] x509EncodedKeySpecArray = this.getKeysSpecs(aSN1ObjectIdentifier, aSN1BitStringArray);
            PublicKey[] publicKeyArray = new PublicKey[aSN1Sequence.size()];
            for (int i = 0; i < aSN1Sequence.size(); ++i) {
                publicKeyArray[i] = list.get(i).generatePublic(x509EncodedKeySpecArray[i]);
            }
            return new CompositePublicKey(aSN1ObjectIdentifier, publicKeyArray);
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw Exceptions.ioException(generalSecurityException.getMessage(), generalSecurityException);
        }
    }

    private List<KeyFactory> getKeyFactoriesFromIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws NoSuchAlgorithmException, NoSuchProviderException {
        ArrayList<KeyFactory> arrayList = new ArrayList<KeyFactory>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        switch (CompositeSignaturesConstants.ASN1IdentifierCompositeNameMap.get(aSN1ObjectIdentifier)) {
            case MLDSA44_Ed25519_SHA512: 
            case MLDSA65_Ed25519_SHA512: {
                arrayList2.add("Dilithium");
                arrayList2.add("Ed25519");
                break;
            }
            case MLDSA87_Ed448_SHA512: {
                arrayList2.add("Dilithium");
                arrayList2.add("Ed448");
                break;
            }
            case MLDSA44_RSA2048_PSS_SHA256: 
            case MLDSA44_RSA2048_PKCS15_SHA256: 
            case MLDSA65_RSA3072_PSS_SHA512: 
            case MLDSA65_RSA3072_PKCS15_SHA512: {
                arrayList2.add("Dilithium");
                arrayList2.add("RSA");
                break;
            }
            case MLDSA44_ECDSA_P256_SHA256: 
            case MLDSA44_ECDSA_brainpoolP256r1_SHA256: 
            case MLDSA65_ECDSA_P256_SHA512: 
            case MLDSA65_ECDSA_brainpoolP256r1_SHA512: 
            case MLDSA87_ECDSA_P384_SHA512: 
            case MLDSA87_ECDSA_brainpoolP384r1_SHA512: {
                arrayList2.add("Dilithium");
                arrayList2.add("ECDSA");
                break;
            }
            case Falcon512_Ed25519_SHA512: {
                arrayList2.add("Falcon");
                arrayList2.add("Ed25519");
                break;
            }
            case Falcon512_ECDSA_P256_SHA256: 
            case Falcon512_ECDSA_brainpoolP256r1_SHA256: {
                arrayList2.add("Falcon");
                arrayList2.add("ECDSA");
                break;
            }
            default: {
                throw new IllegalArgumentException("Cannot create KeyFactories. Unsupported algorithm identifier.");
            }
        }
        arrayList.add(KeyFactory.getInstance((String)arrayList2.get(0), "BC"));
        arrayList.add(KeyFactory.getInstance((String)arrayList2.get(1), "BC"));
        return Collections.unmodifiableList(arrayList);
    }

    private X509EncodedKeySpec[] getKeysSpecs(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1BitString[] aSN1BitStringArray) throws IOException {
        X509EncodedKeySpec[] x509EncodedKeySpecArray = new X509EncodedKeySpec[aSN1BitStringArray.length];
        SubjectPublicKeyInfo[] subjectPublicKeyInfoArray = new SubjectPublicKeyInfo[aSN1BitStringArray.length];
        switch (CompositeSignaturesConstants.ASN1IdentifierCompositeNameMap.get(aSN1ObjectIdentifier)) {
            case MLDSA44_Ed25519_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium2Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ed25519Identifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA44_ECDSA_P256_SHA256: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium2Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ecdsaP256Identifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA44_ECDSA_brainpoolP256r1_SHA256: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium2Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ecdsaBrainpoolP256r1Identifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA44_RSA2048_PSS_SHA256: 
            case MLDSA44_RSA2048_PKCS15_SHA256: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium2Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(rsaIdentifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA65_Ed25519_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium3Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ed25519Identifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA65_ECDSA_P256_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium3Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ecdsaP256Identifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA65_ECDSA_brainpoolP256r1_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium3Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ecdsaBrainpoolP256r1Identifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA65_RSA3072_PSS_SHA512: 
            case MLDSA65_RSA3072_PKCS15_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium3Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(rsaIdentifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA87_Ed448_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium5Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ed448Identifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA87_ECDSA_P384_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium5Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ecdsaP384Identifier, aSN1BitStringArray[1]);
                break;
            }
            case MLDSA87_ECDSA_brainpoolP384r1_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(dilithium5Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ecdsaBrainpoolP384r1Identifier, aSN1BitStringArray[1]);
                break;
            }
            case Falcon512_Ed25519_SHA512: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(falcon512Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ed25519Identifier, aSN1BitStringArray[1]);
                break;
            }
            case Falcon512_ECDSA_P256_SHA256: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(falcon512Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ecdsaP256Identifier, aSN1BitStringArray[1]);
                break;
            }
            case Falcon512_ECDSA_brainpoolP256r1_SHA256: {
                subjectPublicKeyInfoArray[0] = new SubjectPublicKeyInfo(falcon512Identifier, aSN1BitStringArray[0]);
                subjectPublicKeyInfoArray[1] = new SubjectPublicKeyInfo(ecdsaBrainpoolP256r1Identifier, aSN1BitStringArray[1]);
                break;
            }
            default: {
                throw new IllegalArgumentException("Cannot create key specs. Unsupported algorithm identifier.");
            }
        }
        x509EncodedKeySpecArray[0] = new X509EncodedKeySpec(subjectPublicKeyInfoArray[0].getEncoded());
        x509EncodedKeySpecArray[1] = new X509EncodedKeySpec(subjectPublicKeyInfoArray[1].getEncoded());
        return x509EncodedKeySpecArray;
    }
}

