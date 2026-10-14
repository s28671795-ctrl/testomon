/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsfed;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.soap.wsfed.AppliesTo;
import org.opensaml.soap.wsfed.RequestedSecurityToken;
import org.opensaml.soap.wsfed.WSFedObject;

public interface RequestSecurityTokenResponse
extends WSFedObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RequestSecurityTokenResponse";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityTokenResponse", "wst");
    public static final String TYPE_LOCAL_NAME = "RequestSecurityTokenResponseType";
    public static final QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityTokenResponseType", "wst");

    public AppliesTo getAppliesTo();

    public void setAppliesTo(AppliesTo var1);

    public List<RequestedSecurityToken> getRequestedSecurityToken();
}

