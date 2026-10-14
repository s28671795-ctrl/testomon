/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.opensaml.core.xml.XMLObject;

public interface XSDateTime
extends XMLObject {
    public static final String TYPE_LOCAL_NAME = "dateTime";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "dateTime", "xsd");

    @Nullable
    public DateTime getValue();

    public void setValue(@Nullable DateTime var1);

    @Nonnull
    public DateTimeFormatter getDateTimeFormatter();

    public void setDateTimeFormatter(@Nonnull DateTimeFormatter var1);
}

