/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.BufferHolder;
import org.omg.ETF.ConnectionOperations;
import org.omg.ETF.Profile;
import org.omg.ETF._ConnectionLocalBase;

public class ConnectionLocalTie
extends _ConnectionLocalBase {
    private static final long serialVersionUID = 1L;
    private ConnectionOperations _delegate;

    public ConnectionLocalTie(ConnectionOperations delegate) {
        this._delegate = delegate;
    }

    public ConnectionOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ConnectionOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public boolean wait_next_data(long time_out) {
        return this._delegate.wait_next_data(time_out);
    }

    @Override
    public boolean is_data_available() {
        return this._delegate.is_data_available();
    }

    @Override
    public boolean is_connected() {
        return this._delegate.is_connected();
    }

    @Override
    public boolean supports_callback() {
        return this._delegate.supports_callback();
    }

    @Override
    public void flush() {
        this._delegate.flush();
    }

    @Override
    public void write(boolean is_first, boolean is_last, byte[] data, int offset, int length, long time_out) {
        this._delegate.write(is_first, is_last, data, offset, length, time_out);
    }

    @Override
    public Profile get_server_profile() {
        return this._delegate.get_server_profile();
    }

    @Override
    public boolean use_handle_time_out() {
        return this._delegate.use_handle_time_out();
    }

    @Override
    public void close() {
        this._delegate.close();
    }

    @Override
    public void connect(Profile server_profile, long time_out) {
        this._delegate.connect(server_profile, time_out);
    }

    @Override
    public int read(BufferHolder data, int offset, int min_length, int max_length, long time_out) {
        return this._delegate.read(data, offset, min_length, max_length, time_out);
    }
}

