/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.security;

import java.util.Set;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationMap;
import org.apache.activemq.security.AuthorizationMap;
import org.apache.activemq.security.TempDestinationAuthorizationEntry;

public class SimpleAuthorizationMap
implements AuthorizationMap {
    private DestinationMap writeACLs;
    private DestinationMap readACLs;
    private DestinationMap adminACLs;
    private TempDestinationAuthorizationEntry tempDestinationAuthorizationEntry;

    public SimpleAuthorizationMap() {
    }

    public SimpleAuthorizationMap(DestinationMap writeACLs, DestinationMap readACLs, DestinationMap adminACLs) {
        this.writeACLs = writeACLs;
        this.readACLs = readACLs;
        this.adminACLs = adminACLs;
    }

    public void setTempDestinationAuthorizationEntry(TempDestinationAuthorizationEntry tempDestinationAuthorizationEntry) {
        this.tempDestinationAuthorizationEntry = tempDestinationAuthorizationEntry;
    }

    public TempDestinationAuthorizationEntry getTempDestinationAuthorizationEntry() {
        return this.tempDestinationAuthorizationEntry;
    }

    public Set<Object> getTempDestinationAdminACLs() {
        if (this.tempDestinationAuthorizationEntry != null) {
            return this.tempDestinationAuthorizationEntry.getAdminACLs();
        }
        return null;
    }

    public Set<Object> getTempDestinationReadACLs() {
        if (this.tempDestinationAuthorizationEntry != null) {
            return this.tempDestinationAuthorizationEntry.getReadACLs();
        }
        return null;
    }

    public Set<Object> getTempDestinationWriteACLs() {
        if (this.tempDestinationAuthorizationEntry != null) {
            return this.tempDestinationAuthorizationEntry.getWriteACLs();
        }
        return null;
    }

    public Set<Object> getAdminACLs(ActiveMQDestination destination) {
        return this.adminACLs.get(destination);
    }

    public Set<Object> getReadACLs(ActiveMQDestination destination) {
        return this.readACLs.get(destination);
    }

    public Set<Object> getWriteACLs(ActiveMQDestination destination) {
        return this.writeACLs.get(destination);
    }

    public DestinationMap getAdminACLs() {
        return this.adminACLs;
    }

    public void setAdminACLs(DestinationMap adminACLs) {
        this.adminACLs = adminACLs;
    }

    public DestinationMap getReadACLs() {
        return this.readACLs;
    }

    public void setReadACLs(DestinationMap readACLs) {
        this.readACLs = readACLs;
    }

    public DestinationMap getWriteACLs() {
        return this.writeACLs;
    }

    public void setWriteACLs(DestinationMap writeACLs) {
        this.writeACLs = writeACLs;
    }
}

