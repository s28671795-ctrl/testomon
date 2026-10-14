/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2mdui.Description;
import org.opensaml.saml.ext.saml2mdui.DisplayName;
import org.opensaml.saml.ext.saml2mdui.InformationURL;
import org.opensaml.saml.ext.saml2mdui.Keywords;
import org.opensaml.saml.ext.saml2mdui.Logo;
import org.opensaml.saml.ext.saml2mdui.PrivacyStatementURL;

public interface UIInfo
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "UIInfo";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "UIInfo", "mdui");
    public static final String TYPE_LOCAL_NAME = "UIInfoType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "UIInfoType", "mdui");
    public static final String LANG_ATTRIB_NAME = "lang";

    public List<DisplayName> getDisplayNames();

    public List<Keywords> getKeywords();

    public List<Description> getDescriptions();

    public List<Logo> getLogos();

    public List<InformationURL> getInformationURLs();

    public List<PrivacyStatementURL> getPrivacyStatementURLs();

    public List<XMLObject> getXMLObjects();

    public List<XMLObject> getXMLObjects(QName var1);
}

