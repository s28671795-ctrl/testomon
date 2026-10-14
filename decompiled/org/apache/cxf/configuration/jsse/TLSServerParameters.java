/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.jsse;

import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.configuration.jsse.TLSParameterBase;
import org.apache.cxf.configuration.security.ClientAuthentication;

public class TLSServerParameters
extends TLSParameterBase {
    ClientAuthentication clientAuthentication;
    List<String> excludeProtocols = new ArrayList<String>();
    List<String> includeProtocols = new ArrayList<String>();

    public final void setClientAuthentication(ClientAuthentication clientAuth) {
        this.clientAuthentication = clientAuth;
    }

    public ClientAuthentication getClientAuthentication() {
        return this.clientAuthentication;
    }

    public final void setExcludeProtocols(List<String> protocols) {
        this.excludeProtocols = protocols;
    }

    public List<String> getExcludeProtocols() {
        if (this.excludeProtocols == null) {
            this.excludeProtocols = new ArrayList<String>();
        }
        return this.excludeProtocols;
    }

    public final void setIncludeProtocols(List<String> protocols) {
        this.includeProtocols = protocols;
    }

    public List<String> getIncludeProtocols() {
        if (this.includeProtocols == null) {
            this.includeProtocols = new ArrayList<String>();
        }
        return this.includeProtocols;
    }
}

