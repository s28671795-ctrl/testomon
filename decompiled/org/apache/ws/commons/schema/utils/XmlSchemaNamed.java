/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.utils.XmlSchemaObjectBase;

public interface XmlSchemaNamed
extends XmlSchemaObjectBase {
    public String getName();

    public boolean isAnonymous();

    public void setName(String var1);

    public XmlSchema getParent();

    public QName getQName();

    public boolean isTopLevel();
}

