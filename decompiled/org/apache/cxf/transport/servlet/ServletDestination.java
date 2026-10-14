/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.servlet;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.servlet.ServletConfigAware;

public class ServletDestination
extends AbstractHTTPDestination
implements ServletConfigAware {
    static final Logger LOG = LogUtils.getL7dLogger(ServletDestination.class);

    public ServletDestination(Bus b, DestinationRegistry registry, EndpointInfo ei, String path) throws IOException {
        super(b, registry, ei, path, false);
    }

    public ServletDestination(Bus b, DestinationRegistry registry, EndpointInfo ei, String path, boolean dp) throws IOException {
        super(b, registry, ei, path, dp);
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    protected String getBasePath(String contextPath) throws IOException {
        String address = this.getAddress().getAddress().getValue();
        if (address == null) {
            return contextPath;
        }
        if (address.startsWith("http")) {
            return URI.create(address.replaceAll(" ", "%20")).getPath();
        }
        return contextPath + address;
    }
}

