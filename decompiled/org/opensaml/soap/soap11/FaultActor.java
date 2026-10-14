/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap11;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.common.SOAPObject;

public interface FaultActor
extends SOAPObject,
XSURI {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "faultactor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("faultactor");
}

