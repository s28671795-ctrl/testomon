/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.util.List;
import org.apache.cxf.wsn.EndpointMBean;

public interface CreatePullPointMBean
extends EndpointMBean {
    public List<String> getPullEndpoints();

    public EndpointMBean getPullEndpoint(String var1);
}

