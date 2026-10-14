/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection;

import com.hierynomus.smbj.session.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SessionTable {
    private ReentrantLock lock = new ReentrantLock();
    private Map<Long, Session> lookup = new HashMap<Long, Session>();

    void registerSession(Long id, Session session) {
        this.lock.lock();
        try {
            this.lookup.put(id, session);
        }
        finally {
            this.lock.unlock();
        }
    }

    public Session find(Long id) {
        this.lock.lock();
        try {
            Session session = this.lookup.get(id);
            return session;
        }
        finally {
            this.lock.unlock();
        }
    }

    public Session removeSession(Long id) {
        this.lock.lock();
        try {
            Session session = this.lookup.remove(id);
            return session;
        }
        finally {
            this.lock.unlock();
        }
    }

    boolean isActive(Long id) {
        this.lock.lock();
        try {
            boolean bl = this.lookup.containsKey(id);
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }

    public Collection<Session> activeSessions() {
        this.lock.lock();
        try {
            ArrayList<Session> arrayList = new ArrayList<Session>(this.lookup.values());
            return arrayList;
        }
        finally {
            this.lock.unlock();
        }
    }
}

