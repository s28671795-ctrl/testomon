/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.net.URI;
import javax.xml.namespace.QName;
import org.apache.cxf.endpoint.ServiceContractResolver;

public interface ServiceContractResolverRegistry {
    public URI getContractLocation(QName var1);

    public void register(ServiceContractResolver var1);

    public void unregister(ServiceContractResolver var1);

    public boolean isRegistered(ServiceContractResolver var1);
}

