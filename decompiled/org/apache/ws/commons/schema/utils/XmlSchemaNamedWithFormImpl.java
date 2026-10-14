/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaException;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.utils.UtilObjects;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedImpl;
import org.apache.ws.commons.schema.utils.XmlSchemaNamedWithForm;

public class XmlSchemaNamedWithFormImpl
extends XmlSchemaNamedImpl
implements XmlSchemaNamedWithForm {
    private XmlSchemaForm form = XmlSchemaForm.NONE;
    private boolean element;
    private QName wireName;

    public XmlSchemaNamedWithFormImpl(XmlSchema parent, boolean topLevel, boolean element) {
        super(parent, topLevel);
        this.element = element;
    }

    @Override
    public boolean equals(Object what) {
        boolean parentCheck = super.equals(what);
        if (!parentCheck) {
            return false;
        }
        if (!(what instanceof XmlSchemaNamedWithFormImpl)) {
            return false;
        }
        XmlSchemaNamedWithFormImpl xsn = (XmlSchemaNamedWithFormImpl)what;
        boolean isElementEq = this.element == xsn.element;
        boolean isFormEq = UtilObjects.equals((Object)this.form, (Object)xsn.form);
        boolean isWireNameEq = UtilObjects.equals(this.wireName, xsn.wireName);
        return isElementEq && isFormEq && isWireNameEq;
    }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(new Object[]{this.form, this.wireName});
        hash += this.element ? 47 : 13;
        return hash ^= super.hashCode();
    }

    @Override
    public XmlSchemaForm getForm() {
        if (this.form != XmlSchemaForm.NONE) {
            return this.form;
        }
        if (this.element) {
            return this.parentSchema.getElementFormDefault();
        }
        return this.parentSchema.getAttributeFormDefault();
    }

    @Override
    public boolean isFormSpecified() {
        return this.form != XmlSchemaForm.NONE;
    }

    @Override
    public void setForm(XmlSchemaForm form) {
        if (form == null) {
            throw new XmlSchemaException("form may not be null. Pass XmlSchemaForm.NONE to use schema default.");
        }
        this.form = form;
        this.setName(this.getName());
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        this.wireName = this.getForm() == XmlSchemaForm.QUALIFIED ? this.getQName() : new QName("", this.getName());
    }

    @Override
    public QName getWireName() {
        if (this.refTwin != null && this.refTwin.getTargetQName() != null) {
            return this.refTwin.getTargetQName();
        }
        return this.wireName;
    }
}

