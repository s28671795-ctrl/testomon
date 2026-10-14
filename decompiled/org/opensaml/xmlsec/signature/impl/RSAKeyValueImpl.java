/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.CryptoBinary;
import org.opensaml.xmlsec.signature.Exponent;
import org.opensaml.xmlsec.signature.Modulus;
import org.opensaml.xmlsec.signature.RSAKeyValue;

public class RSAKeyValueImpl
extends AbstractXMLObject
implements RSAKeyValue {
    private Modulus modulus;
    private Exponent exponent;

    protected RSAKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Modulus getModulus() {
        return this.modulus;
    }

    @Override
    public void setModulus(Modulus newModulus) {
        this.modulus = this.prepareForAssignment(this.modulus, newModulus);
    }

    @Override
    public Exponent getExponent() {
        return this.exponent;
    }

    @Override
    public void setExponent(Exponent newExponent) {
        this.exponent = this.prepareForAssignment(this.exponent, newExponent);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<CryptoBinary> children = new ArrayList<CryptoBinary>();
        if (this.modulus != null) {
            children.add(this.modulus);
        }
        if (this.exponent != null) {
            children.add(this.exponent);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

