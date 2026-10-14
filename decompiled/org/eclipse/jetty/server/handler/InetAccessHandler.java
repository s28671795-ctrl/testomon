/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.InetAccessSet;
import org.eclipse.jetty.util.IncludeExcludeSet;
import org.eclipse.jetty.util.InetAddressPattern;
import org.eclipse.jetty.util.component.DumpableCollection;

public class InetAccessHandler
extends HandlerWrapper {
    private final IncludeExcludeSet<InetAccessSet.PatternTuple, InetAccessSet.AccessTuple> _set = new IncludeExcludeSet(InetAccessSet.class);

    public void clear() {
        this._set.clear();
    }

    public void include(String pattern) {
        this._set.include(InetAccessSet.PatternTuple.from(pattern));
    }

    public void include(String ... patterns) {
        for (String pattern : patterns) {
            this.include(pattern);
        }
    }

    public void include(String connectorName, String addressPattern, PathSpec pathSpec) {
        this._set.include(new InetAccessSet.PatternTuple(connectorName, InetAddressPattern.from(addressPattern), pathSpec));
    }

    public void exclude(String pattern) {
        this._set.exclude(InetAccessSet.PatternTuple.from(pattern));
    }

    public void exclude(String ... patterns) {
        for (String pattern : patterns) {
            this.exclude(pattern);
        }
    }

    public void exclude(String connectorName, String addressPattern, PathSpec pathSpec) {
        this._set.exclude(new InetAccessSet.PatternTuple(connectorName, InetAddressPattern.from(addressPattern), pathSpec));
    }

    @Deprecated
    public void includeConnector(String name) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void excludeConnector(String name) {
        this._set.exclude(new InetAccessSet.PatternTuple(name, null, null));
    }

    @Deprecated
    public void includeConnectors(String ... names) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void excludeConnectors(String ... names) {
        for (String name : names) {
            this.excludeConnector(name);
        }
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        InetSocketAddress address;
        HttpChannel channel = baseRequest.getHttpChannel();
        if (channel != null && (address = channel.getRemoteAddress()) != null && !this.isAllowed(address.getAddress(), baseRequest, request)) {
            response.sendError(403);
            baseRequest.setHandled(true);
            return;
        }
        this.getHandler().handle(target, baseRequest, request, response);
    }

    protected boolean isAllowed(InetAddress addr, Request baseRequest, HttpServletRequest request) {
        String connectorName = baseRequest.getHttpChannel().getConnector().getName();
        String path = baseRequest.getMetaData().getURI().getDecodedPath();
        return this._set.test(new InetAccessSet.AccessTuple(connectorName, addr, path));
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        this.dumpObjects(out, indent, new DumpableCollection("included", this._set.getIncluded()), new DumpableCollection("excluded", this._set.getExcluded()));
    }
}

