/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAnyAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeOrGroupRef;
import org.apache.ws.commons.schema.XmlSchemaComplexContentExtension;
import org.apache.ws.commons.schema.XmlSchemaComplexContentRestriction;
import org.apache.ws.commons.schema.XmlSchemaContent;
import org.apache.ws.commons.schema.XmlSchemaContentModel;
import org.apache.ws.commons.schema.XmlSchemaContentType;
import org.apache.ws.commons.schema.XmlSchemaDerivationMethod;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaType;

public class XmlSchemaComplexType
extends XmlSchemaType {
    private XmlSchemaAnyAttribute anyAttribute;
    private XmlSchemaAnyAttribute attributeWildcard;
    private List<XmlSchemaAttributeOrGroupRef> attributes = Collections.synchronizedList(new ArrayList());
    private XmlSchemaDerivationMethod block = XmlSchemaDerivationMethod.NONE;
    private XmlSchemaDerivationMethod blockResolved;
    private XmlSchemaContentModel contentModel;
    private XmlSchemaContentType contentType;
    private XmlSchemaParticle particleType;
    private XmlSchemaParticle particle;
    private boolean isAbstract = false;
    private boolean isMixed = false;

    public XmlSchemaComplexType(XmlSchema schema, boolean topLevel) {
        super(schema, topLevel);
    }

    public XmlSchemaAnyAttribute getAnyAttribute() {
        return this.anyAttribute;
    }

    public void setAnyAttribute(XmlSchemaAnyAttribute anyAttribute) {
        this.anyAttribute = anyAttribute;
    }

    public List<XmlSchemaAttributeOrGroupRef> getAttributes() {
        return this.attributes;
    }

    public XmlSchemaAnyAttribute getAttributeWildcard() {
        return this.attributeWildcard;
    }

    public XmlSchemaDerivationMethod getBlock() {
        return this.block;
    }

    public void setBlock(XmlSchemaDerivationMethod block) {
        this.block = block;
    }

    public XmlSchemaDerivationMethod getBlockResolved() {
        return this.blockResolved;
    }

    public XmlSchemaContentModel getContentModel() {
        return this.contentModel;
    }

    public void setContentModel(XmlSchemaContentModel contentModel) {
        this.contentModel = contentModel;
    }

    public XmlSchemaContentType getContentType() {
        return this.contentType;
    }

    public void setContentType(XmlSchemaContentType contentType) {
        this.contentType = contentType;
    }

    public XmlSchemaParticle getContentTypeParticle() {
        return this.particleType;
    }

    public boolean isAbstract() {
        return this.isAbstract;
    }

    public void setAbstract(boolean b) {
        this.isAbstract = b;
    }

    @Override
    public boolean isMixed() {
        return this.isMixed;
    }

    @Override
    public void setMixed(boolean b) {
        this.isMixed = b;
    }

    public XmlSchemaParticle getParticle() {
        return this.particle;
    }

    public void setParticle(XmlSchemaParticle particle) {
        this.particle = particle;
    }

    public QName getBaseSchemaTypeName() {
        XmlSchemaContentModel model = this.getContentModel();
        if (model == null) {
            return null;
        }
        XmlSchemaContent content = model.getContent();
        if (content == null) {
            return null;
        }
        if (content instanceof XmlSchemaComplexContentExtension) {
            return ((XmlSchemaComplexContentExtension)content).getBaseTypeName();
        }
        if (content instanceof XmlSchemaComplexContentRestriction) {
            return ((XmlSchemaComplexContentRestriction)content).getBaseTypeName();
        }
        return null;
    }

    void setAttributeWildcard(XmlSchemaAnyAttribute attributeWildcard) {
        this.attributeWildcard = attributeWildcard;
    }

    void setAttributes(List<XmlSchemaAttributeOrGroupRef> attributes) {
        this.attributes = attributes;
    }

    void setBlockResolved(XmlSchemaDerivationMethod blockResolved) {
        this.blockResolved = blockResolved;
    }

    void setParticleType(XmlSchemaParticle particleType) {
        this.particleType = particleType;
    }

    XmlSchemaParticle getParticleType() {
        return this.particleType;
    }
}

