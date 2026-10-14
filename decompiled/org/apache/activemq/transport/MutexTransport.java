/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;

public class MutexTransport
extends TransportFilter {
    private final ReentrantLock writeLock = new ReentrantLock();
    private boolean syncOnCommand;

    public MutexTransport(Transport next) {
        super(next);
        this.syncOnCommand = false;
    }

    public MutexTransport(Transport next, boolean syncOnCommand) {
        super(next);
        this.syncOnCommand = syncOnCommand;
    }

    @Override
    public void onCommand(Object command) {
        if (this.syncOnCommand) {
            this.writeLock.lock();
            try {
                this.transportListener.onCommand(command);
            }
            finally {
                this.writeLock.unlock();
            }
        } else {
            this.transportListener.onCommand(command);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FutureResponse asyncRequest(Object command, ResponseCallback responseCallback) throws IOException {
        this.writeLock.lock();
        try {
            FutureResponse futureResponse = this.next.asyncRequest(command, null);
            return futureResponse;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public void oneway(Object command) throws IOException {
        this.writeLock.lock();
        try {
            this.next.oneway(command);
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public Object request(Object command) throws IOException {
        this.writeLock.lock();
        try {
            Object object = this.next.request(command);
            return object;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object request(Object command, int timeout2) throws IOException {
        this.writeLock.lock();
        try {
            Object object = this.next.request(command, timeout2);
            return object;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public String toString() {
        return this.next.toString();
    }

    public boolean isSyncOnCommand() {
        return this.syncOnCommand;
    }

    public void setSyncOnCommand(boolean syncOnCommand) {
        this.syncOnCommand = syncOnCommand;
    }
}

