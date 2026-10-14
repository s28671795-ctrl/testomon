/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface XSBoolean
extends XMLObject {
    public static final String TYPE_LOCAL_NAME = "boolean";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "boolean", "xsd");

    @Nullable
    public XSBooleanValue getValue();

    public void setValue(@Nullable XSBooleanValue var1);
}

