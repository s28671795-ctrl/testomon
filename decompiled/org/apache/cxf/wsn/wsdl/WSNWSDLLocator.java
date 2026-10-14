/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.wsdl;

import java.net.URL;

public final class WSNWSDLLocator {
    private WSNWSDLLocator() {
    }

    public static URL getWSDLUrl() {
        return WSNWSDLLocator.class.getResource("wsn.wsdl");
    }
}

