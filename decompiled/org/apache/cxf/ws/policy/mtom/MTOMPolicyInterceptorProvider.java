/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.mtom;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.policy.mtom.MTOMPolicyInterceptor;
import org.apache.cxf.ws.policy.mtom.MetadataConstants;

@NoJSR250Annotations
public class MTOMPolicyInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = -2101800288259099105L;
    private static final Collection<QName> ASSERTION_TYPES;
    private static final MTOMPolicyInterceptor INTERCEPTOR;

    public MTOMPolicyInterceptorProvider() {
        super(ASSERTION_TYPES);
        this.getInInterceptors().add(INTERCEPTOR);
        this.getOutInterceptors().add(INTERCEPTOR);
        this.getInFaultInterceptors().add(INTERCEPTOR);
        this.getOutFaultInterceptors().add(INTERCEPTOR);
    }

    static {
        INTERCEPTOR = new MTOMPolicyInterceptor();
        ArrayList<QName> types = new ArrayList<QName>();
        types.add(MetadataConstants.MTOM_ASSERTION_QNAME);
        ASSERTION_TYPES = types;
    }
}

