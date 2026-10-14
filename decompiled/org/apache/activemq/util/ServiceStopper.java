/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.util.List;
import org.apache.activemq.Service;
import org.apache.activemq.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceStopper {
    private Throwable firstException;

    public void stop(Service service2) {
        try {
            if (service2 != null) {
                service2.stop();
            }
        }
        catch (Exception e) {
            this.onException(service2, e);
        }
    }

    public void run(Callback stopClosure) {
        try {
            stopClosure.execute();
        }
        catch (Throwable e) {
            this.onException(stopClosure, e);
        }
    }

    public void stopServices(List services) {
        for (Service service2 : services) {
            this.stop(service2);
        }
    }

    public void onException(Object owner, Throwable e) {
        this.logError(owner, e);
        if (this.firstException == null) {
            this.firstException = e;
        }
    }

    public void throwFirstException() throws Exception {
        if (this.firstException != null) {
            if (this.firstException instanceof Exception) {
                Exception e = (Exception)this.firstException;
                throw e;
            }
            throw new RuntimeException("Unknown type of exception: " + this.firstException, this.firstException);
        }
    }

    protected void logError(Object service2, Throwable e) {
        Logger log = LoggerFactory.getLogger(service2.getClass());
        log.error("Could not stop service: " + service2 + ". Reason: " + e, e);
    }
}

