/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import javax.xml.namespace.QName;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public interface EndpointResolver {
    public EndpointReferenceType resolve(EndpointReferenceType var1);

    public EndpointReferenceType renew(EndpointReferenceType var1, EndpointReferenceType var2);

    public EndpointReferenceType mint(QName var1);

    public EndpointReferenceType mint(EndpointReferenceType var1);
}

