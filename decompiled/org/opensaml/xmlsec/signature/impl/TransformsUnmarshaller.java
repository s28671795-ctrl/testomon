/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.Transforms;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureUnmarshaller;

public class TransformsUnmarshaller
extends AbstractXMLSignatureUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        Transforms transforms = (Transforms)parentXMLObject;
        if (childXMLObject instanceof Transform) {
            transforms.getTransforms().add((Transform)childXMLObject);
        } else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}

