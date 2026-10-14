/*
 * Decompiled with CFR 0.152.
 */
package de.onvif.discovery;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DeviceDiscovery {
    public static final String WS_DISCOVERY_SOAP_VERSION = "SOAP 1.2 Protocol";
    public static final String WS_DISCOVERY_CONTENT_TYPE = "application/soap+xml";
    public static final int WS_DISCOVERY_TIMEOUT = 4000;
    public static final int WS_DISCOVERY_PORT = 3702;
    public static final String WS_DISCOVERY_ADDRESS_IPv4 = "239.255.255.250";
    public static final boolean enableIPv6 = false;
    public static final String WS_DISCOVERY_ADDRESS_IPv6 = "[FF02::C]";
    public static final String WS_DISCOVERY_PROBE_MESSAGE = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" xmlns:tns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\"><soap:Header><wsa:Action>http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</wsa:Action><wsa:MessageID>urn:uuid:c032cfdd-c3ca-49dc-820e-ee6696ad63e2</wsa:MessageID><wsa:To>urn:schemas-xmlsoap-org:ws:2005:04:discovery</wsa:To></soap:Header><soap:Body><tns:Probe/></soap:Body></soap:Envelope>";
    private static final Random random = new SecureRandom();

    public static void main(String[] args2) throws InterruptedException {
        Collection<URL> urls2 = DeviceDiscovery.discoverWsDevicesAsUrls();
        for (URL url2 : urls2) {
            System.out.println("Device discovered: " + url2.toString());
        }
    }

    public static Collection<URL> discoverWsDevicesAsUrls() {
        return DeviceDiscovery.discoverWsDevicesAsUrls("", "");
    }

    public static Collection<URL> discoverWsDevicesAsUrls(String regexpProtocol, String regexpPath) {
        TreeSet<URL> urls2 = new TreeSet<URL>(new Comparator<URL>(){

            @Override
            public int compare(URL o1, URL o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        for (String key : DeviceDiscovery.discoverWsDevices()) {
            try {
                URL url2 = new URL(key);
                boolean ok = true;
                if (regexpProtocol.length() > 0 && !url2.getProtocol().matches(regexpProtocol)) {
                    ok = false;
                }
                if (regexpPath.length() > 0 && !url2.getPath().matches(regexpPath)) {
                    ok = false;
                }
                if (ok && url2.getHost().startsWith("[")) {
                    ok = false;
                }
                if (!ok) continue;
                urls2.add(url2);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return urls2;
    }

    public static Collection<String> discoverWsDevices() {
        final ConcurrentSkipListSet<String> addresses = new ConcurrentSkipListSet<String>();
        final CountDownLatch serverStarted = new CountDownLatch(1);
        final CountDownLatch serverFinished = new CountDownLatch(1);
        ArrayList<InetAddress> addressList = new ArrayList<InetAddress>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    NetworkInterface anInterface = interfaces.nextElement();
                    if (anInterface.isLoopback()) continue;
                    List<InterfaceAddress> interfaceAddresses = anInterface.getInterfaceAddresses();
                    for (InterfaceAddress address : interfaceAddresses) {
                        Class<?> clz = address.getAddress().getClass();
                        if (address.getAddress() instanceof Inet6Address) continue;
                        addressList.add(address.getAddress());
                    }
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        for (final InetAddress address : addressList) {
            Runnable runnable2 = new Runnable(){

                @Override
                public void run() {
                    try {
                        String uuid = UUID.randomUUID().toString();
                        String probe = DeviceDiscovery.WS_DISCOVERY_PROBE_MESSAGE.replaceAll("<wsa:MessageID>urn:uuid:.*</wsa:MessageID>", "<wsa:MessageID>urn:uuid:" + uuid + "</wsa:MessageID>");
                        int port = random.nextInt(20000) + 40000;
                        final DatagramSocket server = new DatagramSocket(port, address);
                        new Thread(){

                            /*
                             * WARNING - Removed try catching itself - possible behaviour change.
                             */
                            @Override
                            public void run() {
                                try {
                                    DatagramPacket packet = new DatagramPacket(new byte[4096], 4096);
                                    server.setSoTimeout(4000);
                                    long timerStarted = System.currentTimeMillis();
                                    while (System.currentTimeMillis() - timerStarted < 4000L) {
                                        serverStarted.countDown();
                                        server.receive(packet);
                                        Collection collection = DeviceDiscovery.parseSoapResponseForUrls(Arrays.copyOf(packet.getData(), packet.getLength()));
                                        for (String key : collection) {
                                            addresses.add(key);
                                        }
                                    }
                                }
                                catch (SocketTimeoutException packet) {
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                finally {
                                    serverFinished.countDown();
                                    server.close();
                                }
                            }
                        }.start();
                        try {
                            serverStarted.await(1000L, TimeUnit.MILLISECONDS);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (address instanceof Inet4Address) {
                            server.send(new DatagramPacket(probe.getBytes(StandardCharsets.UTF_8), probe.length(), InetAddress.getByName(DeviceDiscovery.WS_DISCOVERY_ADDRESS_IPv4), 3702));
                        } else if (!(address instanceof Inet6Address)) assert (false);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        serverFinished.await(4000L, TimeUnit.MILLISECONDS);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService2.submit(runnable2);
        }
        try {
            executorService2.shutdown();
            executorService2.awaitTermination(6000L, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        return addresses;
    }

    private static Collection<Node> getNodeMatching(Node body2, String regexp) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        if (body2.getNodeName().matches(regexp)) {
            nodes.add(body2);
        }
        if (body2.getChildNodes().getLength() == 0) {
            return nodes;
        }
        NodeList returnList = body2.getChildNodes();
        for (int k = 0; k < returnList.getLength(); ++k) {
            Node node = returnList.item(k);
            nodes.addAll(DeviceDiscovery.getNodeMatching(node, regexp));
        }
        return nodes;
    }

    private static Collection<String> parseSoapResponseForUrls(byte[] data) throws SOAPException, IOException {
        ArrayList<String> urls2 = new ArrayList<String>();
        MessageFactory factory2 = MessageFactory.newInstance(WS_DISCOVERY_SOAP_VERSION);
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-type", WS_DISCOVERY_CONTENT_TYPE);
        SOAPMessage message = factory2.createMessage(headers, new ByteArrayInputStream(data));
        SOAPBody body2 = message.getSOAPBody();
        for (Node node : DeviceDiscovery.getNodeMatching(body2, ".*:XAddrs")) {
            if (node.getTextContent().length() <= 0) continue;
            urls2.addAll(Arrays.asList(node.getTextContent().split(" ")));
        }
        return urls2;
    }
}

