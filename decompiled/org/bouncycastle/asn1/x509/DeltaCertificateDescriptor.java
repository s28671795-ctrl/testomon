/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificate;

public class DeltaCertificateDescriptor
extends ASN1Object {
    private final ASN1Integer serialNumber;
    private AlgorithmIdentifier signature;
    private X500Name issuer;
    private ASN1Sequence validity;
    private X500Name subject;
    private SubjectPublicKeyInfo subjectPublicKeyInfo;
    private Extensions extensions;
    private final ASN1BitString signatureValue;

    public static DeltaCertificateDescriptor getInstance(Object object) {
        if (object instanceof DeltaCertificateDescriptor) {
            return (DeltaCertificateDescriptor)object;
        }
        if (object != null) {
            return new DeltaCertificateDescriptor(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public static DeltaCertificateDescriptor fromExtensions(Extensions extensions) {
        return DeltaCertificateDescriptor.getInstance(Extensions.getExtensionParsedValue(extensions, Extension.deltaCertificateDescriptor));
    }

    private DeltaCertificateDescriptor(ASN1Sequence aSN1Sequence) {
        ASN1TaggedObject aSN1TaggedObject;
        this.serialNumber = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        int n = 1;
        ASN1Encodable aSN1Encodable = aSN1Sequence.getObjectAt(n);
        while (aSN1Encodable instanceof ASN1TaggedObject) {
            aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Encodable);
            switch (aSN1TaggedObject.getTagNo()) {
                case 0: {
                    this.signature = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                    break;
                }
                case 1: {
                    this.issuer = X500Name.getInstance(aSN1TaggedObject, true);
                    break;
                }
                case 2: {
                    this.validity = ASN1Sequence.getInstance(aSN1TaggedObject, false);
                    break;
                }
                case 3: {
                    this.subject = X500Name.getInstance(aSN1TaggedObject, true);
                }
            }
            aSN1Encodable = aSN1Sequence.getObjectAt(n++);
        }
        this.subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(aSN1Encodable);
        aSN1Encodable = aSN1Sequence.getObjectAt(n);
        while (aSN1Encodable instanceof ASN1TaggedObject) {
            aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Encodable);
            switch (aSN1TaggedObject.getTagNo()) {
                case 4: {
                    this.extensions = Extensions.getInstance(aSN1TaggedObject, false);
                }
            }
            aSN1Encodable = aSN1Sequence.getObjectAt(n++);
        }
        this.signatureValue = ASN1BitString.getInstance(aSN1Encodable);
    }

    public ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public X500Name getIssuer() {
        return this.issuer;
    }

    public ASN1Sequence getValidity() {
        return this.validity;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public ASN1BitString getSignatureValue() {
        return this.signatureValue;
    }

    public DeltaCertificateDescriptor trimTo(TBSCertificate tBSCertificate, Extensions extensions) {
        ASN1Object aSN1Object;
        Object object;
        Object object2;
        ASN1Object aSN1Object2;
        ASN1TaggedObject aSN1TaggedObject;
        AlgorithmIdentifier algorithmIdentifier = tBSCertificate.signature;
        X500Name x500Name = tBSCertificate.issuer;
        DERSequence dERSequence = new DERSequence(new ASN1Encodable[]{tBSCertificate.startDate, tBSCertificate.endDate});
        X500Name x500Name2 = tBSCertificate.subject;
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(this.toASN1Primitive());
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration enumeration = aSN1Sequence.getObjects();
        aSN1EncodableVector.add((ASN1Encodable)enumeration.nextElement());
        ASN1Encodable aSN1Encodable = (ASN1Encodable)enumeration.nextElement();
        while (aSN1Encodable instanceof ASN1TaggedObject) {
            aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Encodable);
            switch (aSN1TaggedObject.getTagNo()) {
                case 0: {
                    aSN1Object2 = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                    if (aSN1Object2.equals(algorithmIdentifier)) break;
                    aSN1EncodableVector.add(aSN1Encodable);
                    break;
                }
                case 1: {
                    object2 = X500Name.getInstance(aSN1TaggedObject, true);
                    if (((X500Name)object2).equals(x500Name)) break;
                    aSN1EncodableVector.add(aSN1Encodable);
                    break;
                }
                case 2: {
                    object = ASN1Sequence.getInstance(aSN1TaggedObject, false);
                    if (((ASN1Primitive)object).equals(dERSequence)) break;
                    aSN1EncodableVector.add(aSN1Encodable);
                    break;
                }
                case 3: {
                    aSN1Object = X500Name.getInstance(aSN1TaggedObject, true);
                    if (((X500Name)aSN1Object).equals(x500Name2)) break;
                    aSN1EncodableVector.add(aSN1Encodable);
                }
            }
            aSN1Encodable = (ASN1Encodable)enumeration.nextElement();
        }
        aSN1EncodableVector.add(aSN1Encodable);
        aSN1Encodable = (ASN1Encodable)enumeration.nextElement();
        while (aSN1Encodable instanceof ASN1TaggedObject) {
            aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Encodable);
            switch (aSN1TaggedObject.getTagNo()) {
                case 4: {
                    aSN1Object2 = Extensions.getInstance(aSN1TaggedObject, false);
                    object2 = new ExtensionsGenerator();
                    object = ((Extensions)aSN1Object2).oids();
                    while (object.hasMoreElements()) {
                        aSN1Object = ((Extensions)aSN1Object2).getExtension((ASN1ObjectIdentifier)object.nextElement());
                        Extension extension = extensions.getExtension(((Extension)aSN1Object).getExtnId());
                        if (extension == null || ((Extension)aSN1Object).equals(extension)) continue;
                        ((ExtensionsGenerator)object2).addExtension((Extension)aSN1Object);
                    }
                    if (((ExtensionsGenerator)object2).isEmpty()) break;
                    aSN1EncodableVector.add(new DERTaggedObject(false, 4, (ASN1Encodable)((ExtensionsGenerator)object2).generate()));
                }
            }
            aSN1Encodable = (ASN1Encodable)enumeration.nextElement();
        }
        aSN1EncodableVector.add(aSN1Encodable);
        return new DeltaCertificateDescriptor(new DERSequence(aSN1EncodableVector));
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, int n, boolean bl, ASN1Object aSN1Object) {
        if (aSN1Object != null) {
            aSN1EncodableVector.add(new DERTaggedObject(bl, n, (ASN1Encodable)aSN1Object));
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(7);
        aSN1EncodableVector.add(this.serialNumber);
        this.addOptional(aSN1EncodableVector, 0, false, this.signature);
        this.addOptional(aSN1EncodableVector, 1, true, this.issuer);
        this.addOptional(aSN1EncodableVector, 2, false, this.validity);
        this.addOptional(aSN1EncodableVector, 3, true, this.subject);
        aSN1EncodableVector.add(this.subjectPublicKeyInfo);
        this.addOptional(aSN1EncodableVector, 4, false, this.extensions);
        aSN1EncodableVector.add(this.signatureValue);
        return new DERSequence(aSN1EncodableVector);
    }
}

