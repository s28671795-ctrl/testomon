/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import org.eclipse.jetty.http.HostPortHttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Request;

public class HostHeaderCustomizer
implements HttpConfiguration.Customizer {
    private final String serverName;
    private final int serverPort;

    public HostHeaderCustomizer() {
        this(null, 0);
    }

    public HostHeaderCustomizer(String serverName) {
        this(serverName, 0);
    }

    public HostHeaderCustomizer(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    @Override
    public void customize(Connector connector, HttpConfiguration channelConfig, Request request) {
        if (request.getHttpVersion() != HttpVersion.HTTP_1_1 && !request.getHttpFields().contains(HttpHeader.HOST)) {
            String host2 = this.serverName == null ? request.getServerName() : this.serverName;
            int port = HttpScheme.normalizePort(request.getScheme(), this.serverPort == 0 ? request.getServerPort() : this.serverPort);
            if (this.serverName != null || this.serverPort > 0) {
                request.setHttpURI(HttpURI.build(request.getHttpURI()).authority(host2, port));
            }
            HttpFields original = request.getHttpFields();
            HttpFields.Mutable httpFields = HttpFields.build(original.size() + 1);
            httpFields.add(new HostPortHttpField(host2, port));
            httpFields.add(request.getHttpFields());
            request.setHttpFields(httpFields);
        }
    }
}

