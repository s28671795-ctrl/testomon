/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.tcp;

import org.apache.activemq.command.Response;

public class ResponseHolder {
    protected Response response;
    protected Object lock = new Object();
    protected boolean notified;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setResponse(Response r) {
        Object object = this.lock;
        synchronized (object) {
            this.response = r;
            this.notified = true;
            this.lock.notify();
        }
    }

    public Response getResponse() {
        return this.getResponse(0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Response getResponse(int timeout2) {
        Object object = this.lock;
        synchronized (object) {
            if (!this.notified) {
                try {
                    this.lock.wait(timeout2);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return this.response;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void close() {
        Object object = this.lock;
        synchronized (object) {
            this.notified = true;
            this.lock.notifyAll();
        }
    }
}

