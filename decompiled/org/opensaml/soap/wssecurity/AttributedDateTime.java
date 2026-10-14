/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface AttributedDateTime
extends XSString,
IdBearing,
AttributeExtensibleXMLObject,
WSSecurityObject {
    public static final String TYPE_LOCAL_NAME = "AttributedDateTime";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "AttributedDateTime", "wsu");
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public DateTime getDateTime();

    public void setDateTime(DateTime var1);

    public DateTimeFormatter getDateTimeFormatter();

    public void setDateTimeFormatter(DateTimeFormatter var1);
}

