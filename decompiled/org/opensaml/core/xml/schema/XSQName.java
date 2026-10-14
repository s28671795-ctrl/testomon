/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface XSQName
extends XMLObject {
    public static final String TYPE_LOCAL_NAME = "QName";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "QName", "xsd");

    @Nullable
    public QName getValue();

    public void setValue(@Nullable QName var1);
}

