/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface OptionalBearing {
    public static final String WSP_OPTIONAL_ATTR_LOCAL_NAME = "Optional";
    public static final QName WSP_OPTIONAL_ATTR_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "Optional", "wsp");

    public Boolean isWSP12Optional();

    public XSBooleanValue isWSP12OptionalXSBoolean();

    public void setWSP12Optional(Boolean var1);

    public void setWSP12Optional(XSBooleanValue var1);
}

