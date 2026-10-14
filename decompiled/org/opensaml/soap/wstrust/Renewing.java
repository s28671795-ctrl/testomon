/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Renewing
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Renewing";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Renewing", "wst");
    public static final String TYPE_LOCAL_NAME = "RenewingType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RenewingType", "wst");
    public static final String ALLOW_ATTRIB_NAME = "Allow";
    public static final String OK_ATTRIB_NAME = "OK";

    public Boolean isAllow();

    public XSBooleanValue isAllowXSBoolean();

    public void setAllow(Boolean var1);

    public void setAllow(XSBooleanValue var1);

    public Boolean isOK();

    public XSBooleanValue isOKXSBoolean();

    public void setOK(Boolean var1);

    public void setOK(XSBooleanValue var1);
}

