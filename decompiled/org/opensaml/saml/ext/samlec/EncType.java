/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.samlec;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;

public interface EncType
extends XSString,
SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncType";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:ietf:params:xml:ns:samlec", "EncType", "samlec");
}

