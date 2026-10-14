/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.util.Trace;
import org.apache.activemq.util.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTracker {
    static final Logger LOG = LoggerFactory.getLogger(ThreadTracker.class);
    static HashMap<String, Tracker> trackers = new HashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void track(String name) {
        Tracker t2;
        String key = name.intern();
        HashMap<String, Tracker> hashMap = trackers;
        synchronized (hashMap) {
            t2 = trackers.get(key);
            if (t2 == null) {
                t2 = new Tracker();
                trackers.put(key, t2);
            }
        }
        t2.track();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void result() {
        HashMap<String, Tracker> hashMap = trackers;
        synchronized (hashMap) {
            for (Map.Entry<String, Tracker> t2 : trackers.entrySet()) {
                LOG.info("Tracker: " + t2.getKey() + ", " + t2.getValue().size() + " entry points...");
                for (Trace trace : t2.getValue().values()) {
                    LOG.info("count: " + trace.count, trace);
                }
                LOG.info("Tracker: " + t2.getKey() + ", done.");
            }
        }
    }
}

