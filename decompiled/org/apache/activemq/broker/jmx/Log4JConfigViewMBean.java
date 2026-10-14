/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.List;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface Log4JConfigViewMBean {
    @MBeanInfo(value="Returns the current logging level of the root logger.")
    public String getRootLogLevel() throws Exception;

    @MBeanInfo(value="Sets the current logging level of the root logger.")
    public void setRootLogLevel(String var1) throws Exception;

    @MBeanInfo(value="List of all loggers that are available for configuration.")
    public List<String> getLoggers() throws Exception;

    @MBeanInfo(value="Returns the current logging level of a named logger.")
    public String getLogLevel(String var1) throws Exception;

    @MBeanInfo(value="Sets the logging level for the named logger.")
    public void setLogLevel(String var1, String var2) throws Exception;

    @MBeanInfo(value="Reloads log4j.properties from the classpath.")
    public void reloadLog4jProperties() throws Throwable;
}

