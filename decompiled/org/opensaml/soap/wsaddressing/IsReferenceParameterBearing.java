/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface IsReferenceParameterBearing {
    public static final String WSA_IS_REFERENCE_PARAMETER_ATTR_LOCAL_NAME = "IsReferenceParameter";
    public static final QName WSA_IS_REFERENCE_PARAMETER_ATTR_NAME = new QName("http://www.w3.org/2005/08/addressing", "IsReferenceParameter", "wsa");

    public Boolean isWSAIsReferenceParameter();

    public XSBooleanValue isWSAIsReferenceParameterXSBoolean();

    public void setWSAIsReferenceParameter(Boolean var1);

    public void setWSAIsReferenceParameter(XSBooleanValue var1);
}

