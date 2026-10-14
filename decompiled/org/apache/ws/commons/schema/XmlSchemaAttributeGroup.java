/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaAnyAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroupMember;
import org.apache.ws.commons.schema.utils.CollectionFactory;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedImpl;

public class XmlSchemaAttributeGroup
extends XmlSchemaAnnotated
implements XmlSchemaNamed,
XmlSchemaAttributeGroupMember {
    private XmlSchemaAnyAttribute anyAttribute;
    private List<XmlSchemaAttributeGroupMember> attributes;
    private XmlSchemaNamedImpl namedDelegate;

    public XmlSchemaAttributeGroup(XmlSchema parent) {
        final XmlSchema fParent = parent;
        this.namedDelegate = new XmlSchemaNamedImpl(parent, true);
        CollectionFactory.withSchemaModifiable(new Runnable(){

            @Override
            public void run() {
                fParent.getItems().add(XmlSchemaAttributeGroup.this);
            }
        });
        this.attributes = CollectionFactory.getList(XmlSchemaAttributeGroupMember.class);
    }

    public XmlSchemaAnyAttribute getAnyAttribute() {
        return this.anyAttribute;
    }

    public void setAnyAttribute(XmlSchemaAnyAttribute anyAttribute) {
        this.anyAttribute = anyAttribute;
    }

    public List<XmlSchemaAttributeGroupMember> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.namedDelegate.getName();
    }

    @Override
    public XmlSchema getParent() {
        return this.namedDelegate.getParent();
    }

    @Override
    public QName getQName() {
        return this.namedDelegate.getQName();
    }

    @Override
    public boolean isAnonymous() {
        return this.namedDelegate.isAnonymous();
    }

    @Override
    public boolean isTopLevel() {
        return this.namedDelegate.isTopLevel();
    }

    @Override
    public void setName(String name) {
        final String fName = name;
        CollectionFactory.withSchemaModifiable(new Runnable(){

            @Override
            public void run() {
                if (fName != null) {
                    XmlSchemaAttributeGroup.this.namedDelegate.getParent().getAttributeGroups().remove(XmlSchemaAttributeGroup.this.getQName());
                }
                XmlSchemaAttributeGroup.this.namedDelegate.setName(fName);
                XmlSchemaAttributeGroup.this.namedDelegate.getParent().getAttributeGroups().put(XmlSchemaAttributeGroup.this.getQName(), XmlSchemaAttributeGroup.this);
            }
        });
    }
}

