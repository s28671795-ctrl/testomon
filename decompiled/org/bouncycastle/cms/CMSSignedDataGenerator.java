/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.CMSUtils;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;

public class CMSSignedDataGenerator
extends CMSSignedGenerator {
    private List signerInfs = new ArrayList();
    private boolean isDefiniteLength = false;

    public CMSSignedDataGenerator() {
    }

    public CMSSignedDataGenerator(DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        super(digestAlgorithmIdentifierFinder);
    }

    public void setDefiniteLengthEncoding(boolean bl) {
        this.isDefiniteLength = bl;
    }

    public CMSSignedData generate(CMSTypedData cMSTypedData) throws CMSException {
        return this.generate(cMSTypedData, false);
    }

    public CMSSignedData generate(CMSTypedData cMSTypedData, boolean bl) throws CMSException {
        Object object;
        ASN1Object aSN1Object;
        Object object22;
        Object object3;
        Object object42;
        if (!this.signerInfs.isEmpty()) {
            throw new IllegalStateException("this method can only be used with SignerInfoGenerator");
        }
        LinkedHashSet<AlgorithmIdentifier> linkedHashSet = new LinkedHashSet<AlgorithmIdentifier>();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        this.digests.clear();
        for (Object object42 : this._signers) {
            CMSUtils.addDigestAlgs(linkedHashSet, (SignerInformation)object42, this.digestAlgIdFinder);
            aSN1EncodableVector.add(((SignerInformation)object42).toASN1Structure());
        }
        ASN1ObjectIdentifier aSN1ObjectIdentifier = cMSTypedData.getContentType();
        object42 = null;
        if (cMSTypedData.getContent() != null) {
            object3 = null;
            if (bl) {
                object3 = new ByteArrayOutputStream();
            }
            object22 = CMSUtils.attachSignersToOutputStream(this.signerGens, object3);
            object22 = CMSUtils.getSafeOutputStream((OutputStream)object22);
            try {
                cMSTypedData.write((OutputStream)object22);
                ((OutputStream)object22).close();
            }
            catch (IOException iOException) {
                throw new CMSException("data processing exception: " + iOException.getMessage(), iOException);
            }
            if (bl) {
                object42 = this.isDefiniteLength ? new DEROctetString(((ByteArrayOutputStream)object3).toByteArray()) : new BEROctetString(((ByteArrayOutputStream)object3).toByteArray());
            }
        }
        for (Object object22 : this.signerGens) {
            aSN1Object = ((SignerInfoGenerator)object22).generate(aSN1ObjectIdentifier);
            linkedHashSet.add(((SignerInfo)aSN1Object).getDigestAlgorithm());
            aSN1EncodableVector.add(aSN1Object);
            object = ((SignerInfoGenerator)object22).getCalculatedDigest();
            if (object == null) continue;
            this.digests.put(((SignerInfo)aSN1Object).getDigestAlgorithm().getAlgorithm().getId(), object);
        }
        object3 = CMSSignedDataGenerator.createSetFromList(this.certs, this.isDefiniteLength);
        object22 = CMSSignedDataGenerator.createSetFromList(this.crls, this.isDefiniteLength);
        aSN1Object = new ContentInfo(aSN1ObjectIdentifier, (ASN1Encodable)object42);
        object = new SignedData(CMSUtils.convertToDlSet(linkedHashSet), (ContentInfo)aSN1Object, (ASN1Set)object3, (ASN1Set)object22, new DERSet(aSN1EncodableVector));
        ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, (ASN1Encodable)object);
        return new CMSSignedData((CMSProcessable)cMSTypedData, contentInfo);
    }

    private static ASN1Set createSetFromList(List list, boolean bl) {
        if (list.size() != 0) {
            return bl ? CMSUtils.createDlSetFromList(list) : CMSUtils.createBerSetFromList(list);
        }
        return null;
    }

    public SignerInformationStore generateCounterSigners(SignerInformation signerInformation) throws CMSException {
        return this.generate(new CMSProcessableByteArray(null, signerInformation.getSignature()), false).getSignerInfos();
    }
}

