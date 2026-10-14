/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.saml.common.SAMLObject;

public interface Logo
extends LangBearing,
SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Logo";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "Logo", "mdui");
    public static final String TYPE_LOCAL_NAME = "LogoType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "LogoType", "mdui");
    public static final String HEIGHT_ATTR_NAME = "height";
    public static final String WIDTH_ATTR_NAME = "width";

    public String getURL();

    public void setURL(String var1);

    public Integer getHeight();

    public void setHeight(Integer var1);

    public Integer getWidth();

    public void setWidth(Integer var1);
}

