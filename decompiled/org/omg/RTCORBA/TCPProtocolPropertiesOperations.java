/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.RTCORBA.ProtocolPropertiesOperations;

public interface TCPProtocolPropertiesOperations
extends ProtocolPropertiesOperations {
    public int send_buffer_size();

    public void send_buffer_size(int var1);

    public int recv_buffer_size();

    public void recv_buffer_size(int var1);

    public boolean keep_alive();

    public void keep_alive(boolean var1);

    public boolean dont_route();

    public void dont_route(boolean var1);

    public boolean no_delay();

    public void no_delay(boolean var1);
}

