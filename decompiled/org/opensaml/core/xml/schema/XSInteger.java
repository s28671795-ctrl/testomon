/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface XSInteger
extends XMLObject {
    public static final String TYPE_LOCAL_NAME = "integer";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "integer", "xsd");

    @Nullable
    public Integer getValue();

    public void setValue(@Nullable Integer var1);
}

