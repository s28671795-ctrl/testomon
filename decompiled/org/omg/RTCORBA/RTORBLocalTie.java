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
import org.omg.RTCORBA.RTORBOperations;
import org.omg.RTCORBA.RTORBPackage.InvalidThreadpool;
import org.omg.RTCORBA.ServerProtocolPolicy;
import org.omg.RTCORBA.TCPProtocolProperties;
import org.omg.RTCORBA.ThreadpoolLane;
import org.omg.RTCORBA.ThreadpoolPolicy;
import org.omg.RTCORBA._RTORBLocalBase;

public class RTORBLocalTie
extends _RTORBLocalBase {
    private static final long serialVersionUID = 1L;
    private RTORBOperations _delegate;

    public RTORBLocalTie(RTORBOperations delegate) {
        this._delegate = delegate;
    }

    public RTORBOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(RTORBOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public ThreadpoolPolicy create_threadpool_policy(int threadpool) {
        return this._delegate.create_threadpool_policy(threadpool);
    }

    @Override
    public PriorityModelPolicy create_priority_model_policy(PriorityModel priority_model, short server_priority) {
        return this._delegate.create_priority_model_policy(priority_model, server_priority);
    }

    @Override
    public ClientProtocolPolicy create_client_protocol_policy(Protocol[] protocols) {
        return this._delegate.create_client_protocol_policy(protocols);
    }

    @Override
    public int create_threadpool_with_lanes(int stacksize, ThreadpoolLane[] lanes, boolean allow_borrowing, boolean allow_request_buffering, int max_buffered_requests, int max_request_buffer_size) {
        return this._delegate.create_threadpool_with_lanes(stacksize, lanes, allow_borrowing, allow_request_buffering, max_buffered_requests, max_request_buffer_size);
    }

    @Override
    public Mutex create_mutex() {
        return this._delegate.create_mutex();
    }

    @Override
    public PriorityBandedConnectionPolicy create_priority_banded_connection_policy(PriorityBand[] priority_bands) {
        return this._delegate.create_priority_banded_connection_policy(priority_bands);
    }

    @Override
    public void destroy_mutex(Mutex the_mutex) {
        this._delegate.destroy_mutex(the_mutex);
    }

    @Override
    public PrivateConnectionPolicy create_private_connection_policy() {
        return this._delegate.create_private_connection_policy();
    }

    @Override
    public int create_threadpool(int stacksize, int static_threads, int dynamic_threads, short default_priority, boolean allow_request_buffering, int max_buffered_requests, int max_request_buffer_size) {
        return this._delegate.create_threadpool(stacksize, static_threads, dynamic_threads, default_priority, allow_request_buffering, max_buffered_requests, max_request_buffer_size);
    }

    @Override
    public ServerProtocolPolicy create_server_protocol_policy(Protocol[] protocols) {
        return this._delegate.create_server_protocol_policy(protocols);
    }

    @Override
    public TCPProtocolProperties create_tcp_protocol_properties(int send_buffer_size, int recv_buffer_size, boolean keep_alive, boolean dont_route, boolean no_delay) {
        return this._delegate.create_tcp_protocol_properties(send_buffer_size, recv_buffer_size, keep_alive, dont_route, no_delay);
    }

    @Override
    public void destroy_threadpool(int threadpool) throws InvalidThreadpool {
        this._delegate.destroy_threadpool(threadpool);
    }
}

