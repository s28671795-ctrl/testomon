/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.ServiceReference
 *  org.osgi.service.log.LogService
 *  org.osgi.util.tracker.BundleTracker
 *  org.osgi.util.tracker.BundleTrackerCustomizer
 *  org.osgi.util.tracker.ServiceTracker
 *  org.osgi.util.tracker.ServiceTrackerCustomizer
 */
package org.apache.geronimo.mail;

import java.util.ArrayList;
import java.util.List;
import org.apache.geronimo.mail.MailProviderBundleTrackerCustomizer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator
extends org.apache.geronimo.osgi.locator.Activator {
    protected BundleTracker bt;
    protected ServiceTracker lst;
    protected List<LogService> logServices = new ArrayList<LogService>();

    public synchronized void start(BundleContext context) throws Exception {
        super.start(context);
        this.lst = new LogServiceTracker(context, LogService.class.getName(), null);
        this.lst.open();
        this.bt = new BundleTracker(context, 32, (BundleTrackerCustomizer)new MailProviderBundleTrackerCustomizer(this, context.getBundle()));
        this.bt.open();
    }

    public synchronized void stop(BundleContext context) throws Exception {
        this.bt.close();
        this.lst.close();
        super.stop(context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void log(int level, String message) {
        List<LogService> list = this.logServices;
        synchronized (list) {
            for (LogService log : this.logServices) {
                log.log(level, message);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void log(int level, String message, Throwable th) {
        List<LogService> list = this.logServices;
        synchronized (list) {
            for (LogService log : this.logServices) {
                log.log(level, message, th);
            }
        }
    }

    private final class LogServiceTracker
    extends ServiceTracker {
        private LogServiceTracker(BundleContext context, String clazz, ServiceTrackerCustomizer customizer) {
            super(context, clazz, customizer);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public Object addingService(ServiceReference reference) {
            Object svc = super.addingService(reference);
            if (svc instanceof LogService) {
                List<LogService> list = Activator.this.logServices;
                synchronized (list) {
                    Activator.this.logServices.add((LogService)svc);
                }
            }
            return svc;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void removedService(ServiceReference reference, Object service2) {
            List<LogService> list = Activator.this.logServices;
            synchronized (list) {
                Activator.this.logServices.remove(service2);
            }
            super.removedService(reference, service2);
        }
    }
}

