/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAttribute;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroup;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaGroup;
import org.apache.ws.commons.schema.XmlSchemaNotation;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;
import org.apache.ws.commons.schema.utils.XmlSchemaRefBase;

public class XmlSchemaRef<T extends XmlSchemaNamed>
extends XmlSchemaRefBase {
    private Class<? extends T> targetClass;
    private T targetObject;

    public XmlSchemaRef(XmlSchema parent, Class<T> targetClass) {
        this.parent = parent;
        this.targetClass = targetClass;
    }

    @Override
    protected void forgetTargetObject() {
        this.targetObject = null;
    }

    public T getTarget() {
        if (this.targetObject == null && this.targetQName != null) {
            Class<? extends T> cls = this.targetClass;
            XmlSchemaCollection parentCollection = this.parent.getParent();
            if (cls == XmlSchemaElement.class) {
                this.targetObject = (XmlSchemaNamed)this.targetClass.cast(parentCollection.getElementByQName(this.targetQName));
            } else if (cls == XmlSchemaAttribute.class) {
                this.targetObject = (XmlSchemaNamed)this.targetClass.cast(parentCollection.getAttributeByQName(this.targetQName));
            } else if (cls == XmlSchemaType.class) {
                this.targetObject = (XmlSchemaNamed)this.targetClass.cast(parentCollection.getTypeByQName(this.targetQName));
            } else if (cls == XmlSchemaAttributeGroup.class) {
                this.targetObject = (XmlSchemaNamed)this.targetClass.cast(parentCollection.getAttributeGroupByQName(this.targetQName));
            } else if (cls == XmlSchemaGroup.class) {
                this.targetObject = (XmlSchemaNamed)this.targetClass.cast(parentCollection.getGroupByQName(this.targetQName));
            } else if (cls == XmlSchemaNotation.class) {
                this.targetObject = (XmlSchemaNamed)this.targetClass.cast(parentCollection.getNotationByQName(this.targetQName));
            }
        }
        return this.targetObject;
    }

    public String toString() {
        return "XmlSchemaRef: " + this.targetClass.getName() + " " + this.targetQName;
    }
}

