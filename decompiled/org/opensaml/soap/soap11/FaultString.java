/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap11;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.soap.common.SOAPObject;

public interface FaultString
extends SOAPObject,
XSString {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "faultstring";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("faultstring");
}

