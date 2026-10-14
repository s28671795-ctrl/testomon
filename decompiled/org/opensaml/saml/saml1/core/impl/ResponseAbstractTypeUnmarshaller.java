/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.saml.saml1.core.impl.ResponseUnmarshaller;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public abstract class ResponseAbstractTypeUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ResponseUnmarshaller.class);

    @Override
    public XMLObject unmarshall(Element domElement) throws UnmarshallingException {
        ResponseAbstractType response = (ResponseAbstractType)super.unmarshall(domElement);
        if (response.getVersion() != SAMLVersion.VERSION_10 && !Strings.isNullOrEmpty(response.getID())) {
            domElement.setIdAttributeNS(null, "ResponseID", true);
        }
        return response;
    }

    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        ResponseAbstractType response = (ResponseAbstractType)parentSAMLObject;
        if (childSAMLObject instanceof Signature) {
            response.setSignature((Signature)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        block21: {
            ResponseAbstractType response = (ResponseAbstractType)samlObject;
            if (attribute.getNamespaceURI() == null) {
                if (attribute.getLocalName().equals("ResponseID")) {
                    response.setID(attribute.getValue());
                } else if (attribute.getLocalName().equals("InResponseTo")) {
                    response.setInResponseTo(attribute.getValue());
                } else if (attribute.getLocalName().equals("IssueInstant") && !Strings.isNullOrEmpty(attribute.getValue())) {
                    response.setIssueInstant(new DateTime((Object)attribute.getValue(), (Chronology)ISOChronology.getInstanceUTC()));
                } else {
                    if (attribute.getLocalName().equals("MajorVersion")) {
                        try {
                            int major = Integer.parseInt(attribute.getValue());
                            if (major != 1) {
                                throw new UnmarshallingException("MajorVersion was invalid, must be 1");
                            }
                            break block21;
                        }
                        catch (NumberFormatException n) {
                            this.log.error("Failed to parse major version", n);
                            throw new UnmarshallingException(n);
                        }
                    }
                    if (attribute.getLocalName().equals("MinorVersion")) {
                        int minor;
                        try {
                            minor = Integer.parseInt(attribute.getValue());
                        }
                        catch (NumberFormatException n) {
                            this.log.error("Failed to parse minor version", n);
                            throw new UnmarshallingException(n);
                        }
                        if (minor == 0) {
                            response.setVersion(SAMLVersion.VERSION_10);
                        } else if (minor == 1) {
                            response.setVersion(SAMLVersion.VERSION_11);
                        }
                    } else if (attribute.getLocalName().equals("Recipient")) {
                        response.setRecipient(attribute.getValue());
                    } else {
                        super.processAttribute(samlObject, attribute);
                    }
                }
            } else {
                super.processAttribute(samlObject, attribute);
            }
        }
    }
}

