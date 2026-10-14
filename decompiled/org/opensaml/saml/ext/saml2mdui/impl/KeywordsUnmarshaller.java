/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui.impl;

import java.util.ArrayList;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdui.Keywords;
import org.w3c.dom.Attr;

public class KeywordsUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        if (attribute.getLocalName().equals("lang") && "http://www.w3.org/XML/1998/namespace".equals(attribute.getNamespaceURI())) {
            Keywords keywords = (Keywords)samlObject;
            keywords.setXMLLang(attribute.getValue());
        }
    }

    @Override
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        Keywords keywords = (Keywords)samlObject;
        String[] words = elementContent.split("\\s+");
        ArrayList<String> wordlist = new ArrayList<String>(words.length);
        for (String s2 : words) {
            wordlist.add(s2);
        }
        keywords.setKeywords(wordlist);
    }
}

