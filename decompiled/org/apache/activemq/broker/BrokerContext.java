/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.Map;

public interface BrokerContext {
    public Object getBean(String var1);

    public Map getBeansOfType(Class var1);

    public String getConfigurationUrl();
}

