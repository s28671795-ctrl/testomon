/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.Iterator;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;

public class SoapHeaderOutFilterInterceptor
extends AbstractSoapInterceptor {
    public static final SoapHeaderOutFilterInterceptor INSTANCE = new SoapHeaderOutFilterInterceptor();

    public SoapHeaderOutFilterInterceptor() {
        super("pre-logical");
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Iterator<Header> iter = message.getHeaders().iterator();
        while (iter.hasNext()) {
            Header hdr2 = iter.next();
            if (hdr2 != null && hdr2.getDirection() != Header.Direction.DIRECTION_IN) continue;
            iter.remove();
        }
    }
}

