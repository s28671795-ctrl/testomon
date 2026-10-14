/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import java.net.InetAddress;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.http.pathmap.ServletPathSpec;
import org.eclipse.jetty.util.InetAddressPattern;
import org.eclipse.jetty.util.StringUtil;

public class InetAccessSet
extends AbstractSet<PatternTuple>
implements Set<PatternTuple>,
Predicate<AccessTuple> {
    private final ArrayList<PatternTuple> tuples = new ArrayList();

    @Override
    public boolean add(PatternTuple storageTuple) {
        return this.tuples.add(storageTuple);
    }

    @Override
    public boolean remove(Object o) {
        return this.tuples.remove(o);
    }

    @Override
    public Iterator<PatternTuple> iterator() {
        return this.tuples.iterator();
    }

    @Override
    public int size() {
        return this.tuples.size();
    }

    @Override
    public boolean test(AccessTuple entry) {
        if (entry == null) {
            return false;
        }
        for (PatternTuple tuple : this.tuples) {
            if (!tuple.test(entry)) continue;
            return true;
        }
        return false;
    }

    public static class PatternTuple
    implements Predicate<AccessTuple> {
        private final String connector;
        private final InetAddressPattern address;
        private final PathSpec pathSpec;

        public static PatternTuple from(String pattern) {
            int addrEnd;
            String path = null;
            int pathIndex = pattern.indexOf(124);
            if (pathIndex >= 0) {
                path = pattern.substring(pathIndex + 1);
            }
            String connector = null;
            int connectorIndex = pattern.indexOf(64);
            if (connectorIndex >= 0) {
                connector = pattern.substring(0, connectorIndex);
            }
            String addr = null;
            int addrStart = connectorIndex < 0 ? 0 : connectorIndex + 1;
            int n = addrEnd = pathIndex < 0 ? pattern.length() : pathIndex;
            if (addrStart != addrEnd) {
                addr = pattern.substring(addrStart, addrEnd);
            }
            return new PatternTuple(connector, InetAddressPattern.from(addr), StringUtil.isEmpty(path) ? null : new ServletPathSpec(path));
        }

        public PatternTuple(String connector, InetAddressPattern address, PathSpec pathSpec) {
            this.connector = connector;
            this.address = address;
            this.pathSpec = pathSpec;
        }

        @Override
        public boolean test(AccessTuple entry) {
            if (this.connector != null && !this.connector.equals(entry.getConnector())) {
                return false;
            }
            if (this.pathSpec != null && !this.pathSpec.matches(entry.getPath())) {
                return false;
            }
            return this.address == null || this.address.test(entry.getAddress());
        }

        public String toString() {
            return String.format("%s@%x{connector=%s, addressPattern=%s, pathSpec=%s}", this.getClass().getSimpleName(), this.hashCode(), this.connector, this.address, this.pathSpec);
        }
    }

    public static class AccessTuple {
        private final String connector;
        private final InetAddress address;
        private final String path;

        public AccessTuple(String connector, InetAddress address, String path) {
            this.connector = connector;
            this.address = address;
            this.path = path;
        }

        public String getConnector() {
            return this.connector;
        }

        public InetAddress getAddress() {
            return this.address;
        }

        public String getPath() {
            return this.path;
        }
    }
}

