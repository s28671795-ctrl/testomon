/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.onvif;

import com.incoresoft.onvif.DiscoveryMode;
import com.incoresoft.onvif.DiscoveryThread;
import com.incoresoft.onvif.listeners.DiscoveryCallback;
import com.incoresoft.onvif.listeners.DiscoveryListener;
import com.incoresoft.onvif.models.Device;
import com.incoresoft.onvif.models.DiscoveryPacket;
import com.incoresoft.onvif.models.OnvifPacket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OnvifDiscovery {
    public static final String TAG = OnvifDiscovery.class.getSimpleName();
    private static final String MULTICAST_ADDRESS_IPV4 = "239.255.255.250";
    private static final String MULTICAST_ADDRESS_IPV6 = "[FF02::C]";
    private static final int DISCOVERY_TIMEOUT = 10000;
    private static final Random random = new SecureRandom();
    private int discoveryTimeout = 10000;
    private DiscoveryMode mode;
    private DiscoveryListener discoveryListener;

    OnvifDiscovery() {
        this(DiscoveryMode.ONVIF);
    }

    public OnvifDiscovery(DiscoveryMode mode) {
        this.mode = mode;
    }

    int getDiscoveryTimeout() {
        return this.discoveryTimeout;
    }

    void setDiscoveryTimeout(int timeoutMs) {
        this.discoveryTimeout = timeoutMs;
    }

    DiscoveryMode getDiscoveryMode() {
        return this.mode;
    }

    void setDiscoveryMode(DiscoveryMode mode) {
        this.mode = mode;
    }

    public void setDiscoveryListener(DiscoveryListener discoveryListener) {
        this.discoveryListener = discoveryListener;
    }

    void probe(DiscoveryMode mode, DiscoveryListener discoveryListener) {
        this.mode = mode;
        this.discoveryListener = discoveryListener;
        List<InetAddress> addresses = this.getInterfaceAddresses();
        this.broadcast(addresses);
    }

    private void broadcast(List<InetAddress> addresses) {
        final ConcurrentSkipListSet devices = new ConcurrentSkipListSet();
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        ExecutorService monitor = Executors.newSingleThreadExecutor();
        final CountDownLatch latch = new CountDownLatch(addresses.size());
        ArrayList<Runnable> runnables = new ArrayList<Runnable>();
        for (final InetAddress address : addresses) {
            runnables.add(() -> {
                try {
                    OnvifPacket packet = this.createDiscoveryPacket();
                    final byte[] data = packet.getData();
                    int port = random.nextInt(20000) + 40000;
                    final DatagramSocket client = new DatagramSocket(port, address);
                    client.setBroadcast(true);
                    new DiscoveryThread(client, this.discoveryTimeout, this.mode, new DiscoveryCallback(){

                        @Override
                        public void onDiscoveryStarted() {
                            try {
                                if (address instanceof Inet4Address) {
                                    client.send(new DatagramPacket(data, data.length, InetAddress.getByName(OnvifDiscovery.MULTICAST_ADDRESS_IPV4), OnvifDiscovery.this.mode.port));
                                } else {
                                    client.send(new DatagramPacket(data, data.length, InetAddress.getByName(OnvifDiscovery.MULTICAST_ADDRESS_IPV6), OnvifDiscovery.this.mode.port));
                                }
                            }
                            catch (IOException iOException) {
                                // empty catch block
                            }
                        }

                        @Override
                        public void onDevicesFound(List<Device> onvifDevices) {
                            devices.addAll(onvifDevices);
                        }

                        @Override
                        public void onDiscoveryFinished() {
                            latch.countDown();
                        }
                    }).start();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        this.notifyDiscoveryStarted();
        monitor.submit(() -> {
            for (Runnable runnable2 : runnables) {
                executorService2.submit(runnable2);
            }
            try {
                executorService2.shutdown();
                latch.await(this.discoveryTimeout, TimeUnit.MILLISECONDS);
                boolean cleanShutdown = executorService2.awaitTermination(this.discoveryTimeout, TimeUnit.MILLISECONDS);
                if (!cleanShutdown) {
                    executorService2.shutdownNow();
                }
                this.notifyDevicesFound(new ArrayList<Device>(devices));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        monitor.shutdown();
    }

    private OnvifPacket createDiscoveryPacket() {
        String uuid = UUID.randomUUID().toString();
        return new DiscoveryPacket(uuid, this.mode);
    }

    List<InetAddress> getInterfaceAddresses() {
        ArrayList<InetAddress> addresses = new ArrayList<InetAddress>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) continue;
                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    addresses.add(address.getAddress());
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    List<InetAddress> getBroadcastAddresses() {
        ArrayList<InetAddress> addresses = new ArrayList<InetAddress>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) continue;
                for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = address.getBroadcast();
                    if (broadcast == null) continue;
                    addresses.add(broadcast);
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress.isLoopbackAddress() || !(inetAddress instanceof Inet4Address)) continue;
                    return inetAddress.getHostAddress();
                }
            }
        }
        catch (SocketException socketException) {
            // empty catch block
        }
        return null;
    }

    private void notifyDiscoveryStarted() {
        if (this.discoveryListener != null) {
            this.discoveryListener.onDiscoveryStarted();
        }
    }

    private void notifyDevicesFound(List<Device> devices) {
        if (this.discoveryListener != null) {
            this.discoveryListener.onDevicesFound(devices);
        }
    }
}

