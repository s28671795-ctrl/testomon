/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.server;

import com.hierynomus.smbj.server.Server;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ServerList {
    private ReentrantLock lock = new ReentrantLock();
    private Map<String, Server> lookup = new HashMap<String, Server>();

    public Server lookup(String name) {
        this.lock.lock();
        try {
            Server server = this.lookup.get(name);
            return server;
        }
        finally {
            this.lock.unlock();
        }
    }

    public void registerServer(Server server) {
        this.lock.lock();
        try {
            this.lookup.put(server.getServerName(), server);
        }
        finally {
            this.lock.unlock();
        }
    }

    public void unregister(String name) {
        this.lock.lock();
        try {
            this.lookup.remove(name);
        }
        finally {
            this.lock.unlock();
        }
    }
}

