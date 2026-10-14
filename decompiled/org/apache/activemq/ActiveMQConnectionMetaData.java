/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.jms.ConnectionMetaData;

public final class ActiveMQConnectionMetaData
implements ConnectionMetaData {
    public static final String PROVIDER_VERSION;
    public static final int PROVIDER_MAJOR_VERSION;
    public static final int PROVIDER_MINOR_VERSION;
    public static final String PROVIDER_NAME = "ActiveMQ";
    public static final String DEFAULT_PLATFORM_DETAILS = "Java";
    public static final String PLATFORM_DETAILS;
    public static final ActiveMQConnectionMetaData INSTANCE;

    private ActiveMQConnectionMetaData() {
    }

    @Override
    public String getJMSVersion() {
        return "1.1";
    }

    @Override
    public int getJMSMajorVersion() {
        return 1;
    }

    @Override
    public int getJMSMinorVersion() {
        return 1;
    }

    @Override
    public String getJMSProviderName() {
        return PROVIDER_NAME;
    }

    @Override
    public String getProviderVersion() {
        return PROVIDER_VERSION;
    }

    @Override
    public int getProviderMajorVersion() {
        return PROVIDER_MAJOR_VERSION;
    }

    @Override
    public int getProviderMinorVersion() {
        return PROVIDER_MINOR_VERSION;
    }

    @Override
    public Enumeration<String> getJMSXPropertyNames() {
        Vector<String> jmxProperties = new Vector<String>();
        jmxProperties.add("JMSXUserID");
        jmxProperties.add("JMSXGroupID");
        jmxProperties.add("JMSXGroupSeq");
        jmxProperties.add("JMSXDeliveryCount");
        jmxProperties.add("JMSXProducerTXID");
        return jmxProperties.elements();
    }

    private static String getPlatformDetails() {
        String details = "java";
        try {
            StringBuilder platformInfo = new StringBuilder(128);
            platformInfo.append("JVM: ");
            platformInfo.append(System.getProperty("java.version"));
            platformInfo.append(", ");
            platformInfo.append(System.getProperty("java.vm.version"));
            platformInfo.append(", ");
            platformInfo.append(System.getProperty("java.vendor"));
            platformInfo.append(", OS: ");
            platformInfo.append(System.getProperty("os.name"));
            platformInfo.append(", ");
            platformInfo.append(System.getProperty("os.version"));
            platformInfo.append(", ");
            platformInfo.append(System.getProperty("os.arch"));
            details = platformInfo.toString();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return details;
    }

    static {
        INSTANCE = new ActiveMQConnectionMetaData();
        String version = null;
        int major = 0;
        int minor = 0;
        try {
            Pattern pattern;
            Matcher m4;
            Package p = Package.getPackage("org.apache.activemq");
            if (p != null && (version = p.getImplementationVersion()) != null && (m4 = (pattern = Pattern.compile("(\\d+)\\.(\\d+).*")).matcher(version)).matches()) {
                major = Integer.parseInt(m4.group(1));
                minor = Integer.parseInt(m4.group(2));
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        PROVIDER_VERSION = version;
        PROVIDER_MAJOR_VERSION = major;
        PROVIDER_MINOR_VERSION = minor;
        PLATFORM_DETAILS = ActiveMQConnectionMetaData.getPlatformDetails();
    }
}

