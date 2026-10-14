/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;
import org.conscrypt.ApplicationProtocolSelector;
import org.conscrypt.Preconditions;
import org.conscrypt.SSLUtils;

final class ApplicationProtocolSelectorAdapter {
    private static final int NO_PROTOCOL_SELECTED = -1;
    private final SSLEngine engine;
    private final SSLSocket socket;
    private final ApplicationProtocolSelector selector;

    ApplicationProtocolSelectorAdapter(SSLEngine engine, ApplicationProtocolSelector selector) {
        this.engine = Preconditions.checkNotNull(engine, "engine");
        this.socket = null;
        this.selector = Preconditions.checkNotNull(selector, "selector");
    }

    ApplicationProtocolSelectorAdapter(SSLSocket socket, ApplicationProtocolSelector selector) {
        this.engine = null;
        this.socket = Preconditions.checkNotNull(socket, "socket");
        this.selector = Preconditions.checkNotNull(selector, "selector");
    }

    int selectApplicationProtocol(byte[] encodedProtocols) {
        if (encodedProtocols == null || encodedProtocols.length == 0) {
            return -1;
        }
        List<String> protocols = Arrays.asList(SSLUtils.decodeProtocols(encodedProtocols));
        String selected = this.engine != null ? this.selector.selectApplicationProtocol(this.engine, protocols) : this.selector.selectApplicationProtocol(this.socket, protocols);
        if (selected == null || selected.isEmpty()) {
            return -1;
        }
        int offset = 0;
        for (String protocol : protocols) {
            if (selected.equals(protocol)) {
                return offset;
            }
            offset += 1 + protocol.length();
        }
        return -1;
    }
}

