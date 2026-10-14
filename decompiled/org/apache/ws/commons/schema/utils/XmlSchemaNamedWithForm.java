/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;

public interface XmlSchemaNamedWithForm
extends XmlSchemaNamed {
    public XmlSchemaForm getForm();

    public void setForm(XmlSchemaForm var1);

    public boolean isFormSpecified();

    public QName getWireName();
}

