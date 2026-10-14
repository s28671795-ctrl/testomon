/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.policy;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;

@NoJSR250Annotations
public class NoOpPolicyInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = -5554625221910391930L;
    private static final Collection<QName> ASSERTION_TYPES;
    private static final QName HTTP_CONF_NAME;

    public NoOpPolicyInterceptorProvider() {
        super(ASSERTION_TYPES);
    }

    static {
        HTTP_CONF_NAME = new QName("http://cxf.apache.org/transports/http/configuration", "client");
        ArrayList<QName> types = new ArrayList<QName>();
        types.add(HTTP_CONF_NAME);
        ASSERTION_TYPES = types;
    }
}

