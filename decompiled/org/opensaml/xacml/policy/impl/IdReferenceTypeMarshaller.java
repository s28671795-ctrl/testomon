/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSStringMarshaller;
import org.opensaml.xacml.policy.IdReferenceType;
import org.w3c.dom.Element;

public class IdReferenceTypeMarshaller
extends XSStringMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        IdReferenceType idReferenceType = (IdReferenceType)xmlObject;
        if (!Strings.isNullOrEmpty(idReferenceType.getEarliestVersion())) {
            domElement.setAttributeNS(null, "EarliestVersion", idReferenceType.getEarliestVersion());
        }
        if (!Strings.isNullOrEmpty(idReferenceType.getLatestVersion())) {
            domElement.setAttributeNS(null, "LatestVersion", idReferenceType.getLatestVersion());
        }
        if (!Strings.isNullOrEmpty(idReferenceType.getVersion())) {
            domElement.setAttributeNS(null, "Version", idReferenceType.getVersion());
        }
    }
}

