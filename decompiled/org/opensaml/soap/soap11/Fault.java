/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.soap11;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.soap.common.SOAPObject;
import org.opensaml.soap.soap11.Detail;
import org.opensaml.soap.soap11.FaultActor;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.soap11.FaultString;

public interface Fault
extends SOAPObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Fault";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault", "soap11");
    public static final String TYPE_LOCAL_NAME = "Fault";
    public static final QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault", "soap11");

    @Nullable
    public FaultCode getCode();

    public void setCode(@Nullable FaultCode var1);

    @Nullable
    public FaultString getMessage();

    public void setMessage(@Nullable FaultString var1);

    @Nullable
    public FaultActor getActor();

    public void setActor(@Nullable FaultActor var1);

    @Nullable
    public Detail getDetail();

    public void setDetail(@Nullable Detail var1);
}

