/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletException
 */
package org.apache.cxf.transport.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.transport.AbstractDestination;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.servlet.ServletConfigAware;

public class DestinationRegistryImpl
implements DestinationRegistry,
ServletConfigAware {
    private static final String SLASH = "/";
    private ConcurrentMap<String, AbstractHTTPDestination> destinations = new ConcurrentHashMap<String, AbstractHTTPDestination>();
    private Map<String, AbstractHTTPDestination> decodedDestinations = new ConcurrentHashMap<String, AbstractHTTPDestination>();

    @Override
    public synchronized void addDestination(AbstractHTTPDestination destination) {
        String path = this.getTrimmedPath(destination.getEndpointInfo().getAddress());
        AbstractHTTPDestination dest = this.destinations.putIfAbsent(path, destination);
        if (dest != null && dest != destination) {
            throw new RuntimeException("Already a destination on " + path);
        }
        try {
            String path2 = URLDecoder.decode(path, "UTF-8");
            if (!path.equals(path2)) {
                this.decodedDestinations.put(path2, destination);
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported Encoding", e);
        }
    }

    @Override
    public synchronized void removeDestination(String path) {
        this.destinations.remove(path);
        try {
            String path2 = URLDecoder.decode(path, "UTF-8");
            if (!path.equals(path2)) {
                this.decodedDestinations.remove(path2);
            }
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported Encoding", e);
        }
    }

    @Override
    public AbstractHTTPDestination getDestinationForPath(String path) {
        return this.getDestinationForPath(path, false);
    }

    @Override
    public AbstractHTTPDestination getDestinationForPath(String path, boolean tryDecoding) {
        String m4 = this.getTrimmedPath(path);
        AbstractHTTPDestination s2 = (AbstractHTTPDestination)this.destinations.get(m4);
        if (s2 == null) {
            s2 = this.decodedDestinations.get(m4);
        }
        return s2;
    }

    @Override
    public AbstractHTTPDestination checkRestfulRequest(String address) {
        AbstractHTTPDestination ret = this.getRestfulDestination(this.getDestinationsPaths(), address);
        if (ret == null) {
            ret = this.getRestfulDestination(this.decodedDestinations.keySet(), address);
        }
        if (ret != null && ret.getMessageObserver() == null) {
            return null;
        }
        return ret;
    }

    private AbstractHTTPDestination getRestfulDestination(Set<String> destPaths, String address) {
        int len = -1;
        AbstractHTTPDestination ret = null;
        for (String path : destPaths) {
            String thePath;
            String string = thePath = path.length() > 1 && path.endsWith(SLASH) ? path.substring(0, path.length() - 1) : path;
            if (!address.equals(thePath) && !SLASH.equals(thePath) && (address.length() <= thePath.length() || !address.startsWith(thePath) || address.charAt(thePath.length()) != '/') || thePath.length() <= len) continue;
            ret = this.getDestinationForPath(path);
            len = path.length();
        }
        return ret;
    }

    @Override
    public Collection<AbstractHTTPDestination> getDestinations() {
        return Collections.unmodifiableCollection(this.destinations.values());
    }

    @Override
    public AbstractDestination[] getSortedDestinations() {
        LinkedList<AbstractHTTPDestination> dest2 = new LinkedList<AbstractHTTPDestination>(this.getDestinations());
        Collections.sort(dest2, new Comparator<AbstractHTTPDestination>(){

            @Override
            public int compare(AbstractHTTPDestination o1, AbstractHTTPDestination o2) {
                InterfaceInfo i1 = o1.getEndpointInfo().getInterface();
                InterfaceInfo i2 = o2.getEndpointInfo().getInterface();
                if (i1 == null && i2 == null) {
                    return 0;
                }
                if (i1 == null) {
                    return -1;
                }
                if (i2 == null) {
                    return 1;
                }
                return i1.getName().getLocalPart().compareTo(i2.getName().getLocalPart());
            }
        });
        return dest2.toArray(new AbstractDestination[0]);
    }

    @Override
    public Set<String> getDestinationsPaths() {
        return Collections.unmodifiableSet(this.destinations.keySet());
    }

    public String getTrimmedPath(String path) {
        if (path == null) {
            return SLASH;
        }
        String lh = "http://localhost/";
        String lhs = "https://localhost/";
        if (path.startsWith("http://localhost/")) {
            path = path.substring("http://localhost/".length());
        } else if (path.startsWith("https://localhost/")) {
            path = path.substring("https://localhost/".length());
        }
        if (!path.contains("://") && !path.startsWith(SLASH)) {
            path = SLASH + path;
        }
        return path;
    }

    @Override
    public void onServletConfigAvailable(ServletConfig config) throws ServletException {
        for (AbstractHTTPDestination destination : this.getDestinations()) {
            if (!(destination instanceof ServletConfigAware)) continue;
            ((ServletConfigAware)((Object)destination)).onServletConfigAvailable(config);
        }
    }
}

