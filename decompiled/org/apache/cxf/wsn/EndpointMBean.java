/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import org.apache.cxf.wsn.EndpointRegistrationException;

public interface EndpointMBean {
    public String getName();

    public String getAddress();

    public void register() throws EndpointRegistrationException;

    public void unregister() throws EndpointRegistrationException;
}

