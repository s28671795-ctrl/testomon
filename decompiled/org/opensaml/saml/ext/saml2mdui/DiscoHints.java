/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2mdui.DomainHint;
import org.opensaml.saml.ext.saml2mdui.GeolocationHint;
import org.opensaml.saml.ext.saml2mdui.IPHint;

public interface DiscoHints
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DiscoHints";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "DiscoHints", "mdui");
    public static final String TYPE_LOCAL_NAME = "DiscoHintsType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "DiscoHintsType", "mdui");

    public List<IPHint> getIPHints();

    public List<DomainHint> getDomainHints();

    public List<GeolocationHint> getGeolocationHints();

    public List<XMLObject> getXMLObjects();

    public List<XMLObject> getXMLObjects(QName var1);
}

