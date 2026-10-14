/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import com.google.common.base.Strings;
import java.util.List;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdui.Keywords;

public class KeywordsImpl
extends AbstractSAMLObject
implements Keywords {
    private String lang;
    private List<String> data;

    protected KeywordsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }

    @Override
    public String getXMLLang() {
        return this.lang;
    }

    @Override
    public void setXMLLang(String newLang) {
        boolean hasValue = newLang != null && !Strings.isNullOrEmpty(newLang);
        this.lang = this.prepareForAssignment(this.lang, newLang);
        this.manageQualifiedAttributeNamespace(LangBearing.XML_LANG_ATTR_NAME, hasValue);
    }

    @Override
    public List<String> getKeywords() {
        return this.data;
    }

    @Override
    public void setKeywords(List<String> val) {
        this.data = this.prepareForAssignment(this.data, val);
    }

    @Override
    public int hashCode() {
        int hash = this.lang.hashCode();
        for (String s2 : this.data) {
            hash = hash * 31 + s2.hashCode();
        }
        return hash;
    }
}

