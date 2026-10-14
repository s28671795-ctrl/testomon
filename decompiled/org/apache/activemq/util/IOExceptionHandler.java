/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import org.apache.activemq.broker.BrokerServiceAware;

public interface IOExceptionHandler
extends BrokerServiceAware {
    public void handle(IOException var1);
}

