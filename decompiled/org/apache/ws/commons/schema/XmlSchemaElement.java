/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.TypeReceiver;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAllMember;
import org.apache.ws.commons.schema.XmlSchemaChoiceMember;
import org.apache.ws.commons.schema.XmlSchemaDerivationMethod;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaIdentityConstraint;
import org.apache.ws.commons.schema.XmlSchemaItemWithRef;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.utils.CollectionFactory;
import org.apache.ws.commons.schema.utils.UtilObjects;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedWithForm;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedWithFormImpl;
import org.apache.ws.commons.schema.utils.XmlSchemaRef;
import org.apache.ws.commons.schema.utils.XmlSchemaRefBase;

public class XmlSchemaElement
extends XmlSchemaParticle
implements TypeReceiver,
XmlSchemaNamedWithForm,
XmlSchemaChoiceMember,
XmlSchemaSequenceMember,
XmlSchemaAllMember,
XmlSchemaItemWithRef<XmlSchemaElement> {
    private XmlSchemaDerivationMethod block;
    private List<XmlSchemaIdentityConstraint> constraints;
    private String defaultValue;
    private String fixedValue;
    private XmlSchemaDerivationMethod finalDerivation;
    private boolean abstractElement;
    private boolean nillable;
    private XmlSchemaRef<XmlSchemaElement> ref;
    private XmlSchemaType schemaType;
    private QName schemaTypeName;
    private QName substitutionGroup;
    private XmlSchemaNamedWithFormImpl namedDelegate;

    public XmlSchemaElement(XmlSchema parentSchema, boolean topLevel) {
        this.namedDelegate = new XmlSchemaNamedWithFormImpl(parentSchema, topLevel, true);
        this.ref = new XmlSchemaRef<XmlSchemaElement>(parentSchema, XmlSchemaElement.class);
        this.namedDelegate.setRefObject(this.ref);
        this.ref.setNamedObject(this.namedDelegate);
        this.constraints = Collections.synchronizedList(new ArrayList());
        this.abstractElement = false;
        this.nillable = false;
        this.finalDerivation = XmlSchemaDerivationMethod.NONE;
        this.block = XmlSchemaDerivationMethod.NONE;
        final XmlSchema fParentSchema = parentSchema;
        if (topLevel) {
            CollectionFactory.withSchemaModifiable(new Runnable(){

                @Override
                public void run() {
                    fParentSchema.getItems().add(XmlSchemaElement.this);
                }
            });
        }
    }

    @Override
    public boolean equals(Object what) {
        boolean parentCheck = super.equals(what);
        if (!parentCheck) {
            return false;
        }
        if (!(what instanceof XmlSchemaElement)) {
            return false;
        }
        XmlSchemaElement xse = (XmlSchemaElement)what;
        boolean isAbstactElementEq = this.abstractElement == xse.abstractElement;
        boolean isNillableEq = this.nillable == xse.nillable;
        boolean isBlockEq = UtilObjects.equals(this.block, xse.block);
        boolean isConstraintsEq = UtilObjects.equals(this.constraints, xse.constraints);
        boolean isDefaultValueEq = UtilObjects.equals(this.defaultValue, xse.defaultValue);
        boolean isFixedValueEq = UtilObjects.equals(this.fixedValue, xse.fixedValue);
        boolean isFinalDerivationEq = UtilObjects.equals(this.finalDerivation, xse.finalDerivation);
        boolean isRefEq = UtilObjects.equals(this.ref, xse.ref);
        boolean isSchemaTypeEq = UtilObjects.equals(this.schemaType, xse.schemaType);
        boolean isSchemaTypeNameEq = UtilObjects.equals(this.schemaTypeName, xse.schemaTypeName);
        boolean isSubstitutionGroupEq = UtilObjects.equals(this.substitutionGroup, xse.substitutionGroup);
        boolean isNamedDelegateEq = UtilObjects.equals(this.namedDelegate, xse.namedDelegate);
        return isAbstactElementEq && isNillableEq && isBlockEq && isConstraintsEq && isDefaultValueEq && isFixedValueEq && isFinalDerivationEq && isRefEq && isSchemaTypeEq && isSchemaTypeNameEq && isSubstitutionGroupEq && isNamedDelegateEq;
    }

    @Override
    public int hashCode() {
        Object[] hashObjects = new Object[]{this.block, this.constraints, this.defaultValue, this.fixedValue, this.finalDerivation, this.ref, this.schemaType, this.schemaTypeName, this.substitutionGroup, this.namedDelegate};
        int hash = Arrays.hashCode(hashObjects);
        hash += this.abstractElement ? 1 : 11;
        hash += this.nillable ? 3 : 13;
        return hash ^= super.hashCode();
    }

    public List<XmlSchemaIdentityConstraint> getConstraints() {
        return this.constraints;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public XmlSchemaDerivationMethod getBlock() {
        return this.block;
    }

    public void setBlock(XmlSchemaDerivationMethod block) {
        this.block = block;
    }

    public XmlSchemaDerivationMethod getFinal() {
        return this.finalDerivation;
    }

    public void setFinal(XmlSchemaDerivationMethod finalDerivationValue) {
        this.finalDerivation = finalDerivationValue;
    }

    public String getFixedValue() {
        return this.fixedValue;
    }

    public void setFixedValue(String fixedValue) {
        this.fixedValue = fixedValue;
    }

    public boolean isAbstract() {
        return this.abstractElement;
    }

    public void setAbstract(boolean isAbstract) {
        this.abstractElement = isAbstract;
    }

    public boolean isNillable() {
        return this.nillable;
    }

    public void setNillable(boolean isNillable) {
        this.nillable = isNillable;
    }

    @Override
    public XmlSchemaRef<XmlSchemaElement> getRef() {
        return this.ref;
    }

    public XmlSchemaType getSchemaType() {
        return this.schemaType;
    }

    public void setSchemaType(XmlSchemaType schemaType) {
        this.schemaType = schemaType;
    }

    public QName getSchemaTypeName() {
        return this.schemaTypeName;
    }

    public void setSchemaTypeName(QName schemaTypeName) {
        this.schemaTypeName = schemaTypeName;
    }

    public QName getSubstitutionGroup() {
        return this.substitutionGroup;
    }

    public void setSubstitutionGroup(QName substitutionGroup) {
        this.substitutionGroup = substitutionGroup;
    }

    @Override
    public void setType(XmlSchemaType type) {
        this.schemaType = type;
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
                if (XmlSchemaElement.this.namedDelegate.isTopLevel() && XmlSchemaElement.this.namedDelegate.getName() != null) {
                    XmlSchemaElement.this.namedDelegate.getParent().getElements().remove(XmlSchemaElement.this.getQName());
                }
                XmlSchemaElement.this.namedDelegate.setName(fName);
                if (XmlSchemaElement.this.namedDelegate.isTopLevel()) {
                    XmlSchemaElement.this.namedDelegate.getParent().getElements().put(XmlSchemaElement.this.getQName(), XmlSchemaElement.this);
                }
            }
        });
    }

    @Override
    public XmlSchemaForm getForm() {
        return this.namedDelegate.getForm();
    }

    @Override
    public boolean isFormSpecified() {
        return this.namedDelegate.isFormSpecified();
    }

    @Override
    public void setForm(XmlSchemaForm form) {
        this.namedDelegate.setForm(form);
    }

    @Override
    public QName getWireName() {
        return this.namedDelegate.getWireName();
    }

    public void setFinalDerivation(XmlSchemaDerivationMethod finalDerivation) {
        this.finalDerivation = finalDerivation;
    }

    public XmlSchemaDerivationMethod getFinalDerivation() {
        return this.finalDerivation;
    }

    public void setAbstractElement(boolean abstractElement) {
        this.abstractElement = abstractElement;
    }

    public boolean isAbstractElement() {
        return this.abstractElement;
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

