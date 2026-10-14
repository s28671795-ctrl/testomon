/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.service.cm.ConfigurationException
 *  org.osgi.service.cm.ManagedServiceFactory
 */
package org.apache.cxf.transport.http.osgi;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.HTTPConduitConfigurer;
import org.apache.cxf.transport.http.osgi.HttpConduitConfigApplier;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;

class ConfigAdminHttpConduitConfigurer
implements ManagedServiceFactory,
HTTPConduitConfigurer {
    public static final String FACTORY_PID = "org.apache.cxf.http.conduits";
    Map<String, PidInfo> props = new ConcurrentHashMap<String, PidInfo>(4, 0.75f, 2);
    CopyOnWriteArrayList<PidInfo> sorted = new CopyOnWriteArrayList();

    ConfigAdminHttpConduitConfigurer() {
    }

    public String getName() {
        return FACTORY_PID;
    }

    public void updated(String pid, Dictionary properties) throws ConfigurationException {
        if (pid == null) {
            return;
        }
        this.deleted(pid);
        String url2 = (String)properties.get("url");
        String name = (String)properties.get("name");
        Matcher matcher = url2 == null ? null : Pattern.compile(url2).matcher("");
        String p = (String)properties.get("order");
        int order = 50;
        if (p != null) {
            order = Integer.parseInt(p);
        }
        PidInfo info = new PidInfo(properties, matcher, order);
        this.props.put(pid, info);
        if (url2 != null) {
            this.props.put(url2, info);
        }
        if (name != null) {
            this.props.put(name, info);
        }
        this.addToSortedInfos(info);
    }

    private synchronized void addToSortedInfos(PidInfo pi) {
        int size = this.sorted.size();
        for (int x = 0; x < size; ++x) {
            PidInfo p = this.sorted.get(x);
            if (pi.compareTo(p) >= 0) continue;
            this.sorted.add(x, pi);
            return;
        }
        this.sorted.add(pi);
    }

    private synchronized void removeFromSortedInfos(PidInfo pi) {
        this.sorted.remove(pi);
    }

    public void deleted(String pid) {
        PidInfo info = this.props.remove(pid);
        if (info == null) {
            return;
        }
        this.removeFromSortedInfos(info);
        Dictionary<String, String> d = info.getProps();
        if (d != null) {
            String url2 = d.get("url");
            String name = d.get("name");
            if (url2 != null) {
                this.props.remove(url2);
            }
            if (name != null) {
                this.props.remove(name);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void configure(String name, String address, HTTPConduit c) {
        PidInfo byName = null;
        PidInfo byAddress = null;
        if (name != null) {
            byName = this.props.get(name);
        }
        if (address != null && (byAddress = this.props.get(address)) == byName) {
            byAddress = null;
        }
        HttpConduitConfigApplier applier = new HttpConduitConfigApplier();
        for (PidInfo info : this.sorted) {
            Matcher m4;
            if (info.getMatcher() == null || info == byName || info == byAddress) continue;
            Matcher matcher = m4 = info.getMatcher();
            synchronized (matcher) {
                m4.reset(address);
                if (m4.matches()) {
                    applier.apply(info.getProps(), c, address);
                }
            }
        }
        if (byAddress != null) {
            applier.apply(byAddress.getProps(), c, address);
        }
        if (byName != null) {
            applier.apply(byName.getProps(), c, address);
        }
    }

    private static class PidInfo
    implements Comparable<PidInfo> {
        final Dictionary<String, String> props;
        final Matcher matcher;
        final int order;

        PidInfo(Dictionary<String, String> p, Matcher m4, int o) {
            this.matcher = m4;
            this.props = p;
            this.order = o;
        }

        public Dictionary<String, String> getProps() {
            return this.props;
        }

        public Matcher getMatcher() {
            return this.matcher;
        }

        @Override
        public int compareTo(PidInfo o) {
            if (this.order < o.order) {
                return -1;
            }
            if (this.order > o.order) {
                return 1;
            }
            if (this.matcher != null) {
                if (o.matcher == null) {
                    return -1;
                }
                return this.matcher.pattern().toString().compareTo(o.matcher.pattern().toString());
            }
            return 0;
        }
    }
}

