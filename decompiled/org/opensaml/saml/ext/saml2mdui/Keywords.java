/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.saml.common.SAMLObject;

public interface Keywords
extends SAMLObject,
LangBearing {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Keywords";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "Keywords", "mdui");
    public static final String TYPE_LOCAL_NAME = "KeywordsType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "KeywordsType", "mdui");

    public List<String> getKeywords();

    public void setKeywords(List<String> var1);
}

