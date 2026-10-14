/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif;

import com.incoresoft.onvif.DiscoveryMode;
import com.incoresoft.onvif.listeners.DiscoveryCallback;
import com.incoresoft.onvif.models.Device;
import com.incoresoft.onvif.parsers.DiscoveryParser;
import com.incoresoft.onvif.responses.OnvifResponse;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class DiscoveryThread
extends Thread {
    public static final String TAG = DiscoveryThread.class.getSimpleName();
    private final DatagramSocket server;
    private final int timeout;
    private final DiscoveryParser parser;
    private final DiscoveryCallback callback;

    DiscoveryThread(DatagramSocket server, int timeout2, DiscoveryMode mode, DiscoveryCallback callback) {
        this.server = server;
        this.timeout = timeout2;
        this.callback = callback;
        this.parser = new DiscoveryParser(mode);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            boolean started = false;
            DatagramPacket packet = new DatagramPacket(new byte[4096], 4096);
            this.server.setSoTimeout(this.timeout);
            long timerStarted = System.currentTimeMillis();
            while (System.currentTimeMillis() - timerStarted < (long)this.timeout) {
                if (!started) {
                    this.callback.onDiscoveryStarted();
                    started = true;
                }
                this.server.receive(packet);
                String response = new String(packet.getData(), 0, packet.getLength());
                this.parser.setHostName(packet.getAddress().getHostName());
                this.callback.onDevicesFound((List<Device>)this.parser.parse(new OnvifResponse(response)));
            }
        }
        catch (IOException iOException) {
        }
        finally {
            this.server.close();
            this.callback.onDiscoveryFinished();
        }
    }
}

