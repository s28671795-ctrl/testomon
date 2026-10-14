/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.operator.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.CompositePrivateKey;
import org.bouncycastle.jcajce.io.OutputStreamFactory;
import org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.RuntimeOperatorException;
import org.bouncycastle.operator.jcajce.OperatorHelper;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.io.TeeOutputStream;

public class JcaContentSignerBuilder {
    private static final Set isAlgIdFromPrivate = new HashSet();
    private static final DefaultSignatureAlgorithmIdentifierFinder SIGNATURE_ALGORITHM_IDENTIFIER_FINDER = new DefaultSignatureAlgorithmIdentifierFinder();
    private final String signatureAlgorithm;
    private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
    private SecureRandom random;
    private AlgorithmIdentifier sigAlgId;
    private AlgorithmParameterSpec sigAlgSpec;

    public JcaContentSignerBuilder(String string) {
        this.signatureAlgorithm = string;
    }

    public JcaContentSignerBuilder(String string, AlgorithmParameterSpec algorithmParameterSpec) {
        this.signatureAlgorithm = string;
        if (algorithmParameterSpec instanceof PSSParameterSpec) {
            PSSParameterSpec pSSParameterSpec = (PSSParameterSpec)algorithmParameterSpec;
            this.sigAlgSpec = pSSParameterSpec;
            this.sigAlgId = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_RSASSA_PSS, JcaContentSignerBuilder.createPSSParams(pSSParameterSpec));
        } else if (algorithmParameterSpec instanceof CompositeAlgorithmSpec) {
            CompositeAlgorithmSpec compositeAlgorithmSpec = (CompositeAlgorithmSpec)algorithmParameterSpec;
            this.sigAlgSpec = compositeAlgorithmSpec;
            this.sigAlgId = new AlgorithmIdentifier(MiscObjectIdentifiers.id_alg_composite, JcaContentSignerBuilder.createCompParams(compositeAlgorithmSpec));
        } else {
            throw new IllegalArgumentException("unknown sigParamSpec: " + (algorithmParameterSpec == null ? "null" : algorithmParameterSpec.getClass().getName()));
        }
    }

    public JcaContentSignerBuilder setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ProviderJcaJceHelper(provider));
        return this;
    }

    public JcaContentSignerBuilder setProvider(String string) {
        this.helper = new OperatorHelper(new NamedJcaJceHelper(string));
        return this;
    }

    public JcaContentSignerBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public ContentSigner build(PrivateKey privateKey) throws OperatorCreationException {
        if (privateKey instanceof CompositePrivateKey && ((CompositePrivateKey)privateKey).getAlgorithmIdentifier().equals(MiscObjectIdentifiers.id_composite_key)) {
            return this.buildComposite((CompositePrivateKey)privateKey);
        }
        try {
            if (this.sigAlgSpec == null) {
                if (isAlgIdFromPrivate.contains(Strings.toUpperCase(this.signatureAlgorithm))) {
                    this.sigAlgId = SIGNATURE_ALGORITHM_IDENTIFIER_FINDER.find(privateKey.getAlgorithm());
                    if (this.sigAlgId == null) {
                        this.sigAlgId = PrivateKeyInfo.getInstance(privateKey.getEncoded()).getPrivateKeyAlgorithm();
                    }
                    this.sigAlgSpec = null;
                } else {
                    this.sigAlgId = SIGNATURE_ALGORITHM_IDENTIFIER_FINDER.find(this.signatureAlgorithm);
                    this.sigAlgSpec = null;
                }
            }
            final AlgorithmIdentifier algorithmIdentifier = this.sigAlgId;
            final Signature signature = this.helper.createSignature(this.sigAlgId);
            if (this.random != null) {
                signature.initSign(privateKey, this.random);
            } else {
                signature.initSign(privateKey);
            }
            return new ContentSigner(){
                private OutputStream stream;
                {
                    this.stream = OutputStreamFactory.createStream(signature);
                }

                @Override
                public AlgorithmIdentifier getAlgorithmIdentifier() {
                    return algorithmIdentifier;
                }

                @Override
                public OutputStream getOutputStream() {
                    return this.stream;
                }

                @Override
                public byte[] getSignature() {
                    try {
                        return signature.sign();
                    }
                    catch (SignatureException signatureException) {
                        throw new RuntimeOperatorException("exception obtaining signature: " + signatureException.getMessage(), signatureException);
                    }
                }
            };
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new OperatorCreationException("cannot create signer: " + generalSecurityException.getMessage(), generalSecurityException);
        }
    }

    private ContentSigner buildComposite(CompositePrivateKey compositePrivateKey) throws OperatorCreationException {
        try {
            List<PrivateKey> list = compositePrivateKey.getPrivateKeys();
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(this.sigAlgId.getParameters());
            final Signature[] signatureArray = new Signature[aSN1Sequence.size()];
            for (int i = 0; i != aSN1Sequence.size(); ++i) {
                signatureArray[i] = this.helper.createSignature(AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i)));
                if (this.random != null) {
                    signatureArray[i].initSign(list.get(i), this.random);
                    continue;
                }
                signatureArray[i].initSign(list.get(i));
            }
            OutputStream outputStream2 = OutputStreamFactory.createStream(signatureArray[0]);
            for (int i = 1; i != signatureArray.length; ++i) {
                outputStream2 = new TeeOutputStream(outputStream2, OutputStreamFactory.createStream(signatureArray[i]));
            }
            final OutputStream outputStream3 = outputStream2;
            return new ContentSigner(){
                OutputStream stream;
                {
                    this.stream = outputStream3;
                }

                @Override
                public AlgorithmIdentifier getAlgorithmIdentifier() {
                    return JcaContentSignerBuilder.this.sigAlgId;
                }

                @Override
                public OutputStream getOutputStream() {
                    return this.stream;
                }

                @Override
                public byte[] getSignature() {
                    try {
                        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                        for (int i = 0; i != signatureArray.length; ++i) {
                            aSN1EncodableVector.add(new DERBitString(signatureArray[i].sign()));
                        }
                        return new DERSequence(aSN1EncodableVector).getEncoded("DER");
                    }
                    catch (IOException iOException) {
                        throw new RuntimeOperatorException("exception encoding signature: " + iOException.getMessage(), iOException);
                    }
                    catch (SignatureException signatureException) {
                        throw new RuntimeOperatorException("exception obtaining signature: " + signatureException.getMessage(), signatureException);
                    }
                }
            };
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new OperatorCreationException("cannot create signer: " + generalSecurityException.getMessage(), generalSecurityException);
        }
    }

    private static RSASSAPSSparams createPSSParams(PSSParameterSpec pSSParameterSpec) {
        AlgorithmIdentifier algorithmIdentifier;
        DefaultDigestAlgorithmIdentifierFinder defaultDigestAlgorithmIdentifierFinder = new DefaultDigestAlgorithmIdentifierFinder();
        AlgorithmIdentifier algorithmIdentifier2 = defaultDigestAlgorithmIdentifierFinder.find(pSSParameterSpec.getDigestAlgorithm());
        if (algorithmIdentifier2.getParameters() == null) {
            algorithmIdentifier2 = new AlgorithmIdentifier(algorithmIdentifier2.getAlgorithm(), DERNull.INSTANCE);
        }
        if ((algorithmIdentifier = defaultDigestAlgorithmIdentifierFinder.find(((MGF1ParameterSpec)pSSParameterSpec.getMGFParameters()).getDigestAlgorithm())).getParameters() == null) {
            algorithmIdentifier = new AlgorithmIdentifier(algorithmIdentifier.getAlgorithm(), DERNull.INSTANCE);
        }
        return new RSASSAPSSparams(algorithmIdentifier2, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new ASN1Integer(pSSParameterSpec.getSaltLength()), new ASN1Integer(pSSParameterSpec.getTrailerField()));
    }

    private static ASN1Sequence createCompParams(CompositeAlgorithmSpec compositeAlgorithmSpec) {
        DefaultSignatureAlgorithmIdentifierFinder defaultSignatureAlgorithmIdentifierFinder = new DefaultSignatureAlgorithmIdentifierFinder();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        List<String> list = compositeAlgorithmSpec.getAlgorithmNames();
        List<AlgorithmParameterSpec> list2 = compositeAlgorithmSpec.getParameterSpecs();
        for (int i = 0; i != list.size(); ++i) {
            AlgorithmParameterSpec algorithmParameterSpec = list2.get(i);
            if (algorithmParameterSpec == null) {
                aSN1EncodableVector.add(defaultSignatureAlgorithmIdentifierFinder.find(list.get(i)));
                continue;
            }
            if (algorithmParameterSpec instanceof PSSParameterSpec) {
                aSN1EncodableVector.add(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_RSASSA_PSS, JcaContentSignerBuilder.createPSSParams((PSSParameterSpec)algorithmParameterSpec)));
                continue;
            }
            throw new IllegalArgumentException("unrecognized parameterSpec");
        }
        return new DERSequence(aSN1EncodableVector);
    }

    static {
        isAlgIdFromPrivate.add("DILITHIUM");
        isAlgIdFromPrivate.add("SPHINCS+");
        isAlgIdFromPrivate.add("SPHINCSPlus");
        isAlgIdFromPrivate.add("ML-DSA");
        isAlgIdFromPrivate.add("SLH-DSA");
        isAlgIdFromPrivate.add("HASH-ML-DSA");
        isAlgIdFromPrivate.add("HASH-SLH-DSA");
    }
}

