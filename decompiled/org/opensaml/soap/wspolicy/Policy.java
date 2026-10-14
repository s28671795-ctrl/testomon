/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.soap.wspolicy.OperatorContentType;
import org.opensaml.soap.wssecurity.IdBearing;

public interface Policy
extends OperatorContentType,
AttributeExtensibleXMLObject,
IdBearing {
    public static final String ELEMENT_LOCAL_NAME = "Policy";
    public static final QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "Policy", "wsp");
    public static final String NAME_ATTRIB_NAME = "Name";

    public String getName();

    public void setName(String var1);
}

