/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroupMember;
import org.apache.ws.commons.schema.XmlSchemaAttributeOrGroupRef;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaItemWithRef;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaUse;
import org.apache.ws.commons.schema.utils.CollectionFactory;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedWithForm;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedWithFormImpl;
import org.apache.ws.commons.schema.utils.XmlSchemaRef;
import org.apache.ws.commons.schema.utils.XmlSchemaRefBase;

public class XmlSchemaAttribute
extends XmlSchemaAttributeOrGroupRef
implements XmlSchemaNamedWithForm,
XmlSchemaAttributeGroupMember,
XmlSchemaItemWithRef<XmlSchemaAttribute> {
    private String defaultValue;
    private String fixedValue;
    private XmlSchemaSimpleType schemaType;
    private QName schemaTypeName;
    private XmlSchemaUse use;
    private XmlSchemaNamedWithFormImpl namedDelegate;
    private XmlSchemaRef<XmlSchemaAttribute> ref;

    public XmlSchemaAttribute(XmlSchema schema, boolean topLevel) {
        this.namedDelegate = new XmlSchemaNamedWithFormImpl(schema, topLevel, false);
        this.ref = new XmlSchemaRef<XmlSchemaAttribute>(schema, XmlSchemaAttribute.class);
        this.namedDelegate.setRefObject(this.ref);
        this.ref.setNamedObject(this.namedDelegate);
        this.use = XmlSchemaUse.NONE;
        final XmlSchema fSchema = schema;
        if (topLevel) {
            CollectionFactory.withSchemaModifiable(new Runnable(){

                @Override
                public void run() {
                    fSchema.getItems().add(XmlSchemaAttribute.this);
                }
            });
        }
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFixedValue() {
        return this.fixedValue;
    }

    public void setFixedValue(String fixedValue) {
        this.fixedValue = fixedValue;
    }

    @Override
    public XmlSchemaRef<XmlSchemaAttribute> getRef() {
        return this.ref;
    }

    public XmlSchemaSimpleType getSchemaType() {
        return this.schemaType;
    }

    public void setSchemaType(XmlSchemaSimpleType schemaType) {
        this.schemaType = schemaType;
    }

    public QName getSchemaTypeName() {
        return this.schemaTypeName;
    }

    public void setSchemaTypeName(QName schemaTypeName) {
        this.schemaTypeName = schemaTypeName;
    }

    public XmlSchemaUse getUse() {
        return this.use;
    }

    public void setUse(XmlSchemaUse use) {
        if (this.namedDelegate.isTopLevel() && use != null) {
            throw new XmlSchemaException("Top-level attributes may not have a 'use'");
        }
        this.use = use;
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
                if (XmlSchemaAttribute.this.namedDelegate.isTopLevel() && XmlSchemaAttribute.this.namedDelegate.getName() != null) {
                    XmlSchemaAttribute.this.namedDelegate.getParent().getAttributes().remove(XmlSchemaAttribute.this.getQName());
                }
                XmlSchemaAttribute.this.namedDelegate.setName(fName);
                if (XmlSchemaAttribute.this.namedDelegate.isTopLevel()) {
                    if (fName == null) {
                        throw new XmlSchemaException("Top-level attributes may not be anonymous");
                    }
                    XmlSchemaAttribute.this.namedDelegate.getParent().getAttributes().put(XmlSchemaAttribute.this.getQName(), XmlSchemaAttribute.this);
                }
            }
        });
    }

    @Override
    public boolean isFormSpecified() {
        return this.namedDelegate.isFormSpecified();
    }

    @Override
    public XmlSchemaForm getForm() {
        return this.namedDelegate.getForm();
    }

    @Override
    public void setForm(XmlSchemaForm form) {
        if (this.namedDelegate.isTopLevel() && form != XmlSchemaForm.NONE) {
            throw new XmlSchemaException("Top-level attributes may not have a 'form'");
        }
        this.namedDelegate.setForm(form);
    }

    @Override
    public QName getWireName() {
        return this.namedDelegate.getWireName();
    }

    @Override
    public boolean isRef() {
        return this.ref.getTargetQName() != null;
    }

    @Override
    public QName getTargetQName() {
        return this.ref.getTargetQName();
    }

    @Override
    public XmlSchemaRefBase getRefBase() {
        return this.ref;
    }
}

