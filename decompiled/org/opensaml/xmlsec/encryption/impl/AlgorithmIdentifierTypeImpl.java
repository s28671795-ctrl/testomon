/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.AlgorithmIdentifierType;

public abstract class AlgorithmIdentifierTypeImpl
extends AbstractXMLObject
implements AlgorithmIdentifierType {
    private String algorithm;
    private XMLObject parameters;

    protected AlgorithmIdentifierTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    @Nullable
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public void setAlgorithm(@Nullable String newAlgorithm) {
        this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
    }

    @Override
    @Nullable
    public XMLObject getParameters() {
        return this.parameters;
    }

    @Override
    public void setParameters(@Nullable XMLObject newParameters) {
        this.parameters = this.prepareForAssignment(this.parameters, newParameters);
    }

    @Override
    @Nullable
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.parameters != null) {
            children.add(this.parameters);
        }
        return Collections.unmodifiableList(children);
    }
}

