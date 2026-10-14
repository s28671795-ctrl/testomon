/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.transforms.implementations;

import java.io.OutputStream;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.TransformationException;

public class TransformXPointer
extends TransformSpi {
    public static final String implementedTransformURI = "http://www.w3.org/TR/2001/WD-xptr-20010108";

    @Override
    protected String engineGetURI() {
        return implementedTransformURI;
    }

    @Override
    protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws TransformationException {
        Object[] exArgs = new Object[]{implementedTransformURI};
        throw new TransformationException("signature.Transform.NotYetImplemented", exArgs);
    }
}

