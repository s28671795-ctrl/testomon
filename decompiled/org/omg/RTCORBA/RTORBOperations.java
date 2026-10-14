/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.RTCORBA.ClientProtocolPolicy;
import org.omg.RTCORBA.Mutex;
import org.omg.RTCORBA.PriorityBand;
import org.omg.RTCORBA.PriorityBandedConnectionPolicy;
import org.omg.RTCORBA.PriorityModel;
import org.omg.RTCORBA.PriorityModelPolicy;
import org.omg.RTCORBA.PrivateConnectionPolicy;
import org.omg.RTCORBA.Protocol;
import org.omg.RTCORBA.RTORBPackage.InvalidThreadpool;
import org.omg.RTCORBA.ServerProtocolPolicy;
import org.omg.RTCORBA.TCPProtocolProperties;
import org.omg.RTCORBA.ThreadpoolLane;
import org.omg.RTCORBA.ThreadpoolPolicy;

public interface RTORBOperations {
    public Mutex create_mutex();

    public void destroy_mutex(Mutex var1);

    public int create_threadpool(int var1, int var2, int var3, short var4, boolean var5, int var6, int var7);

    public int create_threadpool_with_lanes(int var1, ThreadpoolLane[] var2, boolean var3, boolean var4, int var5, int var6);

    public void destroy_threadpool(int var1) throws InvalidThreadpool;

    public PriorityModelPolicy create_priority_model_policy(PriorityModel var1, short var2);

    public ThreadpoolPolicy create_threadpool_policy(int var1);

    public PriorityBandedConnectionPolicy create_priority_banded_connection_policy(PriorityBand[] var1);

    public ServerProtocolPolicy create_server_protocol_policy(Protocol[] var1);

    public ClientProtocolPolicy create_client_protocol_policy(Protocol[] var1);

    public PrivateConnectionPolicy create_private_connection_policy();

    public TCPProtocolProperties create_tcp_protocol_properties(int var1, int var2, boolean var3, boolean var4, boolean var5);
}

