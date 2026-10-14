/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectQuery;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeUnmarshaller;

public abstract class SubjectQueryUnmarshaller
extends RequestAbstractTypeUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        SubjectQuery sq = (SubjectQuery)parentSAMLObject;
        if (childSAMLObject instanceof Subject) {
            sq.setSubject((Subject)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }
}

