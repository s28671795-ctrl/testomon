/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.net.URI;
import javax.xml.namespace.QName;

public interface ServiceContractResolver {
    public URI getContractLocation(QName var1);
}

