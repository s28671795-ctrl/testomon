/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsfed;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wsfed.WSFedObject;

public interface RequestedSecurityToken
extends WSFedObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RequestedSecurityToken";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedSecurityToken", "wst");
    public static final String TYPE_LOCAL_NAME = "RequestedSecurityTokenType";
    public static final QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedSecurityTokenType", "wst");

    public List<XMLObject> getSecurityTokens();
}

