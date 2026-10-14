/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaDerivationMethod;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.utils.CollectionFactory;
import org.apache.ws.commons.schema.utils.UtilObjects;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedImpl;

public abstract class XmlSchemaType
extends XmlSchemaAnnotated
implements XmlSchemaNamed {
    private XmlSchemaDerivationMethod deriveBy;
    private XmlSchemaDerivationMethod finalDerivation;
    private XmlSchemaDerivationMethod finalResolved;
    private boolean isMixed;
    private XmlSchemaNamedImpl namedDelegate;

    protected XmlSchemaType(XmlSchema schema, boolean topLevel) {
        final XmlSchema fSchema = schema;
        this.namedDelegate = new XmlSchemaNamedImpl(schema, topLevel);
        this.finalDerivation = XmlSchemaDerivationMethod.NONE;
        if (topLevel) {
            CollectionFactory.withSchemaModifiable(new Runnable(){

                @Override
                public void run() {
                    fSchema.getItems().add(XmlSchemaType.this);
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
        if (!(what instanceof XmlSchemaType)) {
            return false;
        }
        XmlSchemaType xst = (XmlSchemaType)what;
        boolean isIsMixedEq = this.isMixed == xst.isMixed;
        boolean isDeriveByEq = UtilObjects.equals(this.deriveBy, xst.deriveBy);
        boolean isFinalDerivationEq = UtilObjects.equals(this.finalDerivation, xst.finalDerivation);
        boolean isFinalResolvedEq = UtilObjects.equals(this.finalResolved, xst.finalResolved);
        boolean isNamedDelegateEq = UtilObjects.equals(this.namedDelegate, xst.namedDelegate);
        return isIsMixedEq && isDeriveByEq && isFinalDerivationEq && isFinalResolvedEq && isNamedDelegateEq;
    }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(new Object[]{this.deriveBy, this.finalDerivation, this.finalResolved, this.namedDelegate});
        hash += this.isMixed ? 29 : 83;
        return hash ^= super.hashCode();
    }

    public XmlSchemaDerivationMethod getDeriveBy() {
        return this.deriveBy;
    }

    public XmlSchemaDerivationMethod getFinal() {
        return this.finalDerivation;
    }

    public void setFinal(XmlSchemaDerivationMethod finalDerivationValue) {
        this.finalDerivation = finalDerivationValue;
    }

    public XmlSchemaDerivationMethod getFinalResolved() {
        return this.finalResolved;
    }

    public boolean isMixed() {
        return this.isMixed;
    }

    public void setMixed(boolean isMixedValue) {
        this.isMixed = isMixedValue;
    }

    @Override
    public String toString() {
        if (this.getName() == null) {
            return super.toString() + "[anonymous]";
        }
        if (this.namedDelegate.getParent().getLogicalTargetNamespace() == null) {
            return super.toString() + "[{}" + this.getName() + "]";
        }
        return super.toString() + "[{" + this.namedDelegate.getParent().getLogicalTargetNamespace() + "}" + this.getName() + "]";
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
        if (this.isTopLevel() && name == null) {
            throw new XmlSchemaException("A non-top-level type may not be anonyous.");
        }
        if (this.namedDelegate.isTopLevel() && this.namedDelegate.getName() != null) {
            this.namedDelegate.getParent().getSchemaTypes().remove(this.getQName());
        }
        this.namedDelegate.setName(name);
        if (this.namedDelegate.isTopLevel()) {
            this.namedDelegate.getParent().getSchemaTypes().put(this.getQName(), this);
        }
    }

    void setFinalResolved(XmlSchemaDerivationMethod finalResolved) {
        this.finalResolved = finalResolved;
    }

    public void setFinalDerivation(XmlSchemaDerivationMethod finalDerivation) {
        this.finalDerivation = finalDerivation;
    }

    public XmlSchemaDerivationMethod getFinalDerivation() {
        return this.finalDerivation;
    }

    public void setDeriveBy(XmlSchemaDerivationMethod deriveBy) {
        this.deriveBy = deriveBy;
    }
}

