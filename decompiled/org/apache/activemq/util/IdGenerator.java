/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.activemq.util.InetAddressUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(IdGenerator.class);
    private static final String UNIQUE_STUB;
    private static int instanceCount;
    private static String hostName;
    private String seed;
    private final AtomicLong sequence = new AtomicLong(1L);
    private int length;
    public static final String PROPERTY_IDGENERATOR_HOSTNAME = "activemq.idgenerator.hostname";
    public static final String PROPERTY_IDGENERATOR_LOCALPORT = "activemq.idgenerator.localport";
    public static final String PROPERTY_IDGENERATOR_PORT = "activemq.idgenerator.port";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IdGenerator(String prefix) {
        String string = UNIQUE_STUB;
        synchronized (string) {
            this.seed = prefix + UNIQUE_STUB + instanceCount++ + ":";
            this.length = this.seed.length() + "9223372036854775807".length();
        }
    }

    public IdGenerator() {
        this("ID:" + hostName);
    }

    public static String getHostName() {
        return hostName;
    }

    public synchronized String generateId() {
        StringBuilder sb = new StringBuilder(this.length);
        sb.append(this.seed);
        sb.append(this.sequence.getAndIncrement());
        return sb.toString();
    }

    public static String sanitizeHostName(String hostName) {
        boolean changed = false;
        StringBuilder sb = new StringBuilder();
        for (char ch : hostName.toCharArray()) {
            if (ch < '\u007f') {
                sb.append(ch);
                continue;
            }
            changed = true;
        }
        if (changed) {
            String newHost = sb.toString();
            LOG.info("Sanitized hostname from: {} to: {}", (Object)hostName, (Object)newHost);
            return newHost;
        }
        return hostName;
    }

    public String generateSanitizedId() {
        String result = this.generateId();
        result = result.replace(':', '-');
        result = result.replace('_', '-');
        result = result.replace('.', '-');
        return result;
    }

    public static String getSeedFromId(String id) {
        int index;
        String result = id;
        if (id != null && (index = id.lastIndexOf(58)) > 0 && index + 1 < id.length()) {
            result = id.substring(0, index);
        }
        return result;
    }

    public static long getSequenceFromId(String id) {
        int index;
        long result = -1L;
        if (id != null && (index = id.lastIndexOf(58)) > 0 && index + 1 < id.length()) {
            String numStr = id.substring(index + 1, id.length());
            result = Long.parseLong(numStr);
        }
        return result;
    }

    public static int compare(String id1, String id2) {
        int result = -1;
        String seed1 = IdGenerator.getSeedFromId(id1);
        String seed2 = IdGenerator.getSeedFromId(id2);
        if (seed1 != null && seed2 != null && (result = seed1.compareTo(seed2)) == 0) {
            long count1 = IdGenerator.getSequenceFromId(id1);
            long count2 = IdGenerator.getSequenceFromId(id2);
            result = (int)(count1 - count2);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        String stub = "";
        boolean canAccessSystemProps = true;
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                sm.checkPropertiesAccess();
            }
        }
        catch (SecurityException se) {
            canAccessSystemProps = false;
        }
        if (canAccessSystemProps) {
            hostName = System.getProperty(PROPERTY_IDGENERATOR_HOSTNAME);
            int localPort = Integer.parseInt(System.getProperty(PROPERTY_IDGENERATOR_LOCALPORT, "0"));
            int idGeneratorPort = 0;
            ServerSocket ss = null;
            try {
                if (hostName == null) {
                    hostName = InetAddressUtil.getLocalHostName();
                }
                if (localPort == 0) {
                    idGeneratorPort = Integer.parseInt(System.getProperty(PROPERTY_IDGENERATOR_PORT, "0"));
                    LOG.trace("Using port {}", (Object)idGeneratorPort);
                    ss = new ServerSocket(idGeneratorPort);
                    localPort = ss.getLocalPort();
                    stub = "-" + localPort + "-" + System.currentTimeMillis() + "-";
                    Thread.sleep(100L);
                } else {
                    stub = "-" + localPort + "-" + System.currentTimeMillis() + "-";
                }
            }
            catch (Exception e) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("could not generate unique stub by using DNS and binding to local port", e);
                } else {
                    LOG.warn("could not generate unique stub by using DNS and binding to local port: {} {}", (Object)e.getClass().getCanonicalName(), (Object)e.getMessage());
                }
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
            finally {
                if (ss != null) {
                    try {
                        ss.close();
                    }
                    catch (IOException ioe) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("Closing the server socket failed", ioe);
                        }
                        LOG.warn("Closing the server socket failed due " + ioe.getMessage());
                    }
                }
            }
        }
        if (hostName == null) {
            hostName = "localhost";
        }
        hostName = IdGenerator.sanitizeHostName(hostName);
        if (stub.length() == 0) {
            stub = "-1-" + System.currentTimeMillis() + "-";
        }
        UNIQUE_STUB = stub;
    }
}

