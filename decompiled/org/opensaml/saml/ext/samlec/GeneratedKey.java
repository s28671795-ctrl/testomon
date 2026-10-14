/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.samlec;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface GeneratedKey
extends XSBase64Binary,
SAMLObject,
MustUnderstandBearing,
ActorBearing {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "GeneratedKey";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:ietf:params:xml:ns:samlec", "GeneratedKey", "samlec");
    public static final String TYPE_LOCAL_NAME = "GeneratedKeyType";
    public static final QName TYPE_NAME = new QName("urn:ietf:params:xml:ns:samlec", "GeneratedKeyType", "samlec");
}

