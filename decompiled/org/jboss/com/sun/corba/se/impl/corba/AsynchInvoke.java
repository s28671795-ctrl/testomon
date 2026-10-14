/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import org.jboss.com.sun.corba.se.impl.corba.RequestImpl;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public class AsynchInvoke
implements Runnable {
    private RequestImpl _req;
    private ORB _orb;
    private boolean _notifyORB;

    public AsynchInvoke(ORB o, RequestImpl reqToInvokeOn, boolean n) {
        this._orb = o;
        this._req = reqToInvokeOn;
        this._notifyORB = n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        this._req.doInvocation();
        RequestImpl requestImpl = this._req;
        synchronized (requestImpl) {
            this._req.gotResponse = true;
            this._req.notify();
        }
        if (this._notifyORB) {
            this._orb.notifyORB();
        }
    }
}

