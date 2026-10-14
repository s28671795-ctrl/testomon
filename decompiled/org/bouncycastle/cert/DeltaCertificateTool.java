/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.cert;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.cert.X509CertificateHolder;

public class DeltaCertificateTool {
    public static Extension makeDeltaCertificateExtension(boolean bl, X509CertificateHolder x509CertificateHolder) throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(x509CertificateHolder.getSerialNumber()));
        aSN1EncodableVector.add(new DERTaggedObject(true, 0, (ASN1Encodable)x509CertificateHolder.getSignatureAlgorithm()));
        aSN1EncodableVector.add(new DERTaggedObject(true, 1, (ASN1Encodable)x509CertificateHolder.getIssuer()));
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector(2);
        aSN1EncodableVector2.add(x509CertificateHolder.toASN1Structure().getStartDate());
        aSN1EncodableVector2.add(x509CertificateHolder.toASN1Structure().getEndDate());
        aSN1EncodableVector.add(new DERTaggedObject(true, 2, (ASN1Encodable)new DERSequence(aSN1EncodableVector2)));
        aSN1EncodableVector.add(new DERTaggedObject(true, 3, (ASN1Encodable)x509CertificateHolder.getSubject()));
        aSN1EncodableVector.add(x509CertificateHolder.getSubjectPublicKeyInfo());
        if (x509CertificateHolder.getExtensions() != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 4, (ASN1Encodable)x509CertificateHolder.getExtensions()));
        }
        aSN1EncodableVector.add(new DERBitString(x509CertificateHolder.getSignature()));
        return new Extension(Extension.deltaCertificateDescriptor, bl, new DERSequence(aSN1EncodableVector).getEncoded("DER"));
    }

    public static X509CertificateHolder extractDeltaCertificate(X509CertificateHolder x509CertificateHolder) {
        Object object;
        Object object2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = Extension.deltaCertificateDescriptor;
        Extension extension = x509CertificateHolder.getExtension(aSN1ObjectIdentifier);
        if (extension == null) {
            throw new IllegalStateException("no deltaCertificateDescriptor present");
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(extension.getParsedValue());
        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(x509CertificateHolder.toASN1Structure().getTBSCertificate().toASN1Primitive());
        int n = 0;
        ASN1Encodable[] aSN1EncodableArray = aSN1Sequence2.toArray();
        aSN1EncodableArray[0] = aSN1Sequence2.getObjectAt(0);
        aSN1EncodableArray[1] = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(n++));
        ASN1Encodable aSN1Encodable = aSN1Sequence.getObjectAt(n++);
        while (aSN1Encodable instanceof ASN1TaggedObject) {
            object2 = ASN1TaggedObject.getInstance(aSN1Encodable);
            switch (((ASN1TaggedObject)object2).getTagNo()) {
                case 0: {
                    aSN1EncodableArray[2] = ASN1Sequence.getInstance((ASN1TaggedObject)object2, true);
                    break;
                }
                case 1: {
                    aSN1EncodableArray[3] = ASN1Sequence.getInstance((ASN1TaggedObject)object2, true);
                    break;
                }
                case 2: {
                    aSN1EncodableArray[4] = ASN1Sequence.getInstance((ASN1TaggedObject)object2, true);
                    break;
                }
                case 3: {
                    aSN1EncodableArray[5] = ASN1Sequence.getInstance((ASN1TaggedObject)aSN1Encodable, true);
                }
            }
            aSN1Encodable = aSN1Sequence.getObjectAt(n++);
        }
        aSN1EncodableArray[6] = aSN1Encodable;
        if (aSN1EncodableArray[2] == null) {
            aSN1EncodableArray[2] = aSN1Sequence2.getObjectAt(2);
        }
        if (aSN1EncodableArray[3] == null) {
            aSN1EncodableArray[3] = aSN1Sequence2.getObjectAt(3);
        }
        if (aSN1EncodableArray[4] == null) {
            aSN1EncodableArray[4] = aSN1Sequence2.getObjectAt(4);
        }
        if (aSN1EncodableArray[5] == null) {
            aSN1EncodableArray[5] = aSN1Sequence2.getObjectAt(5);
        }
        object2 = DeltaCertificateTool.extractExtensions(aSN1Sequence2);
        if (n < aSN1Sequence.size() - 1) {
            if (((ASN1TaggedObject)(object = ASN1TaggedObject.getInstance(aSN1Encodable = aSN1Sequence.getObjectAt(n++)))).getTagNo() != 4) {
                throw new IllegalArgumentException("malformed delta extension");
            }
            ASN1Sequence aSN1Sequence3 = ASN1Sequence.getInstance((ASN1TaggedObject)object, true);
            for (int i = 0; i != aSN1Sequence3.size(); ++i) {
                Extension extension2 = Extension.getInstance(aSN1Sequence3.getObjectAt(i));
                ((ExtensionsGenerator)object2).replaceExtension(extension2);
            }
            aSN1EncodableArray[7] = new DERTaggedObject(3, ((ExtensionsGenerator)object2).generate());
        } else {
            aSN1EncodableArray[7] = !((ExtensionsGenerator)object2).isEmpty() ? new DERTaggedObject(3, ((ExtensionsGenerator)object2).generate()) : null;
        }
        object = new ASN1EncodableVector(7);
        for (int i = 0; i != aSN1EncodableArray.length; ++i) {
            if (aSN1EncodableArray[i] == null) continue;
            ((ASN1EncodableVector)object).add(aSN1EncodableArray[i]);
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DERSequence((ASN1EncodableVector)object));
        aSN1EncodableVector.add(ASN1Sequence.getInstance(aSN1EncodableArray[2]));
        aSN1EncodableVector.add(ASN1BitString.getInstance(aSN1Sequence.getObjectAt(aSN1Sequence.size() - 1)));
        return new X509CertificateHolder(Certificate.getInstance(new DERSequence(aSN1EncodableVector)));
    }

    private static ExtensionsGenerator extractExtensions(ASN1Sequence aSN1Sequence) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = Extension.deltaCertificateDescriptor;
        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(aSN1Sequence.size() - 1)), true);
        ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
        for (int i = 0; i != aSN1Sequence2.size(); ++i) {
            Extension extension = Extension.getInstance(aSN1Sequence2.getObjectAt(i));
            if (aSN1ObjectIdentifier.equals(extension.getExtnId())) continue;
            extensionsGenerator.addExtension(extension);
        }
        return extensionsGenerator;
    }
}

