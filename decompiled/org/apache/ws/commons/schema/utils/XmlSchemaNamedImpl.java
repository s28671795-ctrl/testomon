/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.utils.UtilObjects;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;
import org.apache.ws.commons.schema.utils.XmlSchemaRefBase;

public class XmlSchemaNamedImpl
implements XmlSchemaNamed {
    protected XmlSchema parentSchema;
    protected XmlSchemaRefBase refTwin;
    private QName qname;
    private boolean topLevel;

    public XmlSchemaNamedImpl(XmlSchema parent, boolean topLevel) {
        this.parentSchema = parent;
        this.topLevel = topLevel;
    }

    public boolean equals(Object what) {
        if (what == this) {
            return true;
        }
        if (!(what instanceof XmlSchemaNamedImpl)) {
            return false;
        }
        XmlSchemaNamedImpl xsn = (XmlSchemaNamedImpl)what;
        boolean isTopLevelEq = this.topLevel == xsn.topLevel;
        boolean isParentSchemaEq = UtilObjects.equals(this.parentSchema, xsn.parentSchema);
        boolean isRefTwinEq = UtilObjects.equals(this.refTwin, xsn.refTwin);
        boolean isQNameEq = UtilObjects.equals(this.qname, xsn.qname);
        return isTopLevelEq && isParentSchemaEq && isRefTwinEq && isQNameEq;
    }

    public int hashCode() {
        Object[] hashObjects = new Object[]{this.parentSchema, this.refTwin, this.qname};
        int hash = Arrays.hashCode(hashObjects);
        hash += this.topLevel ? 39 : 107;
        return hash ^= super.hashCode();
    }

    public void setRefObject(XmlSchemaRefBase refBase) {
        this.refTwin = refBase;
    }

    @Override
    public String getName() {
        if (this.qname == null) {
            return null;
        }
        return this.qname.getLocalPart();
    }

    @Override
    public boolean isAnonymous() {
        return this.qname == null;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            this.qname = null;
        } else {
            if ("".equals(name)) {
                throw new XmlSchemaException("Attempt to set empty name.");
            }
            if (this.refTwin != null && this.refTwin.getTargetQName() != null) {
                throw new XmlSchemaException("Attempt to set name on object with ref='xxx'");
            }
            this.qname = new QName(this.parentSchema.getLogicalTargetNamespace(), name);
        }
    }

    @Override
    public XmlSchema getParent() {
        return this.parentSchema;
    }

    @Override
    public QName getQName() {
        return this.qname;
    }

    @Override
    public boolean isTopLevel() {
        return this.topLevel;
    }
}

