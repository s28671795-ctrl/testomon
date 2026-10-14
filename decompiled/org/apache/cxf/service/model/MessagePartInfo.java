/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import javax.xml.namespace.QName;
import org.apache.cxf.service.model.AbstractMessageContainer;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.NamedItem;
import org.apache.ws.commons.schema.XmlSchemaAnnotated;
import org.apache.ws.commons.schema.XmlSchemaElement;

public final class MessagePartInfo
extends AbstractPropertiesHolder
implements NamedItem {
    private QName pname;
    private AbstractMessageContainer mInfo;
    private boolean isElement;
    private QName typeName;
    private QName elementName;
    private QName concreteName;
    private XmlSchemaAnnotated xmlSchema;
    private Class<?> typeClass;
    private int index;

    public MessagePartInfo(QName n, AbstractMessageContainer info) {
        this.mInfo = info;
        this.pname = n;
    }

    public void setMessageContainer(AbstractMessageContainer info) {
        this.mInfo = info;
    }

    @Override
    public QName getName() {
        return this.pname;
    }

    public void setName(QName n) {
        this.pname = n;
    }

    public QName getConcreteName() {
        return this.concreteName;
    }

    public void setConcreteName(QName concreteName) {
        this.concreteName = concreteName;
    }

    public boolean isElement() {
        return this.isElement;
    }

    public void setElement(boolean b) {
        this.isElement = b;
    }

    public QName getElementQName() {
        if (this.isElement) {
            return this.elementName;
        }
        return null;
    }

    public QName getTypeQName() {
        if (!this.isElement) {
            return this.typeName;
        }
        if (this.xmlSchema instanceof XmlSchemaElement) {
            return ((XmlSchemaElement)this.xmlSchema).getSchemaTypeName();
        }
        return null;
    }

    public void setTypeQName(QName qn) {
        this.isElement = false;
        if (this.concreteName == null) {
            this.concreteName = new QName(null, this.pname.getLocalPart());
        }
        this.typeName = qn;
    }

    public void setElementQName(QName qn) {
        this.isElement = true;
        this.elementName = qn;
        this.concreteName = qn;
    }

    public AbstractMessageContainer getMessageInfo() {
        return this.mInfo;
    }

    public XmlSchemaAnnotated getXmlSchema() {
        return this.xmlSchema;
    }

    public void setXmlSchema(XmlSchemaAnnotated xmlSchema) {
        this.xmlSchema = xmlSchema;
    }

    public Class<?> getTypeClass() {
        return this.typeClass;
    }

    public void setTypeClass(Class<?> typeClass) {
        this.typeClass = typeClass;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String toString() {
        return "[MessagePartInfo name=" + this.getName() + ", ConcreteName=" + this.getConcreteName();
    }

    public int hashCode() {
        return this.pname == null ? -1 : this.pname.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MessagePartInfo)) {
            return false;
        }
        MessagePartInfo oi = (MessagePartInfo)o;
        return MessagePartInfo.equals(this.pname, oi.pname) && this.isElement == oi.isElement && MessagePartInfo.equals(this.typeName, oi.typeName) && MessagePartInfo.equals(this.elementName, oi.elementName) && MessagePartInfo.equals(this.concreteName, oi.concreteName) && MessagePartInfo.equals(this.typeClass, oi.typeClass);
    }
}

