/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.utils.CollectionFactory;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedImpl;

public class XmlSchemaNotation
extends XmlSchemaAnnotated
implements XmlSchemaNamed {
    private String system;
    private String publicNotation;
    private XmlSchemaNamedImpl namedDelegate;

    public XmlSchemaNotation(XmlSchema parent) {
        this.namedDelegate = new XmlSchemaNamedImpl(parent, true);
        final XmlSchema fParent = parent;
        CollectionFactory.withSchemaModifiable(new Runnable(){

            @Override
            public void run() {
                fParent.getItems().add(XmlSchemaNotation.this);
            }
        });
    }

    public String getPublic() {
        return this.publicNotation;
    }

    public void setPublic(String isPublic) {
        this.publicNotation = isPublic;
    }

    public String getSystem() {
        return this.system;
    }

    public void setSystem(String system) {
        this.system = system;
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
    public String toString() {
        return this.namedDelegate.toString();
    }

    void setPublicNotation(String publicNotation) {
        this.publicNotation = publicNotation;
    }

    String getPublicNotation() {
        return this.publicNotation;
    }

    @Override
    public String getName() {
        return this.namedDelegate.getName();
    }

    @Override
    public void setName(String name) {
        final String fName = name;
        CollectionFactory.withSchemaModifiable(new Runnable(){

            @Override
            public void run() {
                if (XmlSchemaNotation.this.namedDelegate.getName() != null) {
                    XmlSchemaNotation.this.namedDelegate.getParent().getNotations().remove(XmlSchemaNotation.this.getQName());
                }
                XmlSchemaNotation.this.namedDelegate.setName(fName);
                XmlSchemaNotation.this.namedDelegate.getParent().getNotations().put(XmlSchemaNotation.this.getQName(), XmlSchemaNotation.this);
            }
        });
    }
}

