/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import org.apache.activemq.Service;

public interface ServiceListener {
    public void started(Service var1);

    public void stopped(Service var1);
}

