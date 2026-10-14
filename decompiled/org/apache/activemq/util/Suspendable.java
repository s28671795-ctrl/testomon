/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

public interface Suspendable {
    public void suspend() throws Exception;

    public void resume() throws Exception;
}

