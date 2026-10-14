/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAllMember;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaChoiceMember;
import org.apache.ws.commons.schema.XmlSchemaGroupParticle;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;
import org.apache.ws.commons.schema.utils.CollectionFactory;
import org.apache.ws.commons.schema.utils.UtilObjects;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedImpl;

public class XmlSchemaGroup
extends XmlSchemaAnnotated
implements XmlSchemaNamed,
XmlSchemaChoiceMember,
XmlSchemaSequenceMember,
XmlSchemaAllMember {
    private XmlSchemaGroupParticle particle;
    private XmlSchemaNamedImpl namedDelegate;

    public XmlSchemaGroup(XmlSchema parent) {
        this.namedDelegate = new XmlSchemaNamedImpl(parent, true);
        final XmlSchema fParent = parent;
        CollectionFactory.withSchemaModifiable(new Runnable(){

            @Override
            public void run() {
                fParent.getItems().add(XmlSchemaGroup.this);
            }
        });
    }

    @Override
    public boolean equals(Object what) {
        boolean parentCheck = super.equals(what);
        if (!parentCheck) {
            return false;
        }
        if (!(what instanceof XmlSchemaGroup)) {
            return false;
        }
        XmlSchemaGroup xsg = (XmlSchemaGroup)what;
        boolean isParticleEq = UtilObjects.equals(this.particle, xsg.particle);
        boolean isNamedDelegateEq = UtilObjects.equals(this.namedDelegate, xsg.namedDelegate);
        return isParticleEq && isNamedDelegateEq;
    }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(new Object[]{this.particle, this.namedDelegate});
        return hash ^= super.hashCode();
    }

    public XmlSchemaGroupParticle getParticle() {
        return this.particle;
    }

    public void setParticle(XmlSchemaGroupParticle particle) {
        this.particle = particle;
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
                if (XmlSchemaGroup.this.namedDelegate.getQName() != null) {
                    XmlSchemaGroup.this.namedDelegate.getParent().getGroups().remove(XmlSchemaGroup.this.namedDelegate.getQName());
                }
                XmlSchemaGroup.this.namedDelegate.setName(fName);
                if (fName != null) {
                    XmlSchemaGroup.this.namedDelegate.getParent().getGroups().put(XmlSchemaGroup.this.namedDelegate.getQName(), XmlSchemaGroup.this);
                }
            }
        });
    }
}

