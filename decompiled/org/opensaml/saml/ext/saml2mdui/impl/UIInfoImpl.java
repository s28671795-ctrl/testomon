/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.Description;
import org.opensaml.saml.ext.saml2mdui.DisplayName;
import org.opensaml.saml.ext.saml2mdui.InformationURL;
import org.opensaml.saml.ext.saml2mdui.Keywords;
import org.opensaml.saml.ext.saml2mdui.Logo;
import org.opensaml.saml.ext.saml2mdui.PrivacyStatementURL;
import org.opensaml.saml.ext.saml2mdui.UIInfo;

public class UIInfoImpl
extends AbstractSAMLObject
implements UIInfo {
    private final IndexedXMLObjectChildrenList<XMLObject> uiInfoChildren = new IndexedXMLObjectChildrenList(this);

    protected UIInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getXMLObjects() {
        return this.uiInfoChildren;
    }

    @Override
    public List<XMLObject> getXMLObjects(QName typeOrName) {
        return this.uiInfoChildren.subList(typeOrName);
    }

    @Override
    public List<Description> getDescriptions() {
        return this.uiInfoChildren.subList(Description.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<DisplayName> getDisplayNames() {
        return this.uiInfoChildren.subList(DisplayName.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<Keywords> getKeywords() {
        return this.uiInfoChildren.subList(Keywords.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<InformationURL> getInformationURLs() {
        return this.uiInfoChildren.subList(InformationURL.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<Logo> getLogos() {
        return this.uiInfoChildren.subList(Logo.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<PrivacyStatementURL> getPrivacyStatementURLs() {
        return this.uiInfoChildren.subList(PrivacyStatementURL.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.uiInfoChildren);
        return children;
    }
}

