/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2alg.impl;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2alg.SigningMethod;

public class SigningMethodImpl
extends AbstractSAMLObject
implements SigningMethod {
    private final IndexedXMLObjectChildrenList<XMLObject> unknownChildren = new IndexedXMLObjectChildrenList(this);
    private String algorithm;
    private Integer minKeySize;
    private Integer maxKeySize;

    public SigningMethodImpl(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    @Nullable
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public void setAlgorithm(@Nullable String newValue) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newValue);
    }

    @Override
    @Nullable
    public Integer getMinKeySize() {
        return this.minKeySize;
    }

    @Override
    public void setMinKeySize(@Nullable Integer newValue) {
        this.minKeySize = this.prepareForAssignment(this.minKeySize, newValue);
    }

    @Override
    @Nullable
    public Integer getMaxKeySize() {
        return this.maxKeySize;
    }

    @Override
    public void setMaxKeySize(@Nullable Integer newValue) {
        this.maxKeySize = this.prepareForAssignment(this.maxKeySize, newValue);
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownChildren;
    }

    @Override
    public List<XMLObject> getUnknownXMLObjects(QName typeOrName) {
        return this.unknownChildren.subList(typeOrName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList(this.unknownChildren);
    }
}

